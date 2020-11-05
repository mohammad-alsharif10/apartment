package com.rent.apartment.utils;

public class SqlQuery {

    public static String insertImageToApartment(Long apartmentId, String path) {
        StringBuilder query = new StringBuilder("insert into apartment.image(is_current_profile_image, path,apartment_id)");
        query.append("VALUES (0,");
        query.append("'").append(path).append("'").append(",");
        query.append(apartmentId).append(")");
        return query.toString();
    }
}
