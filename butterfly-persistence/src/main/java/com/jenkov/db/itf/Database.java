/*
    Copyright 2008 Jenkov Development

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/



package com.jenkov.db.itf;

import com.jenkov.db.jdbc.SimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;


/**
 *
 * Signals the type of database a given IPersistenceConfiguration is targeted against.
 * The database type can be used to avoid calling JDBC methods that the given database
 * does not implement, or implements slightly differently than other databases etc.
 *
 * @author Jakob Jenkov - Copyright 2005 Jenkov Development
 */
public class Database {

    public static final Database DEFAULT    = new Database("Default - JDBC Compliant Database", true, true, true);
    public static final Database DAFFODIL   = new Database("DaffodilDB"          , true, true, true);   //true, false, true ??
    public static final Database DERBY      = new Database("Apache Derby"        , true, true, false);
    public static final Database HSQLDB     = new Database("HSQL Database Engine", true, false, true);
    public static final Database H2         = new Database("H2"                  , false, true, true);
    public static final Database MYSQL      = new Database("MySQL"               , true, true, true);
    public static final Database POSTGRESQL = new Database("PostgreSQL"          , true, false, true);
    public static final Database FIREBIRD   = new Database("Firebird"            , true, false, true);

    //todo add static factory method that can determine the type of a database from a connection.
    protected String  name                                                         = null;
    protected boolean isPreparedStatementParameterCountSupported                   = true;
    protected boolean isPrepareStatementStatement_RETURN_GENERATED_KEYS_supported  = true;
    protected boolean isResultSetGetRowSupported                                   = true;

    public Database(String name, boolean preparedStatementParameterCountSupported, boolean prepareStatementStatement_RETURN_GENERATED_KEYS_supported, boolean resultSetGetRowSupported) {
        this.name = name;
        isPreparedStatementParameterCountSupported = preparedStatementParameterCountSupported;
        isPrepareStatementStatement_RETURN_GENERATED_KEYS_supported = prepareStatementStatement_RETURN_GENERATED_KEYS_supported;
        isResultSetGetRowSupported = resultSetGetRowSupported;
    }

    public String getName(){
        return this.name;
    }

    public boolean isPreparedStatementParameterCountSupported() {
        return isPreparedStatementParameterCountSupported;
    }

    public boolean isPrepareStatementStatement_RETURN_GENERATED_KEYS_supported() {
        return isPrepareStatementStatement_RETURN_GENERATED_KEYS_supported;
    }

    public boolean isResultSetGetRowSupported() {
        return isResultSetGetRowSupported;
    }


    public String toString() {
        return this.name;
    }

    public boolean equals(Object o){
        return o == this;
    }


    public static void  setDatabaseOnConfiguration(IPersistenceConfiguration configuration, Connection connection){
        synchronized(configuration){
            if(configuration.getDatabase() == null){
                configuration.setDatabase(determineDatabase(connection));
            }
        }
    }

    public static Database determineDatabase(Connection connection){
        String databaseName = "JDBC-Compliant";
        try {
            databaseName = connection.getMetaData().getDatabaseProductName();
//            System.out.println("databaseName = " + databaseName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(databaseName == null) return DEFAULT;

        if("Apache Derby"        .equals(databaseName)) return DERBY;
        if("DaffodilDB"          .equals(databaseName)) return DAFFODIL;
        if("HSQL Database Engine".equals(databaseName)) return HSQLDB;
        if("H2"                  .equals(databaseName)) return H2;
        if("MySQL"               .equals(databaseName)) return MYSQL;
        if("PostgreSQL"          .equals(databaseName)) return POSTGRESQL;
        if(databaseName.startsWith("Firebird")) return FIREBIRD;

        return DEFAULT;
    }




}
