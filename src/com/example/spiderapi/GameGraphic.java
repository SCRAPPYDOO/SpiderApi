package com.example.spiderapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Matrix.ScaleToFit;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameGraphic extends SurfaceView
{
	public GameGraphic(Context context) 
	{
		super(context);
		this.OnCreate();
	}
	
	//Screen Size variables
    private int screenHeight = 0;
    private int screenWidth = 0;	
    //Screen Size methods
    public int getScreenHeight() { return screenHeight; }
    public int getScreenWidth() { return screenWidth; }
	public void SetScreenWidth(int width) { screenWidth = width; }
	public void SetScreenHeight(int height) { screenHeight = height; }	
	
	private SurfaceHolder surfHolder = null;
	
	public void OnCreate()
	{
		surfHolder = getHolder();
	    screenHeight = 0;
	    screenWidth = 0;  
	}
	
	public void OnUpdate()
	{	
        //code something u want to do
		if(!surfHolder.getSurface().isValid())
			return;	
		
		Canvas canvas = surfHolder.lockCanvas();
		canvas.drawRGB(0, 0, 0);
		
		//Displaying Shit starts from here
		BackgroundMenager.OnDraw(canvas);
						
		if(GameCore.GetLoadingState() != true)
		{
			switch(GameCore.GetCurrentGameState())
			{
				case LaunchingScreen:
				{
				
					break;
				}
			    case LoadingScreen:
			    {
			    	break;
			    }
				case Game:
				{
					Terrarium.OnDraw(canvas);		
					WormMenager.OnDraw(canvas);
					AnimalMenager.OnDraw(canvas);
					
					break;
				}
				case InGameSpiderStat:
				{
					break;
				}
				case InGameMenu:
				{
					break;
				}
				case MainMenu:
				{
					
					break;
				}
				default: break;
			}					

			ButtonMenager.OnDraw(canvas);
									
		}
		MsgMenager.OnDraw(canvas);

		surfHolder.unlockCanvasAndPost(canvas);
    }
	
	public void OnDraw(Canvas canvas,Bitmap bitmap,float fPosX, float fPosY)
	{
		if(bitmap == null || canvas == null)
			return;
		
		canvas.drawBitmap(bitmap, fPosX, fPosY, null);
	}
	
	public void OnDraw(Canvas canvas,Bitmap bitmap, Rect src, Rect dst)
	{
		if(bitmap == null || canvas == null)
			return;

		canvas.drawBitmap(bitmap, null, dst, null);
	}
	
	public void OnDraw(Canvas canvas,Bitmap bitmap, RectF src, RectF dst)
	{
		if(bitmap == null || canvas == null)
			return;

		Matrix matrix = new Matrix();
		matrix.reset();
		ScaleToFit stf = ScaleToFit.FILL;
		matrix.setRectToRect(src, dst, stf);		

		canvas.drawBitmap(bitmap, matrix, null);
	}
	
	public Bitmap LoadBitmap(int BitmapID)
	{
		return BitmapFactory.decodeResource(getResources(), BitmapID);
	}
	
}
