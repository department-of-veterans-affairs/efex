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

import gov.va.vinci.ef.types.*;
import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import org.apache.commons.lang3.StringUtils;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.impl.TypeDescription_impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Flatten relationships for easier output.
 * <p>
 * Created by vhaslcpatteo on 9/16/2015.
 * Edited  by Thomas Ginter on 09/18/2015.  Added the setValueStrings method.
 */
public class FlattenRelationAE extends LeoBaseAnnotator {
  protected Pattern numberPattern = Pattern.compile("\\d+(\\.\\d+)?");

  @Override
  public void annotate(JCas aJCas) throws AnalysisEngineProcessException {
     Collection<EfRelation> relations = AnnotationLibrarian.getAllAnnotationsOfType(aJCas, EfRelation.type, false);
    for (EfRelation relation : relations) {
      //Create the output annotation
      Relation out = new Relation(aJCas, relation.getBegin(), relation.getEnd());
      out.addToIndexes();
      //Set the string value features
      setValueStrings(relation, out);
    }

  }

  protected void setValueStrings(EfRelation in, Relation out) {

    //Get the NumericValue annotation from the merged set
    Annotation value = null;
    Annotation measure = null;
    FSArray merged = in.getLinked();
    for (int i = 0; i < merged.size(); i++) {
      Annotation a = (Annotation) merged.get(i);
      String typeName = a.getType().getName();
      if (typeName.equals(Measurement.class.getCanonicalName())){
        measure = a;
        if (measure != null) {
          out.setTerm(measure.getCoveredText());
        }
      }

      else if (typeName.equals(NumericValue.class.getCanonicalName())) {
        value = a;
      }
    }
    //Exit if no value found
    if (value == null)
      return;
    //Get the values
    String valueText = value.getCoveredText();
    Matcher m = numberPattern.matcher(valueText);
    ArrayList<Double> values = new ArrayList<Double>(2);
    while (m.find())
      values.add(new Double(valueText.substring(m.start(), m.end())));
    Collections.sort(values);

    //Set the values
    if (values.size() > 0) out.setValue(values.get(0).toString());
    if (values.size() > 1) out.setValue2(values.get(values.size() - 1).toString());
    if (StringUtils.isNotBlank(valueText)) out.setValueString(valueText);

  }

  @Override
  public LeoTypeSystemDescription getLeoTypeSystemDescription() {
    TypeDescription relationFtsd;
    String relationParent = "gov.va.vinci.ef.types.Relation";
    relationFtsd = new TypeDescription_impl(relationParent, "", "uima.tcas.Annotation");
    relationFtsd.addFeature("Term", "", "uima.cas.String"); // Extracted term string
    relationFtsd.addFeature("Value", "", "uima.cas.String"); // Numeric  value
    relationFtsd.addFeature("Value2", "", "uima.cas.String"); // Numeric  value
    relationFtsd.addFeature("ValueString", "", "uima.cas.String"); // String  value with units and extra modifiers

    LeoTypeSystemDescription types = new LeoTypeSystemDescription();
    try {
      types.addType(relationFtsd); // for target concepts with single mapping

    } catch (Exception e) {
      e.printStackTrace();
    }
    return types;
  }


}
