package com.bluesetStudio.stereogif;

import android.R.string;
import android.app.Application;

public class StereoGIF extends Application {
    private int photoCount;
    private String[] photoPath;
    
    public int getPhotoCount(){
        return photoCount;
    }
    
    public void setPhotoSount(int p){
        photoCount = p;
    }
    
    public String[] getPhotoPath(){
        return photoPath;
    }
    
    public String getPhotoPath(int i){
        return photoPath[i];
    }
    
    /**
     * Set photoPath with id.  
     * @param path The path string.
     * @param i The id of photoPath array
     */
    public void setPhotoPaths(String path, int i){
        photoPath[i] = path;  
    }
    
    /**
     * Set photoPath as new item.
     * @param path Path string.
     */
    public void setPhotoPaths(String path){
        photoPath[photoPath.length] = path;
    }
    
    @Override
    public void onCreate(){
        super.onCreate();
        setPhotoSount(0);
    }
}
