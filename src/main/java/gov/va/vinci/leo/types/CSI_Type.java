
/* First created by JCasGen Tue Jun 28 15:42:22 CDT 2016 */
package gov.va.vinci.leo.types;

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

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** CSI Annotation
 * Updated by JCasGen Tue Jun 28 15:42:22 CDT 2016
 * @generated */
public class CSI_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (CSI_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = CSI_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new CSI(addr, CSI_Type.this);
  			   CSI_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new CSI(addr, CSI_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = CSI.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("gov.va.vinci.leo.types.CSI");
 
  /** @generated */
  final Feature casFeat_ID;
  /** @generated */
  final int     casFeatCode_ID;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getID(int addr) {
        if (featOkTst && casFeat_ID == null)
      jcas.throwFeatMissing("ID", "gov.va.vinci.leo.types.CSI");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ID);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setID(int addr, String v) {
        if (featOkTst && casFeat_ID == null)
      jcas.throwFeatMissing("ID", "gov.va.vinci.leo.types.CSI");
    ll_cas.ll_setStringValue(addr, casFeatCode_ID, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Locator;
  /** @generated */
  final int     casFeatCode_Locator;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLocator(int addr) {
        if (featOkTst && casFeat_Locator == null)
      jcas.throwFeatMissing("Locator", "gov.va.vinci.leo.types.CSI");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Locator);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLocator(int addr, String v) {
        if (featOkTst && casFeat_Locator == null)
      jcas.throwFeatMissing("Locator", "gov.va.vinci.leo.types.CSI");
    ll_cas.ll_setStringValue(addr, casFeatCode_Locator, v);}
    
  
 
  /** @generated */
  final Feature casFeat_RowData;
  /** @generated */
  final int     casFeatCode_RowData;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getRowData(int addr) {
        if (featOkTst && casFeat_RowData == null)
      jcas.throwFeatMissing("RowData", "gov.va.vinci.leo.types.CSI");
    return ll_cas.ll_getRefValue(addr, casFeatCode_RowData);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRowData(int addr, int v) {
        if (featOkTst && casFeat_RowData == null)
      jcas.throwFeatMissing("RowData", "gov.va.vinci.leo.types.CSI");
    ll_cas.ll_setRefValue(addr, casFeatCode_RowData, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getRowData(int addr, int i) {
        if (featOkTst && casFeat_RowData == null)
      jcas.throwFeatMissing("RowData", "gov.va.vinci.leo.types.CSI");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_RowData), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_RowData), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_RowData), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setRowData(int addr, int i, String v) {
        if (featOkTst && casFeat_RowData == null)
      jcas.throwFeatMissing("RowData", "gov.va.vinci.leo.types.CSI");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_RowData), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_RowData), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_RowData), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_PropertiesKeys;
  /** @generated */
  final int     casFeatCode_PropertiesKeys;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPropertiesKeys(int addr) {
        if (featOkTst && casFeat_PropertiesKeys == null)
      jcas.throwFeatMissing("PropertiesKeys", "gov.va.vinci.leo.types.CSI");
    return ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPropertiesKeys(int addr, int v) {
        if (featOkTst && casFeat_PropertiesKeys == null)
      jcas.throwFeatMissing("PropertiesKeys", "gov.va.vinci.leo.types.CSI");
    ll_cas.ll_setRefValue(addr, casFeatCode_PropertiesKeys, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getPropertiesKeys(int addr, int i) {
        if (featOkTst && casFeat_PropertiesKeys == null)
      jcas.throwFeatMissing("PropertiesKeys", "gov.va.vinci.leo.types.CSI");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setPropertiesKeys(int addr, int i, String v) {
        if (featOkTst && casFeat_PropertiesKeys == null)
      jcas.throwFeatMissing("PropertiesKeys", "gov.va.vinci.leo.types.CSI");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesKeys), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_PropertiesValues;
  /** @generated */
  final int     casFeatCode_PropertiesValues;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPropertiesValues(int addr) {
        if (featOkTst && casFeat_PropertiesValues == null)
      jcas.throwFeatMissing("PropertiesValues", "gov.va.vinci.leo.types.CSI");
    return ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPropertiesValues(int addr, int v) {
        if (featOkTst && casFeat_PropertiesValues == null)
      jcas.throwFeatMissing("PropertiesValues", "gov.va.vinci.leo.types.CSI");
    ll_cas.ll_setRefValue(addr, casFeatCode_PropertiesValues, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getPropertiesValues(int addr, int i) {
        if (featOkTst && casFeat_PropertiesValues == null)
      jcas.throwFeatMissing("PropertiesValues", "gov.va.vinci.leo.types.CSI");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues), i);
	return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setPropertiesValues(int addr, int i, String v) {
        if (featOkTst && casFeat_PropertiesValues == null)
      jcas.throwFeatMissing("PropertiesValues", "gov.va.vinci.leo.types.CSI");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_PropertiesValues), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public CSI_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ID = jcas.getRequiredFeatureDE(casType, "ID", "uima.cas.String", featOkTst);
    casFeatCode_ID  = (null == casFeat_ID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ID).getCode();

 
    casFeat_Locator = jcas.getRequiredFeatureDE(casType, "Locator", "uima.cas.String", featOkTst);
    casFeatCode_Locator  = (null == casFeat_Locator) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Locator).getCode();

 
    casFeat_RowData = jcas.getRequiredFeatureDE(casType, "RowData", "uima.cas.StringArray", featOkTst);
    casFeatCode_RowData  = (null == casFeat_RowData) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_RowData).getCode();

 
    casFeat_PropertiesKeys = jcas.getRequiredFeatureDE(casType, "PropertiesKeys", "uima.cas.StringArray", featOkTst);
    casFeatCode_PropertiesKeys  = (null == casFeat_PropertiesKeys) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_PropertiesKeys).getCode();

 
    casFeat_PropertiesValues = jcas.getRequiredFeatureDE(casType, "PropertiesValues", "uima.cas.StringArray", featOkTst);
    casFeatCode_PropertiesValues  = (null == casFeat_PropertiesValues) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_PropertiesValues).getCode();

  }
}



    