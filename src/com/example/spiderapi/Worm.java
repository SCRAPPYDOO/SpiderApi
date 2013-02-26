package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Worm extends Animal
{		
	Bitmap WormBitmap = null;
	private boolean IsInWormBox = false;

	public Worm(int animalType, int positionX, int positionY) 
	{
		AnimalType = animalType;
		PositionX = positionX;
		PositionY = positionY;
		this.OnCreate();
	}

	@Override
	protected void OnCreate() 
	{	
		super.OnCreate();

		Bitmap temp = null;
		GameGraphic graphic = GameCore.GetGraphicEngine();
		temp = graphic.LoadBitmap(R.drawable.worm);

		if(temp != null)
		{
			WormBitmap = Bitmap.createScaledBitmap(temp, 40, 40, false);
		}
		
		AnimalHeight = WormBitmap.getHeight();
		AnimalWidth = WormBitmap.getWidth();			
		
		WormMenager.AddWorm(this);
	}

	@Override
	public void OnDraw(Canvas canvas) 
	{	
		super.OnDraw(canvas);
		
		if(UnitFlag == 1)
			return;		
		
		if(WormBitmap == null)
			return;
	
		GameCore.GetGraphicEngine().OnDraw(canvas, WormBitmap, (int)(PositionX - 0.5*AnimalWidth), (int)(PositionY - 0.5*AnimalHeight));		
	}

	@Override
	public void OnUpdate(long diff) 
	{
		super.OnUpdate(diff);
	}

	public void OnDelete() 
	{ 
		UnitFlag = 1; 
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
