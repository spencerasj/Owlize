package team.project.owlize;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * MainActivity directs the user to eithe the manual dashboard or the API dashboards
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Token = "tokenKey";

    SharedPreferences sharedpreferences;
    TextView tv_canvasToken;
    Button btn_getStarted;

    /**
     * onCreate provides the splash page, and interacts with the user to either access Canvas API dashboard or manual dashboard. Also sets API token
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create button to start the app
        Button getStartedBtn = (Button) findViewById(R.id.getStartedBtn);
        getStartedBtn.setOnClickListener(v -> openDashboard());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        // Create button to start manual dashboard
        Button manualDashBoardBtn = (Button) findViewById(R.id.manualDashBoardBtn);
        manualDashBoardBtn.setOnClickListener(v -> openManualDashboard());

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tv_canvasToken = findViewById(R.id.tv_canvasToken);
        btn_getStarted = findViewById(R.id.getStartedBtn);
        String savedToken = sharedpreferences.getString(Token, "");
        if (!savedToken.isEmpty()) {
            tv_canvasToken.setText("");
            btn_getStarted.setText("Canvas Assignment Sync");
        }


    }

    /**
     * openDashboard will display a prompt asking for a Canvas token ID, after which, the user will be taken to the API view of Canvas
     */
    private void openDashboard() {

        String savedToken = sharedpreferences.getString(Token, "");
        if (savedToken.isEmpty()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Canvas Token:");

// Set up the input
            final EditText input = new EditText(this);
            input.setText(sharedpreferences.getString(Token, ""));
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", (dialog, which) -> {
                String m_Text = input.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Token, m_Text);
                editor.apply();

                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
            }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        } else {
            Toast.makeText(MainActivity.this, "Downloading Courses", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
        }
        Log.d(TAG, "Send openDashboard intent");
    }

    /**
     * openManualDashboard opens the manual dashboard so that users are able to track their own courses and assignments with due dates
     */
    private void openManualDashboard() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "My Notification");
        builder.setContentTitle("Reminder Notification");
        builder.setContentText("You have assignments due soon");
        builder.setSmallIcon(R.drawable.owl3);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, builder.build());

        Intent intent2 = new Intent(this, CourseActivity.class);
        startActivity(intent2);

        Log.d(TAG, "Send intent to CourseActivity");
    }
}