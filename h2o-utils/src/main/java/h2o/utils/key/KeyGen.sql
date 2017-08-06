:{selseq=select cyclicspace ,  seqno from sys_cycseq where seqobj = :seqobj for update };
:{insseq=insert into sys_cycseq ( seqobj , cyclicspace ,  seqno ) values ( :seqobj , :cyclicspace , "0" ) };
:{updseq=update sys_cycseq set seqno = :seqno , cyclicspace = :cyclicspace  where seqobj = :seqobj };