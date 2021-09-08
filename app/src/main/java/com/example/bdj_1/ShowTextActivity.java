package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                File saveFile = new File(getExternalFilesDir(null)+"/camdata");
                if(!saveFile.exists()){
                    saveFile.mkdirs();
                    Log.d("폴더","만들어짐");
                }

                try{
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String nowTime = sdf.format(date);

                    BufferedWriter buf = new BufferedWriter(new FileWriter(saveFile+"/txtData.txt",true));
                    buf.append(nowTime+" | ");
                    buf.append(nt);
                    buf.newLine();
                    buf.close();
                    Log.d("텍스트파일저장","됐음");
                } catch (FileNotFoundException e){
                    Log.d("텍스트파일저장","안 됐음");
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    //메소드 테스트 용 토스트 출력 메소드
    public void TestToast(String message){
        Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show();
    }

    public void mainLogo(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
