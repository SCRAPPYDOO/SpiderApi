package com.example.spiderapi;

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
	protected int AnimationCurrentState = 0;
	protected int AnimationTimer = 1000; //first animation
	protected int AnimationNextTimer = 1000; // time between animation change
	protected int MaxAnimationsDirection = 8;
	protected int MaxAnimationFrames = 1;
	protected int AnimalBitmapHeight = 520;
	protected int AnimalBitmapWidth = 520;
	//Flags
	protected int MovementFlag = 0;
	protected int UnitFlag = 0;
	//Positions
	protected int PositionX = 50;
	protected int PositionY = 50;
	
	public class Waypoint
	{
		public int PositionX = 0;
		public int PositionY = 0;
		public int PositionZ = 0;
		
		Waypoint(int x, int y) { PositionX = x; PositionY = y; }	
		Waypoint(int x, int y, int z) { PositionX = x; PositionY = y; PositionZ = z; }
	}	
	
	Waypoint wayPoint = null;
	
	protected int AnimalHeight = 0;
	protected int AnimalWidth = 0;
	protected float fSpeed = 0.0f;
	protected int Orientation = 0;
	protected long OrientationTimer = 1000;
	protected int MoveDirection = 0;
	protected int Radius = 0;
	//Social
	protected int AnimalType = 1;
	protected int AnimalSize = 1;
	protected int Health = 100;
	protected int HealthTimer = 10000;
	protected int Hungry = 80;
	protected int HungryTimer = 20000; //when spider whant to eat
	
	public Animal() 
	{
		this.OnCreate();
	}
	
	public Animal(int animalType) 
	{
		AnimalType = animalType;
		this.OnCreate();
	}	
	
	protected void OnCreate() {}
	public void OnDraw(Canvas canvas) {}
	public void OnUpdate(long diff) {}	
	public void OnDelete() { UnitFlag = 1; }		
	
	//Position Methods
	public int GetX() { return PositionX; }
	public int GetY() { return PositionY; }
	public float GetW() { return AnimalWidth; }
	public float GetH() { return AnimalHeight; }
	public int GetType() { return AnimalType; }
	
	public void SetPosition(int posX, int posY) 
	{ 
		if(IsInTerrarium(posX, posY) == true)
		{
			PositionX = posX; PositionY = posY; 
		}
	}
	
	//Social Methods
	public int GetHealth() { return Health; }
	public int GetHungry() {return Hungry;	}
	
	protected boolean IsInTerrarium(int posX, int posY) 
	{
		if(posX - 0.5*AnimalWidth > 0 && posX + 0.5*AnimalWidth < Terrarium.GetWidth()
				&& posY - 0.5*AnimalHeight > 0 && posY  + 0.5*AnimalHeight < Terrarium.GetHeight())
			return true;
		return false;
	}
	
	protected boolean IsInTerrarium(/*this*/) 
	{
		if(this.PositionX - 0.5 * this.AnimalWidth > 0 && this.PositionX + 0.5 * this.AnimalWidth < Terrarium.GetWidth()
				&& this.PositionY - 0.5 * this.AnimalHeight > 0 && this.PositionY  + 0.5 * this.AnimalHeight < Terrarium.GetHeight())
			return true;
		return false;
	}	
	
	public void SetMovementFlag(int Flag) { MovementFlag = Flag; }


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


