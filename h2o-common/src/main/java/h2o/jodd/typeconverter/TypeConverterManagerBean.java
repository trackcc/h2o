// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package h2o.jodd.typeconverter;

import h2o.jodd.datetime.JDateTime;
import h2o.jodd.mutable.*;
import h2o.jodd.typeconverter.impl.*;
import h2o.jodd.util.ReflectUtil;
import h2o.jodd.datetime.JDateTime;
import h2o.jodd.mutable.MutableByte;
import h2o.jodd.mutable.MutableDouble;
import h2o.jodd.mutable.MutableFloat;
import h2o.jodd.mutable.MutableInteger;
import h2o.jodd.mutable.MutableLong;
import h2o.jodd.mutable.MutableShort;
import h2o.jodd.typeconverter.impl.ArrayConverter;
import h2o.jodd.typeconverter.impl.BigDecimalConverter;
import h2o.jodd.typeconverter.impl.BigIntegerConverter;
import h2o.jodd.typeconverter.impl.BooleanArrayConverter;
import h2o.jodd.typeconverter.impl.BooleanConverter;
import h2o.jodd.typeconverter.impl.ByteArrayConverter;
import h2o.jodd.typeconverter.impl.ByteConverter;
import h2o.jodd.typeconverter.impl.CalendarConverter;
import h2o.jodd.typeconverter.impl.CharacterArrayConverter;
import h2o.jodd.typeconverter.impl.CharacterConverter;
import h2o.jodd.typeconverter.impl.ClassArrayConverter;
import h2o.jodd.typeconverter.impl.ClassConverter;
import h2o.jodd.typeconverter.impl.CollectionConverter;
import h2o.jodd.typeconverter.impl.DateConverter;
import h2o.jodd.typeconverter.impl.DoubleArrayConverter;
import h2o.jodd.typeconverter.impl.DoubleConverter;
import h2o.jodd.typeconverter.impl.FileConverter;
import h2o.jodd.typeconverter.impl.FloatArrayConverter;
import h2o.jodd.typeconverter.impl.FloatConverter;
import h2o.jodd.typeconverter.impl.IntegerArrayConverter;
import h2o.jodd.typeconverter.impl.IntegerConverter;
import h2o.jodd.typeconverter.impl.JDateTimeConverter;
import h2o.jodd.typeconverter.impl.LocaleConverter;
import h2o.jodd.typeconverter.impl.LongArrayConverter;
import h2o.jodd.typeconverter.impl.LongConverter;
import h2o.jodd.typeconverter.impl.MutableByteConverter;
import h2o.jodd.typeconverter.impl.MutableDoubleConverter;
import h2o.jodd.typeconverter.impl.MutableFloatConverter;
import h2o.jodd.typeconverter.impl.MutableIntegerConverter;
import h2o.jodd.typeconverter.impl.MutableLongConverter;
import h2o.jodd.typeconverter.impl.MutableShortConverter;
import h2o.jodd.typeconverter.impl.ShortArrayConverter;
import h2o.jodd.typeconverter.impl.ShortConverter;
import h2o.jodd.typeconverter.impl.SqlDateConverter;
import h2o.jodd.typeconverter.impl.SqlTimeConverter;
import h2o.jodd.typeconverter.impl.SqlTimestampConverter;
import h2o.jodd.typeconverter.impl.StringArrayConverter;
import h2o.jodd.typeconverter.impl.StringConverter;
import h2o.jodd.typeconverter.impl.TimeZoneConverter;
import h2o.jodd.typeconverter.impl.URIConverter;
import h2o.jodd.typeconverter.impl.URLConverter;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Provides dynamic object conversion to a type.
 * Contains a map of registered converters. User may add new converters.
 * Instantiable version of {@link TypeConverterManager}.
 */
public class TypeConverterManagerBean {

	private final HashMap<Class, TypeConverter> converters = new HashMap<Class, TypeConverter>(70);

	// ---------------------------------------------------------------- converter

	protected ConvertBean convertBean = new ConvertBean();

	/**
	 * Returns {@link ConvertBean}.
	 */
	public ConvertBean getConvertBean() {
		return convertBean;
	}

	// ---------------------------------------------------------------- methods

	public TypeConverterManagerBean() {
		registerDefaults();
	}

