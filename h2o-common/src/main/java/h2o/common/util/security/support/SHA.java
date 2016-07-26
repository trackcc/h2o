package h2o.common.util.security.support;

import h2o.common.util.security.Encryptor;
import h2o.common.util.security.MessageDigestUtil;



public class SHA implements Encryptor {

	public String enc(String str) {
		return MessageDigestUtil.digest("SHA", str);
	}

}