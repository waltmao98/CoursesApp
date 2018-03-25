package com.watshoulditake.waltermao.coursesapp.database;

import java.util.Arrays;
import java.util.List;

public class DBSchema {

    public static final String DB_NAME = "courses.db";
    public static final String TABLE_NAME = "COURSES";

    private DBSchema() {
    }

    public static class Cols {
        static final String COURSE_CODE = "course_code"; // CS246
        static final String TITLE = "title"; // Object Oriented Software Development
        static final String SUBJECT = "subject"; // CS
        static final String CATOLOG_NUMBER = "cat_num"; // 246
        static final String UNITS = "units";
        static final String DESCRIPTION = "description";
        static final String INSTRUCTIONS = "instructions";
        static final String PREREQS_STRING = "prereqs_string"; // english description of prereqs
        static final String ANTIREQS = "antireqs";
        static final String PREREQS_LIST = "prereqs_list"; // list of prereqs in JSON string format
        static final String FUTURE_COURSES_LIST = "future_courses_list";
        static final String TERMS_OFFERED = "terms_offered";
        static final String NOTES = "notes";
        static final String IS_ONLINE = "is_online";
        static final String URL = "url"; // link to UW website for official course info
        static final String FAVOURITE = "favourite";


        static final List<String> ALL_COLS =
                Arrays.asList(
                        COURSE_CODE,
                        TITLE, SUBJECT,
                        CATOLOG_NUMBER,
                        UNITS,
                        DESCRIPTION,
                        INSTRUCTIONS,
                        PREREQS_STRING,
                        ANTIREQS,
                        PREREQS_LIST,
                        FUTURE_COURSES_LIST,
                        TERMS_OFFERED,
                        NOTES,
                        IS_ONLINE,
                        URL,
                        FAVOURITE);

        static final String[] COURSE_SUMMARY_COLS = new String[]{
                DBSchema.Cols.COURSE_CODE,
                DBSchema.Cols.TITLE,
                DBSchema.Cols.SUBJECT,
                DBSchema.Cols.CATOLOG_NUMBER
        };
    }
}
