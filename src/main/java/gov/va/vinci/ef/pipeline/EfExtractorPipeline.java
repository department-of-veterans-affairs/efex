package gov.va.vinci.ef.pipeline;

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

import gov.va.vinci.ef.ae.*;
import gov.va.vinci.leo.conceptlink.ae.ConceptLinkAnnotator;
import gov.va.vinci.leo.conceptlink.ae.MatchMakerAnnotator;
import gov.va.vinci.leo.descriptors.LeoAEDescriptor;
import gov.va.vinci.leo.descriptors.LeoTypeSystemDescription;
import gov.va.vinci.leo.filter.ae.FilterAnnotator;
import gov.va.vinci.leo.regex.ae.RegexAnnotator;
import gov.va.vinci.leo.types.TypeLibrarian;
import gov.va.vinci.leo.window.ae.WindowAnnotator;
import org.apache.uima.resource.metadata.TypeDescription;
import org.apache.uima.resource.metadata.impl.TypeDescription_impl;

import java.util.regex.Pattern;

/**
 * Based on the Pipeline class this pipeline is a scalable, optimized echo extraction pipeline that attempts to zero in
 * on concepts and measurements as fast as possible.
 * <p>
 * Created by thomasginter on 07/28/2015.
 * Updated by olga patterson on 06/14/2016
 */
public class EfExtractorPipeline implements PipelineInterface {
  LeoAEDescriptor pipeline = null;
  LeoTypeSystemDescription description = null;

  protected static String RESOURCE_PATH = "resources/";

  public EfExtractorPipeline() {
    this.getLeoTypeSystemDescription();
  }

