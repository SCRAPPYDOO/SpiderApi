package com.example.spiderapi;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

public class WormMenager 
{
	private static final List<Worm> WormList = new ArrayList<Worm>();
		
	static public void OnCreate()
	{
		MsgMenager.AddLoadingInfo(0, "Loading WormMenager");
	}
	
	static public boolean AddWorm(Worm NewWorm)
	{
		if(WormList.contains(NewWorm))
		{
			return false;
		}
		else
		{
			WormList.add(NewWorm);
			return true;
		}
	}	
	
	static public boolean RemoveWorm(Worm WormToRemove)
	{
		if(WormList.remove(WormToRemove))
			return true;
		return false;		
	}
	
	static public Worm GetWorm(float fOnTouchX, float fOnTouchY)
	{
		for(int i=0; i<WormList.size(); ++i)
		{		
			if(WormList.get(i) != null)
			{
				if(WormList.get(i).IsOnPosition(fOnTouchX, fOnTouchY))	
					return WormList.get(i);	
			}		
		}	

	    return null;
	}
	
	static public Worm GetRandomWorm()
	{		
		for(int i=0; i<WormList.size(); ++i)
		{
			if(WormList.get(i) != null)
			{
				return WormList.get(i);	
			}		
		}	
	    return null;
	}	
	
	static public int GetWormsNumber() { return WormList.size(); }
	
	static public void OnUpdate(long diff)
	{		
		for(int i=0; i<WormList.size(); ++i)
		{
			if(WormList.get(i) != null)
			{
				WormList.get(i).OnUpdate(diff);
			}		
		}
	}
	
	static public void OnDraw(Canvas canvas) 
	{			
		for(int i=0; i<WormList.size(); ++i)
		{
			if(WormList.get(i) != null)
			{
				WormList.get(i).OnDraw(canvas);
			}		
		}
	}

	public static void OnDelete() 
	{
		WormList.clear();
	}

	public static Worm GetWormFromBox(int positionX, int positionY) 
	{
		if(!WormBox.isEmpty())
		{
			WormBox.DecreaseWormNumber();
			Worm temp = new Worm(1, positionX, positionY);
			return temp;
		}
		return null;
	}

	public static boolean AddWormToBox(Worm temp) 
	{	
		if(RemoveWorm(temp))
		{
			WormBox.AddWorm();
			return true;
		}
		return false;
	}	
	
	public static void SetNumberOfWormsInTerrarium(int value)
	{
		for(int i=0; i<value; ++i)
		{
			AddWorm(new Worm(1, 50, 50 /*RandomPositionX(), RandomPositionY()*/));
		}
	}
}



