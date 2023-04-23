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

import java.util.Objects;

public class SecondActivity extends AppCompatActivity {

    private int playerOneHp = 10;
    private int playerOneEnergy = 5;
    private int playerTwoHp = 10;
    private int playerTwoEnergy = 5;
    private TextView playerOneStatsText;
    private TextView playerTwoStatsText;
    private Button buttonActionPlayerOne;
    private Button buttonActionPlayerTwo;
    private TextView playerOneText;
    private TextView playerTwoText;
    static final String PLAYER = "PLAYER";
    static final String ENERGY = "ENERGY";
    static final String HEALTH = "HEALTH";
    static final String MESSAGE = "MESSAGE";
    String message;
    String[] playerOneAction = null;
    String[] playerTwoAction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Bundle extras = getIntent().getExtras();
        String playerOne = "";
        String playerTwo = "";
        if (extras != null) {
            playerOne = extras.getString(MainActivity.PLAYER_ONE);
            playerTwo = extras.getString(MainActivity.PLAYER_TWO);
            playerOneText = findViewById(R.id.playerOneName);
            playerTwoText = findViewById(R.id.playerTwoName);
            playerOneText.setText(playerOne);
            playerTwoText.setText(playerTwo);
        }
        refreshPlayerStats();

        buttonActionPlayerOne = findViewById(R.id.actionPlayerOne);
        buttonActionPlayerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerOne = "PLAYER_ONE";
                Intent intent = new Intent
                        (SecondActivity.this, ThirdActivity.class);
                intent.putExtra(PLAYER, playerOne);
                intent.putExtra(ENERGY, playerOneEnergy);
                intent.putExtra(HEALTH, playerOneHp);
                buttonActionPlayerOne.setEnabled(false);
                mStartForResult.launch(intent);
            }
        });
        buttonActionPlayerTwo = findViewById(R.id.actionPlayerTwo);
        buttonActionPlayerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerTwo = "PLAYER_TWO";
                Intent intent = new Intent
                        (SecondActivity.this, ThirdActivity.class);
                intent.putExtra(PLAYER, playerTwo);
                intent.putExtra(ENERGY, playerTwoEnergy);
                intent.putExtra(HEALTH, playerTwoHp);
                buttonActionPlayerTwo.setEnabled(false);
                mStartForResult.launch(intent);
            }
        });
    }

    public void refreshPlayerStats() {
        playerOneStatsText = findViewById(R.id.playerOneStats);
        playerTwoStatsText = findViewById(R.id.playerTwoStats);
        playerOneStatsText.setText(playerOneHp + " здоровья\n\n" + playerOneEnergy + " энергии");
        playerTwoStatsText.setText(playerTwoHp + " здоровья\n\n" + playerTwoEnergy + " энергии");
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    TextView textView = findViewById(R.id.textViewInfo);
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
//                        String accessMessage =
//                                intent.getStringExtra(ACCESS_MESSAGE);
                        message = intent.getStringExtra(MESSAGE);
                        parseMessage(message);
                        if (playerOneAction != null && playerTwoAction != null) {
                            turnResult();
                        }
                        //textView.setText(message);
                    }
                }
            }
    );

    public void turnResult() {
        TextView textViewInfo = findViewById(R.id.textViewInfo);
        String result = "";
        int multiply = 1;
        int damage = 0;
        switch (playerOneAction[1]) {
            case "POWER":
                result += "Сильная атака И1 в ";
                multiply = 2;
                playerOneEnergy -= 2;
                break;
            case "ATTACK":
                result += "Атака И1 в ";
                multiply = 1;
                playerOneEnergy--;
                break;
            case "NOT_ATTACK":
                result += "И1 не атаковал";
                multiply = 0;
                playerOneEnergy++;
                break;
        }
        switch (playerOneAction[2]) {
            case "HEAD":
                result += "голову";
                damage = 3;
                break;
            case "BODY":
                result += "тело";
                damage = 2;
                break;
            case "LEGS":
                result += "ноги";
                damage = 1;
                break;
            default:
                damage = 0;
                break;
        }
        if (Objects.equals(playerOneAction[2], playerTwoAction[3])) {
            result += " заблокирована";
        } else {
            result += " прошла";
            playerTwoHp -= damage * multiply;
        }
        textViewInfo.setText(textViewInfo.getText() + result + "\n");
        result = "";
        switch (playerTwoAction[1]) {
            case "POWER":
                result += "Сильная атака И2 в ";
                multiply = 2;
                playerTwoEnergy -= 2;
                break;
            case "ATTACK":
                multiply = 1;
                result += "Атака И2 в ";
                playerTwoEnergy--;
                break;
            case "NOT_ATTACK":
                result += "И2 не атаковал";
                multiply = 0;
                playerTwoEnergy++;
                break;
        }
        switch (playerTwoAction[2]) {
            case "HEAD":
                result += "голову";
                damage = 3;
                break;
            case "BODY":
                result += "тело";
                damage = 2;
                break;
            case "LEGS":
                result += "ноги";
                damage = 1;
                break;
            default:
                damage = 0;
                break;
        }
        if (Objects.equals(playerTwoAction[2], playerOneAction[3])) {
            result += " заблокирована";
        } else {
            result += " прошла";
            playerOneHp -= damage * multiply;
        }
        textViewInfo.setText(textViewInfo.getText() + result + "\n");
        playerOneAction = null;
        playerTwoAction = null;
        refreshPlayerStats();
        buttonActionPlayerOne = findViewById(R.id.actionPlayerOne);
        buttonActionPlayerOne.setEnabled(true);
        buttonActionPlayerTwo = findViewById(R.id.actionPlayerTwo);
        buttonActionPlayerTwo.setEnabled(true);
    }

    public void parseMessage(String message) {
        if (Objects.equals(message.split(" ")[0], "PLAYER_ONE")) {
            playerOneAction = message.split(" ");
        } else {
            playerTwoAction = message.split(" ");
        }
    }
}