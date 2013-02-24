package com.example.spiderapi;

import android.graphics.Bitmap;
import android.graphics.Canvas;

enum UnitFlag
{
	UNIT_FLAG_VISIBLE, // 0x0002	
}

enum MovementFlag
{
	UNIT_MOVE_NOTMOVING, // 0x0002
	UNIT_MOVE_MOVING,    // 0x0020	
	UNIT_MOVE_MOVEDBYUSER// 0x0200
}

public class Animal 
{		
	//Graphics
	protected int ObjectID = 0;
	protected int bmpAnimalBitmapID = 0;
	protected Bitmap bmpAnimalBitmap = null;
	protected Bitmap bmpAnimalBitmapT[][] = new Bitmap[8][8];
	protected int AnimationCurrentState = 0;
	protected int AnimationTimer = 1000; //first animation
	protected int AnimationNextTimer = 1000; // time between animation change
	protected int MaxAnimationsDirection = 8;
	protected int MaxAnimationFrames = 8;
	protected int AnimalBitmapHeight = 520;
	protected int AnimalBitmapWidth = 520;
	//Flags
	protected int MovementFlag = 0;
	protected int UnitFlag = 0;
	//Positions
	protected int PositionX = 50;
	protected int PositionY = 50;
	
	Waypoint wayPoint = null;
	
	protected class Waypoint
	{
		public int PositionX = 0;
		public int PositionY = 0;
		public int PositionZ = 0;
		
		Waypoint(int x, int y) { PositionX = x; PositionY = y; }	
		Waypoint(int x, int y, int z) { PositionX = x; PositionY = y; PositionZ = z; }
	}

	protected int AnimalHeight = 0;
	protected int AnimalWidth = 0;
	protected float fSpeed = 0.0f;
	protected int Orientation = 0;
	protected int Radius = 0;
	protected MoveDirection moveDirection = MoveDirection.Down;
	//Social
	protected int AnimalSize = 1;
	protected int Health = 5;
	protected int HungryTimer = 5000; //when spider whant to eat
	//Position Methods
	public int GetX() { return PositionX; }
	public int GetY() { return PositionY; }
	public float GetW() { return AnimalWidth; }
	public float GetH() { return AnimalHeight; }
	
	public void SetPosition(int posX, int posY) 
	{ 
		if(IsPositionInTerrarium(posX, posY) == true)
		{
			PositionX = posX; PositionY = posY; 
		}
	}
	
	protected boolean IsPositionInTerrarium(int posX, int posY) 
	{
		if(posX - 0.5*AnimalWidth > 0 && posX + 0.5*AnimalWidth < Terrarium.GetWidth()
				&& posY - 0.5*AnimalHeight > 0 && posY  + 0.5*AnimalHeight < Terrarium.GetHeight())
			return true;
		return false;
	}
	public void SetMovementFlag(int Flag) { MovementFlag = Flag; }
	//Constructor
	public Animal() 
	{
		this.OnCreate();
	}
	
	public Animal(int objectID) 
	{
		ObjectID = objectID;
		this.OnCreate();
	}
	
	//Social Methods
	public int GetHealth() { return Health; }
	
	protected void OnCreate()
	{	
		for(int i = 0;i<MaxAnimationFrames; ++i)
		{
			Bitmap temp = GameCore.GetGraphicEngine().LoadBitmap(bmpBitmapIDTable[0][i]);
			
			bmpAnimalBitmapT[0][i] = Bitmap.createScaledBitmap(temp, 200, 200, false);	
		}
		AnimalHeight = bmpAnimalBitmapT[0][0].getHeight();
		AnimalWidth = bmpAnimalBitmapT[0][0].getWidth();	
	}
	
	private int bmpBitmapIDTable[][] = 
	{
			{ R.drawable.l1, R.drawable.l1a, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l4a, R.drawable.l5, R.drawable.l6 },
			{ R.drawable.l1, R.drawable.l1a, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l4a, R.drawable.l5, R.drawable.l6 },
	};
	
	public void OnDraw(Canvas canvas)
	{	
		if(UnitFlag == 1)
			return;
		
		if(bmpAnimalBitmapT[0][AnimationCurrentState] == null)
			return;
	
		GameCore.GetGraphicEngine().OnDraw(canvas, bmpAnimalBitmapT[0][AnimationCurrentState], (int)(PositionX - 0.5*AnimalWidth), (int)(PositionY - 0.5*AnimalHeight));
	}
	
	public void OnUpdate(long diff)
	{	
		
	}	
	
	public void OnDelete() 
	{
		bmpAnimalBitmap = null;
		
		for(int x = 0;x<MaxAnimationFrames; ++x)
		{
			for(int z = 0;z<MaxAnimationsDirection; ++z)
			{
				bmpAnimalBitmapT[x][z] = null;	
			}
		}		
	}	

	public void OnAnimate(long diff)
	{
		if(AnimationTimer < diff)			
		{
			++AnimationCurrentState;
			
			if(AnimationCurrentState == MaxAnimationFrames)
				AnimationCurrentState = 0;
			
			AnimationTimer = AnimationNextTimer;
		}AnimationTimer -= diff;
	}
	
	public void OnRemove()	
	{
		UnitFlag = 1;
	}
	
	public boolean IsOnPosition(float fOnTouchX, float fOnTouchY)
	{
		if(fOnTouchX > PositionX - 0.5*AnimalWidth && fOnTouchX < PositionX + 0.5*AnimalWidth
				&& fOnTouchY > PositionY - 0.5*AnimalHeight && fOnTouchY < PositionY + 0.5*AnimalHeight)
				return true;

	    return false;
	}	
	
	public boolean IsNearObject(Animal animal, Worm worm, Spider spider)
	{
		return false;
	}
	
	public boolean IsInDistance(Animal animal, float fPosX, float fPosY, float fDistance)
	{
		return false;
	}
		
	protected double GetDistance(float x2, float y2 )
	{ 
	    return Math.sqrt((x2-PositionX)*(x2-PositionX) + (y2-PositionY)*(y2-PositionY)); 	
	}	
	
	public boolean IsInRange(Animal animal) 
	{
		if(GetDistance(animal.GetX(),  animal.GetY()) < Radius + animal.Radius)
			return true;
	
		return false;
	}	
}


