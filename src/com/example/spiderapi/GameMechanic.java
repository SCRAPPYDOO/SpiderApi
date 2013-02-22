package com.example.spiderapi;

public class GameMechanic
{
	private long LastCurrentTime, CurrentTime;
	private static long LaunchingScreenTimer;
	
	public GameMechanic()
	{
		OnCreate();
	}
	
	public void OnCreate()
	{
		//Timer
		CurrentTime = LastCurrentTime = 0;	
	
		LaunchingScreenTimer = 10000;
		GameCore.SetCurrentGameState(EnumGameState.LaunchingScreen);
		
		BackgroundMenager.OnCreate();
		BackgroundMenager.LoadBackground();	
	}
	
	public void OnUpdate()
	{
		CurrentTime = System.currentTimeMillis();	
		long TimeDiff = CurrentTime - LastCurrentTime;
		LastCurrentTime = CurrentTime;
			
		switch(GameCore.GetCurrentGameState())
		{
			case LaunchingScreen:
			{
		    	if(LaunchingScreenTimer < TimeDiff)
		    	{
		    		GameCore.SetCurrentGameState(EnumGameState.MainMenu);
		    	}LaunchingScreenTimer -= TimeDiff;	
		    	
				break;
			}
			
			default:
				break;
		}
		
		MsgMenager.OnUpdate(TimeDiff);
					
		AnimalMenager.OnUpdate(TimeDiff);
		
		Terrarium.OnUpdate(TimeDiff);
		
		WormBox.OnUpdate(TimeDiff);
		
		WormMenager.OnUpdate(TimeDiff);
		
		try
		{
			float FrameRate = 60;
			float PauseTime = 1000 / FrameRate;
			Thread.sleep((long) PauseTime);	
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}		
	}
}
