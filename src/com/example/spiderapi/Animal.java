package com.example.spiderapi;
//abcdadadad
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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
	protected int AnimationCurrentState = 0;
	protected int AnimationTimer = 1000; //first animation
	protected int AnimationNextTimer = 1000; // time between animation change
	protected int MaxAnimationsDirection = 8;
	protected int MaxAnimationFrames = 8;
	protected Rect srcSampleAnimalBmp = new Rect(0,0,0,0);
	protected Rect dstRectangleOnDraw = new Rect(0,0,0,0); 	
	protected int AnimalBitmapHeight = 480;
	protected int AnimalBitmapWidth = 640;
	//Flags
	protected int MovementFlag = 0;
	protected int UnitFlag = 0;
	//Positions
	protected int PositionX = 50;
	protected int PositionY = 50;
	protected float fGoX = 50.0f;
	protected float fGoY = 50.0f;
	protected int AnimalHeight = 0;
	protected int AnimalWidth = 0;
	protected float fSpeed = 0.0f;
	protected int Orientation = 0;
	protected float fRadius = 0.0f;
	protected MoveDirection moveDirection = MoveDirection.Down;
	//Social
	protected int AnimalSize = 1;
	protected int Health = 5;
	protected int HungryTimer = 5000; //when spider whant to eat
	//Position Methods
	public float GetX() { return PositionX; }
	public float GetY() { return PositionY; }
	public float GetW() { return AnimalWidth; }
	public float GetH() { return AnimalHeight; }
	public void SetPosition(float posX, float posY) { PositionX = (int) posX; PositionY = (int) posY; }
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
		GetAnimalBitmapID();
		
		bmpAnimalBitmap = GFXSurface.GetSurface().LoadBitmap(bmpAnimalBitmapID);
			
		GetAnimalSize();
	}
	
	protected void GetAnimalSize() 
	{	
		AnimalHeight = 100;
		AnimalWidth = 100;
	}
	
	public void GetAnimalBitmapID() 
	{
		bmpAnimalBitmapID = R.drawable.l1;
	}
	
	public void OnDraw(Canvas canvas)
	{	
		if(UnitFlag == 1)
			return;
		
		if(bmpAnimalBitmap == null)
			return;
		
		GFXSurface.GetSurface().OnDraw(canvas, bmpAnimalBitmap, srcSampleAnimalBmp, dstRectangleOnDraw);
		//TEST !!!
		//HitBox
		Bitmap bmp = GFXSurface.GetSurface().LoadBitmap(R.drawable.worm);
		//GFXSurface.GetSurface().OnDraw(canvas, bmp, null, dstRectangleOnDraw);
		GFXSurface.GetSurface().OnDraw(canvas, bmp, PositionX, PositionY);
	}
	
	public void OnUpdate(long diff)
	{	
		OnAnimate(diff);
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
		
		//Set Sample of Animal bitmap to show up
		srcSampleAnimalBmp.set(AnimationCurrentState * AnimalBitmapWidth, Orientation * AnimalBitmapHeight, AnimalBitmapWidth, AnimalBitmapHeight);
		//scale the sample
		dstRectangleOnDraw.set(PositionX-AnimalWidth/2, PositionY-AnimalHeight/2, PositionX+AnimalWidth/2, PositionY+AnimalHeight/2);		
	}
	
	public void OnRemove()	
	{
		UnitFlag = 1;
	}
	
	public boolean IsOnPosition(float fOnTouchX, float fOnTouchY)
	{
		if( ( fOnTouchX > PositionX-(AnimalWidth/2) ) && ( fOnTouchX < PositionX + (AnimalWidth/2)) 
				&& ( fOnTouchY > PositionY - (AnimalHeight/2) ) && ( fOnTouchY < PositionY + (AnimalHeight/2) ) )	
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
		
	private double GetDistance(float x1, float y1, float x2, float y2 )
	{ 
	    return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); 	
	}	
	
	public boolean IsInRange(Animal animal) 
	{
		if(GetDistance(PositionX, PositionY, animal.GetX(),  animal.GetY()) < fRadius + animal.fRadius)
			return true;
	
		return false;
	}	
}


