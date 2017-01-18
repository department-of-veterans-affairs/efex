package gov.va.vinci.ef.tools;

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

/**
 * Created by thomasginter on 8/4/15.
 */

import org.apache.uima.jcas.tcas.Annotation;

import java.util.Comparator;

/**
 * Compares Annotations based on starting indexes ascending and ending indexes descending
 */
public class AnnotationComparator implements Comparator<Annotation> {

    /**
     * Compare two uima.tcas.Annotation objects or children based on starting and ending indexes.
     *
     * @param o1 first Annotation to compare
     * @param o2 second Annotation to compare
     * @return -1 if o1.begin < o2.begin || o1.begin == o2.begin && o1.end > o2.end,
     * 0 if o1.begin == o2.begin && o2.end == o1.end,
     * 1 if o1.begin > o2.being || o1.begin == o1.begin && o1.end < o2.end
     */
    @Override
    public int compare(Annotation o1, Annotation o2) {
        if (o1 == null || o1 == null)
            throw new NullPointerException("Arguments cannot be null");

        if (o1.getBegin() < o2.getBegin())
            return -1;
        else if (o1.getBegin() > o2.getBegin())
            return 1;
        else if (o1.getBegin() == o2.getBegin()) {
            if (o1.getEnd() > o2.getEnd())
                return -1;
            else if (o1.getEnd() < o2.getEnd())
                return 1;
        }
        return 0;
    }

    /**
     * Compares this Comparator object to other Comparators. In this case we only return true if the class instance
     * to be compared is an AnnotationComparator class or a child of that class.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return obj.getClass().isAssignableFrom(AnnotationComparator.class);
    }
}
