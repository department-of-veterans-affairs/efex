package readers

import gov.va.vinci.leo.cr.FileCollectionReader;
String pathToFiles = "data/test/"

boolean recurse = false

reader = new FileCollectionReader(new File(pathToFiles), recurse);