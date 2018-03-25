import com.oracle.javafx.jmx.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FetchCoursesData {

    private static final String API_KEY = "aa38ff42e009e0c3980ccfaa0bb4abc1";
    private static final String BASE_URL = "https://api.uwaterloo.ca/v2/courses";
    private static final String GET_ALL_COURSES = ".json";

    public static String getAllCoursesJSON() throws JSONException {
        return getJSONData(BASE_URL + GET_ALL_COURSES + "?key=" + API_KEY);
    }

    public static String getCourseDetailsJSON(String subject, String catalogNumber) throws JSONException {
        return getJSONData(BASE_URL + "/" +
                subject + "/" +
                catalogNumber +
                ".json" +
                "?key=" + API_KEY);
    }

    public static String getPrereqJSON(String subject, String catalogNum) {
        return getJSONData(BASE_URL + "/" +
                subject + "/" +
                catalogNum +
                "/prerequisites.json" +
                "?key=" + API_KEY);
    }

    public static String getScheduleJSON(String subject, String catalogNum) {
        return getJSONData(BASE_URL + "/" +
                subject + "/" +
                catalogNum + "/" +
                "schedule.json" +
                "?key=" + API_KEY);
    }

    private static String getJSONData(String urlString) {
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String responseJSON = "";
            String buffer;
            while ((buffer = in.readLine()) != null)
                responseJSON = responseJSON.concat(buffer);
            in.close();
            return responseJSON;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
