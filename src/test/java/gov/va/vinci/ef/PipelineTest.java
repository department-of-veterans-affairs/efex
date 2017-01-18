package gov.va.vinci.ef;

/*
 * #%L
 * EF_Exctractor
 * %%
 * Copyright (C) 2010 - 2017 Department of Veterans Affairs
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

import gov.va.vinci.ef.pipeline.*;
import gov.va.vinci.ef.types.Relation;
import junit.framework.Assert;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import java.io.File;
import java.util.Iterator;

public class PipelineTest {
  protected gov.va.vinci.leo.descriptors.LeoAEDescriptor aggregate = null;
  protected gov.va.vinci.leo.descriptors.LeoTypeSystemDescription types = null;
  protected String inputDir = "data/test/";
  protected String outputDir = "data/output/xmi/";
  protected boolean launchView = false;


  @org.junit.Before
  public void setup() throws Exception {
    EfExtractorPipeline bs = new EfExtractorPipeline();
    types = bs.getLeoTypeSystemDescription();
    aggregate = bs.getPipeline();

    File o = new File(outputDir);
    if (!o.exists()) {
      o.mkdirs();
    }//if
  }//setup


  @org.junit.Test
  public void testSimple() throws Exception {
    String docText = "Various unrelated text. Ejection Fraction = 55-60%. Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    Assert.assertTrue(aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "55.0");
      Assert.assertEquals(r.getValue2(), "60.0");
      Assert.assertEquals(r.getTerm(), "Ejection Fraction");
      Assert.assertFalse(aList.hasNext());
    }
  }

  @org.junit.Test
  public void testSimple2() throws Exception {
    String docText = "Various unrelated text. Abnormal with EF 42% Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    Assert.assertTrue(aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "42.0");
      Assert.assertNull(r.getValue2());
      Assert.assertEquals(r.getTerm(), "EF");
      Assert.assertFalse(aList.hasNext());
    }
  }

  @org.junit.Test
  public void testSimple3() throws Exception {
    String docText = "Various unrelated text. he wall motion and left ventricular systolic function appears hyperdynamic with estimated ejection fraction of 70% to 75%. There is near-cavity obliteration seen. Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    assert (aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "70.0");
      Assert.assertEquals(r.getValue2(), "75.0");
      Assert.assertEquals(r.getTerm(), "estimated ejection fraction");
      Assert.assertFalse(aList.hasNext());
    }
  }


  @org.junit.Test
  public void testTemplate() throws Exception {

    String docText = "Various unrelated text. \n" +
        "\n" +
        "Overall ESTIMATED EF:\n" +
        "        [ ]>50   [ ]45-55   [ ]40-50   [ ]35-45   [ ]30-40   [x]20-30   [ ]<20\n" +
        "\n" +
        " Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    assert (aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "20.0");
      Assert.assertEquals(r.getValue2(), "30.0");
      Assert.assertEquals(r.getTerm(), "ESTIMATED EF");
      Assert.assertFalse(aList.hasNext());
    }
  }

  @org.junit.Test
  public void testList() throws Exception {

    String docText = "Various unrelated text. \n" +
        "\n" +
        "3. Normal LV systolic function. \n" +
        "4. Ejection fraction estimated at 0.60.\n" +
        "5. Aortic valve seen with good motion.\n" +
        "\n" +
        " Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    assert (aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "0.6");
      Assert.assertNull(r.getValue2());
      Assert.assertEquals(r.getTerm(), "Ejection fraction");
      Assert.assertFalse(aList.hasNext());
    }
  }

  @org.junit.Test
  public void testList2() throws Exception {

    String docText = "Various unrelated text. \n" +
        "\n" +
        "COMMENTS:\n" +
        "1. Wall motion and left ventricular systolic function is low/borderline with estimated ejection fraction of 50% to 55% \n" +
        "2. There is increased left ventricular outflow consistent with low/borderline left ventricular systolic function. \n" +
        "3. Systolic blood pressure was measured at 90 mmHg. \n" +
        " Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    assert (aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "50.0");
      Assert.assertEquals(r.getValue2(), "55.0");
      Assert.assertEquals(r.getTerm(), "estimated ejection fraction");
      Assert.assertFalse(aList.hasNext());
    }
  }

  @org.junit.Test
  public void testTest1() throws Exception {

    String docText = "Various unrelated text. \n" +
        "\n" +
        "2. Notable chamber enlargement and hypertrophy is present.\n " +
        "3. The left ventricle is mildly enlarged to 6.0 cm, wall thickness is normal, with mild global hypokinesis with ejection fraction by 35% by M-mode and 40% by Simpson's method.  \n" +
        "4. Pericardial effusion and vegetations are observed.  \n" +
        " Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    assert (aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "35.0");
      Assert.assertNull(r.getValue2());
      Assert.assertEquals(r.getTerm(), "ejection fraction");
      Assert.assertTrue(aList.hasNext());
    }
    if (aList.hasNext()) {  //TODO: Fix the pipeline to merge the two annotations into one.
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "35.0");
      Assert.assertNull(r.getValue2());
      Assert.assertEquals(r.getTerm(), "M-mode");
      Assert.assertTrue(aList.hasNext());
    }
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "40.0");
      Assert.assertNull(r.getValue2());
      Assert.assertEquals(r.getTerm(), "Simpson's method");
      Assert.assertFalse(aList.hasNext());
    }
  }


  @org.junit.Test
  public void testTest2() throws Exception {

    String docText = "Various unrelated text. \n" +
        "\n" +
        "OTHER CONCLUSIONS:\n " +
        "LV systolic dysfunction present with EF 0.2. \n" +
        "\n" +
        "Date:  09/20/2009  \n" +
        " Various unrelated text. ";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(aggregate.getAnalysisEngineDescription());
    JCas jcas = ae.newJCas();
    jcas.setDocumentText(docText);
    ae.process(jcas);
    Iterator<Annotation> aList = jcas.getAnnotationIndex(Relation.type).iterator();
    assert (aList.hasNext());
    if (aList.hasNext()) {
      Relation r = (Relation) aList.next();
      Assert.assertEquals(r.getValue(), "0.2");
      Assert.assertNull(r.getValue2());
      Assert.assertEquals(r.getTerm(), "EF");
      Assert.assertFalse(aList.hasNext());
    }
  }

  @org.junit.Test
  public void testXmi() throws Exception {

    String docText = "";
    AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(
        aggregate.getAnalysisEngineDescription());

    File[] files = (new File(this.inputDir)).listFiles();
    if (files != null)
      for (File infile : files) {
        String filePath = infile.getAbsolutePath();
        if (infile.getName().endsWith(".txt")) {
          System.out.println("Processing " + filePath);
          try {

            docText = org.apache.uima.util.FileUtils.file2String(infile);
          } catch (Exception e) {
            System.out.println("Missing file!!");
          }
          if (org.apache.commons.lang3.StringUtils.isBlank(docText)) {
            System.out.println("Blank file!!");
          }

          JCas jcas = ae.newJCas();
          jcas.setDocumentText(docText);
          ae.process(jcas);
          try {
            File xmio = new File(outputDir, infile.getName() + ".xmi");
            XmiCasSerializer.serialize(jcas.getCas(), new java.io.FileOutputStream(xmio));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    if (launchView) launchViewer();
    assert (true);
    if (launchView) System.in.read();


  }//testXmi method

  protected void launchViewer() throws Exception {
    if (aggregate == null) {
      throw new RuntimeException("Aggregate is null, unable to generate descriptor for viewing xmi");
    }
    aggregate.toXML(outputDir);
    String aggLoc = aggregate.getDescriptorLocator().substring(5);
    java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("org/apache/uima/tools/AnnotationViewer");
    if (aggLoc != null) {
      prefs.put("taeDescriptorFile", aggLoc);
    }//if mAggDesc != null
    if (outputDir != null) {
      prefs.put("inDir", outputDir);
    }//if mOutputDir != null
    org.apache.uima.tools.AnnotationViewerMain avm = new org.apache.uima.tools.AnnotationViewerMain();
    avm.setBounds(0, 0, 1000, 225);
    avm.setVisible(true);
  }//launchViewer method

  /**
   * @After
   */
  public void cleanup() throws Exception {
    File o = new File(outputDir);
    if (o.exists()) {
      org.apache.uima.util.FileUtils.deleteRecursive(o);
    }//if
  }//cleanup method
}//EchoTest class
