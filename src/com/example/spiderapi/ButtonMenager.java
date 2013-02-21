package com.example.spiderapi;

import android.graphics.Canvas;

public class ButtonMenager 
{
	private static int nButtonTableSize = 10;
	private static InterfaceButton ButtonTable[] = { null, null, null, null, null, null, null, null, null ,null };
	
	public static InterfaceButton GetButtonOnPosition(float PositionX, float PositionY)
	{
		for(int i=0; i<nButtonTableSize; ++i)
		{
			if(ButtonTable[i] != null && ButtonTable[i].IsOnPosition(PositionX, PositionY))
				return ButtonTable[i];
		}
		
		return null;
	}

	public static void AddButton(InterfaceButton NewButton) 
	{
		if(NewButton != null)
		{
			for(int i=0; i<nButtonTableSize; ++i)
			{
				if(ButtonTable[i] == null)
				{
					ButtonTable[i] = NewButton;
					return;
				}		
			}	
		}	
	}
	
	public static void OnDraw(Canvas canvas)
	{
		for(int i=0; i<nButtonTableSize; ++i)
		{
			if(ButtonTable[i] != null)
				ButtonTable[i].OnDraw(canvas);
		}	
	}

	public static void CreateButtons(EnumGameState currentGameState)
	{
		RemoveButtons();
		/*
		int ButtonTable[][] =
		{
			{ 1,300 },
			{ 1,301 },
			{ 1,302 },
			{ , },
			{ , },
			{ , },
			{ , },
			{ , },
			{ , },
			{ , },
			//{ , },
		}
		*/
		
		switch(currentGameState)
		{
			case MainMenu:
			{
				new InterfaceButton(303);
				new InterfaceButton(304);
				new InterfaceButton(305);
				new InterfaceButton(306);
				break;
			}
			case Game:
			case InGameMenu:
			case InGameSpiderStat:
			case InGameWormShop:
			{
				new InterfaceButton(300);
				new InterfaceButton(301);
				new InterfaceButton(302);
				break;
			}
		
			default:
				break;
		}
	}

	private static void RemoveButtons() 
	{
		for(int i=0; i<nButtonTableSize; ++i)
		{
			ButtonTable[i] = null;
		}		
	}
}
