package com.example.spiderapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;

enum EnumGameState 
{
	LoadingScreen,
	Game,
	InGameMenu,
	InGameSpiderStat,
	InGameWormShop,
	MainMenu,
	LaunchingScreen,
}

public class GameCore extends Activity implements OnTouchListener
{
	private static GameGraphic GraphicEngine = null;
	public static GameGraphic GetGraphicEngine() { return GraphicEngine; }
	
	private static GameMechanic GameMechanicC = null;
	public static GameMechanic GetGameMechanic() { return GameMechanicC; }
	
	public static GameEvent EventMenager = null;
	
	private Thread ThreadOne = null;
	private Thread ThreadTwo = null;
	
	private static WakeLock wakeLock = null;
	private static boolean IsRunning = true;
	
	//Actual Gamestate
	private static EnumGameState CurrentGameState = EnumGameState.LaunchingScreen;
	private static EnumGameState LastGameState = EnumGameState.LaunchingScreen;	
	
	private static boolean IsGameLoading = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	
		//Full Screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		GraphicEngine = new GameGraphic(this);
		GraphicEngine.setOnTouchListener(this);
		
		SetUpScreenSize();
		
		GameMechanicC = new GameMechanic();
		
		EventMenager = new GameEvent();
		
		//WakeLock
		PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wakeLock = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "whatever");
		wakeLock.acquire();
				
		SetCurrentGameState(EnumGameState.LaunchingScreen);
	}

	public boolean onTouch(View v, MotionEvent event) 
	{
		return EventMenager.onTouch(v, event);
	}
	
	@Override
	public void onBackPressed() 
	{
		switch(GetCurrentGameState())
		{
			case Game: SetCurrentGameState(EnumGameState.MainMenu); break;
			case LaunchingScreen:
			case MainMenu: 
				break;
			
			default:
				break;
		}
	}
	
	@Override
	protected void onPause() 
	{
		Log.i("GameCore", "OnPause");
		wakeLock.release();	
		IsRunning = false;
		
		while(true)
		{
			try
			{
				ThreadOne.join();
				ThreadTwo.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			break;
		}
		
		ThreadOne = null;	
		ThreadTwo = null;
		Log.i("GameCore", "Threads are null");
		
		Log.i("GameCore", "finish called");
		
		super.onPause();
	}

	@Override
	protected void onResume() 
	{				
		setContentView(GraphicEngine);
		wakeLock.acquire();
		
		IsRunning = true;
		
		ThreadOne = new Thread(new Runnable() 
		{
		    public void run()
		    {
		    	while(IsRunning)
		    	{
		    		GameMechanicC.OnUpdate();
		    	}
		    	Log.i("GameCore", "Thread 1 closed");
		    	finish();
		    }
		});
		
		ThreadTwo = new Thread(new Runnable() 
		{
		    public void run()
		    {
		    	while(IsRunning)
		    	{
		    		GraphicEngine.OnUpdate();
		    	}
				ThreadOne.interrupt();
				ThreadTwo.interrupt();
		    	Log.i("GameCore", "Thread 2 closed");
		    	finish();
		    }
		});		
		
		ThreadOne.start();
		Log.i("GameCore", "Thread 1 started");
		ThreadTwo.start();
		Log.i("GameCore", "Thread 2 started");
		super.onResume();
	}
	
	//Compatibility for android version  < API 13
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void SetUpScreenSize() 
	{
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		int h,w = 0;
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			display.getSize(size);

			w = size.x;
			h = size.y;
		}
		else
		{
			w = display.getWidth(); 
			h = display.getHeight(); 
		}	
		
		GraphicEngine.SetScreenWidth(w);
		GraphicEngine.SetScreenHeight(h);	
	}

	public static EnumGameState GetCurrentGameState() { return CurrentGameState; }
	
	public static void SetCurrentGameState(EnumGameState GameState) 
	{
		LastGameState = CurrentGameState;
		CurrentGameState = GameState; 
		//Do everything from old state
	
		//If we back from game to menu
		if(LastGameState == EnumGameState.MainMenu && CurrentGameState == EnumGameState.Game)
		{
			IsGameLoading = true;
			BackgroundMenager.LoadBackground(EnumGameState.LoadingScreen);
			//UnloadMainMenu();
			LoadGame();
			IsGameLoading = false;
		}
		
		if(LastGameState == EnumGameState.Game && CurrentGameState == EnumGameState.MainMenu )
		{
			IsGameLoading = true;
			BackgroundMenager.LoadBackground(EnumGameState.LoadingScreen);
			UnloadGame();
			//LoadMainMenu():
			IsGameLoading = false;
		}
		
		BackgroundMenager.LoadBackground(CurrentGameState);
		ButtonMenager.CreateButtons(CurrentGameState);
		Log.i("GameCore", "Switched gamestate to " + CurrentGameState + "");
	}
		
	private static void UnloadGame() 
	{
		WormMenager.OnDelete();
		WormBox.OnDelete();
		Terrarium.OnDelete();
		//if(spider != null) spider.OnDelete();	
	}
	
	private static void LoadGame() 
	{
		WormMenager.OnCreate();
		WormBox.OnCreate();
		Terrarium.OnCreate();
		//spider = new Spider(0);
	}

	public static boolean GetLoadingState() { return IsGameLoading; }

	public static void QuitFromGame() 
	{
		IsRunning = false;

	}	
}
