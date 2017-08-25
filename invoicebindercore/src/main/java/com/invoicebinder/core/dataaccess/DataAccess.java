/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.invoicebinder.core.dataaccess;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author mon2
 */
public class DataAccess {
    private String username;
    private String password;
    private String hostname;
    private String dbname;
    private String dbport;
    
    public DataAccess() {
    }
    
    public DataAccess(String dbUser, String dbPasswd, String dbHost, String dbPort) {
        username = dbUser;
        password = dbPasswd;
        hostname = dbHost;
        dbport = dbPort;
    }
    
    public DataAccess(String dbUser, String dbPasswd, String dbHost, String dbPort, String dbName) {
        username = dbUser;
        password = dbPasswd;
        hostname = dbHost;
        dbport = dbPort;
        dbname = dbName;
    }
    
    public Connection GetMySQLDatabaseConnection() throws SQLException, IOException {
        Connection conn;
        
        try {
            String url = "jdbc:mysql://" + hostname + ":3306";
            if (dbname != null) {
                url += "/" + dbname; 
                        
            }
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        catch (SQLException e) {
            throw e;
        }
    }
    
    public void CloseConnection(Connection conn) throws SQLException {
        try {
            if (!conn.isClosed()){
                conn.close();
            }
        }
        catch (SQLException ex) {
            throw ex;
        }
    }   
    
    public ResultSet executeQuery(Connection conn, String sql) throws SQLException {
        Statement statement;
        ResultSet resultset;
        statement = conn.prepareStatement(sql);
        resultset = statement.executeQuery(sql);
        return resultset;
    }
    
    public int createStatement(Connection conn, String sql) throws SQLException {
        Statement s = conn.createStatement();
        return s.executeUpdate(sql);
    }
    
    public int executeUpdate(Connection conn, String sql, List<Parameter> params) throws SQLException {
        Integer index = 1;
        PreparedStatement statement;
        statement = conn.prepareStatement(sql);
        
        if (params != null) {
            for (Parameter param : params) {
                switch(param.getParameterType()) {
                    case String: statement.setString(index, (String)param.getParameterValue());
                    break;
                    case Integer: statement.setInt(index, (Integer)param.getParameterValue());
                    break;
                    case Boolean: statement.setBoolean(index, (Boolean)param.getParameterValue());
                    break;
                    case Date: statement.setDate(index, (Date)param.getParameterValue());
                    break;
                }
                index++;
            }
        }
        
        return statement.executeUpdate();
    }
    
    public int executeUpdate(Connection conn, String sql) throws SQLException {
        return executeUpdate(conn, sql, null);
    }
    
    public void executeStoredProcedure(Connection conn, String sql) throws SQLException {
        executeStoredProcedure(conn, sql, null, null, null);
    }
    
    public StoredProcResult executeStoredProcedure(Connection conn, String sql, List<Parameter> params, List<Parameter> outParams, Parameter retValue) throws SQLException {
        CallableStatement cstmt = conn.prepareCall(sql);
        StoredProcResult result = new StoredProcResult();
        Integer index = 1;
        

        
        try { 
            //return parameter
            switch(retValue.getParameterType()) {
                case String: cstmt.registerOutParameter(index, java.sql.Types.VARCHAR);
                break;
                case Integer: cstmt.registerOutParameter(index, java.sql.Types.INTEGER);
                break;
                case Boolean: cstmt.registerOutParameter(index, java.sql.Types.BIT);
                break;
                case Date: cstmt.registerOutParameter(index, java.sql.Types.DATE);
                break;
            }
            index++;   
        
            //input parameters
            for (Parameter param : params) {
                switch(param.getParameterType()) {
                    case String: cstmt.setString(index, (String)param.getParameterValue());
                    break;
                    case Integer: cstmt.setInt(index, (Integer)param.getParameterValue());
                    break;
                    case Boolean: cstmt.setBoolean(index, (Boolean)param.getParameterValue());
                    break;
                    case Date: cstmt.setDate(index, (Date)param.getParameterValue());
                    break;
                }
                index++;
            }
            
            //output parameters
            for (Parameter param : outParams) {
                switch(param.getParameterType()) {
                    case String: cstmt.registerOutParameter(index, java.sql.Types.VARCHAR);
                    break;
                    case Integer: cstmt.registerOutParameter(index, java.sql.Types.INTEGER);
                    break;
                    case Boolean: cstmt.registerOutParameter(index, java.sql.Types.BIT);
                    break;
                    case Date: cstmt.registerOutParameter(index, java.sql.Types.DATE);
                    break;
                }
                index++;
            }
            cstmt.execute();
            
            switch(retValue.getParameterType()) {
                case String: retValue.setParameterValue(cstmt.getString(1));
                break;
                case Integer: retValue.setParameterValue(cstmt.getInt(1));
                break;
                case Boolean: retValue.setParameterValue(cstmt.getBoolean(1));
                break;
                case Date: retValue.setParameterValue(cstmt.getDate(1));
                break;
            }
                        
            index = index - outParams.size();
            for (Parameter param : outParams) {
                switch(param.getParameterType()) {
                    case String: param.setParameterValue(cstmt.getString(index));
                    break;
                    case Integer: param.setParameterValue(cstmt.getInt(index));
                    break;
                    case Boolean: param.setParameterValue(cstmt.getBoolean(index));
                    break;
                    case Date: param.setParameterValue(cstmt.getDate(index));
                    break;
                }
                index++;
            }
                   
            
            result.setOutputParams(outParams);
            result.setReturnParam(retValue);

            return result;
        }
        catch (SQLException ex) {
            throw ex;
        }
        finally {
            cstmt.close();
        }
    }    
}
