package com.watshoulditake.waltermao.coursesapp.utils;

public interface JSONKeys {
    String DATA = "data";
    String META = "meta";
    String STATUS = "status";

    // COURSE
    String SUBJECT = "subject";
    String CATALOG_NUMBER = "catalog_number";
    String TITLE = "title";
    String UNITS = "units";
    String DESCRIPTION = "description";
    String INSTRUCTIONS = "instructions";
    String PREREQS = "prerequisites";
    String ANTIREQS = "antirequisites";
    String TERMS_OFFERED = "terms_offered";
    String NOTES = "notes";
    String OFFERINGS = "offerings";
    String ONLINE = "online";
    String URL = "url";
    String PREREQS_PARSED = "prerequisites_parsed";

    // SCHEDULE
    String SECTION = "section";
    String CAMPUS = "campus";
    String CAPACITY = "enrollment_capacity";
    String OCCUPIED = "enrollment_total";
    String CLASSES = "classes";
    String DATE = "date";
    String START_TIME = "start_time";
    String END_TIME = "end_time";
    String WEEKDAYS = "weekdays";
    String LOCATION = "location";
    String BUILDING = "building";
    String ROOM = "room";
    String INSTRUCTORS = "instructors";
    String TERM = "term";
}