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
import android.widget.Toast;

import java.util.Objects;

public class ThirdActivity extends AppCompatActivity {

    String message = "";
    String attack = "";
    String def = "";
    int energy = 0;
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
        initButton();

    }

    private void initButton() {
        Button buttonEndTurn = findViewById(R.id.buttonEndTurn);
        buttonEndTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //message += " POWER HEAD LEGS";
                if (Objects.equals(attack, "") || Objects.equals(def, "")) {
                    Toast toast = Toast.makeText(ThirdActivity.this, "Выберите атаку и/или защиту",Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    message += attack + def;
                    Intent intent = new Intent
                            (ThirdActivity.this, SecondActivity.class);
                    intent.putExtra(MESSAGE, message);
                    setResult(RESULT_OK, intent);
                    onBackPressed();
                }
            }
        });

        Button buttonHeadAttack = findViewById(R.id.buttonHeadAttack);
        Button buttonBodyAttack = findViewById(R.id.buttonBodyAttack);
        Button buttonLegsAttack = findViewById(R.id.buttonLegsAttack);

        Button buttonHeadPowerAttack = findViewById(R.id.buttonHeadPowerAttack);
        Button buttonBodyPowerAttack = findViewById(R.id.buttonBodyPowerAttack);
        Button buttonLegsPowerAttack = findViewById(R.id.buttonLegsPowerAttack);

        Button buttonHeadDef = findViewById(R.id.buttonHeadDef);
        Button buttonBodyDef = findViewById(R.id.buttonBodyDef);
        Button buttonLegsDef = findViewById(R.id.buttonLegsDef);

        Button buttonDontAttack = findViewById(R.id.buttonDontAttack);

        if (energy < 2) {
            buttonHeadPowerAttack.setEnabled(false);
            buttonBodyPowerAttack.setEnabled(false);
            buttonLegsPowerAttack.setEnabled(false);
        }
        if (energy < 1) {
            buttonHeadAttack.setEnabled(false);
            buttonBodyAttack.setEnabled(false);
            buttonLegsAttack.setEnabled(false);
        }
        View.OnClickListener listenerAttack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeAttack = ((Button) view).getText().toString();
                switch (placeAttack) {
                    case "Голова":
                        attack = " ATTACK HEAD";
                        break;
                    case "Тело":
                        attack = " ATTACK BODY";
                        break;
                    case "Ноги":
                        attack = " ATTACK LEGS";
                        break;
                }
            }
        };
        buttonHeadAttack.setOnClickListener(listenerAttack);
        buttonBodyAttack.setOnClickListener(listenerAttack);
        buttonLegsAttack.setOnClickListener(listenerAttack);

        View.OnClickListener listenerPowerAttack = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeAttack = ((Button) view).getText().toString();
                switch (placeAttack) {
                    case "Голова":
                        attack = " POWER HEAD";
                        break;
                    case "Тело":
                        attack = " POWER BODY";
                        break;
                    case "Ноги":
                        attack = " POWER LEGS";
                        break;
                }
            }
        };
        buttonHeadPowerAttack.setOnClickListener(listenerPowerAttack);
        buttonBodyPowerAttack.setOnClickListener(listenerPowerAttack);
        buttonLegsPowerAttack.setOnClickListener(listenerPowerAttack);

        buttonDontAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attack = " NOT_ATTACK EMPTY";
            }
        });

        View.OnClickListener listenerDef = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeDef = ((Button) view).getText().toString();
                switch (placeDef) {
                    case "Голова":
                        def = " HEAD";
                        break;
                    case "Тело":
                        def = " BODY";
                        break;
                    case "Ноги":
                        def = " LEGS";
                        break;
                }
            }
        };
        buttonHeadDef.setOnClickListener(listenerDef);
        buttonBodyDef.setOnClickListener(listenerDef);
        buttonLegsDef.setOnClickListener(listenerDef);
    }
}