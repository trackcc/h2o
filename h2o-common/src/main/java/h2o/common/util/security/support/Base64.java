package h2o.common.util.security.support;

import h2o.common.util.security.Base64Util;
import h2o.common.util.security.Encryptor;

public class Base64 implements Encryptor {

	private volatile Base64Util b64Util;

	public void setBase64Util(Base64Util b64Util) {
		this.b64Util = b64Util;
	}

	public Base64() {
		this.setBase64Util(new Base64Util());
	}

	public Base64(Base64Util b64Util) {
		this.setBase64Util(b64Util);
	}

	public String enc(String str) {
		return b64Util.encode(str);
	}

}
