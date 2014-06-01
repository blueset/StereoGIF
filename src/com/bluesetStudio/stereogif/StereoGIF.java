package com.bluesetStudio.stereogif;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;

public class StereoGIF extends Application {
    private int photoCount = 0;
    private ArrayList<String> photoPath = new ArrayList<String>();
    private String GIFpathString;
    private static final String E_TAG = "globalVar";  
    
    public int getPhotoCount(){
        return photoCount;
    }
    
    public void setPhotoCount(int p){
        photoCount = p;
    }
    
    public String[] getPhotoPath(){
        return (String[])photoPath.toArray(new String[photoPath.size()]);
    }
    
    public String getPhotoPath(int i){
        return (String)photoPath.get(i);
    }
    
    /**
     * Set photoPath with id.  
     * @param path The path string.
     * @param i The id of photoPath array
     */
    public void setPhotoPaths(String path, int i){
        if (i < photoPath.size()){
            photoPath.set(i, path);
        } else {
          addPhotoPath (path);  
        }
    }
    
    /**
     * Set photoPath as new item.
     * @param path Path string.
     */
    public void addPhotoPath(String path){
        photoPath.add(path);
        Log.v(E_TAG, path+" is added to the paths.");
    }
    /**
     * Get the size of photoPath
     * @return Size of photoPath
     */
    public int getPhotoPathCount(){
        return photoPath.size();
    }
    public void clearPhotoPath(){
        photoPath.clear();
    }
    
    
    public String getGIFPath(){
        return GIFpathString;
    }
    
    public void setGIFPath(String path){
        GIFpathString = path;
    }
    
    public void clearGIFPath(){
        GIFpathString = "";
    }
    @Override
    public void onCreate(){
        super.onCreate();
        setPhotoCount(0);
    }
    
}
