:{selSeq=SELECT SEQNO FROM SYS_VERSION WHERE SEQOBJ = ? };
:{insSeq=insert into SYS_VERSION ( SEQOBJ , SEQNO ) values ( ? , ? )};
:{updSeq=update SYS_VERSION set SEQNO = ?  where SEQOBJ = ?  and SEQNO = ? };