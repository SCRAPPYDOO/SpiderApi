package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

public class InterfaceButton 
{
	private int nPositionX = 0;
	private int nPositionY = 0;
	private int nHeight = 0;
	private int nWidth = 0;
	private int nButtonID= 0;
	private int nBitmapID= 0;
	private Bitmap bmpBitmap = null;
	private boolean IsDrawAble = true;
	private EnumGameState gamestateToDraw = EnumGameState.MainMenu;
	
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
		
		//SetGameStateToDraw();		
		
		ButtonMenager.AddButton(this);
	}

	private void SetGameStateToDraw() 
	{
		EnumGameState GameStateTable[] =
		{
			EnumGameState.Game,
			EnumGameState.Game,
			EnumGameState.Game,
		};
		
		if(GameStateTable[nButtonID-300] != null)
			gamestateToDraw = GameStateTable[nButtonID-300];
		
	}

	private void SetUpPosition() 
	{
		int ScreenWidth = GameCore.GetGraphicEngine().getScreenWidth();
		int ScreenHeight = GameCore.GetGraphicEngine().getScreenHeight();
				
		switch(nButtonID)
		{
			//Game UI Buttons
			case 300:
				nPositionX = 0;
				nPositionY = ScreenHeight - nHeight;
				break;
			
			case 301:
				nPositionX = nWidth;
				nPositionY = ScreenHeight - nHeight;
				break;
				
			case 302:
				nPositionX = nWidth*2;
				nPositionY = ScreenHeight - nHeight;
				break;
				
			//Main Menu Buttons
			case 303:
				nPositionX = (int) (ScreenWidth*0.5 - this.nWidth * 0.5);
				nPositionY = (int) (ScreenHeight * 0.5 - 150);
				break;
			case 304:
				nPositionX = (int) (ScreenWidth*0.5 - this.nWidth * 0.5);
				nPositionY = (int) (ScreenHeight * 0.5 - 50);
				break;		
			case 305:
				nPositionX = (int) (ScreenWidth*0.5 - this.nWidth * 0.5);
				nPositionY = (int) (ScreenHeight * 0.5 + 50);
				break;	
			case 306:
				nPositionX = (int) (ScreenWidth*0.5 - this.nWidth * 0.5);
				nPositionY = (int) (ScreenHeight * 0.5 + 150);
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
		if(bmpBitmap != null && IsDrawAble == true)
			GameCore.GetGraphicEngine().OnDraw(canvas, bmpBitmap, nPositionX, nPositionY);
	}

	public void OnUpdate(long diff)
	{
		//Check if  we can draw to screen and be clicked
		IsDrawAble = true;
		
		/*
		EnumGameState CoreState = GameCore.GetCurrentGameState();
		
		switch(nButtonID)
		{
			case 300: if(CoreState == EnumGameState.InGameMenu || CoreState == EnumGameState.Game || CoreState == EnumGameState.InGameSpiderStat || CoreState == EnumGameState.InGameWormShop); IsDrawAble = true; break;
			case 301: 	
		
			default:
				if(CoreState == gamestateToDraw)
					IsDrawAble = true;
				break;
		}
		*/
	}
	
	public void OnClickMove()
	{
		switch(nButtonID)
		{		
			default: break;
		}		
	}
	
	public void OnClickUp(MotionEvent event) 
	{
		if(IsDrawAble == false)
		{
			Log.i("InterfaceButton", "OnClickUp: " + nButtonID + " Button nie jest odblokowany: IsDrawAble == false");
			return;
		}
		
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
			case 302:
				//If we are in worm shop we back to game
				if(GameCore.GetCurrentGameState() == EnumGameState.InGameWormShop)
				{
					GameCore.SetCurrentGameState(EnumGameState.Game);
				}
				else
				{
					//If we push screen when we hold a worm and we are at the worm button
					Worm temp = GameCore.GetGameEvent().GetTouchedWorm();
					if(temp != null)
					{
						WormMenager.AddWormToBox(temp);
						GameCore.GetGameEvent().SetTouchedWorm(null);
					}
					
					if(WormBox.GetWormNumber() <= 0)
					{
						GameCore.SetCurrentGameState(EnumGameState.InGameWormShop);
					}
				}
				
				//if robakow 0 wlacz sklepik
				break;
			case 303:
				if(GameCore.GetCurrentGameState() == EnumGameState.MainMenu)
					GameCore.SetCurrentGameState(EnumGameState.Game);		
				break;
			case 304: break; //launch main menu options
			case 305: break; //tworcy :DD
			case 306: GameCore.QuitFromGame(); break; //Quit from game
				
			default: break;
		}	
	}
	
	public void OnClickDown(MotionEvent event)
	{		
		switch(nButtonID)
		{	
			case 302:
				Worm temp = WormMenager.GetWormFromBox((int)event.getX(), (int)event.getY());
				if(temp != null)
					GameCore.GetGameEvent().SetTouchedWorm(temp);
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
