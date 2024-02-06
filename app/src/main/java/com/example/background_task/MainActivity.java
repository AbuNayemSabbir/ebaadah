package com.example.background_task;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
    Button btnStartService, btnStopService, btnGoToCardActivity,buttonGoToHomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.buttonStartService);
        btnStopService = findViewById(R.id.buttonStopService);
        btnGoToCardActivity = findViewById(R.id.buttonGoToCardActivity);
        buttonGoToHomeActivity = findViewById(R.id.buttonGoToHomeActivity);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        btnGoToCardActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCardActivity();
            }
        });
        buttonGoToHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHomeActivity();
            }
        });
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    public void goToCardActivity() {
        Intent intent = new Intent(this, EditNamajActivity.class);
        startActivity(intent);
    }
    public void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
