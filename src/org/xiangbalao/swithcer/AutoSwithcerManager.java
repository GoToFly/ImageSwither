package org.xiangbalao.swithcer;

import java.util.Timer;

import org.xiangbalao.main.swithcer.ImageActivity.ImageTimerTask;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;


/**
 * @author longtaoger
 * 
 */
public class AutoSwithcerManager {

	public static GuideGallery images_ga;
	public static int positon = 0;
	public static Thread timeThread = null;
	public static boolean timeFlag = true;
	public static boolean isExit = false;
	public static ImageTimerTask timeTaks = null;
	public static Uri uri;
	public static int gallerypisition = 0;
	public static Timer autoGallery = new Timer();
	
	
	public static final Handler autoGalleryHandler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			switch (message.what) {
			case 1:
				AutoSwithcerManager.images_ga.setSelection(message.getData()
						.getInt("pos"));
				break;
			}
		}
	};
	
	
	
	
	
	
	
	
}
