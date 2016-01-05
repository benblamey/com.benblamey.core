package com.benblamey.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Various utility methods for converting MySQL results into plain Java types.
 * Used for dumping / logging.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class SQLUtils {

    public static List<Map<String, Object>> getAllResultData(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        List<Map<String, Object>> result = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> row = getResultRowInner(resultSet, metaData);
            result.add(row);
        }

        return result;
    }

    public static Map<String, Object> getResultRow(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        return getResultRowInner(resultSet, metaData);
    }

    private static Map<String, Object> getResultRowInner(ResultSet resultSet, ResultSetMetaData metaData) throws SQLException {
        HashMap<String, Object> row = new HashMap<>();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            row.put(columnName, resultSet.getObject(i));
        }
        return row;
    }

}
