package h2o.common.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.security.support.ToHex;

public final class MessageDigestUtil {
	
	private MessageDigestUtil() {}

	public static String digest(String mdName, String str) {

		try {
			MessageDigest md = MessageDigest.getInstance(mdName);
			return ToHex.byteArrayToHexString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			Tools.log.debug("", e);
			throw ExceptionUtil.toRuntimeException(e);
		}

	}
	

}
