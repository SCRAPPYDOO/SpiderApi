package com.example.spiderapi;

import android.content.SharedPreferences;

public class DataMenager 
{
	private static final String gameVersion = "ver 0.12";
	public static final String GetGameVersion() { return gameVersion; }
	
	private static final String filename = "SaveData";
	private static SharedPreferences Data = null;	
	
	public static final String GetDataFilename() { return filename; }
	
	private static SharedPreferences.Editor Editor = null;
	
	private static final String[] KeyTable =
	{
		"SpiderType", 
		"SpiderPosX", 
		"SpiderPosY", 
		"SpiderHealth", 
		"SpiderSluff",
		"WormNumber",
		"WormNumberInTerrarium",
	};	
		
	public static void OnCreate(SharedPreferences data)
	{
		Data = data;
		Editor = Data.edit();
	}
	
	public static void OnSave()
	{
		if(Data == null) return;

		Spider ourSpider = AnimalMenager.GetSpider();
		if(ourSpider != null)
		{
			//For Integer Saving
			for(int i=0; i<KeyTable.length; ++i)
			{
				int tempint = 0;
				switch(i)
				{
					case 0: tempint = ourSpider.GetType(); break;
					case 1: tempint = ourSpider.GetX(); break;
					case 2: tempint = ourSpider.GetY(); break;
					case 3: tempint = ourSpider.GetHealth(); break;
					case 4: tempint = ourSpider.GetSluffLevel(); break;
					case 5: tempint = WormBox.GetWormNumber(); break;
					case 6: tempint = WormMenager.GetWormsNumber(); break;
					default: break;
				}
				
				Editor.putInt(KeyTable[i] + GameCore.GetTerrariumNumber(), tempint);
			}
		}	
		
		Editor.putBoolean("IsSaveEmpty" + GameCore.GetTerrariumNumber(), false);
		
		Editor.commit();
	}
	
	public static void OnLoad()
	{	
		if(Data == null) return;
		
		if(IsSaveEmpty(GameCore.GetTerrariumNumber()) == true)
			return;
			
		Spider ourSpider = AnimalMenager.GetSpider();
		if(ourSpider != null)
		{
			//For Integer Loading
			for(int i=0; i<KeyTable.length; ++i)
			{
				int tempint = Data.getInt(KeyTable[i] + GameCore.GetTerrariumNumber(), 100);
				
				switch(i)
				{
					case 0: ourSpider.SetType(tempint); break;
					case 1: ourSpider.SetPositionX(tempint); break;
					case 2: ourSpider.SetPositionY(tempint); break;
					case 3: ourSpider.SetHealth(tempint); break;
					case 4: ourSpider.SetSluffLevel(tempint); break;
					case 5: WormBox.SetWormNumber(tempint); break;
					case 6: WormMenager.SetNumberOfWormsInTerrarium(tempint); break;			
					
					default: break;
				}
			}	
		}
	}
	
	public static void SaveTerrarium()
	{
		if(Data == null) return;
		
		Editor.putInt("TerrariumTerrain", Terrarium.GetTerrain());
		Editor.putInt("TerrariumAvailableT", Terrarium.GetAvailableTerrain());		
		
		Editor.commit();			
	}
	
	public static void LoadTerrarium()
	{
		if(Data == null) return;
		
		Terrarium.SetTerrain(Data.getInt("TerrariumTerrain", 100));
		Terrarium.SetAvailableT(Data.getInt("TerrariumAvailableT", 0));			
	}

	public static boolean IsSaveEmpty(int SaveNumber) 
	{
		return Data.getBoolean("IsSaveEmpty" + SaveNumber, true);
	}
	
	public static void deleteSavedGame(int SaveNumber)
	{
		Editor.putBoolean("IsSaveEmpty" + SaveNumber, true);
	}
}
