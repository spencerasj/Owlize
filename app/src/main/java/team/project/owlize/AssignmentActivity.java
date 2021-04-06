package team.project.owlize;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class AssignmentActivity extends AppCompatActivity {
    private ArrayList<String> assignments;
    private ArrayAdapter<String> assignmentsAdapter;
    private ListView assignmentList;
    private ListView assignmentListView;
    private TextView courseTitleView;
//    private List<Assignment> mAssignmentList;
    Bundle extras;
    SharedPreferences sharedpreferences;

    DBHelper myDB;
    private String taskDate = "";
    private String selectedCourse;
    private int selectedID;
    int taskCntO;
    int taskCnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        assignmentList = findViewById(R.id.assignmentList);
        assignments = new ArrayList<>();
        extras = getIntent().getExtras();
        courseTitleView = (TextView) findViewById(R.id.courseTitle);
        int taskCnt = 0;
        int taskCntO = 0;

        myDB = new DBHelper(this);

        // get the intent extra from the SubjectListActivity
        Intent receivedIntent = getIntent();
        //now get the subjectID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1);
        //now get the name we passed as an extra
        selectedCourse = receivedIntent.getStringExtra("name");
        courseTitleView.setText(selectedCourse);
        assignmentListView = (ListView) findViewById(R.id.assignmentList);

        populateData();

        longClickDelete();
    }
    //Populate the data for Course Assignments and format it
    private void populateData() {
        List<Assignment> mAssignmentList = new ArrayList<>();
        Cursor assignments = myDB.getAssignmentData(selectedID);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        if (assignments.getCount() == 0){
//            return;
        } else {
            while (assignments.moveToNext()) {

                String dbDate = assignments.getString(2);
                mAssignmentList.add(new Assignment(assignments.getInt(0), assignments.getString(1), dbDate));
                Date strDate;

                try {
                    strDate = sdf.parse(dbDate);
                    if (new Date().after(strDate)){
                        taskCntO += 1;
                    } else {
                        taskCnt +=1;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        }

        //Init adapter
        AssignmentAdapter adapter = new AssignmentAdapter(getApplicationContext(), mAssignmentList);
        assignmentList.setAdapter(adapter);

        assignmentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent editTask = new Intent(TaskOverviewActivity.this,EditTaskActivity.class);
//                Object taskID = view.getTag();
//                editTask.putExtra("taskID",taskID.toString());
//                editTask.putExtra("subID",selectedID);
//                editTask.putExtra("name",selectedSubject);
//                startActivity(editTask);
//                Toast.makeText(getApplicationContext(), "Task ID = " + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    //Long click deletes record
    private void longClickDelete() {
        assignmentList.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    Object AssignmentId = item.getTag();

                    myDB.deleteAssignment(AssignmentId.toString());
//                    myDB.deleteSubj(Integer.toString(selectedID));
//                    assignments.remove(pos);
                    populateData();
//                    assignmentsAdapter.notifyDataSetChanged();
                    return true;
                });
    }
    // Adds assignments and prompts a date selector
    public void addAssignment(View view) {
        final Calendar myCalendar = Calendar.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Assignment");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText assignmentNameBox = new EditText(this);
        assignmentNameBox.setHint("Assignment Name");
        layout.addView(assignmentNameBox);
        final EditText dueDateBox = new EditText(this);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
            private void updateLabel() {
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dueDateBox.setText(sdf.format(myCalendar.getTime()));
            }
        };
        dueDateBox.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                new DatePickerDialog(AssignmentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
            return false;
        });
        dueDateBox.setHint("Due Date");
        layout.addView(dueDateBox);
        builder.setView(layout);
// Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {

            String newAssignment = assignmentNameBox.getText().toString();
            if(newAssignment.length() != 0 && dueDateBox.length() != 0){
                taskDate = dueDateBox.getText().toString();
                addAssignment(taskDate,newAssignment,selectedID);

                populateData();
//                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(AssignmentActivity.this,"Please fill all fields",Toast.LENGTH_LONG).show();
            }

            assignmentNameBox.setText("");
            dueDateBox.setText("");

//            writeItems();
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void addAssignment(String newTaskDate, String newTaskDesc, int subjectID){
        boolean insertData = myDB.insertAssignment(newTaskDate,newTaskDesc,subjectID);
        if(insertData) {
            Toast.makeText(AssignmentActivity.this, "Task Inserted", Toast.LENGTH_LONG).show();
//            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(AssignmentActivity.this, "Task not inserted", Toast.LENGTH_LONG).show();
        }
    }
}