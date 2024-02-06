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

    private SharedPreferences namajPreferences;
    private static final int NUMBER_OF_CARDS = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        namajPreferences = getSharedPreferences("NamajPreferences", MODE_PRIVATE);

        // Retrieve data and dynamically create cards
        for (int i = 1; i <= NUMBER_OF_CARDS; i++) {
            addCard(i);
        }

        // Add the "Edit Namaj Time" button
        Button editNamajButton = findViewById(R.id.editNamajButton);
        editNamajButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditNamajButtonClick(v);
            }
        });
    }

    private void addCard(int cardNumber) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.card_layouts, null);

        String keyStartTime = "startTime_" + cardNumber;
        String keyFinishTime = "finishTime_" + cardNumber;

        TextView startTimeTextView = cardView.findViewById(R.id.startTimeTextView);
        TextView finishTimeTextView = cardView.findViewById(R.id.finishTimeTextView);

        String startTime = namajPreferences.getString(keyStartTime, "");
        String finishTime = namajPreferences.getString(keyFinishTime, "");

        startTimeTextView.setText(startTime);
        finishTimeTextView.setText("-   " + finishTime);

        // Set fixed namaj titles based on cardNumber
        setFixedNamajTitle(cardView, cardNumber);

        // Add the card to the LinearLayout
        LinearLayout container = findViewById(R.id.container);

        // Set layout parameters with margins for the card
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int marginInDp = getResources().getDimensionPixelSize(R.dimen.card_margin); // Adjust the margin as needed
        layoutParams.setMargins(0, 0, 0, marginInDp);

        cardView.setLayoutParams(layoutParams);

        container.addView(cardView);
    }

    private void setFixedNamajTitle(View cardView, int cardNumber) {
        TextView titleTextView = cardView.findViewById(R.id.titleTextView);

        switch (cardNumber) {
            case 1:
                titleTextView.setText("FAJR");
                break;
            case 2:
                titleTextView.setText("DHUHR ");
                break;
            case 3:
                titleTextView.setText("ASR");
                break;
            case 4:
                titleTextView.setText("MAGRIB");
                break;
            case 5:
                titleTextView.setText("ISHA");
                break;
            case 6:
                titleTextView.setText("JUMMAH");
                break;
            default:
                titleTextView.setText("");
                break;
        }
    }

    public void onEditNamajButtonClick(View view) {
        // Handle button click, navigate to EditNamajActivity
        Intent intent = new Intent(this, EditNamajActivity.class);
        startActivity(intent);
    }
}
