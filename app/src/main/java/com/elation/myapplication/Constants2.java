package com.elation.myapplication;

public class Constants2 {

    public static final class HTTP {
        public static final String BASE_Url="http://japware.com/";
    }

    public static final class DATABASE2 {

        public static final String DB_NAME = "MyDBNames.db";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "contactsss";

        public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

        public static final String GET_FLOWERS_QUERY = "SELECT * FROM " + TABLE_NAME;

        public static final String CONTACT_IDS = "id";
        public static final String MOBILENUMBERS = "mobile_number";



        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + CONTACT_IDS + " TEXT not null,"
                + MOBILENUMBERS + " TEXT not null)";
    }

    public static final class REFERENCE {
        public static final String FLOWER = Config.PACKAGE_NAME + "myapplication";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.example.";
    }
}