	/**
	 * Registers default set of converters.
	 */
	@SuppressWarnings( {"UnnecessaryFullyQualifiedName"})
	public void registerDefaults() {
		register(String.class, new StringConverter());
		register(String[].class, new StringArrayConverter(this));

		IntegerConverter integerConverter = new IntegerConverter();
		register(Integer.class, integerConverter);
		register(int.class, integerConverter);
		register(MutableInteger.class, new MutableIntegerConverter(this));

		ShortConverter shortConverter = new ShortConverter();
		register(Short.class, shortConverter);
		register(short.class, shortConverter);
		register(MutableShort.class, new MutableShortConverter(this));

		LongConverter longConverter = new LongConverter();
		register(Long.class, longConverter);
		register(long.class, longConverter);
		register(MutableLong.class, new MutableLongConverter(this));

		ByteConverter byteConverter = new ByteConverter();
		register(Byte.class, byteConverter);
		register(byte.class, byteConverter);
		register(MutableByte.class, new MutableByteConverter(this));

		FloatConverter floatConverter = new FloatConverter();
		register(Float.class, floatConverter);
		register(float.class, floatConverter);
		register(MutableFloat.class, new MutableFloatConverter(this));

		DoubleConverter doubleConverter = new DoubleConverter();
		register(Double.class, doubleConverter);
		register(double.class, doubleConverter);
		register(MutableDouble.class, new MutableDoubleConverter(this));

		BooleanConverter booleanConverter = new BooleanConverter();
		register(Boolean.class, booleanConverter);
		register(boolean.class, booleanConverter);

		CharacterConverter characterConverter = new CharacterConverter();
		register(Character.class, characterConverter);
		register(char.class, characterConverter);

		register(byte[].class, new ByteArrayConverter(this));
		register(short[].class, new ShortArrayConverter(this));
		register(int[].class, new IntegerArrayConverter(this));
		register(long[].class, new LongArrayConverter(this));
		register(float[].class, new FloatArrayConverter(this));
		register(double[].class, new DoubleArrayConverter(this));
		register(boolean[].class, new BooleanArrayConverter(this));
		register(char[].class, new CharacterArrayConverter(this));

		// we don't really need these, but converters will be cached and not created every time
		register(Integer[].class, new ArrayConverter<Integer>(this, Integer.class) {
			@Override
			protected Integer[] createArray(int length) {
				return new Integer[length];
			}
		});
		register(Long[].class, new ArrayConverter<Long>(this, Long.class) {
			@Override
			protected Long[] createArray(int length) {
				return new Long[length];
			}
		});
		register(Byte[].class, new ArrayConverter<Byte>(this, Byte.class) {
			@Override
			protected Byte[] createArray(int length) {
				return new Byte[length];
			}
		});
		register(Short[].class, new ArrayConverter<Short>(this, Short.class) {
			@Override
			protected Short[] createArray(int length) {
				return new Short[length];
			}
		});
		register(Float[].class, new ArrayConverter<Float>(this, Float.class) {
			@Override
			protected Float[] createArray(int length) {
				return new Float[length];
			}
		});
		register(Double[].class, new ArrayConverter<Double>(this, Double.class) {
			@Override
			protected Double[] createArray(int length) {
				return new Double[length];
			}
		});
		register(Boolean[].class, new ArrayConverter<Boolean>(this, Boolean.class) {
			@Override
			protected Boolean[] createArray(int length) {
				return new Boolean[length];
			}
		});
		register(Character[].class, new ArrayConverter<Character>(this, Character.class) {
			@Override
			protected Character[] createArray(int length) {
				return new Character[length];
			}
		});

		register(MutableInteger[].class, new ArrayConverter<MutableInteger>(this, MutableInteger.class));
		register(MutableLong[].class, new ArrayConverter<MutableLong>(this, MutableLong.class));
		register(MutableByte[].class, new ArrayConverter<MutableByte>(this, MutableByte.class));
		register(MutableShort[].class, new ArrayConverter<MutableShort>(this, MutableShort.class));
		register(MutableFloat[].class, new ArrayConverter<MutableFloat>(this, MutableFloat.class));
		register(MutableDouble[].class, new ArrayConverter<MutableDouble>(this, MutableDouble.class));

		register(BigDecimal.class, new BigDecimalConverter());
		register(BigInteger.class, new BigIntegerConverter());
		register(BigDecimal[].class, new ArrayConverter<BigDecimal>(this, BigDecimal.class));
		register(BigInteger[].class, new ArrayConverter<BigInteger>(this, BigInteger.class));

		register(java.util.Date.class, new DateConverter());
		register(java.sql.Date.class, new SqlDateConverter());
		register(Time.class, new SqlTimeConverter());
		register(Timestamp.class, new SqlTimestampConverter());
		register(Calendar.class, new CalendarConverter());
		register(GregorianCalendar.class, new CalendarConverter());
		register(JDateTime.class, new JDateTimeConverter());

		register(File.class, new FileConverter());

		register(Class.class, new ClassConverter());
		register(Class[].class, new ClassArrayConverter(this));

		register(URI.class, new URIConverter());
		register(URL.class, new URLConverter());

		register(Locale.class, new LocaleConverter());
		register(TimeZone.class, new TimeZoneConverter());
	}

