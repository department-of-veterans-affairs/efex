import gov.va.vinci.leo.aucompare.listener.AuSummaryListener
import gov.va.vinci.leo.aucompare.comparators.SpanAuComparator;

HashMap auMap = [ "gov.va.vinci.kttr.types.RefSt_EfValue":"gov.va.vinci.ef.types.Relation"
]

listener = new AuSummaryListener(new SpanAuComparator(auMap, true));