package h2o.common.util.bean.serialize;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;

import java.io.ByteArrayOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoEncoder implements BeanSerialize {

	public final Kryo kryo;
	
	public KryoEncoder() {
		this.kryo = new Kryo();
	}

	public KryoEncoder(Kryo kryo) {
		this.kryo = kryo;
	}

	public byte[] bean2bytes(Object bean) {

		Output out = null;
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			out = new Output(bao);
			kryo.writeClassAndObject(out, bean);

			return out.toBytes();

		} catch (Exception e) {
			Tools.log.debug(e);
			throw ExceptionUtil.toRuntimeException(e);
		} finally {
			if( out != null ) {
				out.close();
			}
		}
	}

	public Object bytes2bean(byte[] bs) {

		Input in = null;
		try {

			Object obj = null;

			in = new Input(bs);
			obj = kryo.readClassAndObject(in);

			return obj;

		} catch (Exception e) {
			Tools.log.debug(e);
			throw ExceptionUtil.toRuntimeException(e);
		} finally {
			if( in != null ) {
				in.close();
			}
		}

	}

}