	/**
	 * Registers a converter for specified type.
	 * User must register converter for all super-classes as well.
	 *
	 * @param type		class that converter is for
	 * @param typeConverter	converter for provided class
	 */
	public void register(Class type, TypeConverter typeConverter) {
		convertBean.register(type, typeConverter);
		converters.put(type, typeConverter);
	}

	/**
	 * Un-registers converter for given type.
	 */
	public void unregister(Class type) {
		convertBean.register(type, null);
		converters.remove(type);
	}

	// ---------------------------------------------------------------- lookup

	/**
	 * Retrieves converter for provided type. Only registered types are matched,
	 * therefore subclasses must be also registered.
	 *
	 * @return founded converter or <code>null</code>
	 */
	public TypeConverter lookup(Class type) {
		return converters.get(type);
	}

	// ---------------------------------------------------------------- convert

	/**
	 * Converts an object to destination type. If type is registered, it's
	 * {@link TypeConverter} will be used. If not, it scans of destination is
	 * an array or enum, as those two cases are handled in a special way.
	 * <p>
	 * If destination type is one of common types, consider using {@link Convert}
	 * instead for somewhat faster approach (no lookup).
	 */
	@SuppressWarnings({"unchecked"})
	public <T> T convertType(Object value, Class<T> destinationType) {
		if (destinationType == Object.class) {
			// no conversion :)
			return (T) value;
		}
		TypeConverter converter = lookup(destinationType);

		if (converter != null) {
			return (T) converter.convert(value);
		}

		// no converter

		if (value == null) {
			return null;
		}

		// handle destination arrays
		if (destinationType.isArray()) {
			ArrayConverter<T> arrayConverter = new ArrayConverter(this, destinationType.getComponentType());

			return (T) arrayConverter.convert(value);
		}

		// handle enums
		if (destinationType.isEnum()) {
			Object[] enums = destinationType.getEnumConstants();
			String valStr = value.toString();
			for (Object e : enums) {
				if (e.toString().equals(valStr)) {
					return (T) e;
				}
			}
		}

		// check same instances
		if (ReflectUtil.isInstanceOf(value, destinationType)) {
			return (T) value;
		}

		// collection
		if (ReflectUtil.isTypeOf(destinationType, Collection.class)) {
			// component type is unknown because of Java's type-erasure
			CollectionConverter<T> collectionConverter =
					new CollectionConverter(this, destinationType, Object.class);

			return (T) collectionConverter.convert(value);
		}

		// fail
		throw new TypeConversionException("Conversion failed: " + destinationType.getName());
	}

	/**
	 * Special case of {@link #convertType(Object, Class)} when target is collection and
	 * when component type is known.
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection<T> convertToCollection(Object value, Class<? extends Collection> destinationType, Class componentType) {
		if (value == null) {
			return null;
		}

		// check same instances
		if (ReflectUtil.isInstanceOf(value, destinationType)) {
			return (Collection<T>) value;
		}

		if (componentType == null) {
			componentType = Object.class;
		}

		CollectionConverter collectionConverter = new CollectionConverter(destinationType, componentType);

		return collectionConverter.convert(value);
	}

}