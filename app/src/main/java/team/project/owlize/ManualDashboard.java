package team.project.owlize;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManualDashboard extends AppCompatActivity {
    String course;
    EditText courseName;
    ArrayList<String> courses;
    RecyclerView recyclerView;
    ManualAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_dashboard);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ManualAdapter( courses);
        recyclerView.setAdapter(adapter);
    }

    public void getCourse(View view) {
        // get course name from edit text and convert to string
        courseName = (EditText) findViewById(R.id.etNewItem);
        course = courseName.getText().toString();
        // add to array of courses for recycler
        courses = new ArrayList<String>();
        courses.add(course);
    }
}
