

/* First created by JCasGen Tue Jun 28 15:42:22 CDT 2016 */
package gov.va.vinci.ef.types;

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
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Jun 28 15:42:22 CDT 2016
 * XML source: C:/Users/VHASLC~3/AppData/Local/Temp/leoTypeDescription_8508a9f0-8c28-4118-921f-f59dc97ac5a96406657150654531677.xml
 * @generated */
public class Regex extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Regex.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Regex() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Regex(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Regex(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Regex(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: pattern

  /** getter for pattern - gets 
   * @generated
   * @return value of the feature 
   */
  public String getPattern() {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_pattern == null)
      jcasType.jcas.throwFeatMissing("pattern", "gov.va.vinci.ef.types.Regex");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Regex_Type)jcasType).casFeatCode_pattern);}
    
  /** setter for pattern - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setPattern(String v) {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_pattern == null)
      jcasType.jcas.throwFeatMissing("pattern", "gov.va.vinci.ef.types.Regex");
    jcasType.ll_cas.ll_setStringValue(addr, ((Regex_Type)jcasType).casFeatCode_pattern, v);}    
   
    
  //*--------------*
  //* Feature: concept

  /** getter for concept - gets 
   * @generated
   * @return value of the feature 
   */
  public String getConcept() {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_concept == null)
      jcasType.jcas.throwFeatMissing("concept", "gov.va.vinci.ef.types.Regex");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Regex_Type)jcasType).casFeatCode_concept);}
    
  /** setter for concept - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setConcept(String v) {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_concept == null)
      jcasType.jcas.throwFeatMissing("concept", "gov.va.vinci.ef.types.Regex");
    jcasType.ll_cas.ll_setStringValue(addr, ((Regex_Type)jcasType).casFeatCode_concept, v);}    
   
    
  //*--------------*
  //* Feature: groups

  /** getter for groups - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getGroups() {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_groups == null)
      jcasType.jcas.throwFeatMissing("groups", "gov.va.vinci.ef.types.Regex");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Regex_Type)jcasType).casFeatCode_groups)));}
    
  /** setter for groups - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setGroups(StringArray v) {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_groups == null)
      jcasType.jcas.throwFeatMissing("groups", "gov.va.vinci.ef.types.Regex");
    jcasType.ll_cas.ll_setRefValue(addr, ((Regex_Type)jcasType).casFeatCode_groups, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for groups - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getGroups(int i) {
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_groups == null)
      jcasType.jcas.throwFeatMissing("groups", "gov.va.vinci.ef.types.Regex");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Regex_Type)jcasType).casFeatCode_groups), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Regex_Type)jcasType).casFeatCode_groups), i);}

  /** indexed setter for groups - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setGroups(int i, String v) { 
    if (Regex_Type.featOkTst && ((Regex_Type)jcasType).casFeat_groups == null)
      jcasType.jcas.throwFeatMissing("groups", "gov.va.vinci.ef.types.Regex");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Regex_Type)jcasType).casFeatCode_groups), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Regex_Type)jcasType).casFeatCode_groups), i, v);}
  }

    