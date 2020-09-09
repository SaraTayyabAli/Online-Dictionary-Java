/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

/**
 *
 * @author saraaa
 */
public class OxfordApi {

    protected static String searchWord(String word) {

        String language = "en";
        String word_to_lowercase = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com/api/v2/entries/" + language + "/" + word_to_lowercase;

    }

    protected static String reteiveData(String... params) {
        String app_id = "355ac2e8";
        String app_key = "67a6a13d18f4e847cdbfd90f0b9ffafd";

        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("app_id", app_id);
            urlConnection.setRequestProperty("app_key", app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringbuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringbuilder.append(line + "\n");
            }

            //return stringbuilder.toString();
            JSONObject json = new JSONObject(stringbuilder.toString());
            JSONArray array = json.getJSONArray("results");
            JSONObject json1 = array.getJSONObject(0);
            JSONArray array2 = json1.getJSONArray("lexicalEntries");
            JSONObject json2 = array2.getJSONObject(0);
            JSONArray array3 = json2.getJSONArray("entries");
            JSONObject json3 = array3.getJSONObject(0);
            JSONArray array4 = json3.getJSONArray("etymologies");
            JSONArray array5 = json3.getJSONArray("senses");
            JSONObject json4 = array5.getJSONObject(0);
            JSONArray array6 = json4.getJSONArray("definitions");
            JSONArray array7 = json4.getJSONArray("examples");
            JSONObject json5 = array7.getJSONObject(0);

            JSONObject lexicalCategory = json2.getJSONObject("lexicalCategory");

            return "Can be a: " + lexicalCategory.getString("text") + "\n" 
                    + "Type: " + json1.getString("type") + "\n "
                    + "Meaning: " + array6.getString(0) + "\n"
                    + "Etymologies: " + array4.getString(0) + "\n"
                    + "Examples: " + json5.getString("text");
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

    }

    public static void main(String[] args) {
        System.out.println(reteiveData(searchWord("clone")));

    }

}
