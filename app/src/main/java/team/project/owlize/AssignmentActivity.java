package team.project.owlize;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
public class AssignmentActivity extends AppCompatActivity {
    private ArrayList<String> assignments;
    private ArrayAdapter<String> assignmentsAdapter;
    private ListView assignmentList;
    private TextView courseTitleView;
    Bundle extras;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        assignmentList = findViewById(R.id.assignmentList);
        assignments = new ArrayList<>();
        extras = getIntent().getExtras();
        courseTitleView = (TextView) findViewById(R.id.courseTitle);
        courseTitleView.setText(extras.getString("CourseListItem"));
        readItems();
        assignmentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assignments);
        assignmentList.setAdapter(assignmentsAdapter);
        longClickDelete();
    }
    private void longClickDelete() {
        assignmentList.setOnItemLongClickListener(
                (adapter, item, pos, id) -> {
                    assignments.remove(pos);
                    assignmentsAdapter.notifyDataSetChanged();
                    return true;
                });
    }
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
                String myFormat = "MM/dd/yy"; //In which you need put here
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
//                String m_Text = input.getText().toString();
            String assignmentData = assignmentNameBox.getText().toString() + ";" + dueDateBox.getText().toString();
//                String courseName = assignmentNameBox.getText().toString();
            assignmentsAdapter.add(assignmentData);
            assignmentNameBox.setText("");
            dueDateBox.setText("");
            writeItems();
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    private void readItems() {
        File filesDir = getFilesDir();
        File coursesFile = new File(filesDir, extras.getString("CourseListItem")+".txt");
        try {
            assignments = new ArrayList<String>(FileUtils.readLines(coursesFile));
        } catch (IOException e) {
            assignments = new ArrayList<>();
        }
    }
    private void writeItems() {
        File filesDir = getFilesDir();
        File coursesFile = new File(filesDir, extras.getString("CourseListItem")+".txt");
        try {
            FileUtils.writeLines(coursesFile, assignments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}