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
    private EditText nameBox;
    private EditText famBox;
    private Button button;
    static final String NAME = "NAME";
    static final String FAM = "FAM";
    static final String ACCESS_MESSAGE = "ACCESS";
    private static final int REQUEST_ACCESS_TYPE = 1;
    //https://metanit.com/java/android/2.11.php
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textOutput);
        nameBox = findViewById(R.id.editTextName);
        famBox = findViewById(R.id.editTextFam);
        button = findViewById(R.id.buttonSend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameBox.getText().toString();
                String fam = famBox.getText().toString();
                Intent intent = new Intent
                        (MainActivity.this, SecondActivity.class);
                intent.putExtra(NAME, name);
                intent.putExtra(FAM, fam);
                mStartForResult.launch(intent);
            }
        });
    }
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    TextView textView = findViewById(R.id.textView);
                    if(result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        String accessMessage =
                                intent.getStringExtra(ACCESS_MESSAGE);
                        textView.setText(accessMessage);
                    } else
                        textView.setText("Ошибка доступа");
                }
            }
    );
}