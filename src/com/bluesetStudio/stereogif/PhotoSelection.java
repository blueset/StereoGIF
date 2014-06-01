package com.bluesetStudio.stereogif;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhotoSelection extends Activity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private StereoGIF stereoGIF;
    private static final String E_TAG = "photoSelection";  
    private String[] photoStrings;
    private SharedPreferences sharedPreferences;
    private ProgressDialog gifGenerateProgressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selection);
        stereoGIF = (StereoGIF) getApplication();
        photoStrings = stereoGIF.getPhotoPath();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.gridview_gallery_item, getData());;
        gridView.setAdapter(gridAdapter);
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        // retrieve String drawable array
        
        for (int i = 0; i < photoStrings.length; i++) {
            File bitmapFile = new File(photoStrings[i]);
            if (bitmapFile.exists()){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                Bitmap bitmap = BitmapFactory.decodeFile(photoStrings[i],options);
                imageItems.add(new ImageItem(bitmap, "Image #" + i));
            }
        }
        return imageItems;
 
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_selection_action_bar, menu);
        return true;
    }
    
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.photoSelectAB_item_discard:
                photoPathsDiscard();
                return true;
            case R.id.photoSelectAB_item_next:
                generateGIF();
                return true;
            case R.id.photoSelectAB_item_Settings:
                startIntentSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void photoPathsDiscard(){
        DiscardDialogFragemnt discardDialogFragemnt = new DiscardDialogFragemnt();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        discardDialogFragemnt.show(fragmentTransaction, "discardPhotoPreview");
    }
    
    private void generateGIF(){
        gifGenerateProgressDialog = new ProgressDialog(PhotoSelection.this);
        gifGenerateProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        gifGenerateProgressDialog.setCancelable(false);
        gifGenerateProgressDialog.setMessage(getString(R.string.progress_loading_image));
        gifGenerateProgressDialog.show();
        GIFGenerateTask gifGenerateTask = new GIFGenerateTask();
        gifGenerateTask.execute(photoStrings);
    }
    
    private void startIntentSettings(){
        Intent settingsIntent = new Intent(PhotoSelection.this,SettingsActivity.class);
        startActivity(settingsIntent);
    }
    class GIFGenerateTask extends AsyncTask<String[], String, byte[]>{

        @Override
        protected byte[] doInBackground(String[]... params) {
            Log.v(E_TAG,"doInBg");
            ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
            for (String path : params[0]){
                Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap tempBitmap =BitmapFactory.decodeFile(path,options);
                int sizeFactor = Integer.parseInt(sharedPreferences.getString("pref_GIF_size", "4"));
                if (sizeFactor == 4){
                    publishProgress(getApplication().getString(R.string.error_get_GIF_size));
                    sizeFactor = 2;
                }
                int curWid = options.outWidth, curHei = options.outHeight;
                int newWid, newHei;
                switch (sizeFactor) {
                    case 1:
                        newWid = 500;
                        break;
                    case 2:
                        newWid = 300;
                        break;
                    case 3:
                        newWid = 100;
                        break;
                    default:
                        newWid = 300;
                        publishProgress(getApplication().getString(R.string.error_get_GIF_size));
                        break;
                }
                newHei = Math.round(((float) (curHei*newWid)/newWid));
                int sampleSize = options.outWidth / 200;
                options.outHeight = newHei;
                options.outWidth = newWid;
                options.inSampleSize = sampleSize;
                options.inJustDecodeBounds = false;
                
                tempBitmap = BitmapFactory.decodeFile(path,options);
                bitmaps.add(tempBitmap);
                
                
            }
            publishProgress(getApplication().getString(R.string.progress_generating_GIF));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.setDelay(Integer.parseInt(sharedPreferences.getString("pref_GIF_delay", "100")));
            encoder.setQuality(Integer.parseInt(sharedPreferences.getString("pref_GIF_quality", "256")));
            encoder.setRepeat(0);
            
            encoder.start(bos);
            for (Bitmap bitmap : bitmaps) {
                encoder.addFrame(bitmap);
            }
            encoder.finish();
            Log.v(E_TAG,"doInBgEnd");
            return bos.toByteArray();
        }
        
        @Override
        protected void onProgressUpdate(String... params){
            Log.v(E_TAG,"prog: "+params[0]);
            gifGenerateProgressDialog.setMessage(params[0]);
        }

        @Override
        protected void onPostExecute (byte[] byteArray){
            Log.v(E_TAG,"postExe");
            gifGenerateProgressDialog.setMessage(getApplication().getString(R.string.progress_saving_GIF));
            BufferedOutputStream stream = null;  
            File file = null;  
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "GIF_" + timeStamp + "_";
            final String filePathString = Environment  
                    .getExternalStorageDirectory()  
                    + "/StereoGIF/"  
                    + imageFileName  
                    + ".gif";
            
            try {  
                file = new File(filePathString);  
                FileOutputStream fstream = new FileOutputStream(file);  
                stream = new BufferedOutputStream(fstream);  
                stream.write(byteArray);  
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                if (stream != null) {  
                    try {  
                        stream.close();  
                        gifGenerateProgressDialog.dismiss();
                    } catch (IOException e1) {  
                        e1.printStackTrace();  
                    }  
                }  
                stereoGIF.setGIFPath(filePathString);
            }
            Log.v(E_TAG,"postExeDone");
            Toast.makeText(getApplicationContext(), "Created GIF: "+filePathString, Toast.LENGTH_LONG).show();
            Intent GIFpreviewIntent = new Intent(PhotoSelection.this, GIFPreview.class);
            startActivity(GIFpreviewIntent);
        }
        



    } 

}
