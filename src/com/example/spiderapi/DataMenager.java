package com.example.spiderapi;

import android.content.SharedPreferences;

public class DataMenager 
{
	private static String gameVersion = "ver 0.12";
	public static String GetGameVersion() { return gameVersion; }
	
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

		int X = AnimalMenager.GetSpider().GetX();
		int Y = AnimalMenager.GetSpider().GetY();
	
		SharedPreferences.Editor Editor = Data.edit();
		Editor.putInt("X", X);
		Editor.putInt("Y", Y);
		
		Editor.commit();	
	}
	
	public static void OnLoad()
	{	
		if(AnimalMenager.GetSpider() == null || Data == null)
			return;		
		
		int x = Data.getInt("X", 0);
		int y = Data.getInt("Y", 0);
		AnimalMenager.GetSpider().SetPosition(x, y);	
	}
}
