package com.example.spiderapi;

import android.graphics.Canvas;

public class WormMenager 
{
	static Worm WormNumber[] = { null, null, null, null, null, null, null, null, null, null };
	static int MaxWormsInTerr = 10;
		
	static public void OnCreate()
	{
		MsgMenager.AddLoadingInfo(0, "Loading WormMenager");
	}

	static public boolean AddWorm(Worm worm/*null doda 1 robaka randomowo*/)
	{
		if(worm != null)
		{
			for(int i=0; i<MaxWormsInTerr; ++i)
			{
				if(WormNumber[i] == null)
				{
					WormNumber[i] = worm;
					return true;	
				}		
			}	
		}

		return false;
	}	
	
	static public boolean RemoveWorm(Worm worm)
	{
		if(worm != null)
		{
			for(int i=0; i<MaxWormsInTerr; ++i)
			{
				if(WormNumber[i] == worm)
				{
					//WormNumber[i].OnRemove();
					WormNumber[i] = null;
					return true;	
				}		
			}	
		}
			
		return false;		
	}
	
	static public Worm GetWorm(float fOnTouchX, float fOnTouchY)
	{
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				if(WormNumber[i].IsOnPosition(fOnTouchX, fOnTouchY))	
					return WormNumber[i];	
			}		
		}	

	    return null;
	}
	
	static public Worm GetRandomWorm()
	{		
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				return WormNumber[i];	
			}		
		}	
	    return null;
	}	
	
	static public int GetWormsNumber() { return MaxWormsInTerr; }
	static public Worm GetWormOnListPosition(int i) { return WormNumber[i]; }
	
	static public void OnUpdate(long diff)
	{		
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				WormNumber[i].OnUpdate(diff);
			}		
		}
	}
	
	static public void OnDraw(Canvas canvas) 
	{			
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				WormNumber[i].OnDraw(canvas);
			}		
		}
	}

	public static void OnDelete() 
	{
		for(int i=0; i<MaxWormsInTerr; ++i)
		{
			if(WormNumber[i] != null)
			{
				WormNumber[i] = null;
			}		
		}	
	}	
}



