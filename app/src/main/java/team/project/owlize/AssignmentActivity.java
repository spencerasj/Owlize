package team.project.owlize;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AssignmentActivity extends AppCompatActivity {
    private ArrayList<String> assignments;
    private ArrayAdapter<String> assignmentsAdapter;
    private ListView assignmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        assignmentList = findViewById(R.id.assignmentList);
        assignments = new ArrayList<String>();
        assignmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, assignments);
        assignmentList.setAdapter(assignmentsAdapter);
        longClickDelete();
    }


    private void longClickDelete() {
        assignmentList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        assignments.remove(pos);
                        assignmentsAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
    }

    public void addAssignment(View view) {
       EditText newAssignment = (EditText) findViewById(R.id.assignmentEditText);
        String courseName = newAssignment.getText().toString();
        assignmentsAdapter.add(courseName);
        newAssignment.setText("");

    }
}