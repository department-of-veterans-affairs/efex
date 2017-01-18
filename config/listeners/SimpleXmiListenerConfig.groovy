package listeners

import gov.va.vinci.leo.listener.SimpleXmiListener

String xmiDir = "data/output/xmi/"

if(!(new File(xmiDir)).exists()) (new File(xmiDir)).mkdirs()

listener = new SimpleXmiListener(new File(xmiDir))
listener.setLaunchAnnotationViewer(true)  // if true, a Viewer will be displayed after processing

//listener.setAnnotationTypeFilter("gov.va.vinci.ef.types.Relation") // This is optional. If this line is commented out, all documents will be written out.