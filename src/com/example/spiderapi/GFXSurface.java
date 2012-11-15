package com.example.spiderapi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class GFXSurface extends Activity implements OnTouchListener
{
	SurfaceClass Surface = null;
	Spider spider = null;
	boolean CanGetMoveOrders = true;
	WakeLock wL =  null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
		setContentView(Surface);
		spider = new Spider(this, Surface);
		
		//wakelock
		PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "whatever");
		wL.acquire();
	}

	@Override
	protected void onPause() 
	{
		super.onPause();
		Surface.pause();
		wL.release();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		Surface.resume();
		wL.acquire();
	}

	//called when u touch the screen with this activity opened
	public boolean onTouch(View v, MotionEvent event)
	{		
		float fOnTouchX = event.getX();
		float fOnTouchY = event.getY();
		
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:		
				break;
			case MotionEvent.ACTION_UP:
				if(CanGetMoveOrders)
					spider.SetUpWaypoint(fOnTouchX, fOnTouchY, 0);
				break;
		}

		return true;
	}
	
	public class SurfaceClass extends SurfaceView implements Runnable
	{
		SurfaceHolder surfHolder;
		Thread ThreadOne = null;
		Thread ThreadTwo = null;
		boolean IsRunning = false;

		Bitmap test = null;
	
		public void OnDraw(Canvas canvas,Bitmap bitmap,float fPosX, float fPosY)
		{
			canvas.drawBitmap(bitmap, fPosX, fPosY, null);
		}		
		
		public Bitmap LoadBitmap(int SpiderID, int BitmapID)
		{
			Bitmap bmp = null;
			switch(SpiderID)
			{
				case 0:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spider); break;
						case 1: break;
					}
					break;
				}
				case 1:
				{
					switch(BitmapID)
					{
						case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.spider); break;
						case 1: break;
					}
					break;					
				}	
			}
			
			return bmp; 
		}		
		
		public SurfaceClass(Context context)
		{
			super(context);
			surfHolder = getHolder();	
		}

		public void pause()
		{
			IsRunning = false;
			while(true)
			{
				try
				{
					ThreadOne.join();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				break;
			}
			ThreadOne = null;
		}
		
		public void resume()
		{
			IsRunning = true;
			ThreadOne = new Thread(this);
			ThreadOne.start();
			ThreadTwo = new Thread(this);
			ThreadTwo.start();
		}
				
		public void run() 
		{
			Thread currentthread = Thread.currentThread();
			if(currentthread == null)
				return;
						
			while(IsRunning)
			{	
				if(currentthread == ThreadOne)
				{	
					if(!surfHolder.getSurface().isValid())
						continue;
					
					//draw background
					Canvas canvas = surfHolder.lockCanvas();
					canvas.drawRGB(0, 254, 0);
					
					if(spider != null)	
					{	
						spider.OnDraw(canvas);
					}							
			
					surfHolder.unlockCanvasAndPost(canvas);					
				}	
				
				if(currentthread == ThreadTwo)
				{				
					try
					{
						//float FrameRate = 60;
						//1000 / frame rate
						//float PauseTime = 1000 / FrameRate;
						Thread.sleep(16);
					} 
					catch (InterruptedException e) 
					{
						e.printStackTrace();
					}						
						
					if(spider != null)	
					{	
						spider.OnUpdate();
					}											
				}						
			}
		}
	}
}
