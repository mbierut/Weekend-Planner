package pl.mbierut.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class JSONReader {
    private RestTemplate restTemplate;





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


    String parseWeatherJSON(JSONObject json, int numberOfDays, int beginDays) {
        StringBuilder sb = new StringBuilder();
        String datePattern = "dd.MM.yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDate date;
        try {
            JSONArray arr = json.getJSONArray("data");
            for (int i = Math.max(0, numberOfDays - 2 - beginDays); i < numberOfDays; i++) {
                date = LocalDate.parse(arr.getJSONObject(i).getString("valid_date"));
                sb.append(dateTimeFormatter.format(date))
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