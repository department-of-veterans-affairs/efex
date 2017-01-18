import gov.va.vinci.leo.aucompare.listener.AuCompareCSVListener
import gov.va.vinci.leo.aucompare.comparators.SpanAuComparator
import gov.va.vinci.leo.tools.LeoUtils;
HashMap auMap = [ "gov.va.vinci.kttr.types.RefSt_EfValue":"gov.va.vinci.ef.types.Relation"
]

String timeStamp = LeoUtils.getTimestampDateDotTime().replaceAll("[.]", "_")
String outPath =  "data/output/auCompare.csv"

listener = new AuCompareCSVListener(new File (outPath), new SpanAuComparator(auMap, true));