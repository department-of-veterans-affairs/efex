package readers

import gov.va.vinci.knowtator.cr.KnowtatorCollectionReader
import gov.va.vinci.knowtator.model.KnowtatorToUimaTypeMap;

def knowtatorToUimaTypeMap = { ->
    KnowtatorToUimaTypeMap map = new KnowtatorToUimaTypeMap();
    // map.addAnnotationTypeMap(String knowtatorType, String uimaType)
    map.addAnnotationTypeMap("EjectionFraction_Value", "gov.va.vinci.kttr.types.RefSt_EfValue")
    // .................
    // map.addFeatureTypeMap(String knowtatorType, String knowtatorFeatureName, String uimaFeatureName)
    // ..................
    return map
}

knowtatorCorpusPath = "data/ReferenceStandard/corpus/"
knowtatorXmlPath = "data/ReferenceStandard/saved/"

reader = new KnowtatorCollectionReader(new File(knowtatorCorpusPath), new File(knowtatorXmlPath),
        knowtatorToUimaTypeMap(), true).produceCollectionReader()