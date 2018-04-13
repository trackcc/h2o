/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package h2o.common.dao.util.namedparam;

import h2o.common.thirdparty.spring.util.Assert;

import java.util.HashMap;
import java.util.Map;



/**
 * Abstract base class for {@link SqlParameterSource} implementations.
 * Provides registration of SQL types per parameter.
 *
 * @author Juergen Hoeller
 * @since 2.0
 */

@SuppressWarnings({"unchecked","rawtypes"})
public abstract class AbstractSqlParameterSource implements SqlParameterSource {

	private final Map sqlTypes = new HashMap();

	private final Map typeNames = new HashMap();


	/**
	 * Register a SQL type for the given parameter.
	 * @param paramName the name of the parameter
	 * @param sqlType the SQL type of the parameter
	 */
	public void registerSqlType(String paramName, int sqlType) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.sqlTypes.put(paramName, new Integer(sqlType));
	}

	/**
	 * Register a SQL type for the given parameter.
	 * @param paramName the name of the parameter
	 * @param typeName the type name of the parameter
	 */
	public void registerTypeName(String paramName, String typeName) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.typeNames.put(paramName, typeName);
	}

	/**
	 * Return the SQL type for the given parameter, if registered.
	 * @param paramName the name of the parameter
	 * @return the SQL type of the parameter,
	 * or <code>TYPE_UNKNOWN</code> if not registered
	 */
	public int getSqlType(String paramName) {
		Assert.notNull(paramName, "Parameter name must not be null");
		Integer sqlType = (Integer) this.sqlTypes.get(paramName);
		if (sqlType != null) {
			return sqlType.intValue();
		}
		return TYPE_UNKNOWN;
	}

	/**
	 * Return the type name for the given parameter, if registered.
	 * @param paramName the name of the parameter
	 * @return the type name of the parameter,
	 * or <code>null</code> if not registered
	 */
	public String getTypeName(String paramName) {
		Assert.notNull(paramName, "Parameter name must not be null");
		return (String) this.typeNames.get(paramName);
	}

}