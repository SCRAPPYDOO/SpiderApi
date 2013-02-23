package com.example.spiderapi;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameEvent 
{
	//OnTouch Actions
	boolean IsWormBoxTaken = false;
	Worm TouchedWorm = null;
	Spider TouchedSpider = null;
	static Spider spider    = null;
	boolean CanGetMoveOrders = true;
	
	//called when u touch the screen with this activity opened
	public boolean onTouch(View v, MotionEvent event)
	{	
		float fOnTouchX = event.getX();
		float fOnTouchY = event.getY();
		int ev = event.getAction();
		
		Log.i("GameEvent", "OnTouch: " + fOnTouchX + " : " + fOnTouchY + "");
		
		InterfaceButton bButton = ButtonMenager.GetButtonOnPosition(fOnTouchX, fOnTouchY);
		if(bButton != null)
			Log.i("GameEvent", "OnTouch: Button On Position");
		
		if(TouchedWorm != null)
			TouchedWorm.SetPosition((int)fOnTouchX, (int)fOnTouchY);
		
		if(TouchedSpider != null)
			TouchedSpider.SetPosition((int)fOnTouchX, (int)fOnTouchY);
				
		switch(ev)
		{
			case MotionEvent.ACTION_DOWN:
			{
				TouchedWorm = WormMenager.GetWorm(fOnTouchX, fOnTouchY);
				
				if(TouchedWorm != null)
				{
					TouchedWorm.SetIsInWormBox(false);
				}
				
				if(AnimalMenager.GetSpider() != null && AnimalMenager.GetSpider().IsOnPosition(fOnTouchX, fOnTouchY) && TouchedWorm == null)
				{
					TouchedSpider = AnimalMenager.GetSpider();
					TouchedSpider.SetMovementFlag(3);
				}
				
				break;
			}
			
			case MotionEvent.ACTION_UP:
			{			
				if(bButton != null) 
				{ 
					bButton.OnClickUp();
					Log.i("GameEvent", "OnTouch: Button OnClick");
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
				
				if(AnimalMenager.GetSpider() != null)
				{
					AnimalMenager.GetSpider().SetMovementFlag(1);
				
					if(CanGetMoveOrders)
						AnimalMenager.GetSpider().SetUpWaypoint(fOnTouchX, fOnTouchY, 0);
				}
				break;
			}
			default:
				break;
		}
		Log.i("GameEvent", "OnTouch: End of method");
		return true;
	}	
}
