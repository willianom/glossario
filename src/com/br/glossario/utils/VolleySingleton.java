package com.br.glossario.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

	private static VolleySingleton mInstance = null;
	
	// FIla de execu��o
	private RequestQueue mRequestQueue;
	// Image Loader
	private ImageLoader mImageLoader;
	
	private VolleySingleton(Context context){
		mRequestQueue = Volley.newRequestQueue(context);
		
		mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
			
			// Usando LRU (Last Recent Used) Cache
	        private final LruCache<String, Bitmap> 
	          mCache = new LruCache<String, Bitmap>(10);
	        
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				 mCache.put(url, bitmap);				
			}
			
			@Override
			public Bitmap getBitmap(String url) {
				return mCache.get(url);
			}
		});
	}
	
	public static VolleySingleton getInstance(
		    Context context){

		    if(mInstance == null){
		      mInstance = new VolleySingleton(context);
		    }
		    return mInstance;
		  }
	
	public RequestQueue getRequestQueue(){
	    return this.mRequestQueue;
	  }
	 
	  public ImageLoader getImageLoader(){
	    return this.mImageLoader;
	  }
}
