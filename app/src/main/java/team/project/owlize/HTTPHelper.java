package team.project.owlize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPHelper extends{
    public StringBuffer readHTTP(String url, String token) {
        StringBuffer response = new StringBuffer();
        try {
            // Convert the String url to a URL object which will allow a connection to be established
            URL urlObj = new URL(url);
            // Open a Connection to the remote site.  This will send the HTTP Get request
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestProperty("Authorization", "Bearer " + token);
            connection.connect();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String output;


            while ((output = in.readLine()) != null) {
                response.append(output);
            }

            in.close();
            // printing result from response
            //System.out.println("Response:-" + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}

