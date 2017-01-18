package gov.va.vinci.ef.ae;

/*
 * #%L
 * Echo concept exctractor
 * %%
 * Copyright (C) 2010 - 2016 Department of Veterans Affairs
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import gov.va.vinci.ef.types.NumericValue;
import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.descriptors.LeoConfigurationParameter;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.tools.LeoUtils;
import gov.va.vinci.leo.window.WindowService;
import gov.va.vinci.leo.window.types.Window;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilizes sets of regular expressions to filter out invalid numeric values.
 * <p>
 * Created by thomasginter on 9/16/15.
 */
public class NumericMeasureFilter extends LeoBaseAnnotator {


  /**
   * Path to the regex file to parse.
   */
  @LeoConfigurationParameter

  protected String regexFilePath = null;

  /**
   * Patterns for which if there is a match in the window before the anchor then the relationship is invalid.
   */
  protected Pattern[] prePatterns = null;

  /**
   * Patterns for which if there is a match in the window after the anchor then the relationship is invalid.
   */
  protected Pattern[] postPatterns = null;

  /**
   * Array of patterns which if matched in the window indicate an invalid relationship.
   */
  protected Pattern[] windowPatterns = null;

  /**
   * Patterns matched on the covered text of the anchor itself.
   */
  protected Pattern[] anchorPatterns = null;

  /**
   * Window service class.
   */
  protected WindowService windowService = new WindowService(2, 2, Window.class.getCanonicalName());

  /**
   * Pattern flags for each regex.
   */
  public static int PATTERN_FLAGS = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

  /**
   * Log messages
   */
  private static final Logger log = Logger.getLogger(LeoUtils.getRuntimeClass());

  @Override
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);

    if (StringUtils.isBlank(regexFilePath))
      throw new ResourceInitializationException("regexFilePath cannot be blank or null", null);

    try {
      parseRegexFile(new File(regexFilePath));
    } catch (IOException e) {
      throw new ResourceInitializationException(e);
    }
  }

  @Override
  public void annotate(JCas aJCas) throws AnalysisEngineProcessException {
    //Get the document ID
    String documentID = null;
    Collection<gov.va.vinci.leo.types.CSI> infoList = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, gov.va.vinci.leo.types.CSI.type, false);
    if (infoList.size() > 0) {
      documentID = infoList.iterator().next().getID();
    } else {
      documentID = "Num:" + this.numberOfCASesProcessed;
    }
    Collection<NumericValue> values = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, NumericValue.type, false);
    for (NumericValue value : values) {
      try {
        Window window = (Window) windowService.buildNonWhitespaceTokenWindow(value);
        if (hasAnchorPatternMatch(value) ||
            hasWindowPatternMatch(window) ||
            hasPrePatternMatch(value, window) ||
            hasPostPatternMatch(value, window)) {
          value.removeFromIndexes();
        }
      } catch (Exception e) {
        log.error("Error processing " + documentID);
        throw new AnalysisEngineProcessException(e);
      }
    }
  }

  /**
   * Return true if one of the pre anchor patterns match.
   *
   * @param a      Anchor annotation
   * @param window window annotation
   * @return true if there is a match, false otherwise
   */
  public boolean hasPrePatternMatch(Annotation a, Annotation window) {
    String coveredText = window.getCoveredText().substring(0, (a.getBegin() - window.getBegin()));
    return hasMatch(prePatterns, coveredText);
  }

  /**
   * Returns true if one of the post anchor patterns match.
   *
   * @param a      Anchor annotation
   * @param window window annotation
   * @return true if there is a match, false otherwise
   */
  public boolean hasPostPatternMatch(Annotation a, Annotation window) {
    String coveredText = window.getCoveredText().substring(a.getEnd() - window.getBegin());
    return hasMatch(postPatterns, coveredText);
  }

  /**
   * Returns true if one of the window patterns match.
   *
   * @param window window annotation
   * @return true if there is a match, false otherwise
   */
  public boolean hasWindowPatternMatch(Annotation window) {
    return hasMatch(windowPatterns, window.getCoveredText());
  }

  /**
   * Return true if one of the anchor patterns match.
   *
   * @param anchor anchor annotation
   * @return true if there is a match, false otherwise
   */
  public boolean hasAnchorPatternMatch(Annotation anchor) {
    return hasMatch(anchorPatterns, anchor.getCoveredText());
  }

  /**
   * Returns true if the text provided has a match in one of the patterns.
   *
   * @param patterns
   * @param text
   * @return
   */
  protected boolean hasMatch(Pattern[] patterns, String text) {
    boolean hasMatch = false;
    for (Pattern p : patterns) {
      if (p.matcher(text).find()) {
        hasMatch = true;
        log.debug("Removing Value:\n\tpattern: " + p.pattern() + "\n\ttext: " + text);
        break;
      }
    }
    return hasMatch;
  }

  /**
   * Get the patterns from the regex file and stash them in the appropriate lists.
   *
   * @param regexFile File from which to retrieve the patterns
   * @throws IOException If there is an error reading the file.
   */
  protected void parseRegexFile(File regexFile) throws IOException {
    if (regexFile == null)
      throw new IllegalArgumentException("regexFile cannot be null");
    //List of Patterns to compile
    ArrayList<Pattern> preList = new ArrayList<Pattern>();
    ArrayList<Pattern> postList = new ArrayList<Pattern>();
    ArrayList<Pattern> winList = new ArrayList<Pattern>();
    ArrayList<Pattern> anchorList = new ArrayList<Pattern>();
    int patternType = 3;
    //Read in the lines of the regex file
    List<String> lines = IOUtils.readLines(FileUtils.openInputStream(regexFile));
    for (String line : lines) {
      if (line.startsWith("#")) {
        if (line.startsWith("#PRE"))
          patternType = 1;
        else if (line.startsWith("#POST"))
          patternType = 2;
        else if (line.startsWith("#WIN"))
          patternType = 3;
        else if (line.startsWith("#ANCHOR"))
          patternType = 4;
      } else if (StringUtils.isNotBlank(line)) {
        if (patternType == 1)
          preList.add(Pattern.compile(line, PATTERN_FLAGS));
        else if (patternType == 2)
          postList.add(Pattern.compile(line, PATTERN_FLAGS));
        else if (patternType == 3)
          winList.add(Pattern.compile(line, PATTERN_FLAGS));
        else if (patternType == 4)
          anchorList.add(Pattern.compile(line, PATTERN_FLAGS));
      }
    }
    //Stash each collection
    prePatterns = preList.toArray(new Pattern[preList.size()]);
    postPatterns = postList.toArray(new Pattern[postList.size()]);
    windowPatterns = winList.toArray(new Pattern[winList.size()]);
    anchorPatterns = anchorList.toArray(new Pattern[anchorList.size()]);
  }

  @Override
  public LeoTypeSystemDescription getLeoTypeSystemDescription() {
    return new LeoTypeSystemDescription();
  }

  public String getRegexFilePath() {
    return regexFilePath;
  }

  public NumericMeasureFilter setRegexFilePath(String regexFilePath) {
    this.regexFilePath = regexFilePath;
    return this;
  }

}
