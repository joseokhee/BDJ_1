package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show);

        Intent intent = getIntent();
        String nt = intent.getStringExtra("listText");

        TextView newText = findViewById(R.id.showtext);
        newText.setText(nt);

    }
}
