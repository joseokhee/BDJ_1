package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.File;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0;
    private ImageView showimg;
    private ImageView picimg;
    private String newtext; //OCR된 텍스트 저장 할 곳

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showimg = findViewById(R.id.imageView2);
        picimg = findViewById(R.id.pic_img);

        picimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    showimg.setImageBitmap(img);

                    newtext = "아악하기시렁";
                    saveExcel();

                    Intent intent2 = new Intent(this,ShowTextActivity.class);
                    intent2.putExtra("newtext",newtext);
                    startActivity(intent2);
                    finish();

                }catch(Exception e){

                }
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "사진오바스",Toast.LENGTH_LONG).show();
            }
        }
    }


    public void saveExcel(){

        jxl.write.Number txt = null;

        try{

            String strDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            String strFileName = "mblist.xls";

            InputStream is = getBaseContext().getResources().getAssets().open("mblist.xls");
            Workbook wb = Workbook.getWorkbook(is);

            WritableWorkbook writableWorkbook = Workbook.createWorkbook(new File(strDir, strFileName), wb);

            WritableSheet sheet = writableWorkbook.getSheet(0);

            int idx_x = sheet.getRows();
            int idx_y = 0;

            jxl.write.Label cellTestLabel = new Label(idx_x,idx_x,newtext,sheet.getWritableCell(idx_x,idx_y).getCellFormat());

            sheet.addCell(cellTestLabel);

            writableWorkbook.write();
            writableWorkbook.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //메소드 테스트 용 토스트 출력 메소드
    public void TestToast(String message){
        Toast.makeText(getApplication(),message,Toast.LENGTH_LONG).show();
    }


    //저장된 목록 볼 때 실행 될 메소드
    public void SaveList(View view){
        Intent intent = new Intent(this,TextListActivity.class);
        startActivity(intent);
    }


}
