package com.tco.database;

import java.util.List;

public class Select {
    private static final String TABLE = "world w INNER JOIN region r ON w.iso_region = r.id INNER JOIN country c ON w.iso_country = c.id";
    private static final String COLUMNS = "w.id, w.name, w.municipality, w.latitude, w.longitude, w.altitude, w.type, r.name AS region, c.name AS country";

    public static String searchNearby(double latMin, double latMax, double lonMin, double lonMax, double distance) {
        String whereClause = buildWhereClause(latMin, latMax, lonMin, lonMax, distance);
        return "SELECT " + COLUMNS + " FROM " + TABLE + " " + whereClause + ";";
    }

    public static String match(String match, List<String> type, List<String> where, int limit) {
        String whereClause = buildWhereClause(match, type, where);
        return "SELECT " + COLUMNS + " FROM " + TABLE + " " + whereClause + " LIMIT " + limit + ";";
    }

    public static String found(String match, List<String> type, List<String> where) {
        String whereClause = buildWhereClause(match, type, where);
        return "SELECT COUNT(*) as count FROM " + TABLE + " " + whereClause + ";";
    }

    private static String buildWhereClause(String match, List<String> type, List<String> where) {
        StringBuilder whereClause = new StringBuilder();
        String matchFormatted = "%" + match + "%";

        whereClause.append("WHERE (w.id = '" + match + "' OR ")  // Add exact ID match
        .append("LOWER(w.name) LIKE LOWER('" + matchFormatted + "') ")
        .append("OR LOWER(w.municipality) LIKE LOWER('" + matchFormatted + "') ")
        .append("OR LOWER(r.name) LIKE LOWER('" + matchFormatted + "') ")
        .append("OR LOWER(c.name) LIKE LOWER('" + matchFormatted + "')) ");

        if (type != null && !type.isEmpty()) {
            whereClause.append("AND (");
            for (int i = 0; i < type.size(); i++) {
                if (i > 0) {
                    whereClause.append(" OR ");
                }
                whereClause.append("w.type LIKE '%").append(type.get(i).replace("'", "''")).append("%'");
            }
            whereClause.append(") ");
        }

        if (where != null && !where.isEmpty()) {
            whereClause.append("AND (");
            for (int i = 0; i < where.size(); i++) {
                String currentWhere = where.get(i).replace("'", "''");
                whereClause.append("(w.municipality LIKE LOWER('%" + currentWhere + "%') ")
                        .append("OR c.name LIKE LOWER('%" + currentWhere + "%') ")
                        .append("OR r.name LIKE LOWER('%" + currentWhere + "%'))");

                if (i < where.size() - 1) {
                    whereClause.append(" OR ");
                }
            }
            whereClause.append(") ");
        }

        return whereClause.toString();
    }


    // Overload buildWhereClause for near
    private static String buildWhereClause(double latMin, double latMax, double lonMin, double lonMax,
            double distance) {
        StringBuilder whereClause = new StringBuilder();
        whereClause.append("WHERE w.latitude BETWEEN " + latMin + " AND " + latMax);

        // If we're very close to poles (e.g., |lat| > 89), don't restrict longitude
        boolean isNearPole = Math.abs(latMin) > 89 || Math.abs(latMax) > 89;
        if (!isNearPole) {
            whereClause.append(" AND w.longitude BETWEEN " + lonMin + " AND " + lonMax);
        }

        return whereClause.toString();
    }
}