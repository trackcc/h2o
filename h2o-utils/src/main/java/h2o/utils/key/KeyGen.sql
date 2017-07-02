:{selseq=select cyclicspace ,  seqno from sys_cycseq where seqobj = ? };
:{insseq=insert into sys_cycseq ( seqobj , cyclicspace ,  seqno ) values ( ? , ? , ? ) };
:{updseq=update sys_cycseq set seqno = ? , cyclicspace = ?  where seqobj = ?  and seqno = ? and cyclicspace = ? };