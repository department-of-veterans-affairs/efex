package listeners

import gov.va.vinci.leo.listener.SimpleCsvListener


String csvDir =   "data/output/csv/SimpleCsvOutput.csv"
if(!(new File(csvDir)).exists()) (new File(csvDir).getParentFile()).mkdirs()

listener = new SimpleCsvListener(new File(csvDir))
listener.setIncludeFeatures(true)
listener.setInputType("gov.va.vinci.ef.types.Relation")