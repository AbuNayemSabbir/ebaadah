package com.example.background_task;

import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class EditNamajActivity extends AppCompatActivity {

    private SharedPreferences namajPreferences;
    private static final int NUMBER_OF_CARDS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_namaj);

        namajPreferences = getSharedPreferences("NamajPreferences", MODE_PRIVATE);

        for (int i = 1; i <= NUMBER_OF_CARDS; i++) {
            setupEditCard(i);
        }

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Toast.makeText(EditNamajActivity.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void setupEditCard(final int cardNumber) {
        String keyStartTime = "startTime_" + cardNumber;
        String keyFinishTime = "finishTime_" + cardNumber;

        int cardLayoutId = getResources().getIdentifier("editCard" + cardNumber, "id", getPackageName());
        View cardView = findViewById(cardLayoutId);

        final EditText startTimeEditText = cardView.findViewById(R.id.startTimeEditText);
        final EditText finishTimeEditText = cardView.findViewById(R.id.finishTimeEditText);
        TextView titleTextView = cardView.findViewById(getResources().getIdentifier("titleTextView" + cardNumber, "id", getPackageName()));

        titleTextView.setText(getPrayerName(cardNumber));

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

        String startTime = namajPreferences.getString(keyStartTime, "");
        String finishTime = namajPreferences.getString(keyFinishTime, "");

        startTimeEditText.setText(startTime);
        finishTimeEditText.setText(finishTime);
    }

    private void saveData() {
        SharedPreferences.Editor editor = namajPreferences.edit();

        for (int i = 1; i <= NUMBER_OF_CARDS; i++) {
            int cardLayoutId = getResources().getIdentifier("editCard" + i, "id", getPackageName());
            View cardView = findViewById(cardLayoutId);

            EditText startTimeEditText = cardView.findViewById(R.id.startTimeEditText);
            EditText finishTimeEditText = cardView.findViewById(R.id.finishTimeEditText);
            TextView titleTextView = cardView.findViewById(getResources().getIdentifier("titleTextView" + i, "id", getPackageName()));

            String keyStartTime = "startTime_" + i;
            String keyFinishTime = "finishTime_" + i;
            String keyPrayerName = "prayerName_" + i;

            editor.putString(keyStartTime, startTimeEditText.getText().toString());
            editor.putString(keyFinishTime, finishTimeEditText.getText().toString());
            editor.putString(keyPrayerName, titleTextView.getText().toString());
        }

        editor.apply();
    }

    private void showTimePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Format the selected time and set it to the EditText
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

    private String getPrayerName(int cardNumber) {
        switch (cardNumber) {
            case 1:
                return "Fajr";
            case 2:
                return "Dhuhr";
            case 3:
                return "Asr";
            case 4:
                return "Maghrib";
            case 5:
                return "Isha";
            case 6:
                return "Jummah";
            default:
                return "";
        }
    }
}
