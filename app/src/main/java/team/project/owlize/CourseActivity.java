package team.project.owlize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private static final String TAG = "CourseActivity";
    private ArrayList<String> courses;
    private ArrayAdapter<String> coursesAdapter;
    private ListView courseList;
    EditText course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        courseList = findViewById(R.id.assignmentList);
        courses = new ArrayList<>();
//        Log.d(TAG, "Received intent with Course");
        coursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courses);
        courseList.setAdapter(coursesAdapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String course = adapterView.getItemAtPosition(i).toString();
                Intent assignIntent = new Intent(CourseActivity.this, AssignmentActivity.class);
                assignIntent.putExtra("CourseListItem", course);
                startActivity(assignIntent);

            }
        });
//        Log.d(TAG, "Received intent with favScripture");
        longClickDelete();
    }

    private void longClickDelete() {
        courseList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        courses.remove(pos);
                        coursesAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
    }

    public void addCourse(View view) {
       course = findViewById(R.id.courseNameEditText);
       String courseName = course.getText().toString();
       coursesAdapter.add(courseName);
       course.setText("");
    }

//    public void receiveCourse(View view) {
//
  }
