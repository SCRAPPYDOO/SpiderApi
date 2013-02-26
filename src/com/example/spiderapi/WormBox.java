package com.example.spiderapi;

public class WormBox 
{
	private static int NumberOfWorm = 2;
			
	public static void OnCreate()
	{	
		MsgMenager.AddLoadingInfo(0, "Loading WormBox");

		//if gra zapisana zaladuj liczbe robakow
		MsgMenager.AddWormBoxInfo("" + NumberOfWorm);
	}

	public static void OnDelete() 
	{
		//zapisz stan robakow
	}

	public static void OnUpdate(long timeDiff) 
	{
		
	}
	
	public static boolean isEmpty()
	{
		if(NumberOfWorm > 0)
			return false;
		return true;
	}
	
	public static void DecreaseWormNumber() { --NumberOfWorm; MsgMenager.AddWormBoxInfo("" + NumberOfWorm); }
	public static int GetWormNumber() { return NumberOfWorm; }
	public static void AddWorm() { ++NumberOfWorm; MsgMenager.AddWormBoxInfo("" + NumberOfWorm); }
}
