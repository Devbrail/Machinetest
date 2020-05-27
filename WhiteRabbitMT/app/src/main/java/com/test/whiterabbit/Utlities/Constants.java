package com.test.whiterabbit.Utlities;

import com.example.whiterabbitmt.BuildConfig;

public class Constants {

    public interface ApiFlags {

        public static final int getEmployees = 1;

    }

    public interface ApiPaths {

        public static final String baseURL = "http://www.mocky.io/v2/";
        public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;

    }

    public interface Db {
        public static final String DATABASE_NAME = "employeesManager";
        public static final String TABLE_employeeS = "employees";

        // Columns
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_PROFILE_URL = "profile_image";
        public static final String COMPANY_NAME = "company";

        public static final  String EMAIL = "email";
        String ADDR = "address";
        String PHONE = "phone";
        String UNAME = "user";
        String WEB = "website";
    }

    public interface SharedPref {

        public static final String HAS_OFFLINE = "isLoaded";

    }
}
