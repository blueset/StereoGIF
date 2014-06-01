
package com.bluesetStudio.stereogif;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GIFPreview extends Activity {
    
    private StereoGIF stereoGIF;
    String gifPathString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_preview);
        
        //init Vars
        stereoGIF = (StereoGIF) getApplication();
        gifPathString = stereoGIF.getGIFPath();
        GifView gifView = (GifView) findViewById(R.id.gifView_gif_preview);
        try {
            byte[] gifByte = readFile(gifPathString);
            gifView.setGifImage(gifByte);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //if isGallery
        Intent intent = getIntent();  
        boolean isGallery = intent.getBooleanExtra("isGallery", false); 
        if(isGallery){
            TextView tempTextView = (TextView) findViewById(R.id.textView_gif_preview_l1);
            tempTextView.setText(R.string.text_gif_preview);
            tempTextView = (TextView) findViewById(R.id.textView_gif_preview_l2);
            tempTextView.setText(getString(R.id.textView_gif_preview_l2)+gifPathString);
        }
        
        //Share.onClick
        Button shareButton = (Button) findViewById(R.id.button_share);
        shareButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);  
                shareIntent.setType("image/*");  
                Uri uri = Uri.fromFile(new File(gifPathString));  
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);  
                startActivity(Intent.createChooser(shareIntent, getString(R.string.popup_share_title)));  
                
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gifpreview, menu);
        return true;
    }
    
    public static byte[] readFile(String file) throws IOException {
        return readFile(new File(file));
    }

    public static byte[] readFile(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }

}
