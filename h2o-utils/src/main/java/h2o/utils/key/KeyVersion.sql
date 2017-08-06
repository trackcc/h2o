:{selseq=select seqno from sys_version where seqobj = :seqobj };
:{insseq=insert into sys_version ( seqobj , seqno ) values ( :seqobj , 0 )};
:{updseq=update sys_version set seqno = seqno + 1 where seqobj = :seqobj };