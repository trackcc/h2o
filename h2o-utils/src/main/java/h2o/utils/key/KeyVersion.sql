:{selseq=select seqno from sys_version where seqobj = ? };
:{insseq=insert into sys_version ( seqobj , seqno ) values ( ? , ? )};
:{updseq=update sys_version set seqno = ?  where seqobj = ?  and seqno = ? };