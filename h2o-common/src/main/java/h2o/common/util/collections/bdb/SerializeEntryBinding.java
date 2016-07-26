package h2o.common.util.collections.bdb;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.bean.serialize.BeanEncoder;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.je.DatabaseEntry;

public class SerializeEntryBinding<E> implements EntryBinding<E> {
	
	private static final BeanEncoder beanEncoder = new BeanEncoder();

	@SuppressWarnings("unchecked")
	public E entryToObject(DatabaseEntry databaseentry) {

		try {
			return (E) beanEncoder.bytes2bean(databaseentry.getData());
		} catch (Exception e) {
			Tools.log.debug("entryToObject", e);
			throw ExceptionUtil.toRuntimeException(e);
		}

	}

	public void objectToEntry(E obj, DatabaseEntry databaseentry) {
		try {
			databaseentry.setData(beanEncoder.bean2bytes(obj));
		} catch (Exception e) {
			Tools.log.debug("objectToEntry", e);
			throw ExceptionUtil.toRuntimeException(e);
		}

	}

}
