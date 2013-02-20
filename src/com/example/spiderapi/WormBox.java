package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class WormBox 
{
	private static int nPositionX = 0;
	private static int nPositionY = 0;
	private static int nHeight = 100;
	private static int nWidth = 100;

	private static int WormboxBitmapID = R.drawable.wormbox01; 
	private static int EmptyWormboxBitmapID = R.drawable.wormbox01addworm;

	private static Bitmap bmpEmptyBox = null;
	private static Bitmap bmpBox = null;
	private static Bitmap bmpOnDrawReady = null;
	
	private static boolean IsBoxEmpty = true;
		
	public static void OnCreate()
	{	
		bmpBox = GFXSurface.GetSurface().LoadBitmap(WormboxBitmapID);	
		bmpEmptyBox = GFXSurface.GetSurface().LoadBitmap(EmptyWormboxBitmapID);
		
		bmpBox = Bitmap.createScaledBitmap(bmpBox, GFXSurface.getScreenWidth()/3, GFXSurface.getScreenHeight()/15, true);
		bmpEmptyBox = Bitmap.createScaledBitmap(bmpEmptyBox, GFXSurface.getScreenWidth()/3, GFXSurface.getScreenHeight()/15, true);
		
		if(bmpBox != null)
		{
			nWidth = bmpBox.getWidth();
			nHeight = bmpBox.getHeight();
		}
		
		nPositionX = GFXSurface.getScreenWidth() - nWidth;
		nPositionY = GFXSurface.getScreenHeight() - nHeight - 100;		
	}
	
	public static boolean IsOnPosition(float fOnTouchX, float fOnTouchY)
	{
	    if( ( fOnTouchX > nPositionX ) && ( fOnTouchX < nPositionX + nWidth) && ( fOnTouchY > nPositionY ) && ( fOnTouchY < nPositionY + nHeight ) )
	        return true;

	    return false;
	}
	
	public static boolean IsEmpty()
	{
		return IsBoxEmpty;
	}
	
	public static void OnUpdate(long diff)
	{		
		IsBoxEmpty = true;
		
		for(int i=0; i<WormMenager.GetWormsNumber(); ++i)
		{
			if(WormMenager.GetWormOnListPosition(i) != null)
			{
				if(WormMenager.GetWormOnListPosition(i).IsInWormBox() == true)
					IsBoxEmpty = false;
			}		
		}
		
		if(IsBoxEmpty == true)
			bmpOnDrawReady = bmpEmptyBox;
		else 
			bmpOnDrawReady = bmpBox;
	}
	
	public static void OnDraw(Canvas canvas) 
	{		
		if(bmpOnDrawReady != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpOnDrawReady, nPositionX, nPositionY);
	}	
	
	public static float GetPositionX() { return nPositionX; }
	public static float GetPositionY() { return nPositionY; }
	public static float GetHeight() { return nHeight; }
	public static float GetWidth() { return nWidth; }
	
	public static void SetPosition(float fOnTouchX, float fOnTouchY) { nPositionX = (int) fOnTouchX; nPositionY = (int) fOnTouchY; }
}
