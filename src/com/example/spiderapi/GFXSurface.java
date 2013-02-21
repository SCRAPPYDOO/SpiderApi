package com.example.spiderapi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
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

public class GFXSurface extends Activity implements OnTouchListener
{
	//Game Options
	//actual gamestate
	private static EnumGameState CurrentGameState = EnumGameState.LaunchingScreen;
	private static EnumGameState LastGameState = EnumGameState.LaunchingScreen;
	//Bool for end of game
	private static boolean IsRunning = false;
	private static boolean IsGameLoading = false;
	
	//Screen Size variables
    private static int screenHeight;
    private static int screenWidth;	
    //Screen Size methods
    public static int getScreenHeight() { return screenHeight; }
    public static int getScreenWidth() { return screenWidth; }
    //  
    private static SurfaceClass Surface 	= null;
  
	static Spider spider    = null;
	private static WakeLock wL     = null;
	
	boolean CanGetMoveOrders = true;
	
	private long LastCurrentTime, CurrentTime;
	private static long LaunchingScreenTimer = 5000;
	//Save Load Variables
	private static String filename = "SaveData";
	private static SharedPreferences Data = null;

	//OnTouch Actions
	boolean IsWormBoxTaken = false;
	Worm TouchedWorm = null;
	Spider TouchedSpider = null;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
			
		//Initialize System
		//full screen before all shit happens
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//Create a surface
		Surface = new SurfaceClass(this);
		Surface.setOnTouchListener(this);
			
		//Get Screen Size
		GetScreenSize();	
		//
		//Timer
		CurrentTime = LastCurrentTime = 0;	

		LaunchingScreenTimer = 10000;
		SetCurrentGameState(EnumGameState.LaunchingScreen);
		IsGameLoading = false;
	
		// !!! All objects loading bitmaps need to be below this line !!!
		//Load BackGround
		BackgroundMenager.OnCreate();
		BackgroundMenager.LoadBackground();	
		
		setContentView(Surface);
		//wakelock
		PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
		wL = pM.newWakeLock(PowerManager.FULL_WAKE_LOCK, "whatever");
		wL.acquire();
		
