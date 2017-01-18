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

import gov.va.vinci.ef.scanners.EFRangeTemplate01Scanner;
import gov.va.vinci.leo.ae.LeoBaseAnnotator;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

/**
 * Find and annotate relationships from templates.
 *
 * Created by thomasginter on 9/15/15.
 */
public class TemplateAnnotator extends LeoBaseAnnotator {
    protected EFRangeTemplate01Scanner template01Scanner = new EFRangeTemplate01Scanner();

    @Override
    public void annotate(JCas aJCas) throws AnalysisEngineProcessException {


        try {
            template01Scanner.scanAll(aJCas);
        } catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
    }


}
