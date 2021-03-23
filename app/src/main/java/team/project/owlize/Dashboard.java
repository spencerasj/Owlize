package team.project.owlize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
           // Create button to Add Content
        Button addContentButton = (Button) findViewById(R.id.addContentBtn);
        addContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSemesterActivity();
            }
        });
    }
    private void openSemesterActivity() {
        Intent intent = new Intent(this, SemesterActivity.class);
        startActivity(intent);
    }
    }
