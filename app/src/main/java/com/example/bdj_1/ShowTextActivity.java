package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class ShowTextActivity extends AppCompatActivity {

    private ImageView saveBtn;
    private String nt;
    private int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        Intent intent = getIntent();
        nt = intent.getStringExtra("newtext");

        TextView newText = findViewById(R.id.newTextView);
        newText.setText(nt);

        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestToast(nt);
            }
        });

    }

    //메소드 테스트 용 토스트 출력 메소드
    public void TestToast(String message){
        Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show();
    }
}
