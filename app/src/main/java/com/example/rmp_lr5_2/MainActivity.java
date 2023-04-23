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
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText playerOneBox;
    private EditText playerTwoBox;
    private Button button;
    static final String PLAYER_ONE = "PLAYER_ONE";
    static final String PLAYER_TWO = "PLAYER_TWO";
    static final String ACCESS_MESSAGE = "ACCESS";
    private static final int REQUEST_ACCESS_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textOutput);
        playerOneBox = findViewById(R.id.editTextPlayer1);
        playerTwoBox = findViewById(R.id.editTextPlayer2);
        button = findViewById(R.id.buttonSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String playerOne = playerOneBox.getText().toString();
                String playerTwo = playerTwoBox.getText().toString();
                Intent intent = new Intent
                        (MainActivity.this, SecondActivity.class);
                intent.putExtra(PLAYER_ONE, playerOne);
                intent.putExtra(PLAYER_TWO, playerTwo);
                mStartForResult.launch(intent);
            }
        });
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    TextView textView = findViewById(R.id.textOutput);
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        String accessMessage =
                                intent.getStringExtra(SecondActivity.WINNER);
                        textView.setText(accessMessage);
                    } else
                        textView.setText("Ошибка доступа");
                }
            }
    );
}


//    Intent intent = new Intent(this, MainActivity.class);
//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(intent);
