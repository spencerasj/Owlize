package team.project.owlize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ManualDashboard extends AppCompatActivity {
    String course;
    EditText courseName;
    ArrayList<String> courses;
//    private ListView lvItems;
    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_dashboard);
        test = (TextView) findViewById(R.id.test);
    }

    public void getCourse(View test) {
        String itemText = test.getText().toString();
        courseName = (EditText) findViewById(R.id.etNewItem);
        course = courseName.getText().toString();
        courses = new ArrayList<String>();
        courses.add(course);
        test.(courses);


//        Intent intent = new Intent(this, AddCourse.class);
//        startActivity(intent);
    }

    /** Called when user clicks + button **/
    public void openAddSubject(View view){

//        Intent intent = new Intent(this, AddCourse.class);
//        startActivity(intent);
    }
}
