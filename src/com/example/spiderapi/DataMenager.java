package com.example.spiderapi;

import android.content.SharedPreferences;

public class DataMenager 
{
	//Save Load Variables
	private static String filename = "SaveData";
	private static SharedPreferences Data = null;	
	
	private static void OnSave()
	{
		/*
		if(GameCore.GetGameMechanic().GetSpider() == null)
			return;
		
		Data = getSharedPreferences(filename, 0);
		
		float X = GameCore.GetGameMechanic().GetSpider().GetX();
		float Y = GameCore.GetGameMechanic().GetSpider().GetY();

		SharedPreferences.Editor Editor = Data.edit();
		Editor.putFloat("X", X);
		Editor.putFloat("Y", Y);
		
		Editor.commit();	
		*/
	}
	
	private void OnLoad()
	{
		/*
		if(GameCore.GetGameMechanic().GetSpider() == null)
			return;		
		
		Data = getSharedPreferences(filename, 0);
		float x = Data.getFloat("X", 0);
		float y = Data.getFloat("Y", 0);
		GameCore.GetGameMechanic().GetSpider().SetPosition(x, y);	
		*/
	}
}
