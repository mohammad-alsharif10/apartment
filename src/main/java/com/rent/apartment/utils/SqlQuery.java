package com.rent.apartment.utils;

public class SqlQuery {

    public static String insertImageToApartment(Long apartmentId, String path) {
        return "insert into apartment.image(is_current_profile_image, path,apartment_id)" + "VALUES (0," +
                "'" + path + "'" + "," +
                apartmentId + ")";
    }
}
