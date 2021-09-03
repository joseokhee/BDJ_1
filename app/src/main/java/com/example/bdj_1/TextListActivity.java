package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class TextListActivity extends Activity implements View.OnClickListener {


    private TableLayout tableLayout;
    private TableRow tableRow;
    private TableRow rowList[];
    private String savetxt[];
    private Intent intent;
    private Integer txtnum = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_list);

        tableLayout = findViewById(R.id.tableLayout);

        readExcel();

        //애들이 잘 저장되어 있나 확인하는 용도
        //for(int i = 1; i < rowList.length; i ++){
            //Log.d("list ",i+"번째");
            //Log.d("in ","id : "+rowList[i]);
            //Log.d("txt ","save : "+savetxt[i]);
            //rowList[i].setTag(i);
            //Log.d("txt ","save : "+ rowList[i].getTag());
            //rowList[i].setOnClickListener(this);
        //}

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

        try{
            InputStream is = getBaseContext().getResources().getAssets().open("mblist.xls");
            Workbook wb = Workbook.getWorkbook(is);


            if(wb != null){
                Sheet sheet = wb.getSheet(0);
                if(sheet != null){

                    int num = sheet.getRows();
                    rowList = new TableRow[num];
                    savetxt = new String[num];

                    int colTotal = 3;
                    int rowIndexStart = 1;
                    int rowTotal = sheet.getColumn(colTotal-1).length;

                    StringBuilder sb;

                    for(int row=rowIndexStart; row<rowTotal; row++){
                        sb = new StringBuilder();

                        tableRow = new TableRow(this);
                        tableRow.setLayoutParams(new TableRow.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        ));

                        for(int col = 0; col<colTotal; col++){

                            String contents = sheet.getCell(col, row).getContents();

                            Log.d("Main",col + "번째 : " + contents);

                            TextView textView = new TextView(this);
                            textView.setText(String.valueOf(contents));
                            textView.setGravity(Gravity.CENTER);
                            tableRow.addView(textView);

                        }

                        // 밑에 두 줄 = 배열에 글 저장 하기
                        String contents2 = sheet.getCell(3, row).getContents();
                        savetxt[row] = contents2;

                        tableRow.setTag(row);
                        Log.d("Main", "아이디 : " + tableRow.getTag());
                        rowList[row] = tableRow;
                        tableLayout.addView(tableRow);
                        rowList[row].setOnClickListener(this);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
