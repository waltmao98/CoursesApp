import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Main method
 */
public class BuildCoursesDB {

    /**
     * UNCOMMENT AND RUN THIS PROGRAM TO DROP PREVIOUS TABLE AND CREATE A NEW FULLY COMPLETE TABLE
     * Note that favourites column was manually added (using ALTER TABLE) after everything else was done
     */
    public static void main(String args[]) {
        /*
        try {
            CoursesDBHelper.dropTable();
            CoursesDBHelper.createTable();
            downloadAllCourses();
            downloadPrereqsAndFutureCourses();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        */
    }

    /**
     * Fetches every course from web server and inserts it into the database
     * Note: doesn't fetch or insert prereqs list or future courses. These should be downloaded separately
     */
    private static void downloadAllCourses() throws CourseServiceException {
        String allCoursesJSON = FetchCoursesData.getAllCoursesJSON();

        List<CourseSummary> courseSummaries = CourseJSONUtils.JSONToCourseSummaryList(allCoursesJSON);

        for (CourseSummary summary : courseSummaries) {
            try {
                downloadCourse(summary.getSubject(), summary.getCatalogNumber());
            } catch (CourseServiceException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Downloads a course from the web server to the database
     *
     * @param subject       subject of course to download
     * @param catalogNumber catalog number of the course to download
     * @throws CourseServiceException
     */
    static void downloadCourse(String subject, String catalogNumber) throws CourseServiceException {
        String courseJSONString = FetchCoursesData.getCourseDetailsJSON(
                subject,
                catalogNumber);
        Course course = CourseJSONUtils.JSONToCourse(courseJSONString);
        if (course != null) {
            CoursesDBHelper.insertCourses(course);
        } else {
            System.err.println("Error fetching: " + subject + catalogNumber);
        }
    }

    static void downloadPrereqsAndFutureCourses() throws CourseServiceException {
        Map<String, List<String>> futureCoursesMap = new HashMap<>();
        Map<String, String> prereqsMap = new LinkedHashMap<>();

        String allCoursesJSON = FetchCoursesData.getAllCoursesJSON();
        List<CourseSummary> courseSummaries = CourseJSONUtils.JSONToCourseSummaryList(allCoursesJSON);

        for (CourseSummary courseSummary : courseSummaries) {
            String courseCode = courseSummary.getCourseCode();
            String catalogNumber = courseSummary.getCatalogNumber();
            String subject = courseSummary.getSubject();

            try {
                String prereqJSON = FetchCoursesData.getPrereqJSON(subject, catalogNumber); //prereqJSON in nested format
                List<String> prereqs = CourseJSONUtils.JSONToPrereqs(prereqJSON); //convert to 1D list format

                prereqsMap.put(courseCode, new Gson().toJson(prereqs));

                for (String prereqCourseCode : prereqs) {
                    futureCoursesMap.computeIfAbsent(prereqCourseCode, k -> new LinkedList<>());
                    futureCoursesMap.get(prereqCourseCode).add(courseCode);
                }
            } catch (CourseServiceException e) {
                e.printStackTrace();
            }
        }

        CoursesDBHelper.setPrereqs(prereqsMap);
        CoursesDBHelper.setFutureCourses(futureCoursesMap);
    }

}
