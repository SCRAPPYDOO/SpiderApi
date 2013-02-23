package com.example.spiderapi;

import android.content.SharedPreferences;

public class DataMenager 
{
	private static String filename = "SaveData";
	private static SharedPreferences Data = null;	
	
	public static String GetDataFilename() { return filename; }
	
	public static void OnCreate(SharedPreferences data)
	{
		Data = data;
	}
	
	public static void OnSave()
	{
		
		if(AnimalMenager.GetSpider() == null || Data == null)
			return;

		float X = AnimalMenager.GetSpider().GetX();
		float Y = AnimalMenager.GetSpider().GetY();
	
		SharedPreferences.Editor Editor = Data.edit();
		Editor.putFloat("X", X);
		Editor.putFloat("Y", Y);
		
		Editor.commit();	
		
	}
	
	public static void OnLoad()
	{	
		if(AnimalMenager.GetSpider() == null || Data == null)
			return;		
		
		float x = Data.getFloat("X", 0);
		float y = Data.getFloat("Y", 0);
		AnimalMenager.GetSpider().SetPosition(x, y);	
	}
}
