package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackgroundMenager 
{
	private static Bitmap bmpLoadingScreen = null;
	
	private static Bitmap bmpDrawableBitmap = null;
	
	public static void OnCreate()
	{
		bmpLoadingScreen = GameCore.GetGraphicEngine().LoadBitmap(BitmapID[0]);
		if(bmpLoadingScreen != null)
			bmpLoadingScreen = Bitmap.createScaledBitmap(bmpLoadingScreen, GameCore.GetGraphicEngine().getScreenWidth(), GameCore.GetGraphicEngine().getScreenHeight(), true);	
	}
	
	private static int BitmapID[] =
	{
		R.drawable.hello,
		R.drawable.menu_background_hdpi,
	};

	public static void LoadBackground()
	{
		LoadBackground(GameCore.GetCurrentGameState()); 
	}
	
	public static void LoadBackground(EnumGameState background) 
	{
		switch(background)
		{
			case LaunchingScreen: bmpDrawableBitmap = bmpLoadingScreen; break;
			case LoadingScreen: bmpDrawableBitmap = bmpLoadingScreen; break;
			case Game: bmpDrawableBitmap = null; break;
			case InGameMenu:
			case InGameSpiderStat:
			case InGameWormShop:
			case MainMenu:
				bmpDrawableBitmap = null;
				break;
				
			default:
				break;
		}	
	}
	
	public static void OnDraw(Canvas canvas) 
	{
		if(bmpDrawableBitmap != null)
			GameCore.GetGraphicEngine().OnDraw(canvas, bmpDrawableBitmap, 0, 0);
	}
}
