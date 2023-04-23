package com.example.rmp_lr5_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    String message = "";
    int energy;
    int health;
    static final String MESSAGE = "MESSAGE";
    private TextView healthText;
    private TextView energyText;


    // message = "PLAYER_NUMB TYPE_ATTACK PLACE_ATTACK PLACE_DEF" если не атаковать то PLACE_ATTACK = EMPTY

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        healthText = findViewById(R.id.textViewHealth);
        energyText = findViewById(R.id.textViewEnergy);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            message += extras.getString(SecondActivity.PLAYER);
            health = extras.getInt(SecondActivity.HEALTH);
            healthText.setText(healthText.getText() + " " + health);
            energy = extras.getInt(SecondActivity.ENERGY);
            energyText.setText("" + energy);
        }
        Button buttonEndTurn = findViewById(R.id.buttonEndTurn);
        buttonEndTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //message += " POWER HEAD LEGS";
                Intent intent = new Intent
                        (ThirdActivity.this, SecondActivity.class);
                intent.putExtra(MESSAGE, message);
                //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                setResult(RESULT_OK, intent);
                onBackPressed();
                //buttonActionPlayerOne.setEnabled(false);
                //startActivity(intent);
            }
        });
    }
}