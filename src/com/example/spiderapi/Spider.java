package com.example.spiderapi;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Spider extends Animal
{	
	private int SluffLevel = 0;//wylinka ze slownika :D
	private int SluffTimer = 10000;

	private Bitmap bmpAnimalBitmapT[][] = new Bitmap[8][8];
	//Moving Variables	
	private int RandomWaypointTimer = 5000;
	
	//Pointers
	private Worm worm = null;	

	public int GetSluffLevel() { return SluffLevel; }
	
	public Spider(int animalType)
	{
		AnimalType = animalType;
		OnCreate();
	}	
	
	public void OnCreate() 
	{
		super.OnCreate();
		
		MsgMenager.AddLoadingInfo(0, "Loading Spider Bitmap");
		
		GameGraphic graphic = GameCore.GetGraphicEngine();
		
		if(graphic == null) return;
		
		for(int i = 0;i<MaxAnimationsDirection; ++i)
		{
			for(int y = 0; y<MaxAnimationFrames; ++y)
			{

				Bitmap temp = null;

				MsgMenager.AddLoadingInfo(0, "Ladownaie Mapy");
				temp = graphic.LoadBitmap(R.drawable.u1);
		
				if(temp != null)
				{
					bmpAnimalBitmapT[i][y] = Bitmap.createScaledBitmap(temp, 200, 200, false);
				}
				
				MsgMenager.AddLoadingInfo(0, "Loading Spider Bitmap: " + i + " " + y + "");
			}
		}	
		
		AnimalHeight = bmpAnimalBitmapT[0][0].getHeight();
		AnimalWidth = bmpAnimalBitmapT[0][0].getWidth();	
		
		//Initialize Variable
		MovementFlag = 1; //super
		fSpeed = 1;
		Radius = 5;
		
		this.SetPosition(300,300);
		this.SetupStats();
	}
	
	private int bmpBitmapIDTable[][] = 
	{
		{ R.drawable.u1, R.drawable.u2, R.drawable.u3, R.drawable.u4, R.drawable.u5, R.drawable.u6, R.drawable.u7, R.drawable.u8 },
		{ R.drawable.ur1, R.drawable.ur2, R.drawable.ur3, R.drawable.ur4, R.drawable.ur5, R.drawable.ur6, R.drawable.ur7, R.drawable.ur8 },
		{ R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7, R.drawable.r8 },
		{ R.drawable.rd1, R.drawable.rd2, R.drawable.rd3, R.drawable.rd4, R.drawable.rd5, R.drawable.rd6, R.drawable.rd7, R.drawable.rd8 },	
		{ R.drawable.d1, R.drawable.d2, R.drawable.d3, R.drawable.d4, R.drawable.d5, R.drawable.d6, R.drawable.d7, R.drawable.d8 },	
		{ R.drawable.ld1, R.drawable.ld1, R.drawable.ld1, R.drawable.ld1, R.drawable.ld1, R.drawable.ld1, R.drawable.ld1, R.drawable.ld1 },	
		{ R.drawable.l1, R.drawable.l2, R.drawable.l3, R.drawable.l4, R.drawable.l5, R.drawable.l6, R.drawable.l7, R.drawable.l8 },
		{ R.drawable.lu1, R.drawable.lu2, R.drawable.lu3, R.drawable.lu4, R.drawable.lu5, R.drawable.lu6, R.drawable.lu7, R.drawable.lu7 },	
	};		
	
	@Override
	public void OnDraw(Canvas canvas) 
	{
		super.OnDraw(canvas);
		
		if(UnitFlag == 1)
			return;		
		
		if(bmpAnimalBitmapT[Orientation][AnimationCurrentState] == null)
			return;
	
		GameCore.GetGraphicEngine().OnDraw(canvas, bmpAnimalBitmapT[Orientation][AnimationCurrentState], (int)(PositionX - 0.5*AnimalWidth), (int)(PositionY - 0.5*AnimalHeight));
	}

	@Override
	public void OnDelete() 
	{
		for(int x = 0;x<MaxAnimationFrames; ++x)
		{
			for(int z = 0;z<MaxAnimationsDirection; ++z)
			{
				bmpAnimalBitmapT[x][z] = null;	
			}
		}			
		
		worm = null;
		super.OnDelete();
	}

	@Override	
	public void OnUpdate(long diff)
	{	
		super.OnUpdate(diff);
		
		if(Health < -5)
			AnimalMenager.DeleteSpider(this);
			
    	if(SluffTimer < diff)
    	{
    		GetNewSluff();
    	}SluffTimer -= diff;	
	
		this.OnEatTime(diff);
		//Move and animations calculation Methods
		this.OnMove(diff);		
	}	
	
	private void SetupStats() 
	{
	
	}
	
	
	private void OnEatTime(long diff)
	{
		if(worm == null)
		{
	    	if(HungryTimer < diff)
	    	{
	    		worm = WormMenager.GetRandomWorm();
	    		if(worm != null && !worm.IsInTerrarium())
	    		{
	    			wayPoint = new Waypoint(worm.GetX(), worm.GetY());
	    		}

	    		Health--;
	    		HungryTimer = 20000;
	    	}HungryTimer -= diff;
		}
		else
		{
			if(worm.IsInRange(this))
			{
				//WormMgr.RemoveWorm(worm); //added support in worm struct
				worm.OnDelete();
				
				Health += 10 * worm.GetType();
				HungryTimer = 8000;
				worm = null;
			}
		}			
	}
	
	private void RandomWaypoint()
	{
		int TerrX, TerrY;
		//TerrX = pTerrarium.GetWidth();
		//TerrY = pTerrarium.GetHeight();
			
		long random = System.currentTimeMillis();
		
		Random r = new Random();
		int randomX = r.nextInt((int) random);
		int randomY = r.nextInt((int) random);
		
		/*
		vectorX = vectorY = 0.0f;
		
		switch(r.nextInt(8))
	    {
	        case 0:   vectorX = 1.0f;     vectorY = -1.0f;    break;
	        case 1: vectorX = 1.0f;     vectorY = 1.0f;     break;
	        case 2:  vectorX = -1.0f;    vectorY = 1.0f;     break;
	        case 3:    vectorX = -1.0f;    vectorY = -1.0f;    break;
	        case 4: 		vectorY = -1.0f; break;
	        case 5: 		vectorY = 1.0f; break;
	        case 6: 		vectorX = -1.0f; break;
	        case 7: 	vectorX = 1.0f; break;
	        default: break;
	    }		
		
		//fGoX = (randomX - (randomX - TerrX)) * vectorX;
		//if(GoX < 0) GoX = 0;
			//RandomWaypoint();
			//fGoX = (randomX - (randomX - TerrX)) * 1;
		//if(fGoX > TerrX) fGoX = 0;
			//RandomWaypoint();
			//fGoX = TerrX;
			
		//fGoY = (randomY - (randomY - TerrY)) * vectorY;
		
		//if(GoY < 0) GoY = 0;
			//fGoY = (randomY - (randomY - TerrY)) * 1;
			//RandomWaypoint();
		//if(fGoY > TerrY) fGoY = 0; 
			//RandomWaypoint();*/
	}
	
	private void OnMove(long diff) 
	{	
		if(MovementFlag == 0)
			return;
		
		if(MovementFlag == 3)
			return;		
			
		if(wayPoint != null)
		{
			if(this.IsOnPosition(wayPoint.PositionX, wayPoint.PositionY))
			{
				if(GetDistance(wayPoint.PositionX, wayPoint.PositionY) < 5/*Radius*/)
				{
					wayPoint = null;
					MovementFlag = 0;
					return;
				}
			}
			
			setupMoveDirection();
			if(OrientationTimer < diff)
			{
				if(setupMoveOrientation() == false)
				{
					OrientationTimer = 1000;
				}
				else 
				{
					calculateMove();
					OnAnimate(diff);
				}	
			}OrientationTimer -= diff;
		}    
	}
	
    private void calculateMove() 
    {
		int fNewX, fNewY, vectorX, vectorY;	
		
		vectorX = vectorY = fNewX = fNewY = 0;  	
    	
		switch(MoveDirection)
	    {
	        case 1: vectorX = 1;     vectorY = -1; break;
	        case 3: vectorX = 1;     vectorY = 1;  break;
	        case 5: vectorX = -1;    vectorY = 1;  break;
	        case 7: vectorX = -1;    vectorY = -1; break;
	        case 0: vectorY = -1; 	break;
	        case 4: vectorY = 1; 	break;
	        case 6: vectorX = -1; 	break;
	        case 2: vectorX = 1; 	break;
	        default: break;
	    }

	    fNewX = PositionX;
	    fNewY = PositionY;
	    
	    if(PositionX != wayPoint.PositionX)
	    {
	    	fNewX = (int) (PositionX + (fSpeed*vectorX));
	    }

	    if(PositionY != wayPoint.PositionY)
	    {
	    	fNewY = (int) (PositionY + (fSpeed*vectorY)); 
	    }

	    SetPosition(fNewX, fNewY);		
	}

	private void setupMoveDirection() 
	{
	    if(PositionX < wayPoint.PositionX && PositionY < wayPoint.PositionY)
	    	MoveDirection = 3;
	    if(PositionX > wayPoint.PositionX && PositionY < wayPoint.PositionY)
	    	MoveDirection = 5;
	    if(PositionX < wayPoint.PositionX && PositionY > wayPoint.PositionY)
	    	MoveDirection = 1;
	    if(PositionX > wayPoint.PositionX && PositionY > wayPoint.PositionY)
	    	MoveDirection = 7;
	    if(PositionX == wayPoint.PositionX && PositionY > wayPoint.PositionY)
	    	MoveDirection = 0;
	    if(PositionX == wayPoint.PositionX && PositionY < wayPoint.PositionY)
	    	MoveDirection = 4;
	    if(PositionX < wayPoint.PositionX && PositionY == wayPoint.PositionY)
	    	MoveDirection = 2;
	    if(PositionX > wayPoint.PositionX && PositionY == wayPoint.PositionY)
	    	MoveDirection = 6;
	}

	boolean CheckOrientation()
    {
    	if(Orientation == MoveDirection)
    		return true;
    	
    	--Orientation;
    	if(Orientation < 0)
    		Orientation = 7;
    	
    	return false;
    }	
	
	private boolean setupMoveOrientation() 
	{
		return CheckOrientation();
	}	
	
	public void SetUpWaypoint(float GoXPoint, float GoYPoint, float GoZPoint)
	{		
		wayPoint = new Waypoint((int)GoXPoint, (int)GoYPoint);
		MovementFlag = 1;

		worm = null;
	}
			
	public void GetNewSluff()
	{
		++SluffLevel;
		//add scale all bitmaps by sluuf size
		//this.bitmap = Surface.LoadBitmap(ObjectID, SluffLevel);
		SluffTimer = 30000;
	}
}
