package listeners

import gov.va.vinci.ef.listeners.CSVListener

String filePath = "data/output/csv/CsvOutput.csv";
fieldList = [
        ["DocID", "0", "bigint"],
        ["Term", "-1", "varchar(500)"],
        ["Value", "-1", "varchar(100)"],
        ["Value2", "-1", "varchar(100)"],
        ["ValueString", "-1", "varchar(100)"],
        ["Snippets", "-1", "varchar(8000)"],
        ["SpanStart", "-1", "int"],
        ["SpanEnd", "-1", "int"]
]
incomingTypes = "gov.va.vinci.ef.types.Relation"
listener = CSVListener.createNewListener(filePath, fieldList, incomingTypes);
listener.writeHeaders();