package com.example.background_task;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout namajLayout;
    private SharedPreferences namajPreferences;
    private Button editNamajButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        namajLayout = findViewById(R.id.namajLayout);
        editNamajButton = findViewById(R.id.editNamajButton);
        namajPreferences = getSharedPreferences("NamajPreferences", MODE_PRIVATE);

        displayNamajCards();

        editNamajButton.setOnClickListener(new View.OnClickListener() {
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
    private void displayNamajCards() {
        LayoutInflater inflater = LayoutInflater.from(this);

        // List of namaj titles
        String[] namajTitles = {"ফজর", "যোহর", "আসর", "মাগরিব", "এশা", "জুমা"};

        for (String namajTitle : namajTitles) {
            String startTimeKey = "startTime_" + namajTitle;
            String finishTimeKey = "finishTime_" + namajTitle;

            // Check if namaj data is available in SharedPreferences
            if (!namajPreferences.getString(startTimeKey,"").isEmpty() && !namajPreferences.getString(finishTimeKey,"").isEmpty()) {
                String startTime = namajPreferences.getString(startTimeKey, "");
                String finishTime = namajPreferences.getString(finishTimeKey, "");
                // Inflate the namaj card layout
                View namajCard = inflater.inflate(R.layout.card_layouts, namajLayout, false);

                // Set namaj title
                TextView titleTextView = namajCard.findViewById(R.id.titleTextView);
                titleTextView.setText(namajTitle);

                // Set start time
                TextView startTimeTextView = namajCard.findViewById(R.id.startTimeTextView);
                startTimeTextView.setText(startTime);

                // Set finish time
                TextView finishTimeTextView = namajCard.findViewById(R.id.finishTimeTextView);
                finishTimeTextView.setText(" -  " + finishTime);

                // Add the namaj card to the layout
                namajLayout.addView(namajCard);
                namajLayout.setVisibility(View.VISIBLE);

            }

        }
    }
}
