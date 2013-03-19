package com.example.spiderapi;

import android.view.MotionEvent;
import android.view.View;

public class GameEvent 
{	
	private static Worm TouchedWorm = null;
	public void SetTouchedWorm(Worm worm) { TouchedWorm = worm; }
	public Worm GetTouchedWorm() { return TouchedWorm; }
	
	Spider TouchedSpider = null;
	boolean CanGetMoveOrders = true;
	
	//called when u touch the screen with this activity opened
	public boolean onTouch(View v, MotionEvent event)
	{	
		int OnTouchX = (int) event.getX();
		int OnTouchY = (int) event.getY();
		int ev = event.getAction();
			
		InterfaceButton bButton = ButtonMenager.GetButtonOnPosition(OnTouchX, OnTouchY);
		
		if(TouchedWorm != null)
			TouchedWorm.SetPosition(OnTouchX, OnTouchY);
		
		if(TouchedSpider != null)
			TouchedSpider.SetPosition(OnTouchX, OnTouchY);
				
		switch(ev)
		{
			case MotionEvent.ACTION_DOWN:
			{
				if(bButton != null) 
				{ 
					bButton.OnClickDown(event);
					bButton = null;  
				} 
				
				TouchedWorm = WormMenager.GetWorm(OnTouchX, OnTouchY);
				
				if(TouchedWorm != null)
				{
					TouchedWorm.SetIsInWormBox(false);
				}
				
				if(AnimalMenager.GetSpider() != null && AnimalMenager.GetSpider().IsOnPosition(OnTouchX, OnTouchY) && TouchedWorm == null)
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
					bButton.OnClickUp(event);
					bButton = null; 
				} 
							
				TouchedWorm = null;
				TouchedSpider = null;
				bButton = null;
				
				if(AnimalMenager.GetSpider() != null)
				{			
					if(CanGetMoveOrders)
						AnimalMenager.GetSpider().SetUpWaypoint(OnTouchX, OnTouchY, 0);
				}
				break;
			}
			
			default:
				break;
		}
		return true;
	}	
}
