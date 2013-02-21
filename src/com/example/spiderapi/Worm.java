package com.example.spiderapi;

import android.graphics.Canvas;

public class Worm extends Animal
{		
	private boolean IsInWormBox = false;

	public Worm(int objectID) 
	{
		ObjectID = objectID;
		this.OnCreate();
	}

	@Override
	protected void OnCreate() 
	{	
		super.OnCreate();

		PositionX = (int) (WormBox.GetPositionX() + 14);
		PositionY = (int) (WormBox.GetPositionY() + 53);

		WormMenager.AddWorm(this);
	}

	@Override
	public void OnDraw(Canvas canvas) 
	{	
		super.OnDraw(canvas);
	}

	@Override
	public void OnUpdate(long diff) 
	{
		super.OnUpdate(diff);
	}

	@Override
	public void OnRemove()
	{
		super.OnRemove();
		WormMenager.RemoveWorm(this);
	}

	public boolean IsInWormBox() 
	{
		return IsInWormBox;
	}

	public void SetIsInWormBox(boolean b) 
	{
		IsInWormBox = b;
	}
}
