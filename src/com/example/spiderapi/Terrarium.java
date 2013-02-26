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

	private static int TerrainAvailable = 0;
	private static int Terrain = 100;
	private static long TerranDecreaseTimer = 10000;
	
	public static void OnCreate()
	{
		MsgMenager.AddLoadingInfo(0, "Loading Terrarium");
		
		bmpTerrariumBitmap = GameCore.GetGraphicEngine().LoadBitmap(TerrBitmapID);
		
		if(bmpTerrariumBitmap == null) return;
			
		bmpTerrariumBitmap = Bitmap.createScaledBitmap(bmpTerrariumBitmap, GameCore.GetGraphicEngine().getScreenWidth(), GameCore.GetGraphicEngine().getScreenHeight() - GameCore.GetGraphicEngine().getScreenHeight()/15, true);
		
		TerrariumWidth = bmpTerrariumBitmap.getWidth();
		TerrariumHeight = bmpTerrariumBitmap.getHeight();
		
		MsgMenager.AddTerrariumInfo(1, "TerrainAvailable: " + TerrainAvailable + "");
		DataMenager.LoadTerrarium();
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
		DataMenager.SaveTerrarium();
		bmpTerrariumBitmap = null;
	}
	
	public static void OnUpdate(long diff)
	{
		if(TerranDecreaseTimer < diff)
		{
			--Terrain;		
			TerranDecreaseTimer = 10000;
			MsgMenager.AddTerrariumInfo(0, "Terrain: " + Terrain + "%");
		}TerranDecreaseTimer -= diff;
		
		if(Terrain < 1)
		{
			/// co sie dzieje jak ziemia do wymiany ! ?
		}
	}
	
	public static void ChangeTerrain()
	{
		if(TerrainAvailable > 0)
		{
			Terrain = 100;
			--TerrainAvailable;
			MsgMenager.AddTerrariumInfo(1, "TerrainAvailable: " + TerrainAvailable + "");
		}
	}

	public static int GetTerrain() { return Terrain; }
	public static int GetAvailableTerrain() { return TerrainAvailable; }

	public static void SetTerrain(int int1) { Terrain = int1; }
	public static void SetAvailableT(int int1) { TerrainAvailable = int1; }
}
