package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class InterfaceButton 
{
	private int nPositionX = 0;
	private int nPositionY = 0;
	private int nHeight = 0;
	private int nWidth = 0;
	private int nButtonID= 0;
	private int nBitmapID= 0;
	private Bitmap bmpBitmap = null;
	
	public InterfaceButton(int ButtonID)
	{
		nButtonID = ButtonID;
		OnCreate();
	}
	
	public void OnCreate()
	{			
		nBitmapID = ButtonBitmapIDTable[nButtonID-300];
		bmpBitmap = GameCore.GetGraphicEngine().LoadBitmap(nBitmapID);
			
		if(bmpBitmap == null)
			return;	

		ScaleBitmap();

		nWidth = bmpBitmap.getWidth();
		nHeight = bmpBitmap.getHeight();

		SetUpPosition();
				
		ButtonMenager.AddButton(this);
	}

	private void SetUpPosition() 
	{
		switch(nButtonID)
		{
			case 300:
				nPositionX = 0;
				nPositionY = GameCore.GetGraphicEngine().getScreenHeight() - nHeight;
				break;
			
			case 301:
				nPositionX = nWidth;
				nPositionY = GameCore.GetGraphicEngine().getScreenHeight() - nHeight;
				break;
				
			case 302:
				nPositionX = nWidth*2;
				nPositionY = GameCore.GetGraphicEngine().getScreenHeight() - nHeight;
				break;
				
			case 303:
				nPositionX = 160;
				nPositionY = 300;
				break;
			case 304:
				nPositionX = 160;
				nPositionY = 400;
				break;		
			case 305:
				nPositionX = 160;
				nPositionY = 500;
				break;	
			case 306:
				nPositionX = 160;
				nPositionY = 600;
				break;	
				
			default: break;
		}
	}

	private void ScaleBitmap() 
	{		
		switch(nButtonID)
		{
			case 300: 
			case 301: 
			case 302:
			case 303:
			case 304:
			case 305:
			case 306:
				bmpBitmap = Bitmap.createScaledBitmap(bmpBitmap, GameCore.GetGraphicEngine().getScreenWidth()/3, GameCore.GetGraphicEngine().getScreenHeight()/15, true);
				break;
				
			default: break;
		}
	}

	public void OnDraw(Canvas canvas) 
	{
		if(bmpBitmap != null)
			GameCore.GetGraphicEngine().OnDraw(canvas, bmpBitmap, nPositionX, nPositionY);	
	}

	public void OnClickMove()
	{
		switch(nButtonID)
		{		
			default: break;
		}		
	}
	
	public void OnClickUp() 
	{
		switch(nButtonID)
		{
			case 300: 
				if(GameCore.GetCurrentGameState() == EnumGameState.InGameMenu)
					GameCore.SetCurrentGameState(EnumGameState.Game);
				else
					GameCore.SetCurrentGameState(EnumGameState.InGameMenu);
				break;
			case 301:
				if(GameCore.GetCurrentGameState() == EnumGameState.InGameSpiderStat)
					GameCore.SetCurrentGameState(EnumGameState.Game);
				else
					GameCore.SetCurrentGameState(EnumGameState.InGameSpiderStat);			
				break;
			case 302: break;
			case 303:
				if(GameCore.GetCurrentGameState() == EnumGameState.MainMenu)
					GameCore.SetCurrentGameState(EnumGameState.Game);		
				break;
			case 304: break; //launch main menu options
			case 305: break; //tworcy :DD
			case 306: GameCore.QuitFromGame(); break; //Quit from game
				
			default: break;
		}
		
		Log.i("InterfaceButton", "OnClickUp: " + nButtonID + "");
	}
	
	public void OnClickDown()
	{		
		switch(nButtonID)
		{	
			default: break;
		}		
	}

	public boolean IsOnPosition(float positionX, float positionY) 
	{
	    if( ( positionX > nPositionX ) && ( positionX < nPositionX + nWidth) && ( positionY > nPositionY ) && ( positionY < nPositionY + nHeight ) )
	        return true;

	    return false;
	}
	
	private int ButtonBitmapIDTable[] =
	{
		//bitmapid, posX, posY
		R.drawable.ingame_button_menu, 
		R.drawable.ingame_button_spider, 
		R.drawable.ingame_button_wormbox, 
		R.drawable.mainmenu_button_play, 
		R.drawable.mainmenu_button_options,
		R.drawable.mainmenu_button_dev,
		R.drawable.mainmenu_button_quit,
	};
}
