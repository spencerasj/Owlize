package team.project.owlize;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Dashboard extends AppCompatActivity {

    String courses = null;
    RecyclerView rvGroup;
    ArrayList<String> arrayListGroup;
    ArrayList<String> courseListGroup;
    LinearLayoutManager layoutManagerGroup;
    CustomAdapter customAdapter;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Token = "tokenKey";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String token = sharedPreferences.getString(Token, "");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String url = "https://byui.instructure.com:443/api/v1/courses?enrollment_state=active";
        courses = getJSON(url, token);
        Log.d("test", courses);

        rvGroup = findViewById(R.id.recyclerView);

        Gson gson = new Gson();
        Type type = new TypeToken<List<CourseModel>>(){}.getType();
        List<CourseModel> courseList = gson.fromJson(courses, type);
        arrayListGroup = new ArrayList<>();
        courseListGroup = new ArrayList<>();
        for (CourseModel course : courseList){
            Log.i("Contact Details", course.id + "-" + course.name + "-" + course.account_id);
            arrayListGroup.add(course.name);
            courseListGroup.add(course.id);
        }

        customAdapter = new CustomAdapter(this, arrayListGroup, courseListGroup);
        layoutManagerGroup = new LinearLayoutManager(this);
        rvGroup.setLayoutManager(layoutManagerGroup);
        rvGroup.setAdapter(customAdapter);


           // Create button to Add Content
//        Button addContentButton = (Button) findViewById(R.id.addContentBtn);
//        addContentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openSemesterActivity();
//            }
//        });
//    }
//    private void openSemesterActivity() {
//        Intent intent = new Intent(this, SemesterActivity.class);
//        startActivity(intent);
   }

    public static String getJSON(String url, String token) {
        HttpURLConnection con = null;

        try {
            URL u = new URL(url);
            con = (HttpURLConnection) u.openConnection();
            con.setRequestProperty("Authorization","Bearer "+token);

            con.setRequestProperty("Content-Type","application/json");
            con.setRequestMethod("GET");
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }
}
