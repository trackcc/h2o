:{selSeq=SELECT CYCLICSPACE ,  SEQNO FROM SYS_CYCSEQ WHERE SEQOBJ = ? };
:{insSeq=insert into SYS_CYCSEQ ( SEQOBJ , CYCLICSPACE ,  SEQNO ) values ( ? , ? , ? ) };
:{updSeq=update SYS_CYCSEQ set SEQNO = ? , CYCLICSPACE = ?  where SEQOBJ = ?  and SEQNO = ? and CYCLICSPACE = ? };