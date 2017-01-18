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

import gov.va.vinci.leo.AnnotationLibrarian;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides the template to create standardized objects that scan annotations to signal when an annotation meets one
 * or more conditions.
 * <p>
 * User: Thomas Ginter
 * Date: 1/22/15
 */
public abstract class BaseAnnotationScanner {

    /**
     * Scan all annotations in the jCas and call scanAction for each annotation that matches the scan conditions.
     *
     * @param jCas JCas to scan for matches
     */
    public void scanAll(JCas jCas) throws Exception {
        List<Annotation> annotations = collectionToScan(jCas);
        for (Annotation a : annotations) {
            if (scan(a)) {
                scanAction(a);
            }
        }
    }

    /**
     * Return a list of annotations with a positive scan.
     *
     * @param jCas JCas with annotations to scan
     * @return list of annotations with a positive scan
     */
    public List<Annotation> scanAllToList(JCas jCas) throws Exception {
        ArrayList<Annotation> list = new ArrayList<Annotation>();
        List<Annotation> annotations = collectionToScan(jCas);
        for (Annotation a : annotations) {
            if (scan(a)) {
                list.add(a);
            }
        }
        return list;
    }

    /**
     * Create the list of annotations to scan from the complete JCas list.  Default implementation grabs all annotations
     * in the JCas.
     *
     * @param jCas JCas from which the list will be created.
     * @return list of annotations to be scanned.
     */
    public List<Annotation> collectionToScan(JCas jCas) {
        return (List<Annotation>) AnnotationLibrarian.copyFSIteratorToCollection(jCas.getAnnotationIndex());
    }

    /**
     * Check the annotation for a match to the implemented conditions.
     *
     * @param a Annotation to check for a match
     * @return true if a match is found, false otherwise.
     */
    public abstract boolean scan(Annotation a) throws Exception;

    /**
     * Action to take when the scan is positive. No action taken by default.
     *
     * @param a Annotation on which the action will be performed.
     */
    public void scanAction(Annotation a) throws Exception {
        /** By default no action is performed **/
        return;
    }
}