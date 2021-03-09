package team.project.owlize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button;

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
    }

    private void openDashboard() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }
}