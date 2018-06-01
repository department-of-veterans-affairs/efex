package listeners

import gov.va.vinci.knowtator.listener.KnowtatorListener
import gov.va.vinci.knowtator.model.KnowtatorToUimaTypeMap
import gov.va.vinci.leo.tools.LeoUtils

String timeStamp = LeoUtils.getTimestampDateDotTime().replaceAll("[.]", "_")

String projectName = "Project_"+timeStamp;
String eHostWorkSpaceName="eHOST_project/"
if(!(new File(eHostWorkSpaceName)).exists()) { new File (eHostWorkSpaceName).mkdirs()}

KnowtatorToUimaTypeMap knowtatorToUimaTypeMap = new KnowtatorToUimaTypeMap();

knowtatorToUimaTypeMap.addAnnotationTypeMap("Relation", "gov.va.vinci.ef.types.Relation");
knowtatorToUimaTypeMap.addFeatureTypeMap("Relation", "Value1", "Value1");

listener = new KnowtatorListener( projectName,  eHostWorkSpaceName, eHostWorkSpaceName,  knowtatorToUimaTypeMap)
listener.setAnnotationTypeFilter("gov.va.vinci.ef.types.Relation")

