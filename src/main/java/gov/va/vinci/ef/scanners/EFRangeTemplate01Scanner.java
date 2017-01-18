package gov.va.vinci.ef.scanners;

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

import gov.va.vinci.ef.types.EfRelation;
import gov.va.vinci.ef.types.Measurement;
import gov.va.vinci.ef.types.NumericValue;
import gov.va.vinci.leo.AnnotationLibrarian;
import gov.va.vinci.leo.conceptlink.ConceptLinkService;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Creates efRelation annotations for the following template:
 * Estimated EF:
 * []>50 []45-55 [x]35-45 []30-40 []20-30 []<20
 * <p>
 * Created by thomasginter on 9/15/15.
 * Updated by olga patterson on 06/14/2016
 */
public class EFRangeTemplate01Scanner extends BaseAnnotationScanner {

    /**
     * Pattern that will match if this is a valid header for the template.
     */
    protected Pattern headerPattern = Pattern.compile("est(\\.|\\w+)?(.{1,3}ejection.{1,3}fraction|.{1,3}ef)",
            Pattern.CASE_INSENSITIVE);

    protected ConceptLinkService mergeService = null;

    public EFRangeTemplate01Scanner() {
        try {
            mergeService = new ConceptLinkService(20, 2, 8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mergeService.addJoinPattern(Pattern.compile("(:|\\[|\\]|x)+", Pattern.CASE_INSENSITIVE));
    }

    /**
     * Scan the Measurement annotations to see if any are headers for our template.
     *
     * @param jCas JCas from which the list will be created.
     * @return
     */
    @Override
    public List<Annotation> collectionToScan(JCas jCas) {
        return (List<Annotation>) AnnotationLibrarian.getAllAnnotationsOfType(jCas, Measurement.type, false);
    }

    /**
     * Returns true if the annotation is Measurement whose covered text is Estimated EF.
     *
     * @param a Annotation to check for a match
     * @return
     * @throws Exception
     */
    @Override
    public boolean scan(Annotation a) throws Exception {
        return (a.getType().getName().equals(Measurement.class.getCanonicalName())
                && headerPattern.matcher(a.getCoveredText()).find());
    }

    /**
     * Uses the MultiMerge annotator to "merge" the header with the NumericValue annotations in the template.  Grab
     * the one with the "X" and create a new relationship annotation with the header and the value.
     *
     * @param a Annotation on which the action will be performed.
     * @throws Exception
     */
    @Override
    public void scanAction(Annotation a) throws Exception {
        JCas jCas = a.getCAS().getJCas();
        String docText = jCas.getDocumentText();
        ArrayList<Annotation> mergeList = new ArrayList<Annotation>(8);
        mergeList.add(a);
        mergeList.addAll(AnnotationLibrarian.getNextAnnotationsOfType(a, NumericValue.type, 7, false));
        //Merge the annotations if they can be
        ArrayList<ConceptLinkService.LinkSpan> spans
                = (ArrayList<ConceptLinkService.LinkSpan>) mergeService.linkSpans(mergeList, docText);
        if (spans.size() < 1)
            return;
        //Check the merged span to make sure it meets the minimum reqs
        ConceptLinkService.LinkSpan span = spans.get(0);
        ArrayList<Annotation> merged = span.getLinked();
        if (merged.size() < 5)
            return;
        //X marks the spot
        Annotation value = null;
        for (Annotation val : merged) {
            if (val.getType().getName().equals(NumericValue.class.getCanonicalName())) {
                String prev = docText.substring((val.getBegin() - 3), val.getBegin());
                if (prev.equalsIgnoreCase("[x]")) {
                    value = val;
                    break;
                }
            }
        }
        //If the value is found then create the output annotation
        if (value != null) {
            int start = a.getBegin();
            int end = value.getEnd();
            EfRelation rel = new EfRelation(jCas, start, end);
            rel.addToIndexes();
            ArrayList<Annotation> m = new ArrayList<Annotation>(2);
            m.add(a);
            m.add(value);
            ConceptLinkService.doSetLinkedTypes(rel, m, "linked", true);
        }
    }
}