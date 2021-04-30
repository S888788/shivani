package com.elation.myapplication;

public class Constants {

    public static final class HTTP {
        public static final String BASE_Url="http://japware.com/";
    }

    public static final class DATABASE {

        public static final String DB_NAME = "MyDBName.db";
        public static final int DB_VERSION = 1;
        public static final String TABLE_NAME = "contacts";
        public static final String CONTACT_ID = "id";
        public static final String MOBILENUMBER = "mobile_number";
        public static final String MaxMessage="5";
        public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;

        public static final String GET_FLOWERS_QUERY = "select "+ CONTACT_ID +", "+ MOBILENUMBER +" from contacts where status=1 limit "+MaxMessage+"";//"select "+ CONTACT_ID +", "+ MOBILENUMBER +" from contacts where status=1";
                //"select "+ CONTACT_ID +", "+ MOBILENUMBER +" from contacts group by "+ CONTACT_ID +", "+ MOBILENUMBER +" HAVING count(*)< 5 and status=1";



        public static final String iswhatapp="iswhatapp";



        public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
                "(" + CONTACT_ID + " TEXT not null,"
                +"status TEXT NOT NULL,"

                + MOBILENUMBER + " TEXT not null)";
//public static final String CREATE_TABLE_QUERY = "CREATE TABLE contacts (ID INTEGER NOT NULL, STATUS INTEGER DEFAULT 0, mobile_number TEXT)";
   }

    public static final class REFERENCE {
        public static final String FLOWER = Config.PACKAGE_NAME + "myapplication";
    }

    public static final class Config {
        public static final String PACKAGE_NAME = "com.example.";
    }
}
