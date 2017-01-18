package readers

import gov.va.vinci.leo.cr.BatchDatabaseCollectionReader;
String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
String url = "jdbc:sqlserver://<database_engine>:1433;databasename=<database_name>;integratedSecurity=true"
String username="";
String password="";
String query = "SELECT  DocID, DocText   FROM <database_name>.<schema>.<table_name> where RowNo > {min} and RowNo < {max}"

int startingIndex = 0;
int endingIndex = 10;
int batch_size = 10000;

reader = new BatchDatabaseCollectionReader(
        driver,
        url,
        username,
        password,
        query,
        "docid","doctext",   /// Make sure that the names of the fields are low case.
        startingIndex , endingIndex
        , batch_size)