		//save load 
		Data = getSharedPreferences(filename, 0);		
	}

	//Compatibility for android version  < API 13
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void GetScreenSize() 
	{
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			display.getSize(size);

			screenWidth = size.x;
			screenHeight = size.y; 
		}
		else
		{
			screenWidth = display.getWidth(); 
			screenHeight = display.getHeight(); 
		}		
	}

	private static void OnSave()
	{
		if(spider == null)
			return;
				
		float X = spider.GetX();
		float Y = spider.GetY();

		SharedPreferences.Editor Editor = Data.edit();
		Editor.putFloat("X", X);
		Editor.putFloat("Y", Y);
		
		Editor.commit();	
	}
	
	private void OnLoad()
	{
		if(spider == null)
			return;		
		
		Data = getSharedPreferences(filename, 0);
		float x = Data.getFloat("X", 0);
		float y = Data.getFloat("Y", 0);
		spider.SetPosition(x, y);	
	}
	
	@Override
	protected void onPause() 
	{
		//OnSave();
		super.onPause();
		Surface.pause();
		wL.release(); //wakelock	
		finish();
	}

	@Override
	protected void onResume() 
	{
		//OnLoad();
		super.onResume();
		Surface.resume();
		wL.acquire(); //wakelock
	}

	@Override
	public void onBackPressed() 
	{
		switch(GFXSurface.GetCurrentGameState())
		{
			case Game: SetCurrentGameState(EnumGameState.MainMenu); break;
			
			case LaunchingScreen:
			case MainMenu: 
				break;
			
			default:
				break;
		}
	}
	//called when u touch the screen with this activity opened
	public boolean onTouch(View v, MotionEvent event)
	{	
		float fOnTouchX = event.getX();
		float fOnTouchY = event.getY();
		
		InterfaceButton bButton = ButtonMenager.GetButtonOnPosition(fOnTouchX, fOnTouchY);
		
		if(TouchedWorm != null)
			TouchedWorm.SetPosition(fOnTouchX, fOnTouchY);
		
		if(TouchedSpider != null)
			TouchedSpider.SetPosition(fOnTouchX, fOnTouchY);
				
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
			{
				TouchedWorm = WormMenager.GetWorm(fOnTouchX, fOnTouchY);
				
				if(TouchedWorm != null)
				{
					TouchedWorm.SetIsInWormBox(false);
				}
				
				if(spider != null && spider.IsOnPosition(fOnTouchX, fOnTouchY) && TouchedWorm == null)
				{
					TouchedSpider = spider;
					TouchedSpider.SetMovementFlag(3);
				}
				
				break;
			}
			
			case MotionEvent.ACTION_UP:
			{			
				if(bButton != null) 
				{ 
					bButton.OnClickUp(); 
					bButton = null; 
					break; 
				} 
				
				if(WormBox.IsOnPosition(fOnTouchX, fOnTouchY))
				{
					if(WormBox.IsEmpty() && TouchedWorm == null)
					{
						//create new content activity with shop 
						Worm worm = new Worm(0);
						worm.SetIsInWormBox(true);
					}
					
					if(TouchedWorm != null)
					{
						TouchedWorm.SetIsInWormBox(true);				
					}
				}				
				
				IsWormBoxTaken = false;
				TouchedWorm = null;
				TouchedSpider = null;
				bButton = null;
				
				if(spider != null)
				{
					spider.SetMovementFlag(1);
				
					if(CanGetMoveOrders)
						spider.SetUpWaypoint(fOnTouchX, fOnTouchY, 0);
				}
				break;
			}
			default:
				break;
		}

		return true;
	}
	
	public static SurfaceClass GetSurface() { return Surface; }
	
	public class SurfaceClass extends SurfaceView
	{
		private SurfaceHolder surfHolder = null;
		private Thread ThreadOne = null;
		private Thread ThreadTwo = null;
		
		public void OnDraw(Canvas canvas,Bitmap bitmap,float fPosX, float fPosY)
		{
			if(bitmap == null || canvas == null)
				return;
			
			canvas.drawBitmap(bitmap, fPosX, fPosY, null);
		}
		
		public void OnDraw(Canvas canvas,Bitmap bitmap, Rect src, Rect dst)
		{
			if(bitmap == null || canvas == null)
				return;

			canvas.drawBitmap(bitmap, null, dst, null);
		}
		
		public void OnDraw(Canvas canvas,Bitmap bitmap, RectF src, RectF dst)
		{
			if(bitmap == null || canvas == null)
				return;

			Matrix matrix = new Matrix();
			matrix.reset();
			ScaleToFit stf = ScaleToFit.FILL;
			matrix.setRectToRect(src, dst, stf);		

			canvas.drawBitmap(bitmap, matrix, null);
		}		
		
		public Bitmap LoadBitmap(int BitmapID)
		{
			return BitmapFactory.decodeResource(getResources(), BitmapID);
		}
				
		public SurfaceClass(Context context)
		{
			super(context);
			surfHolder = getHolder();	
		}

		public void pause()
		{
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
			
			finish(); //kill loading screen activity
		}
		
		public void resume()
		{
			IsRunning = true;
		
			ThreadOne = new Thread(new Runnable() 
			{
			    public void run()
			    {
					while(IsRunning)
					{	
			            //code something u want to do
						if(!surfHolder.getSurface().isValid())
							continue;	
						
						Canvas canvas = surfHolder.lockCanvas();
						canvas.drawRGB(0, 0, 0);
						
						//Displaying Shit starts from here
						BackgroundMenager.OnDraw(canvas);
										
						if(IsGameLoading != true)
						{
							switch(CurrentGameState)
							{
								case LaunchingScreen:
								{
								
									break;
								}
							    case LoadingScreen:
							    {
							    	break;
							    }
								case Game:
								{
									Terrarium.OnDraw(canvas);		
									WormMenager.OnDraw(canvas);
									
									if(spider != null)	
										spider.OnDraw(canvas);

									break;
								}
								case InGameSpiderStat:
								{
									break;
								}
								case InGameMenu:
								{
									break;
								}
								case MainMenu:
								{
									
									break;
								}
								default: break;
							}					
	
							ButtonMenager.OnDraw(canvas);
													
						}
						MsgMenager.OnDraw(canvas);
						
						//Displaying Shit ends here
						surfHolder.unlockCanvasAndPost(canvas);
			        	//Log.i("Thread1", "Running parallely");
			        }
			    }
			});
			
			
			ThreadTwo = new Thread(new Runnable() 
			{
			    public void run()
			    {
					while(IsRunning)
					{	
						CurrentTime = System.currentTimeMillis();	
						long TimeDiff = CurrentTime - LastCurrentTime;
						LastCurrentTime = CurrentTime;
						
						switch(CurrentGameState)
						{
							case LaunchingScreen:
							{
						    	if(LaunchingScreenTimer < TimeDiff)
						    	{
						    		SetCurrentGameState(EnumGameState.MainMenu);
						    	}LaunchingScreenTimer -= TimeDiff;	
						    	
								break;
							}
							
							default:
								break;
						}
						
						MsgMenager.OnUpdate(TimeDiff);
						
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
							
						if(spider != null)		
							spider.OnUpdate(TimeDiff);

						WormBox.OnUpdate(TimeDiff);
						
						WormMenager.OnUpdate(TimeDiff);
			        	
					}
					
					onPause();
		        	//Log.i("Thread2", "Running parallely");
		        }
			});
			
			ThreadOne.start();
			//ThreadTwo = new Thread(this);
			ThreadTwo.start();
		}
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
	}
	
	private static void UnloadGame() 
	{
		WormMenager.OnDelete();
		WormBox.OnDelete();
		Terrarium.OnDelete();
		if(spider != null) spider.OnDelete();	
	}
	
	private static void LoadGame() 
	{
		WormMenager.OnCreate();
		WormBox.OnCreate();
		Terrarium.OnCreate();
		spider = new Spider(0);
	}
	
	public static void QuitFromGame() 
	{
		IsRunning = false;
		//delete all
	}
}
