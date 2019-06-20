package pl.mbierut.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class JSONReader {
    private ReverseDate reverseDate;

    private JSONReader(ReverseDate reverseDate){
        this.reverseDate = reverseDate;
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    JSONArray readJsonArrFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }


    String parseWeatherJSON(JSONObject json, int dayNum, int beginDays) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONArray arr = json.getJSONArray("data");
            for (int i = Math.max(0, dayNum - 2 - beginDays); i < dayNum; i++) {
                sb.append(reverseDate.reverse(arr.getJSONObject(i).getString("valid_date")))
                        .append(System.getProperty("line.separator"))
                        .append(arr.getJSONObject(i).getJSONObject("weather").getString("description"))
                        .append(System.getProperty("line.separator"))
                        .append(arr.getJSONObject(i).getString("app_min_temp"))
                        .append(" - ")
                        .append(arr.getJSONObject(i).getString("app_max_temp"))
                        .append(System.getProperty("line.separator"));
            }
            return sb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "JSON exception";
        }
    }
}