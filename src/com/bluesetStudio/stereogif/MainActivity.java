package com.bluesetStudio.stereogif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
<<<<<<< HEAD

import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

=======
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc

public class MainActivity extends Activity {

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
<<<<<<< HEAD

        //ButtonShot onClick
        ImageButton buttonShot = (ImageButton) findViewById(R.id.button_shot);

=======
        
        Button buttonShot = (Button) findViewById(R.id.button_shot);
>>>>>>> 437cc804f0ea715d4be86b2970c9a7487ca8b4dc
        buttonShot.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // Create and start activity "Camera Activity"
                Intent intentCamera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intentCamera);
                
            }
        });
        
        //ButtonGallery onClick
        Button galleryButton = (Button) findViewById(R.id.button_gallery);
        galleryButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.text_underDevelopement, Toast.LENGTH_SHORT).show();
            }
        });
        
        //Create StereoGIF Folder
        File folder = new File(Environment.getExternalStorageDirectory() + "/StereoGIF");
        if (!folder.exists()) {
            folder.mkdir();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
        
    }
    
    //Action bar manager
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.mainAB_action_setting:
                onClickSetting();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    //OnClick setting
    public void onClickSetting(){
        Intent settingIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingIntent);
    }
    
}
