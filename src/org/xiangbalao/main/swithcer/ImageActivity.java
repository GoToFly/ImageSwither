package org.xiangbalao.main.swithcer;

import java.util.TimerTask;

import org.xiangbalao.swithcer.AutoSwithcerManager;
import org.xiangbalao.swithcer.GuideGallery;
import org.xiangbalao.swithcer.ImageAdapter;

import com.drocode.swithcer.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ImageActivity extends Activity {

	ImageAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.url_connection_image);

		init();
		starSwithcerTast();
	}

	private void starSwithcerTast() {

		AutoSwithcerManager.timeTaks = new ImageTimerTask();
		AutoSwithcerManager.autoGallery.scheduleAtFixedRate(
				AutoSwithcerManager.timeTaks, 1000, 1000);
		AutoSwithcerManager.timeThread = new Thread() {
			public void run() {
				while (!AutoSwithcerManager.isExit) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (AutoSwithcerManager.timeTaks) {
						if (!AutoSwithcerManager.timeFlag) {
							AutoSwithcerManager.timeTaks.timeCondition = true;
							AutoSwithcerManager.timeTaks.notifyAll();
						}
					}
					AutoSwithcerManager.timeFlag = true;
				}
			};
		};
		AutoSwithcerManager.timeThread.start();
	}

	private void init() {

		Bitmap image = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon);

		AutoSwithcerManager.images_ga = (GuideGallery) findViewById(R.id.image_wall_gallery);
		imageAdapter = new ImageAdapter(this);
		AutoSwithcerManager.images_ga.setAdapter(imageAdapter);

		LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		pointLinear.setBackgroundColor(Color.argb(200, 135, 135, 152));
		for (int i = 0; i < 4; i++) {
			ImageView pointView = new ImageView(this);
			if (i == 0) {
				pointView.setBackgroundResource(R.drawable.feature_point_cur);
			} else
				pointView.setBackgroundResource(R.drawable.feature_point);
			pointLinear.addView(pointView);
		}
		AutoSwithcerManager.images_ga
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						switch (arg2 % 4) {
						case 0:
							Toast.makeText(ImageActivity.this, arg2 + "", 0)
									.show();
							AutoSwithcerManager.uri = AutoSwithcerManager.uri
									.parse("http://www.36939.net/");
							Intent intent = new Intent(Intent.ACTION_VIEW,
									AutoSwithcerManager.uri);
							// startActivity(intent);

							Log.i("top", "http://www.36939.net/");

							break;

						default:
							break;
						}

					}
				});

	}

	public void changePointView(int cur) {
		LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		View view = pointLinear.getChildAt(AutoSwithcerManager.positon);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			AutoSwithcerManager.positon = cur;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// AutoSwithcerManager.timeTaks.timeCondition = true;
		AutoSwithcerManager.timeFlag = false;
	
		
	}

	@Override
	protected void onPause() {
		super.onPause();

		AutoSwithcerManager.timeTaks.timeCondition = false;
	}

	public class ImageTimerTask extends TimerTask {
		public volatile boolean timeCondition = true;

		public void run() {
			synchronized (this) {
				while (!timeCondition) {
					try {
						Thread.sleep(100);
						wait();
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
			}
			try {

				AutoSwithcerManager.gallerypisition = AutoSwithcerManager.images_ga
						.getSelectedItemPosition() + 1;
				System.out.println(AutoSwithcerManager.gallerypisition + "");
				Message msg = new Message();
				Bundle date = new Bundle();// 存放数据
				date.putInt("pos", AutoSwithcerManager.gallerypisition);
				msg.setData(date);
				msg.what = 1;// 消息标识
				AutoSwithcerManager.autoGalleryHandler.sendMessage(msg);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

}