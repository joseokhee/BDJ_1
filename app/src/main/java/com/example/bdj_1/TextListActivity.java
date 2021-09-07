package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class TextListActivity extends Activity implements View.OnClickListener {


    private TableLayout tableLayout;
    private TableRow tableRow;
    private TableRow rowList[];
    private String savetxt[];
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_list);

        tableLayout = findViewById(R.id.tableLayout);

        readExcel();

    }


    @Override
    public void onClick(View v){
        TableRow newRow = (TableRow) v;
        for(TableRow temp : rowList){
            if(temp == newRow){
                int position = (Integer)v.getTag();
                intent = new Intent(this,ListShowActivity.class);
                intent.putExtra("listText",savetxt[position]);
                startActivity(intent);
                Log.d("제발","제발요 : "+savetxt[position]);
            }
        }
    }


    public void readExcel(){

        String test = null;
        String line = null;
        File saveFile = new File(getExternalFilesDir(null)+"/camdata");

        if(!saveFile.exists()){
            saveFile.mkdirs();
        }

        int num = 0;
        int num2 = 0;

        try{
            BufferedReader buf = new BufferedReader(new FileReader(saveFile + "/txtData.txt"));
            BufferedReader buf2 = new BufferedReader(new FileReader(saveFile + "/txtData.txt"));
            while((test = buf2.readLine())!=null){
                num=num+1;
            }
            Log.d("출력해봄",""+num);

            rowList = new TableRow[num];
            savetxt = new String[num];

            while((line=buf.readLine())!=null){
                Log.d("엥왜안됨?",""+line);
                savetxt[num2] = line;
                Log.d("savetxt",""+savetxt[num2]);

                tableRow = new TableRow(this);
                tableRow.setLayoutParams(new TableRow.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));


                String line2 = line.substring(line.indexOf("|")+1);
                Log.d("내용",""+line2);
                line = line.substring(0,line.indexOf(" "));
                Log.d("내용",""+line);

                TextView textView = new TextView(this);
                textView.setText(String.valueOf(line));
                textView.setGravity(Gravity.LEFT);

                TextView textView2 = new TextView(this);
                textView2.setText(String.valueOf(line2));
                textView2.setGravity(Gravity.LEFT);

                tableRow.addView(textView);
                tableRow.addView(textView2);

                tableRow.setTag(num2);
                Log.d("Main", "아이디 : " + tableRow.getTag());

                rowList[num2] = tableRow;
                tableLayout.addView(tableRow);
                rowList[num2].setOnClickListener(this);

                num2 += 1;
            }
            buf2.close();
            buf.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void mainLogo(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
