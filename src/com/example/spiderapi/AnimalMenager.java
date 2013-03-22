package com.example.spiderapi;

import android.graphics.Canvas;

public class AnimalMenager 
{	
	static Spider spider = null;

	public static void OnCreate() 
	{
		LoadSpider();		
	}	
	
	private static void LoadSpider() 
	{
		if(spider == null)
			spider = new Spider(0);
	}

	static public void OnUpdate(long diff)
	{		
		if(spider != null)
			spider.OnUpdate(diff);
	}
	
	static public void OnDraw(Canvas canvas) 
	{			
		if(spider != null)
			spider.OnDraw(canvas);
	}

	public static void OnDelete() 
	{
		spider = null;
	}
	
	public static Spider GetSpider() { return spider; }

	public static void DeleteSpider(Spider spider2) 
	{
		if(spider == spider2)
		{
			spider.OnDelete();
			spider = null;
		}
	}

	public static void CreateNewSpider() 
	{
		int Type = GameCore.GetSelectedAnimal(0);
		int Kind = GameCore.GetSelectedAnimal(1);
		spider = new Spider(Type, Kind);
		// TODO Auto-generated method stub		
	}
}
