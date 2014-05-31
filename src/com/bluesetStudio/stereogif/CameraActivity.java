package com.bluesetStudio.stereogif;

import com.bluesetStudio.stereogif.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
<<<<<<< HEAD
import android.app.AlertDialog;
import android.content.DialogInterface;
=======
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
<<<<<<< HEAD
import android.widget.ImageView;
=======
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> d27d50b67986b23b60dd2cc2289cd16e9b26e574
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc

import java.io.File;
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

    SurfaceView sView;  
    SurfaceHolder surfaceHodler;  
    int screenWidth, screenHeight;  
    Camera camera;  // 定义系统所用的照相机 
    boolean isPreview = false; // 是否存在预览中  
    //*** End. ***
            
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_camera);
        StereoGIF = (StereoGIF) getApplication();

<<<<<<< HEAD
       
=======
<<<<<<< HEAD
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView,
                HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView
                                    .animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE
                                    : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
=======
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc
        //OnClick Next
        
        Button nextButton = (Button) findViewById(R.id.button_next);
        nextButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent photoSelectionIntent = new Intent(CameraActivity.this, PhotoSelection.class);
                startActivity(photoSelectionIntent);
                
<<<<<<< HEAD

=======
>>>>>>> d27d50b67986b23b60dd2cc2289cd16e9b26e574
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc
            }
        });
        
       
        
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
<<<<<<< HEAD
        
        

        //dispatchTakePictureIntent();
        
        
        //Code for full control camera
        // 获取窗口管理器  
        WindowManager wm = getWindowManager();  
        Display display = wm.getDefaultDisplay();  
        DisplayMetrics metrics = new DisplayMetrics();  
        // 获取屏幕的宽和高  
        display.getMetrics(metrics);
        screenWidth = metrics.widthPixels;  
        screenHeight = metrics.heightPixels;  
        sView = (SurfaceView) findViewById(R.id.sView);  
        // 获得SurfaceView的SurfaceHolder  
        surfaceHodler = sView.getHolder();  
        // 为srfaceHolder添加一个回调监听器  
        surfaceHodler.addCallback(new Callback() {  
  
            @Override  
            public void surfaceDestroyed(SurfaceHolder arg0) {  
                // 如果camera不为null，释放摄像头  
                if (camera != null) {  
                    if (isPreview)  
                        camera.stopPreview();  
                    camera.release();  
                    camera = null;  
                }  
            }  
  
            @Override  
            public void surfaceCreated(SurfaceHolder arg0) {  
                // 打开摄像头  
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
=======
        findViewById(R.id.dummy_button).setOnTouchListener(
                mDelayHideTouchListener);
        findViewById(R.id.dummy_button).setOnClickListener(mDummyButtonOnClickListener);
        
        dispatchTakePictureIntent();
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc
        
        
    }
<<<<<<< HEAD
=======
    
  //Code for full control of camera
    private void initCamera() {  
        if (!isPreview) {  
            // 此处默认打开后置摄像头  
            // 通过传入参数可以打开前置摄像头  
            camera = Camera.open();  
            camera.setDisplayOrientation(90);  
        }  
        if (!isPreview && camera != null) {  
            Camera.Parameters parameters = camera.getParameters();  
            // 设置预览照片的大小  
            parameters.setPreviewSize(screenWidth, screenHeight);  
            // 设置预览照片时每秒显示多少帧的最小值和最大值  
            parameters.setPreviewFpsRange(4, 10);  
            // 设置照片的格式  
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置JPG照片的质量  
            parameters.set("jpeg-quality", 85);  
            // 设置照片的大小  
            parameters.setPictureSize(screenWidth, screenHeight);  
            // 通过SurfaceView显示取景画面  
            try {  
                camera.setPreviewDisplay(surfaceHodler);  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            // 开始预览  
            camera.startPreview();  
            isPreview = true;  
        }  
    }  
    
    public void capture(View source) {  
        if (camera != null) {  
            // 控制摄像头自动对焦后才拍摄  
            camera.autoFocus(autoFocusCallback);  
        }  
    }  
    
    AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {  
        
        @Override  
        public void onAutoFocus(boolean arg0, Camera arg1) {  
            if (arg0) {  
                // takePicture()方法需要传入三个监听参数  
                // 第一个监听器；当用户按下快门时激发该监听器  
                // 第二个监听器；当相机获取原始照片时激发该监听器  
                // 第三个监听器；当相机获取JPG照片时激发该监听器  
                camera.takePicture(new ShutterCallback() {  
  
                    @Override  
                    public void onShutter() {  
                        // 按下快门瞬间会执行此处代码  
                    }  
                }, new PictureCallback() {  
  
                    @Override  
                    public void onPictureTaken(byte[] arg0, Camera arg1) {  
                        // 此处代码可以决定是否需要保存原始照片信息  
                    }  
                }, myJpegCallback);  
            }  
  
        }  
    };
    public Bitmap caputBitmap;
    PictureCallback myJpegCallback = new PictureCallback() {  
        
        @Override  
        public void onPictureTaken(byte[] data, Camera camera) {  
            
            // 根据拍照所得的数据创建位图  
            final Bitmap caputBitmap = BitmapFactory.decodeByteArray(data, 0,  
                    data.length);  
            //ImageView previewView = (ImageView) findViewById(R.id.imageView_preview);
            //previewView.setImageBitmap(caputBitmap);
            //previewView.setVisibility(View.VISIBLE);
            
            final View saveDialog = getLayoutInflater().inflate(R.layout.dialog_photo_confirm, null);  
            ImageView show = (ImageView) saveDialog.findViewById(R.id.imageView_previewDiag);
            TextView textPathTextView = (TextView) saveDialog.findViewById(R.id.TextViewFileLocation);
            
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            final String filePathString = Environment  
                    .getExternalStorageDirectory()  
                    + "/StereoGIF/"  
                    + imageFileName  
                    + ".jpg";
            
            
            textPathTextView.setText(filePathString);
            
            show.setImageBitmap(caputBitmap);
            
            new AlertDialog.Builder(CameraActivity.this)
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
                                    //把位图输出到指定的文件中  
                                    caputBitmap.compress(CompressFormat.JPEG, 100, fileOutStream);  
                                    fileOutStream.close();  
                                    
                                    
                                } catch (IOException io) {  
                                    io.printStackTrace();  
                                }  
                                StereoGIF.addPhotoPath(filePathString);
                            }
                        }).show();
            camera.stopPreview();  
            camera.startPreview();  
            isPreview=true;  
        }  
        
    };  
    //end;
<<<<<<< HEAD
    
    
=======
>>>>>>> d27d50b67986b23b60dd2cc2289cd16e9b26e574

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }
    
    View.OnClickListener mDummyButtonOnClickListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            
            showImage();
        }
    };

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    //


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //Toast.makeText(this, mCurrentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
    }
   
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            showImage();
            
        }
    }
    private void showImage(){
        Log.i("main", mCurrentPhotoPath);
        		
        ImageView imageView = (ImageView) findViewById(R.id.imageView_preview);
        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

        /* Get the size of the ImageView */
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        Log.i(mTAG, "TargetW,"+Integer.toString(targetW)+". TargetH,"+Integer.toString(targetH));
        
        
        /* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        
        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH); 
        }
        Log.i(mTAG,"scale="+Integer.toString(scaleFactor));

        
        /* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        
        /* Associate the Bitmap to the ImageView */
        imageView.setImageBitmap(bitmap);
        
    }
    

>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc
}
