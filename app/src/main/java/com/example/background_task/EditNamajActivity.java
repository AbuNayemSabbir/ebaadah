package com.example.background_task;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Locale;

public class EditNamajActivity extends AppCompatActivity {

    private SharedPreferences namajPreferences;
    private static final int NUMBER_OF_CARDS = 6;
    private String fajrTitle, dhuhrTitle, asrTitle, maghribTitle, ishaTitle, jummahTitle;
    private String fajrStartTime, fajrFinishTime, dhuhrStartTime, dhuhrFinishTime,
            asrStartTime, asrFinishTime, maghribStartTime, maghribFinishTime,
            ishaStartTime, ishaFinishTime, jummahStartTime, jummahFinishTime;
    private EditText startTimeEditText1, finishTimeEditText1;
    private EditText startTimeEditText2, finishTimeEditText2;
    private EditText startTimeEditText3, finishTimeEditText3;
    private EditText startTimeEditText4, finishTimeEditText4;
    private EditText startTimeEditText5, finishTimeEditText5;
    private EditText startTimeEditText6, finishTimeEditText6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_namaj);

        startTimeEditText1 = findViewById(R.id.startTimeEditText1);
        finishTimeEditText1 = findViewById(R.id.finishTimeEditText1);

        startTimeEditText2 = findViewById(R.id.startTimeEditText2);
        finishTimeEditText2 = findViewById(R.id.finishTimeEditText2);

        startTimeEditText3 = findViewById(R.id.startTimeEditText3);
        finishTimeEditText3 = findViewById(R.id.finishTimeEditText3);

        startTimeEditText4 = findViewById(R.id.startTimeEditText4);
        finishTimeEditText4 = findViewById(R.id.finishTimeEditText4);

        startTimeEditText5 = findViewById(R.id.startTimeEditText5);
        finishTimeEditText5 = findViewById(R.id.finishTimeEditText5);

        startTimeEditText6 = findViewById(R.id.startTimeEditText6);
        finishTimeEditText6 = findViewById(R.id.finishTimeEditText6);

        namajPreferences = getSharedPreferences("NamajPreferences", MODE_PRIVATE);

        // Setup edit cards for each namaj
        setupEditCard("ফজর", R.id.editCard1, R.id.startTimeEditText1, R.id.finishTimeEditText1, R.id.titleTextView1/*, R.id.shareButton1*/);
        setupEditCard("যোহর", R.id.editCard2, R.id.startTimeEditText2, R.id.finishTimeEditText2, R.id.titleTextView2/*, R.id.shareButton2*/);
        setupEditCard("আসর", R.id.editCard3, R.id.startTimeEditText3, R.id.finishTimeEditText3, R.id.titleTextView3/*, R.id.shareButton3*/);
        setupEditCard("মাগরিব", R.id.editCard4, R.id.startTimeEditText4, R.id.finishTimeEditText4, R.id.titleTextView4/*, R.id.shareButton4*/);
        setupEditCard("এশা", R.id.editCard5, R.id.startTimeEditText5, R.id.finishTimeEditText5, R.id.titleTextView5/*, R.id.shareButton5*/);
        setupEditCard("জুমা", R.id.editCard6, R.id.startTimeEditText6, R.id.finishTimeEditText6, R.id.titleTextView6/*, R.id.shareButton6*/);

        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(EditNamajActivity.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                goToHomeActivity();
               // startService();
            }
        });
     /*   btnStartService.setOnClickListener(new View.OnClickListener() {
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
        });*/
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
    public void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    // Method to setup each edit card
    // Method to setup each edit card
    private void setupEditCard(final String namajTitle, int cardLayoutId, int startTimeEditTextId,
                               int finishTimeEditTextId, int titleTextViewId/*, int shareButtonId*/) {
        RelativeLayout cardView = findViewById(cardLayoutId);
        final EditText startTimeEditText = cardView.findViewById(startTimeEditTextId);
        final EditText finishTimeEditText = cardView.findViewById(finishTimeEditTextId);
/*
        final Button shareButton = cardView.findViewById(shareButtonId);
*/
        final TextView titleTextView = cardView.findViewById(titleTextViewId);

        titleTextView.setText(namajTitle);

        // Retrieve existing data and populate EditText fields
        String startTimeKey = "startTime_" + namajTitle;
        String finishTimeKey = "finishTime_" + namajTitle;
        String startTime = namajPreferences.getString(startTimeKey, "");
        String finishTime = namajPreferences.getString(finishTimeKey, "");
        startTimeEditText.setText(startTime);
        finishTimeEditText.setText(finishTime);

       /* // Set up share button visibility based on data existence in shared preferences
        if (!startTime.isEmpty() && !finishTime.isEmpty()) {
            shareButton.setVisibility(View.VISIBLE);
        } else {
            shareButton.setVisibility(View.INVISIBLE);
        }*/

        startTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(startTimeEditText);
            }
        });

        finishTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(finishTimeEditText);
            }
        });

        // Set up click listeners for share buttons
   /*     shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement share functionality here
            }
        });*/
    }

    // Method to save data for each namaj
    private void saveData() {
        SharedPreferences.Editor editor = namajPreferences.edit();
        fajrStartTime = startTimeEditText1.getText().toString();
        fajrFinishTime = finishTimeEditText1.getText().toString();
        editor.putString("ফজর", fajrTitle);
        editor.putString("startTime_ফজর", fajrStartTime);
        editor.putString("finishTime_ফজর", fajrFinishTime);
        dhuhrStartTime = startTimeEditText2.getText().toString();
        dhuhrFinishTime = finishTimeEditText2.getText().toString();
        editor.putString("যোহর", dhuhrTitle);
        editor.putString("startTime_যোহর", dhuhrStartTime);
        editor.putString("finishTime_যোহর", dhuhrFinishTime);
        asrStartTime = startTimeEditText3.getText().toString();
        asrFinishTime = finishTimeEditText3.getText().toString();
        editor.putString("আসর", asrTitle);
        editor.putString("startTime_আসর", asrStartTime);
        editor.putString("finishTime_আসর", asrFinishTime);
        maghribStartTime = startTimeEditText4.getText().toString();
        maghribFinishTime = finishTimeEditText4.getText().toString();
        editor.putString("মাগরিব", maghribTitle);
        editor.putString("startTime_মাগরিব", maghribStartTime);
        editor.putString("finishTime_মাগরিব", maghribFinishTime);
        ishaStartTime = startTimeEditText5.getText().toString();
        ishaFinishTime = finishTimeEditText5.getText().toString();
        editor.putString("এশা", ishaTitle);
        editor.putString("startTime_এশা", ishaStartTime);
        editor.putString("finishTime_এশা", ishaFinishTime);
        jummahStartTime = startTimeEditText6.getText().toString();
        jummahFinishTime = finishTimeEditText6.getText().toString();
        editor.putString("জুমা", jummahTitle);
        editor.putString("startTime_জুমা", jummahStartTime);
        editor.putString("finishTime_জুমা", jummahFinishTime);

        editor.apply();
    }

    // Method to show time picker dialog
    private void showTimePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        editText.setText(formattedTime);
                    }
                },
                hour,
                minute,
                android.text.format.DateFormat.is24HourFormat(this)
        );

        timePickerDialog.show();
    }
}
