package com.codecool.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommonDAO {


    // SELECT
    protected List<Map<String, String>> executeSQLSelect(Connection connection, String sqlString, Object sqlArg) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData = null;

        List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
        Map<String, String> rowMap;

        try {
            preparedStatement = connection.prepareStatement(sqlString);
            if (sqlArg != null) {
                if (sqlArg instanceof Integer) {
                    preparedStatement.setInt(1, (Integer) sqlArg);
                } else if (sqlArg instanceof String) {
                    preparedStatement.setString(1, (String) sqlArg);
                }
            }
            resultSet = preparedStatement.executeQuery();
            metaData = resultSet.getMetaData();

            while(resultSet.next()) {
                rowMap = new HashMap<String, String>();

                for(int i = 1; i <= metaData.getColumnCount(); i++) {
                    rowMap.put(metaData.getColumnName(i), resultSet.getString(metaData.getColumnName(i)));
                }
                resultList.add(rowMap);
            }
            preparedStatement.close();
            resultSet.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }   
        return resultList;
    }

    protected List<Map<String, String>> executeSQLSelect(Connection connection, String sqlString) {

        return executeSQLSelect(connection, sqlString, null);
    }

    // INSERT, UPDATE
    protected int executeSQLUpdateDB(Connection connection, String sqlString, Map<Integer, Object> argsMap) {
        PreparedStatement preparedStatement = null;

        int result = 0;

        try {
            preparedStatement = connection.prepareStatement(sqlString);

            for (Integer key : argsMap.keySet()) {
                if (argsMap.get(key) instanceof Integer) {
                    preparedStatement.setInt(key, (Integer) argsMap.get(key));
                } else if (argsMap.get(key) instanceof String) {
                    preparedStatement.setString(key, (String) argsMap.get(key));
                }
            }

            result = preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }   
        return result;
    }
}