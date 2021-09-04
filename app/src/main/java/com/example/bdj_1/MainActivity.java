package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Build;
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

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends AppCompatActivity {

    private TessBaseAPI mTess;

    private static final int REQUEST_CODE = 0;
    private ImageView showimg;
    private ImageView picimg;
    private String newtext; //OCR된 텍스트 저장 할 곳

    Bitmap img;

    private String datapath = ""; //데이터경로

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showimg = findViewById(R.id.imageView2);
        picimg = findViewById(R.id.pic_img);

        img = BitmapFactory.decodeResource(getResources(),R.drawable.sampleimg);
        showimg.setImageBitmap(img);

        PermissionCheck();

        mTess = new TessBaseAPI();

        String dir = getFilesDir() + "/tessract/";
        Log.d("씨발새끼",""+dir);

        if(checkLanguageFile(dir+"/tessdata")) {
            Log.d("씨발새끼",""+dir);
            mTess.init(dir, "kor+eng");
        }


        picimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        Intent intent = new Intent();
        //        intent.setType("image/*");

         //       intent.setAction(Intent.ACTION_GET_CONTENT);
         //       startActivityForResult(intent,REQUEST_CODE);
                processImage();
            }

        });
    }

    boolean checkLanguageFile(String dir)
    {
        File file = new File(dir);
        if(!file.exists() && file.mkdirs())
            createFiles(dir);
        else if(file.exists()){
            String filePath = dir + "/eng.traineddata";
            File langDataFile = new File(filePath);
            if(!langDataFile.exists())
                createFiles(dir);
        }
        return true;
    }

    private void createFiles(String dir)
    {
        AssetManager assetMgr = this.getAssets();

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = assetMgr.open("eng.traineddata");

            String destFile = dir + "/eng.traineddata";

            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processImage() {
        Log.d("","화내기 : 여기까진 됐음");
        String OCRresult = null;
        Log.d("","화내기 : 여기도 됐음");
        mTess.setImage(img);
        Log.d("","화내기 : 여기까지도 아직 됐음");
        OCRresult = mTess.getUTF8Text();
        Log.d("","화내기 : 여기가 안 되네 시팔");
        Log.d("","결과 : "+OCRresult);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    in.close();

                    showimg.setImageBitmap(img);

                    String OCRresult = null;
                    mTess.setImage(img);
                    OCRresult = mTess.getUTF8Text();
                    newtext = OCRresult;
                    //saveExcel();

                    //Intent intent2 = new Intent(this,ShowTextActivity.class);
                    //intent2.putExtra("newtext",newtext);
                    //startActivity(intent2);
                    //finish();

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


    public void PermissionCheck() {
        /**
         * 6.0 마시멜로우 이상일 경우에는 권한 체크후 권한을 요청한다.
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED &&
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                // 권한 없음
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ConstantDefine.PERMISSION_CODE);
            } else {
                // 권한 있음
            }
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
