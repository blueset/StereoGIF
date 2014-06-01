
package com.bluesetStudio.stereogif;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;

public class GalleryActivity extends Activity {
    
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private StereoGIF stereoGIF;
    private static final String E_TAG = "gallery";  
    private String[] photoStrings;
    final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        stereoGIF = (StereoGIF) getApplication();
        photoStrings = stereoGIF.getPhotoPath();
        gridView = (GridView) findViewById(R.id.gridView_gallery);
        FileScannerTask fileScannerTask = new FileScannerTask();
        fileScannerTask.execute(Environment.getExternalStorageDirectory() + "/StereoGIF/");
        
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                stereoGIF.setGIFPath(photoStrings[position]);
                Intent previewIntent = new Intent(GalleryActivity.this, GIFPreview.class);
                previewIntent.putExtra("isGallery", true);
                startActivity(previewIntent);
                
            }
            
        });
    }

    class FileScannerTask extends AsyncTask<String, String, ArrayList<ImageItem>>{
    
        @Override
        protected ArrayList<ImageItem> doInBackground(String... params) {
            ArrayList<String> lstFile = new ArrayList<String>();  //结果 List
            File[] files = new File(params[0]).listFiles();
            String Extension = ".gif";
            for (int i = 0; i < files.length; i++){
                File f = files[i];
                if (f.isFile()){
                    if (f.getPath().substring(f.getPath().length() - Extension.length()).equals(Extension))  //判断扩展名
                        lstFile.add(f.getPath());
                }
            }
            photoStrings = (String[]) lstFile.toArray(new String[lstFile.size()]);
            for (String pathString : lstFile){
                File bitmapFile = new File(pathString);
                if (bitmapFile.exists()){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Bitmap bitmap = BitmapFactory.decodeFile(pathString,options);
                    imageItems.add(new ImageItem(bitmap, bitmapFile.getName()));
                }
            }
            return imageItems;
        }
        
        @Override
        protected void onPostExecute(ArrayList<ImageItem> arrayList){
            
            gridAdapter = new GridViewAdapter(GalleryActivity.this, R.layout.gridview_gallery_item, arrayList);
            gridView.setAdapter(gridAdapter);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar_gallery);
            progressBar.setVisibility(View.GONE);
            GridView gridView = (GridView) findViewById(R.id.gridView_gallery);
            gridView.setVisibility(View.VISIBLE);
            
        }
        

    }

}