  @Override
  public LeoAEDescriptor getPipeline() throws Exception {
    if (pipeline != null)
      return pipeline;

    //Build the pipeline
    LeoTypeSystemDescription types = getLeoTypeSystemDescription();
    pipeline = new LeoAEDescriptor();

    // Module 1: MeasurementRegex -- find mentions of left ventricular ejection fraction
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "efMeasure.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setOutputType("gov.va.vinci.ef.types.Measurement")
        .setName("MeasurementRegex")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 2: WindowAnnotator -- make a context window of -7...+7 tokens around the measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setWindowSize(7)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.Measurement"})
        .setOutputType("gov.va.vinci.ef.types.MeasurementWindow")
        .setName("WindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 3: AnatomyAnnotator -- find an anatomy phrase in the window around measurement
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "efAnatomy.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.MeasurementWindow"})
        .setOutputType("gov.va.vinci.ef.types.Anatomy")
        .setName("AnatomyAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 4: ConceptCollector -- merge anatomy and measurement annotations into a single annotation
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setIncludeWhiteSpace(false)
        .setMaxDifference(2)
        .setMaxDistance(20)
        .setPatternFile(RESOURCE_PATH + "conceptCollection.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.Anatomy", "gov.va.vinci.ef.types.Measurement"})
        .setOutputType("gov.va.vinci.ef.types.Measurement")
        .setName("ConceptCollector")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 5: ContextWindowAnnotator -- create a window of -15 ... +15 tokens around measurement phrase
    pipeline.addDelegate(new WindowAnnotator()
        .setAnchorFeature("Anchor")
        .setWindowSize(15)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.Measurement"})
        .setOutputType("gov.va.vinci.ef.types.ContextWindow")
        .setName("ContextWindowAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 6: NumericValueAnnotator -- find numeric value in the context window
    pipeline.addDelegate(new RegexAnnotator()
        .setResource(RESOURCE_PATH + "efNumericValue.regex")
        .setCaseSensitive(false)
        .setPatternFlags(Pattern.DOTALL)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.ContextWindow"})
        .setOutputType("gov.va.vinci.ef.types.NumericValue")
        .setName("NumericValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 7: MergeRangeValueAnnotator - create a range annotation when the value is expressed as a range
    pipeline.addDelegate(new ConceptLinkAnnotator()
        .setIncludeChildren(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(0)
        .setMaxDistance(10)
        .setPatternFile(RESOURCE_PATH + "efRange.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.NumericValue"})
        .setOutputType("gov.va.vinci.ef.types.NumericValue")
        .setName("MergeRangeValueAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 8: NumericMeasureFilter -- Filter out all numeric values that do not meet context criteria
    pipeline.addDelegate(new NumericMeasureFilter()
        .setRegexFilePath(RESOURCE_PATH + "efInvalidNumeric.regex")
        .setName("NumericMeasureFilter")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 9: EFValidatorAnnotator -- Filter out all numeric values that do not meet valid range criteria
    pipeline.addDelegate(new EFValidatorAnnotator()
        .setRemoveIfAnyInvalid(false)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.NumericValue"})
        .setName("EFValidatorAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    // Module 10: TemplateAnnotator -- Create LVEF concept-value pair for a common template in VA data
    pipeline.addDelegate(
        new TemplateAnnotator()
            .setName("TemplateAnnotator")
            .addLeoTypeSystemDescription(types)
            .getDescriptor());

    // Module 11: MeasureToValueRelationAnnotator -- Create LVEF concept-value pair based on the relational patterns between measurement and value
    pipeline.addDelegate(new MatchMakerAnnotator()
        .setConceptTypeName("gov.va.vinci.ef.types.Measurement")
        .setValueTypeName("gov.va.vinci.ef.types.NumericValue")
        .setPeekRightFirst(true)
        .setMaxCollectionSize(2)
        .setMaxDifference(2)
        .setMaxDistance(50)
        .setPatternFile(RESOURCE_PATH + "middleStuff.regex")
        .setRemoveCovered(true)
        .setInputTypes(new String[]{"gov.va.vinci.ef.types.Measurement",
            "gov.va.vinci.ef.types.NumericValue"})
        .setOutputType("gov.va.vinci.ef.types.EfRelation")
        .setName("MeasureToValueRelationAnnotator")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());
    
    // Module12: FlattenRelationAE -- Create a new type for ease of writing results to csv or database
    pipeline.addDelegate(new FlattenRelationAE()
        .setName("FlattenRelationAE")
        .addLeoTypeSystemDescription(types)
        .getDescriptor());

    return pipeline;
  }


  @Override
  public LeoTypeSystemDescription getLeoTypeSystemDescription() {
    if (description != null)
      return description;
    description = new LeoTypeSystemDescription();
    String linkParent = "gov.va.vinci.leo.conceptlink.types.Link";
    // Pattern Type description
    TypeDescription regexFtsd;
    String regexParent = "gov.va.vinci.ef.types.Regex";
    regexFtsd = new TypeDescription_impl(regexParent, "", "uima.tcas.Annotation");
    regexFtsd.addFeature("pattern", "", "uima.cas.String");
    regexFtsd.addFeature("concept", "", "uima.cas.String");
    regexFtsd.addFeature("groups", "", "uima.cas.StringArray");

    // Total type definition
    try {
      description.addType(TypeLibrarian.getCSITypeSystemDescription())
          .addTypeSystemDescription(new WindowAnnotator().getLeoTypeSystemDescription())
          .addTypeSystemDescription(new ConceptLinkAnnotator().getLeoTypeSystemDescription())
          .addType(regexFtsd)
          .addType("gov.va.vinci.ef.types.EfRelation", "", linkParent)
          .addType("gov.va.vinci.ef.types.MeasurementWindow", "", "gov.va.vinci.leo.window.types.Window")
          .addType("gov.va.vinci.ef.types.ContextWindow", "", "gov.va.vinci.leo.window.types.Window")
         // .addType("gov.va.vinci.ef.types.NoEvidence", "", "uima.tcas.Annotation")
          .addType("gov.va.vinci.ef.types.Anatomy", "", regexParent)
          .addType("gov.va.vinci.ef.types.Measurement", "", linkParent)
          .addType("gov.va.vinci.ef.types.NumericValue", "", linkParent)
          .addType("gov.va.vinci.kttr.types.RefSt_EfValue", "", "uima.tcas.Annotation")
          .addTypeSystemDescription(new FlattenRelationAE().getLeoTypeSystemDescription())
      ;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return description;
  }
}
