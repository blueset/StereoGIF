package com.bluesetStudio.stereogif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Button onclick
        ImageButton buttonShot = (ImageButton) findViewById(R.id.button_shot);
        buttonShot.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // Create and start activity "Camera Activity"
                Intent intentCamera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intentCamera);
                
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
        
    }
    /*  Simply fetch the image from camera and show it's thumbnail some where.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap); 
        }
    }
    */
    
}
