package gov.va.vinci.ef.listeners;

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

import gov.va.vinci.knowtator.model.Document;
import gov.va.vinci.knowtator.model.KnowtatorToUimaTypeMap;
import gov.va.vinci.knowtator.service.KnowtatorOutputService;
import gov.va.vinci.knowtator.tools.KnowtatorUtils;
import gov.va.vinci.leo.listener.BaseListener;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.collection.EntityProcessStatus;
import org.apache.uima.jcas.JCas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Created by OlgaPatterson on 11/5/16.
 */
public class EfKnowtatorListener  extends BaseListener {
    
    // private String outputDir = null;
    private String projectName = null;
    private String outputProjectDirectory = null;
    private String outputProjectDirectoryConfig = null;
    private String outputProjectDirectorySaved = null;
    private String outputProjectDirectoryCorpus = null;
    private String homeDir = null;
    private int inputFileCounter = 0;
    private boolean classDefinitionsWritten = false;
    private String eHostWorkSpaceName = null;
    
    private KnowtatorToUimaTypeMap knowtatorToUimaTypeMap = null;
    private Set<String> outputTypeHash = null;
    
    private KnowtatorOutputService knowtator;
    
    public EfKnowtatorListener(String projectName, String eHostWorkSpaceName,
                             KnowtatorToUimaTypeMap knowtatorToUimaTypeMap) {
      this.projectName = projectName;
      //this.outputDir = outputDir;
      this.eHostWorkSpaceName = eHostWorkSpaceName;
      this.knowtatorToUimaTypeMap = knowtatorToUimaTypeMap;
      
      this.outputProjectDirectory = this.eHostWorkSpaceName + "/" + this.projectName;
      this.outputProjectDirectoryConfig = this.outputProjectDirectory + "/config";
      this.outputProjectDirectorySaved = this.outputProjectDirectory + "/saved";
      this.outputProjectDirectoryCorpus = this.outputProjectDirectory + "/corpus";
      
      // ------------------------------------------------
      // Create the project directories and populate them
      // ------------------------------------------------
      
      new File(this.outputProjectDirectoryConfig).mkdirs();
      new File(this.outputProjectDirectorySaved).mkdirs();
      new File(this.outputProjectDirectoryCorpus).mkdirs();
      
      this.homeDir = KnowtatorUtils.getHomeDirectory();
      this.classDefinitionsWritten = false;
      knowtator = new KnowtatorOutputService(knowtatorToUimaTypeMap);
      
    }
    
    /**
     * @param aCas    the CAS containing the processed entity and the analysis results
     * @param aStatus the status of the processing. This object contains a record of any Exception that occurred, as well as timing information.
     * @see org.apache.uima.aae.client.UimaAsBaseCallbackListener#entityProcessComplete(org.apache.uima.cas.CAS, org.apache.uima.collection.EntityProcessStatus)
     */
    @Override
    public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {
      super.entityProcessComplete(aCas, aStatus);
      if(hasFilteredAnnotation(aCas)){
      JCas pJCas = null;
      try {
        pJCas = aCas.getJCas();
      } catch (CASException e) {
        throw new RuntimeException(e);
      }
      
      
      
      // -------------------------------------------
      // The class definition file and attributes file needs to be
      // written out for each corpus.  Since I don't have a handle
      // on where to write it out until a file with the path to
      // the output, it's written out once here.
      // -------------------------------------------
      if (!this.classDefinitionsWritten) {
        try {
          this.outputTypeHash = knowtatorToUimaTypeMap.getKnowtatorTypes();
          knowtator.writeClassDefinitions(outputProjectDirectoryConfig,knowtatorToUimaTypeMap, pJCas);
          this.classDefinitionsWritten = true;
        } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e);
        }
      } // end if the class file hasn't been written
      
      String outputFileName = getOutputFileName(pJCas);
      
      // --------------------------------------------------
      // Write the source file out to the corpus directory
      // --------------------------------------------------
      try {
        String sourceFileName = this.getSourceName(pJCas);
        PrintWriter out = new PrintWriter(this.outputProjectDirectoryCorpus + "/" + sourceFileName+".txt");
        out.println(pJCas.getDocumentText());
        out.close();
        
      } catch (FileNotFoundException e1) {
        
        e1.printStackTrace();
        throw new RuntimeException("Something went wrong in KnowtatorListener writing out the source file : "
            + e1.toString());
      }
      
      try {
        Document knowtatorDocument = knowtator.convert(pJCas, outputFileName);
        
        knowtator.write(outputFileName, knowtatorDocument);
      } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
        
      }
      }
    } // end Method process
    
    
    /**
     * getOuputFileName extracts the name of the cas,
     * and returns the path defined by
     * [homeDir]/this.outputDir.casName.cm.xml
     *
     * @param pJCas
     * @return String
     */
    // -----------------------------------------
    private String getOutputFileName(JCas pJCas) {
      String returnValue = null;
      
      String name = getSourceName(pJCas);
      
      if ((this.outputProjectDirectorySaved.startsWith("/")) || (this.outputProjectDirectorySaved.startsWith("\\"))
          || (this.outputProjectDirectorySaved.indexOf(":") == 1))
        returnValue = this.outputProjectDirectorySaved + "/" + name + ".txt.knowtator.xml";
      else
        returnValue = this.homeDir + "/" + this.eHostWorkSpaceName + "/" + this.projectName + "/saved/" + name
            + ".txt.knowtator.xml";
      
      return returnValue;
    } // end Method getOutputFileName() --------
    
    // -----------------------------------------
    
    /**
     * getSourceName extracts the name of the cas,
     * devoid of a class path
     *
     * @param pJCas
     * @return String
     */
    // -----------------------------------------
    private String getSourceName(JCas pJCas) {
      
      String sourceURI = getReferenceLocation(pJCas);
      String name = null;
      if (sourceURI != null) {
        File aFile = new File(sourceURI);
        name = aFile.getName();
        
      } else {
        name = "knowtator_" + String.valueOf(this.inputFileCounter++);
      }
      
      return name;
    } // end Method getOutputFileName() --------
    
  } // end Class toKnowtator
  

