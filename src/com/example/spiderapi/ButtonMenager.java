package com.example.spiderapi;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Canvas;
import android.util.Log;

public class ButtonMenager 
{
	private static final List<InterfaceButton> ButtonList = new ArrayList<InterfaceButton>();
	
	public static InterfaceButton GetButtonOnPosition(float PositionX, float PositionY)
	{
		InterfaceButton temp = null;
		
		for(int i=0; i<ButtonList.size(); ++i)
		{
			temp = ButtonList.get(i);
			if(temp != null && temp.IsOnPosition(PositionX, PositionY))
				return temp;
		}
		
		return null;
	}

	public static void AddButton(InterfaceButton NewButton) 
	{
		ButtonList.add(NewButton);
	}
	
	public static void RemoveButton(InterfaceButton NewButton) 
	{
		ButtonList.remove(NewButton);
	}
	
	public static void OnDraw(Canvas canvas)
	{
		for(int i=0; i<ButtonList.size(); ++i)
		{
			if(ButtonList.get(i) != null)
				ButtonList.get(i).OnDraw(canvas);
		}	
	}

	public static void CreateButtons(EnumGameState currentGameState)
	{	
		RemoveButtons();
		
		int ButtonStateTable[][] = // 0 - GameState | 1 - Button ID
		{
			//Game Buttons
			{ 1, 300 }, 
			{ 1, 301 },
			{ 1, 302 },
			//In Game Menu
			{ 2, 300 },
			{ 2, 301 },
			{ 2, 302 },
			//In Game Spidet Stats
			{ 3, 300 },
			{ 3, 301 },
			{ 3, 302 },
			//In Game Shop
			{ 4, 300 },
			{ 4, 301 },
			{ 4, 302 },
			//Main Menu
			{ 5, 303 },
			{ 5, 304 },
			{ 5, 305 },
			{ 5, 306 },
			//TerrariumSwitch
			{ 9, 307 },
			{ 9, 308 },
			{ 9, 309 },
			{ 9, 310 },
			{ 9, 311 },
		};			
		
		// !! WARNING !! //
		// Always change table size when adding buttons
		int ButtonTableSize = 21;
		// !! WARNING !! ??
		
		int state = currentGameState.ordinal();
		
		for(int i=0; i<ButtonTableSize; ++i)
		{
			if(ButtonStateTable[i][0] == state)
				new InterfaceButton(ButtonStateTable[i][1]);
		}
	}

	private static void RemoveButtons() 
	{
		ButtonList.clear();	
	}
}
