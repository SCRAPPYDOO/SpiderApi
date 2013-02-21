package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class BackgroundMenager 
{
	private static Bitmap bmpLoadingScreen = null;
	
	private static Bitmap bmpDrawableBitmap = null;
	
	public static void OnCreate()
	{
		bmpLoadingScreen = GFXSurface.GetSurface().LoadBitmap(BitmapID[0]);
		if(bmpLoadingScreen != null)
			bmpLoadingScreen = Bitmap.createScaledBitmap(bmpLoadingScreen, GFXSurface.getScreenWidth(), GFXSurface.getScreenHeight(), true);	
	}
	
	private static int BitmapID[] =
	{
		R.drawable.hello,
		R.drawable.menu_background_hdpi,
	};

	public static void LoadBackground()
	{
		LoadBackground(GFXSurface.GetCurrentGameState()); 
	}
	
	public static void LoadBackground(EnumGameState loadingscreen) 
	{
		switch(loadingscreen)
		{
			case LaunchingScreen: bmpDrawableBitmap = bmpLoadingScreen; break;
			case LoadingScreen: bmpDrawableBitmap = bmpLoadingScreen; break;
			case Game: bmpDrawableBitmap = null; break;
			case InGameMenu:
			case InGameSpiderStat:
			case InGameWormShop:
			case MainMenu:
				break;
				
			default:
				break;
		}	
	}
	
	public static void OnDraw(Canvas canvas) 
	{
		if(bmpDrawableBitmap != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpDrawableBitmap, 0, 0);
	}


}
