package team.project.owlize;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    String assignments;
    final String strAssignmentsURL = "https://byui.instructure.com/api/v1/courses/127496/assignments";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new Thread(runnable).start();
        //assignments = getHTTPData("https://byui.instructure.com/api/v1/courses/127496/assignments");



        //Log.d("id", "ID used: "+id);
        //Log.d("Oauth2Demo","Data: "+data);

        // Create button to start the app
        Button getStartedBtn = (Button) findViewById(R.id.getStartedBtn);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });
    }

    private class RetreiveData extends AsyncTask<Void, Void, Void> {
        String result;
        @Override
        protected Void doInBackground(Void... voids) {

            try {
                HTTPHelper httpHelper = new HTTPHelper();
                StringBuffer data = null;
                data = httpHelper.readHTTP(strAssignmentsURL,
                        "API KEY HERE");

                result = String.valueOf(data);

            } catch (Exception e) {
                e.printStackTrace();
                result = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            lvItems = (ListView) findViewById(R.id.lvItems);
            items = new ArrayList<>();
            itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
            lvItems.setAdapter(itemsAdapter);

            JSONArray json = null;
            JSONObject obj = null;
            String id = null;
            try {
                json = new JSONArray(String.valueOf(assignments));
                Log.d("jsonCount", "count: "+json.length());
                for (int i = 0; i < json.length(); i++) {
                    obj = json.getJSONObject(i);
                    id = obj.getString("name");
                    items.add(id);
                    Log.d("obj", id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHTTPData(String url) {

    }

    private void openDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }


//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//
//            HTTPHelper httpHelper = new HTTPHelper();
//            StringBuffer data = null;
//            data = httpHelper.readHTTP("https://byui.instructure.com/api/v1/courses/127496/assignments",
//                    "API KEY HERE");
//
//            lvItems = (ListView) findViewById(R.id.lvItems);
//            items = new List<String>();
//            itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
//            lvItems.setAdapter(itemsAdapter);
//
//            JSONArray json = null;
//            JSONObject obj = null;
//            String id = null;
//            try {
//                json = new JSONArray(String.valueOf(data));
//                Log.d("jsonCount", "count: "+json.length());
//                for (int i = 0; i < json.length(); i++) {
//                    obj = json.getJSONObject(i);
//                    id = obj.getString("name");
//                    items.add(id);
//                    Log.d("obj", id);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            //Log.d("id", "ID used: "+id);
//            Log.d("Oauth2Demo","Data: "+data);
//        }
//    };
}


