package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ListShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_show);

        Intent intent = getIntent();
        String nt = intent.getStringExtra("listText");

        nt = nt.substring(nt.indexOf("|")+1);

        TextView newText = findViewById(R.id.showtext);
        newText.setText(nt);

    }
    public void mainLogo(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
