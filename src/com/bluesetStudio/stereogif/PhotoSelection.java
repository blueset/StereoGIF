package com.bluesetStudio.stereogif;

import android.R.integer;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

public class PhotoSelection extends Activity {

    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private StereoGIF stereoGIF;
    private static final String E_TAG = "photoSelection";  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_selection);
        stereoGIF = (StereoGIF) getApplication(); 
        gridView = (GridView) findViewById(R.id.gridView);
        gridAdapter = new GridViewAdapter(this, R.layout.gridview_gallery_item, getData());
        gridView.setAdapter(gridAdapter);
    }

    private ArrayList<ImageItem> getData() {
        final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
        // retrieve String drawable array
        String[] photoStrings = stereoGIF.getPhotoPath();
        Log.v(E_TAG, "length of photoString is "+Integer.toString(photoStrings.length));
        Log.v(E_TAG,"starting loop from 0 to "+Integer.toString(photoStrings.length));
        for (int i = 0; i < photoStrings.length; i++) {
            Log.v(E_TAG,"I'm going through the loooooooop.");
            File bitmapFile = new File(photoStrings[i]);
            if (bitmapFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(photoStrings[i]);
                imageItems.add(new ImageItem(bitmap, "Image #" + i));
            }
        }
        Log.v(E_TAG,"It ends as: "+imageItems.toString());
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

}
