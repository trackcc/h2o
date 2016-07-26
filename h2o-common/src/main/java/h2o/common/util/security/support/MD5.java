package h2o.common.util.security.support;

import h2o.common.util.security.Encryptor;
import h2o.common.util.security.MessageDigestUtil;



public class MD5 implements Encryptor {

	public String enc(String str) {
		return MessageDigestUtil.digest("MD5", str);
	}

}
