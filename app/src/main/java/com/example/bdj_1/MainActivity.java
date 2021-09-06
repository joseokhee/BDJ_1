package com.example.bdj_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
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

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends AppCompatActivity {
    TessBaseAPI tess;
    String dataPath;
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

    public String processImage(Bitmap bitmap){
        String OCRresult=null;
        tess.setImage(bitmap);
        OCRresult=tess.getUTF8Text();
        return OCRresult;
    }

    private void copyFiles(String lang){
        try{
          String filepath=dataPath+"/tessdata/"+lang+".traineddata";

            AssetManager assetManager=getAssets();

            InputStream inStream=assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outStream=new FileOutputStream(filepath);

            byte[] buffer=new byte[1024];
            int read;
            while((read=inStream.read(buffer))!=-1){
                outStream.write(buffer,0,read);
            }
            outStream.flush();
            outStream.close();
            inStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void checkFile(File dir,String lang){
        boolean t1=dir.exists();
        boolean t2=dir.mkdirs();


        if(!dir.exists() && dir.mkdirs()){
            copyFiles(lang);
        }
        if(dir.exists()){
            String datafilepath=dataPath+"/tessdata/"+lang+".traineddata";
            File datafile=new File(datafilepath);
            boolean a= datafile.exists();
            if(!datafile.exists()){
                copyFiles(lang);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                   // showimg.setImageBitmap(img);

                    dataPath=getFilesDir()+"/tesseract/";
                    checkFile(new File(dataPath+"tessdata/"),"kor");
                    checkFile(new File(dataPath+"tessdata/"),"eng");

                    String lang="kor+eng";
                    tess=new TessBaseAPI();
                    tess.init(dataPath,lang);



                    newtext = processImage(img);

                    File saveFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/camdata");

                    if(!saveFile.exists()){
                        saveFile.mkdir();
                    }

                    try{
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String nowTime = sdf.format(date);

                        BufferedWriter buf = new BufferedWriter(new FileWriter(saveFile+"/txtData.txt",true));
                        buf.append(nowTime+" | ");
                        buf.append(newtext);
                        buf.newLine();
                        buf.close();
                        Log.d("텍스트파일저장","됐음");
                    } catch (FileNotFoundException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    Intent intent2 = new Intent(this,ShowTextActivity.class);
                    intent2.putExtra("newtext",newtext);
                    startActivity(intent2);
                    finish();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "사진오바스",Toast.LENGTH_LONG).show();
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
