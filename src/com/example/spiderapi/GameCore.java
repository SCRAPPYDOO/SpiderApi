package com.example.spiderapi;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class GameCore extends Activity implements OnTouchListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public boolean onTouch(View v, MotionEvent event) 
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onBackPressed() 
	{
		switch(GFXSurface.GetCurrentGameState())
		{
			//case Game: SetCurrentGameState(EnumGameState.MainMenu); break;
			
			case LaunchingScreen:
			case MainMenu: 
				break;
			
			default:
				break;
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
}
