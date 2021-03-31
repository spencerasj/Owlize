package team.project.owlize;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddCourse extends AppCompatActivity {
    private static final String TAG = "AddCourseActivity";
    EditText courseNameEt, assignmentNameEt, dueDateEt;
    String courseName, assignmentName, dueDate;
    ArrayList<AddCourse> courseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
    }
//
//    public void addNewCourse(View view) {
//        courseNameEt = findViewById(R.id.course_name);
//        courseName = courseNameEt.getText().toString();
//
//        Log.d(TAG, "About to create intent with " + courseArrayList);
//        Intent intent = new Intent(this, CourseActivity.class);
//        intent.putExtra("NewCourse", "Course: " + courseName )+ " Assignment: " + assignmentName + " Due Date:" + dueDate);
////        startActivity(intent);
////        Log.d(TAG, "Sent intent with " + courseName + " " + assignmentName +  " " + dueDate);
//    }
}
//    ArrayList<Object> object = new ArrayList<Object>();
//    Intent intent = new Intent(Current.class, Transfer.class);
//    Bundle args = new Bundle();
//args.putSerializable("ARRAYLIST",(Serializable)object);
//        intent.putExtra("BUNDLE",args);
//        startActivity(intent);
//        In the Transfer.class
//
//Intent intent = getIntent();
//        Bundle args = intent.getBundleExtra("BUNDLE");
//        ArrayList<Object> object = (ArrayList<Object>) args.getSerializable("ARRAYLIST");

//class Assignment() {
//    String assignmentName, dueDate;
//
//    public String getAssignmentName() {
//        return assignmentName;
//    }
//
//    public String getDueDate() {
//        return dueDate;
//    }
//
//}
//    ArrayList<Employee> employees= new ArrayList<>();
//        employees.add(new Hourly("Harry Hacker", 123, 15.00, 30));
