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

import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.descriptors.LeoConfigurationParameter;
import org.apache.commons.validator.GenericValidator;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds the numeric values covered by the input annotation types and then passes them to an abstract method for
 * validation.
 * <p>
 * Created by thomasginter on 9/1/15.
 * updated by olga patterson on 6/14/2016
 */
public abstract class NumericValidatorAnnotator extends LeoBaseAnnotator {


  /**
   * Pattern for finding numbers in the covered text.
   */
  protected Pattern numberPattern = Pattern.compile("\\d+(\\.\\d+)?");

  /**
   * Remove the annotation if any of the values are invalid, the default.  If false then remove only if all the values
   * are invalid.
   */
  @LeoConfigurationParameter
  protected boolean removeIfAnyInvalid = true;


  @Override
  public void annotate(JCas aJCas) throws AnalysisEngineProcessException {
    for (String inputType : inputTypes) {
      try {
        Collection<Annotation> annotations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, inputType, false);
        for (Annotation a : annotations) {
          String covered = a.getCoveredText();
          Matcher m = numberPattern.matcher(covered);
          boolean foundValid = false, hasNumber = false;
          while (m.find()) {
            String number = covered.substring(m.start(), m.end());
            if (GenericValidator.isDouble(number)) {
              hasNumber = true;
              if (!isValid(new Double(number))) {
                if (removeIfAnyInvalid) {
                  foundValid = false;
                  break;
                }
              } else {
                foundValid = true;
              }
            }
          }
          if (hasNumber && !foundValid)
            a.removeFromIndexes();
        }
      } catch (CASException e) {
        throw new AnalysisEngineProcessException(e);
      }
    }
  }

  /**
   * Check the numeric value and return true if the value meets requirements.
   *
   * @param value Double value to be validated
   * @return true if number is valid
   */
  protected abstract boolean isValid(double value);

  public boolean isRemoveIfAnyInvalid() {
    return removeIfAnyInvalid;
  }

  public NumericValidatorAnnotator setRemoveIfAnyInvalid(boolean removeIfAnyInvalid) {
    this.removeIfAnyInvalid = removeIfAnyInvalid;
    return this;
  }


}
