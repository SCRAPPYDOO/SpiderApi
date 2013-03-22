package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
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
			
			case 307:
			case 308:
			case 309:
			case 310:
			case 311:	
				nPositionX = (int) (ScreenWidth*0.5 - this.nWidth * 0.5);
				nPositionY = (int) (100 + ((nButtonID - 306) * nHeight + (20* (nButtonID - 306)) ));
				break;	
			
			case 312:
				nPositionX = (int) (ScreenWidth*0.5 - this.nWidth * 0.5);
				nPositionY = (int) (ScreenHeight - 150);
				break;
				
			default: break;
		}
	}

	private void ScaleBitmap() 
	{		
		int Width, Height;
		Width = Height = 100;
		
		switch(nButtonID)
		{
			case 300: 
			case 301: 
			case 302:
			case 303:
			case 304:
			case 305:
			case 306:
			case 307:
			case 308:
			case 309:
			case 310:
			case 311:
			case 312:
				Width = GameCore.GetGraphicEngine().getScreenWidth()/3; 
				Height = GameCore.GetGraphicEngine().getScreenHeight()/15;
				break;
						
			default: break;
		}
		
		bmpBitmap = Bitmap.createScaledBitmap(bmpBitmap, Width, Height, true);
	}

	public void OnDraw(Canvas canvas) 
	{	
		if(bmpBitmap != null)
			GameCore.GetGraphicEngine().OnDraw(canvas, bmpBitmap, nPositionX, nPositionY);
	}

	public void OnUpdate(long diff)
	{

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
					
					//run shop
					if(WormBox.GetWormNumber() <= 0)
					{
						GameCore.SetCurrentGameState(EnumGameState.InGameWormShop);
					}
				}
				break;
			case 303:
				if(GameCore.GetCurrentGameState() == EnumGameState.MainMenu)
					GameCore.SetCurrentGameState(EnumGameState.TerrariumSwitch);		
				break;
			case 304: GameCore.SetCurrentGameState(EnumGameState.MainMenuOptions); break; //launch main menu options
			case 305: GameCore.SetCurrentGameState(EnumGameState.MainMenuDevelopers); break; //tworcy :DD
			case 306: GameCore.QuitFromGame(); break; //Quit from game
			
			//Terrarium Switch Buttons
			case 307:
			case 308: 
			case 309: 
			case 310: 
			case 311: 
				GameCore.SetTerrariumNumber(nButtonID - 307);
				
				if(DataMenager.IsSaveEmpty(nButtonID - 307))
				{
					GameCore.SetCurrentGameState(EnumGameState.MainMenuAnimalSelection);
				}
				else
				{
					GameCore.SetCurrentGameState(EnumGameState.Game); 
				}
				break;
				
			//Animal Selection -> StartGame	
			case 312:
				AnimalMenager.CreateNewSpider();
				GameCore.SetCurrentGameState(EnumGameState.Game);
				break;
			//Next Animal -->
			case 313:
				break;
			//Next Animal <--
			case 314:
				break;
			
				
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
		//Terrariumswitch buttons
		R.drawable.terrariumswitch_terrariumbutton,
		R.drawable.terrariumswitch_terrariumbutton,
		R.drawable.terrariumswitch_terrariumbutton,
		R.drawable.terrariumswitch_terrariumbutton,
		R.drawable.terrariumswitch_terrariumbutton,
		R.drawable.animalselection_select,
		//
	};
}
