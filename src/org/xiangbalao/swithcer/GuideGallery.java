package org.xiangbalao.swithcer;

import java.util.TimerTask;

import org.xiangbalao.main.swithcer.ImageActivity;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GuideGallery extends Gallery {

	//private ImageActivity m_iact;

	public GuideGallery(Context context) {
		super(context);

	}

	public GuideGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GuideGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}



	/**
	 * e1 ��ʼ������������ָ��һ�δ������λ�� e2 ���һ����ָ�������λ�ã� ���������� velocityX
	 * ����fling��X���ϵ��ٶȣ�����/ÿ��
	 * 
	 * ����velocityY ����fling��Y���ϵ��ٶȣ�����/ÿ��
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
			System.out.println("AAAA" + this.getSelectedItemPosition());
		} else { // Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
			System.out.println("BBB" + this.getSelectedItemPosition());
		}
		onKeyDown(kEvent, null);
		if (this.getSelectedItemPosition() == 0)
			this.setSelection(ImageAdapter.imgs.length);
		System.out.println("DDD" + this.getSelectedItemPosition());
		new java.util.Timer().schedule(new TimerTask() {
			public void run() {
				AutoSwithcerManager.timeFlag = false;
				this.cancel();
			}
		}, 2000);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		System.out.println(this.getSelectedItemPosition());
		return e2.getX() > e1.getX();

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		AutoSwithcerManager.timeTaks.timeCondition = false;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

}
