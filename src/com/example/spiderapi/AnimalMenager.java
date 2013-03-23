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
		if(DataMenager.loadSpiderData(spider) != true)
		{
			spider = new Spider(GameCore.GetSelectedAnimal(0),GameCore.GetSelectedAnimal(1));
		}
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
}
