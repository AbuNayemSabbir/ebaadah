package com.example.background_task;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    Button btnGoToCardActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkNotificationPolicyAccess(this);
        btnGoToCardActivity = findViewById(R.id.buttonGoToCardActivity);

        btnGoToCardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCardActivity();
            }
        });

    }

    public void goToCardActivity() {
        Intent intent = new Intent(this, EditNamajActivity.class);
        startActivity(intent);
    }
    private boolean checkNotificationPolicyAccess(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (!notificationManager.isNotificationPolicyAccessGranted()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return false; // Permission not granted
            }
        }
        return true; // Permission granted or SDK version is lower than M
    }
}
