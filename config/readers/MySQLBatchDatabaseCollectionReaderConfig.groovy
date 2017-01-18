package readers

import gov.va.vinci.leo.cr.BatchDatabaseCollectionReader;

// String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
String driver = "com.mysql.jdbc.Driver";

//String url = "jdbc:sqlserver://<database_engine>:1433;databasename=<database_name>;integratedSecurity=true"
String url = "jdbc:mysql://localhost/demo"
// ""jdbc:sqlserver://<database_engine>:1433;databasename=<database_name>;integratedSecurity=true"

String username = "root";

String password = "";

String query = "select  ROW_ID, `TEXT` from NOTEEVENTS where ROW_ID > {min} and ROW_ID < {max}"

//"select id, document from example_document where row_number >{min} and row_number < {max}"
//"SELECT  DocID, DocText   FROM <database_name>.<schema>.<table_name> where RowNo > {min} and RowNo < {max}"

int minRecordNumber = 0;
int maxRecordNumber = 100000;
int batchSize = 10000;
String idColumn = "row_id";
String noteColumn = "text"

reader = new BatchDatabaseCollectionReader(driver, url, username, password, query,
        idColumn, noteColumn,
        minRecordNumber, maxRecordNumber, batchSize)