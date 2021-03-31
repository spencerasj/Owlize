package team.project.owlize;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create button to start the app
        Button getStartedBtn = (Button) findViewById(R.id.getStartedBtn);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });

        // Create button to start manual dashboard
        Button manualDashBoardBtn = (Button) findViewById(R.id.manualDashBoardBtn);
        manualDashBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManualDashboard();
            }
        });
    }

    private void openDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        Log.d(TAG, "Send openDashboard intent");
    }

    private void openManualDashboard() {
        Intent intent2 = new Intent(this, CourseActivity.class);
        startActivity(intent2);
        Log.d(TAG, "Send intent to CourseActivity");
    }
}