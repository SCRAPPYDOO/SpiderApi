package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Terrarium 
{
	private static int TerrariumPosX = 0;
	private static int TerrariumPosY = 0;
	private static int TerrariumWidth = 400;
	private static int TerrariumHeight = 600;	
	private static Bitmap bmpTerrariumBitmap = null;
	private static int TerrBitmapID = R.drawable.terrarium01;

	public static void OnCreate()
	{
		bmpTerrariumBitmap = GFXSurface.GetSurface().LoadBitmap(TerrBitmapID);
		
		if(bmpTerrariumBitmap == null) return;
			
		bmpTerrariumBitmap = Bitmap.createScaledBitmap(bmpTerrariumBitmap, GFXSurface.getScreenWidth(), (int) (GFXSurface.getScreenHeight() - WormBox.GetHeight()), true);
		
		TerrariumWidth = bmpTerrariumBitmap.getWidth();
		TerrariumHeight = bmpTerrariumBitmap.getHeight();		
	}
	
	public int GetWidth() {return TerrariumWidth; }
	public int GetHeight() {return TerrariumHeight; }	
	
	public static void OnDraw(Canvas canvas)
	{
		if(bmpTerrariumBitmap != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpTerrariumBitmap, TerrariumPosX, TerrariumPosY);
	}

	public static void OnDelete() 
	{
		bmpTerrariumBitmap = null;
	}
}
