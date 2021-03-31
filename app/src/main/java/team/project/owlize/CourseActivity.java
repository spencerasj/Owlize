package team.project.owlize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
        readItems();
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
                        writeItems();
                        return true;
                    }
                });
    }

    public void addCourse(View view) {
        course = findViewById(R.id.courseNameEditText);
        String courseName = course.getText().toString();
        if (courseName.length() == 0) {
            toastMessage("Please enter a course name");
            return;
        }
        coursesAdapter.add(courseName);
        course.setText("");
        writeItems();
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    public void receiveCourse(View view) {
//


    private void readItems() {
        File filesDir = getFilesDir();
        File coursesFile = new File(filesDir, "todo.txt");
        try {
            courses = new ArrayList<String>(FileUtils.readLines(coursesFile));
        } catch (IOException e) {
            courses = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File coursesFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(coursesFile, courses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
