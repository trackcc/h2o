package h2o.common.util.bean.serialize;


public interface BeanSerialize {
	
	byte[] bean2bytes(Object bean);

	Object bytes2bean(byte[] bs);
}
