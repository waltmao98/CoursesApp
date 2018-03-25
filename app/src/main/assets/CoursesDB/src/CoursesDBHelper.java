import com.google.gson.Gson;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Helper class for accessing the courses.db (COURSES table)
 */
public class CoursesDBHelper {

    /**
     * Sets a set of future courses via SQL update statement
     *
     * @param futureCourses Map<courseCode,list of this course's future courses>
     */
    public static void setFutureCourses(Map<String, List<String>> futureCourses) {
        for (Map.Entry<String, List<String>> entry : futureCourses.entrySet()) {
            setFutureCourse(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Sets future course via SQL update statement
     *
     * @param courseCode
     * @param futureCourses list of the course's future courses
     */
    public static void setFutureCourse(String courseCode, List<String> futureCourses) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            String updateSQL = "UPDATE " + TABLE_NAME +
                    " SET " + Cols.FUTURE_COURSES_LIST + "=?" +
                    " WHERE " + Cols.COURSE_CODE + "=?;";
            PreparedStatement stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, new Gson().toJson(futureCourses));
            stmt.setString(2, courseCode);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Failed to update future courses of " + courseCode);
            e.printStackTrace();
        }
    }

    /**
     * Sets a set of prereqs via SQL update statement
     *
     * @param prereqs Map<course code, json string representation of prereqs>
     */
    public static void setPrereqs(Map<String, String> prereqs) {
        for (Map.Entry<String, String> entry : prereqs.entrySet()) {
            setPrereq(entry.getKey(), entry.getValue());
        }
    }

    /**
     * @param courseCode  course code of the course that has prereqs
     * @param prereqsJSON prereqs of the course in JSON string format
     */
    public static void setPrereq(String courseCode, String prereqsJSON) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            String updateSQL = "UPDATE " + TABLE_NAME +
                    " SET " + Cols.PREREQS_LIST + "=?" +
                    " WHERE " + Cols.COURSE_CODE + "=?;";
            PreparedStatement stmt = conn.prepareStatement(updateSQL);
            stmt.setString(1, prereqsJSON);
            stmt.setString(2, courseCode);
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Failed to update prereqs of " + courseCode);
            e.printStackTrace();
        }
    }

    /**
     * Inserts a set of courses in to the database via SQL insert statement
     *
     * @param courses
     */
    public static void insertCourses(Course... courses) {
        for (Course course : courses) {
            try {
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

                PreparedStatement stmt = conn.prepareStatement(getInsertTemplate());
                stmt = prepareCourseStatement(course, stmt);
                stmt.executeUpdate();
                stmt.close();
                conn.close();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("Failed to insert " + course.getCourseCode());
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the given courses from the database
     *
     * @param courses courses to delete from the database
     */
    public static void deleteItems(Course... courses) {
        String deleteSQL = "";
        for (Course course : courses) {
            deleteSQL = deleteSQL.concat("DELETE FROM " + TABLE_NAME +
                    " WHERE " + Cols.COURSE_CODE + "=" + "\'" + course.getCourseCode() + "\';");
        }
        execUpdateSQL(deleteSQL);
    }

    public static void addColumn(String columnName, String columnType) {
        String addColSQL = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + columnName + " " + columnType + ";";
        execUpdateSQL(addColSQL);
    }

    /**
     * Creates the COURSES table
     */
    public static void createTable() {
        String createTableSQL = getCreateTableSQL();
        execUpdateSQL(createTableSQL);
    }

    /**
     * DELETES THE COURSES TABLE. USE WITH CAUTION!
     */
    public static void dropTable() {
        execUpdateSQL("DROP TABLE " + TABLE_NAME + ";");
    }

    /////////////////////////// DB SCHEMA //////////////////////////////////

    public static final String DB_NAME = "courses.db";
    public static final String TABLE_NAME = "COURSES";

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

        static List<String> getColsList() {
            return Arrays.asList(
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
        }

        private Cols() {
        }
    }

    /////////////////////////// SQL STATEMENT HELPERS ////////////////////

    /**
     * Sets the parameters for a course insert prepared statement
     *
     * @param course course to insert
     * @param stmt   PreparedStatement object used for the insert
     * @return
     * @throws SQLException
     */
    private static PreparedStatement prepareCourseStatement(Course course, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, course.getCourseCode());
        stmt.setString(2, course.getTitle());
        stmt.setString(3, course.getSubject());
        stmt.setString(4, course.getCatalogNumber());
        stmt.setDouble(5, course.getUnits());
        stmt.setString(6, course.getDescription());
        stmt.setString(7, course.getInstructionsJSONString());
        stmt.setString(8, course.getPrereqsString());
        stmt.setString(9, course.getAntiRequisites());
        stmt.setString(10, course.getPrereqsJSONString());
        stmt.setString(11, course.getFutureCoursesJSONString());
        stmt.setString(12, course.getTermsOfferedJSONString());
        stmt.setString(13, course.getNotes());
        stmt.setInt(14, course.isOnline() ? 1 : 0);
        stmt.setString(15, course.getURL());
        stmt.setInt(16, course.isFavourite() ? 1 : 0);
        return stmt;
    }

    /**
     * @return prepared insert statement SQL string: INSERT INTO (column_names...) ... VALUES (?,?,?...?)
     */

    private static String getInsertTemplate() {
        List<String> cols = Cols.getColsList();
        String colsSQL = "INSERT INTO " + TABLE_NAME + " (";
        for (int i = 0; i < cols.size() - 1; ++i) {
            colsSQL = colsSQL.concat(cols.get(i).concat(","));
        }
        colsSQL = colsSQL.concat(cols.get(cols.size() - 1).concat(") VALUES ("));
        for (int i = 0; i < cols.size() - 1; ++i) {
            colsSQL = colsSQL.concat("?,");
        }
        colsSQL = colsSQL.concat("?);");
        return colsSQL;
    }

    private static String getCreateTableSQL() {
        return "CREATE TABLE " + TABLE_NAME +
                "(" + Cols.COURSE_CODE + " TEXT PRIMARY KEY   NOT NULL," +
                Cols.TITLE + " TEXT," +
                Cols.SUBJECT + " TEXT NOT NULL," +
                Cols.CATOLOG_NUMBER + " TEXT NOT NULL," +
                Cols.UNITS + " TEXT," +
                Cols.DESCRIPTION + " TEXT," +
                Cols.INSTRUCTIONS + " TEXT," +
                Cols.PREREQS_STRING + " TEXT," +
                Cols.ANTIREQS + " TEXT," +
                Cols.PREREQS_LIST + " TEXT," +
                Cols.FUTURE_COURSES_LIST + " TEXT," +
                Cols.TERMS_OFFERED + " TEXT," +
                Cols.NOTES + " TEXT," +
                Cols.IS_ONLINE + " INT," +
                Cols.URL + " TEXT," +
                Cols.FAVOURITE + " TEXT);";
    }

    public static ResultSet execQuerySQL(String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            Statement stmt = c.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(sql);
            e.printStackTrace();
            return null;
        }
    }

    private static void execUpdateSQL(String sql) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);

            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(sql);
            e.printStackTrace();
        }
    }

}
