package com.tco.database;

import com.tco.misc.*;
import com.tco.database.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {
    private static final String COLUMNS = "id,name,municipality,region,country,latitude,longitude,altitude,type";
    
    private static ResultSet performQuery(String sql) throws Exception {
        String url      = Credential.url();
        String user     = Credential.USER;
        String password = Credential.PASSWORD;
        try (
              // connect to the database and query
              Connection conn = DriverManager.getConnection(url, user, password);
              Statement query = conn.createStatement();
        ) {
            ResultSet results = query.executeQuery(sql);
            return results;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Integer found(String sql) throws Exception {
        ResultSet results = performQuery(sql);
        if (!results.next()) {
            throw new Exception("No count results in found query.");
        }
        return results.getInt("count");
    }


    public static Places places(String sql) throws Exception {
        ResultSet results = performQuery(sql);
        String columns = COLUMNS;
        int count = 0;
        String[] cols = columns.split(",");
        Places places = new Places();
        while (results.next()) {
            Place place = new Place();
            for (String col : cols) {
                place.put(col, results.getString(col));
            }
            place.put("index", String.format("%d", ++count));
            places.add(place);
        }
        return places;
    }

    public static int count(String sql) {
        int count = 0;
        try {
            ResultSet rs = performQuery(sql);
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (Exception e) {
            System.err.println("Error executing count query: " + e.getMessage());
        }
        return count;
    }
}
