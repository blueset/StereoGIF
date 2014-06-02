package com.bluesetStudio.stereogif;

import com.bluesetStudio.stereogif.util.SystemUiHider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class CameraActivity extends Activity {
    private StereoGIF StereoGIF; 
    
    private int photoCount = 0;
    static final int REQUEST_TAKE_PHOTO = 1;
    
    static final int REQUEST_IMAGE_CAPTURE = 1;
    
    private static String mTAG = "CameraActivity"; 
    
    private SensorManager mSensorManager;
    private Sensor mSensor;
    
    private ImageView semiTransImageView;

    private boolean isNewActivity = true;
    SurfaceView sView;  
    SurfaceHolder surfaceHodler;  
    int screenWidth, screenHeight;  
    Camera camera;  // ����ϵͳ���õ������ 
    boolean isPreview = false; // �Ƿ����Ԥ����  
    //*** End. ***
            
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_camera);
        StereoGIF = (StereoGIF) getApplication();
        
        semiTransImageView = (ImageView) findViewById(R.id.imageView_semi_trans_preview);
        //Assign sensors
        /*
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        
        SensorEventListener mSensorEventListener = new SensorEventListener() {
            
            @Override
            public void onSensorChanged(SensorEvent event) {
                //if(photoCount > 0){
                if (true){
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];
                    float a = event.values[3];
                    
                    double cosA = 2*Math.acos(a)/3.1415926535897932384626433832795028841971693993*180;
                    double calcY = (y)/(Math.sin(cosA/2));
                    
                    TextView sensorInfoTextView = (TextView) findViewById(R.id.rotate_sensor_info);
                    String text;
                    text = getString(R.string.text_sensor_info);
                    sensorInfoTextView.setText(String.format(text,a,cosA,y,calcY));
                    
                }
            }
            
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // TODO Auto-generated method stub
                
            }
        };
        
        mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        */
        
        //OnClick photoCountNumber
        final TextView photoCountTextView = (TextView) findViewById(R.id.textView_photoCount);
        photoCountTextView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (semiTransImageView.getVisibility() == View.VISIBLE){
                    semiTransImageView.setVisibility(View.INVISIBLE);                    
                }else{
                    semiTransImageView.setVisibility(View.VISIBLE);
                }
                
            }
        });
        
        //OnClick Next
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (camera != null) {  
                    if (isPreview)  
                        camera.stopPreview();  
                    camera.release();  
                    camera = null;  
                }  
                isPreview = false;
                Intent photoSelectionIntent = new Intent(CameraActivity.this, PhotoSelection.class);
                startActivity(photoSelectionIntent);
            }
        });
        
        //Code for full control camera
        // ��ȡ���ڹ�����  
        WindowManager wm = getWindowManager();  
        Display display = wm.getDefaultDisplay();  
        DisplayMetrics metrics = new DisplayMetrics();  
        // ��ȡ��Ļ�Ŀ�͸�  
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;  
        screenHeight = metrics.heightPixels;  
        sView = (SurfaceView) findViewById(R.id.sView);  
        // ���SurfaceView��SurfaceHolder  
        surfaceHodler = sView.getHolder();  
        // ΪsrfaceHolder���һ���ص�������  
        surfaceHodler.addCallback(new Callback() {  
  
            @Override  
            public void surfaceDestroyed(SurfaceHolder arg0) {  
                // ���camera��Ϊnull���ͷ�����ͷ  
                if (camera != null) {  
                    if (isPreview)  
                        camera.stopPreview();  
                    camera.release();  
                    camera = null;  
                }  
            }  
  
            @Override  
            public void surfaceCreated(SurfaceHolder arg0) {  
                // ������ͷ  
                initCamera();  
  
            }  
  
            @Override  
            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,  
                    int arg3) {  
            }  
        });  
        //end;


        findViewById(R.id.dummy_button).setOnClickListener(new OnClickListener( ) {
            
            @Override
            public void onClick(View arg0) {
                if (camera != null){
                    capture(sView);
                }
                
            }
        });
        
        sView.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                if (camera != null){
                    camera.autoFocus(null);
                }
                
            }
        });
        
         
        
    }
    
  //Code for full control of camera
    private void initCamera() {  
        
    }  
    
    
    public void capture(View source) {  
        if (camera != null) {  
            // ��������ͷ�Զ��Խ��������  
            camera.autoFocus(autoFocusCallback);  
        }  
    }  
    
    AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {  
        
        @Override  
        public void onAutoFocus(boolean arg0, Camera arg1) {  
            if (arg0) {  
                // takePicture()������Ҫ����������������  
                // ��һ�������������û����¿���ʱ�����ü�����  
                // �ڶ������������������ȡԭʼ��Ƭʱ�����ü�����  
                // ���������������������ȡJPG��Ƭʱ�����ü�����  
                camera.takePicture(new ShutterCallback() {  
  
                    @Override  
                    public void onShutter() {  
                        // ���¿���˲���ִ�д˴�����  
                    }  
                }, new PictureCallback() {  
  
                    @Override  
                    public void onPictureTaken(byte[] arg0, Camera arg1) {  
                        // �˴�������Ծ����Ƿ���Ҫ����ԭʼ��Ƭ��Ϣ  
                    }  
                }, myJpegCallback);  
            }  
  
        }  
    };
    public Bitmap caputBitmap;
    PictureCallback myJpegCallback = new PictureCallback() {  
        
        @Override  
        public void onPictureTaken(byte[] data, Camera camera) {  
            
            // �����������õ����ݴ���λͼ  
            Bitmap caputBitmap = BitmapFactory.decodeByteArray(data, 0,  
                    data.length);  
            
            //ImageView previewView = (ImageView) findViewById(R.id.imageView_preview);
            //previewView.setImageBitmap(caputBitmap);
            //previewView.setVisibility(View.VISIBLE);
            
            final Bitmap finalBitmap = caputBitmap;
            
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            
            Matrix matrix = new Matrix(); 
            matrix.postRotate(90);  
            final Bitmap rotatedBitmap = Bitmap.createBitmap(caputBitmap, 0, 0, 
                        caputBitmap.getWidth(), caputBitmap.getHeight(), matrix, true);
                
             
           
              

            
            final View saveDialog = getLayoutInflater().inflate(R.layout.dialog_photo_confirm, null);  
            ImageView show = (ImageView) saveDialog.findViewById(R.id.imageView_previewDiag);
            TextView textPathTextView = (TextView) saveDialog.findViewById(R.id.TextViewFileLocation);
            final TextView photoCountTextView = (TextView) findViewById(R.id.textView_photoCount);
            
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            final String filePathString = Environment  
                    .getExternalStorageDirectory()  
                    + "/StereoGIF/"  
                    + imageFileName  
                    + ".jpg";
            
            
            textPathTextView.setText(filePathString);
            if(sharedPreferences.getBoolean("pref_camera_rotate", true)){
                show.setImageBitmap(rotatedBitmap);
            }else{
                show.setImageBitmap(finalBitmap);
            }
            
            
            final AlertDialog photoConfirmAlertDialog = new AlertDialog.Builder(CameraActivity.this)
                .setView(saveDialog)
                .setNegativeButton(R.string.button_cancel, null)
                .setPositiveButton(R.string.button_save, 
                        new DialogInterface.OnClickListener() {
                            
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                String imageFileName = "JPEG_" + timeStamp + "_";
                                File file = new File(filePathString);  
                                FileOutputStream fileOutStream=null;  
                                try {  
                                    fileOutStream=new FileOutputStream(file);  
                                    //��λͼ�����ָ�����ļ���  
                                    if(sharedPreferences.getBoolean("pref_camera_rotate", true)){
                                        rotatedBitmap.compress(CompressFormat.JPEG, 100, fileOutStream);
                                    }else{
                                        finalBitmap.compress(CompressFormat.JPEG, 100, fileOutStream);
                                    } 
                                    fileOutStream.close();  
                                    
                                    
                                } catch (IOException io) {  
                                    io.printStackTrace();  
                                }  
                                photoCount += 1;
                                photoCountTextView.setText(Integer.toString(photoCount));
                                StereoGIF.addPhotoPath(filePathString);
                                semiTransImageView.setImageBitmap(rotatedBitmap);
                                
                                
                            }
                        }).create();
            photoConfirmAlertDialog.show();
            camera.stopPreview();  
            camera.startPreview();  
            isPreview=true;  
        }  
        
    };  
    //end;
    
    @Override
    public void onResume(){
        
        // open camera
        camera = Camera.open();
        camera.setDisplayOrientation(90); 

        // init surface view
        sView = (SurfaceView)this.findViewById(R.id.sView);
                surfaceHodler = sView.getHolder(); 
                surfaceHodler.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
                surfaceHodler.setSizeFromLayout();
                surfaceHodler.addCallback(new Callback() {  
                    
                    @Override  
                    public void surfaceDestroyed(SurfaceHolder arg0) {  
                        // ���camera��Ϊnull���ͷ�����ͷ  
                        if (camera != null) {  
                            if (isPreview)  
                                camera.stopPreview();  
                            camera.release();  
                            camera = null;  
                        }  
                    }  
          
                    @Override  
                    public void surfaceCreated(SurfaceHolder arg0) {  
                        // ������ͷ  
                         
          
                    }  
          
                    @Override  
                    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,  
                            int arg3) {

                        Camera.Parameters parameters = camera.getParameters();  
                        // ����Ԥ����Ƭ�Ĵ�С  
                        parameters.setPreviewSize(screenWidth, screenHeight);  
                        // ����Ԥ����Ƭʱÿ����ʾ����֡����Сֵ�����ֵ  
                        parameters.setPreviewFpsRange(4, 10);  
                        // ������Ƭ�ĸ�ʽ  
                        parameters.setPictureFormat(ImageFormat.JPEG);
                        // ����JPG��Ƭ������  
                        parameters.set("jpeg-quality", 85);  
                        // ������Ƭ�Ĵ�С  
                        parameters.setPictureSize(screenWidth, screenHeight);  
                        // ͨ��SurfaceView��ʾȡ������  
                        try {  
                            camera.setPreviewDisplay(arg0);  
                            Log.v(mTAG,"if2try");
                        } catch (IOException e) {    
                            e.printStackTrace();
                            Log.v(mTAG,"if2catch");
                        }  
                        // ��ʼԤ��  
                        camera.startPreview();  
                        isPreview = true; 
                        isNewActivity = false;
                    }  
                }); 
        
                
        
        super.onResume();
    }
    @Override  
    public void onBackPressed() {  
        DiscardDialogFragemnt discardDialogFragemnt = new DiscardDialogFragemnt();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        discardDialogFragemnt.show(fragmentTransaction, "discardPhotoPreview");
        //super.onBackPressed();           
    
    }
}


