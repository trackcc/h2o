package h2o.common.util.bean.serialize;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BeanEncoder implements BeanSerialize {

	private String sharsetName = "UTF-8";

	public BeanEncoder setSharsetName(String sharsetName) {
		this.sharsetName = sharsetName;
		return this;
	}

	public byte[] bean2bytes(Object bean) {

		try {

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bao);
			out.writeObject(bean);

			out.close();

			return bao.toByteArray();

		} catch (Exception e) {
			Tools.log.debug(e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}

	public Object bytes2bean(byte[] bs) {

		try {

			Object obj = null;
			ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bs));

			try {
				obj = oin.readObject();
			} finally {
				if (oin != null) {
					oin.close();
				}
			}

			return obj;

		} catch (Exception e) {
			Tools.log.debug(e);
			throw ExceptionUtil.toRuntimeException(e);
		}

	}

	public String bean2xml(Object bean) {

		try {

			ByteArrayOutputStream bao = new ByteArrayOutputStream();

			XMLEncoder xe = new XMLEncoder(bao);
			xe.writeObject(bean);
			xe.close();

			return new String(bao.toByteArray(), sharsetName);

		} catch (Exception e) {
			Tools.log.debug(e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}

	public Object xml2bean(String xml) {

		try {

			ByteArrayInputStream bai = new ByteArrayInputStream(xml.getBytes(sharsetName));

			XMLDecoder xd = new XMLDecoder(bai);
			Object bean = xd.readObject();
			xd.close();

			return bean;

		} catch (Exception e) {
			Tools.log.debug(e);
			throw ExceptionUtil.toRuntimeException(e);
		}
	}

}
