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
		MsgMenager.AddMassage(0, "Loading Terrarium");
		
		bmpTerrariumBitmap = GameCore.GetGraphicEngine().LoadBitmap(TerrBitmapID);
		
		if(bmpTerrariumBitmap == null) return;
			
		bmpTerrariumBitmap = Bitmap.createScaledBitmap(bmpTerrariumBitmap, GameCore.GetGraphicEngine().getScreenWidth(), (int) (GameCore.GetGraphicEngine().getScreenHeight() - WormBox.GetHeight()), true);
		
		TerrariumWidth = bmpTerrariumBitmap.getWidth();
		TerrariumHeight = bmpTerrariumBitmap.getHeight();		
	}
	
	public static int GetWidth() {return TerrariumWidth; }
	public static int GetHeight() {return TerrariumHeight; }	

	public static void OnDraw(Canvas canvas)
	{
		if(bmpTerrariumBitmap != null)
			GameCore.GetGraphicEngine().OnDraw(canvas, bmpTerrariumBitmap, TerrariumPosX, TerrariumPosY);
	}

	public static void OnDelete() 
	{
		bmpTerrariumBitmap = null;
	}
	
	public static void OnUpdate(long diff)
	{
		
	}
}
