package com.example.spiderapi;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Spider extends Animal
{	
	//Graphics
	private Bitmap bmpBitmapTable[][] = {   { null, null, null, null, null, null, null, null, }, 
											{ null, null, null, null, null, null, null, null, },
											{ null, null, null, null, null, null, null, null, },
											{ null, null, null, null, null, null, null, null, },
											{ null, null, null, null, null, null, null, null, },
											{ null, null, null, null, null, null, null, null, },
											{ null, null, null, null, null, null, null, null, },
											{ null, null, null, null, null, null, null, null, },
										};

	private int SluffLevel = 0;//wylinka ze slownika :D
	private int SluffTimer = 10000;

	//Moving Variables

	//private float fGoZ = 0.0f;
	
	private float vectorX, vectorY, fNewX, fNewY;	
	private int RandomWaypointTimer = 5000;
	
	//Pointers
	private Worm worm = null;	
		
	public Spider()
	{
		this.OnCreate();
	}	
	
	@Override
	protected void OnCreate() 
	{
		super.OnCreate();

		ObjectID = 0;
		
		//Initialize Variable
		fRadius = 10.0f;
		MovementFlag = 1; //super
		fSpeed = 0.5f;

		this.SetPosition(100,100);
		
		bmpBitmapTable[0][0] = GFXSurface.GetSurface().LoadBitmap(ObjectID, 0, 0);
		
		//Load Bitmaps
		for(int x=0; x<MaxAnimationsDirection; ++x)
		{
			for(int i=0; i<MaxAnimationFrames; ++i)
			{		
				
				bmpBitmapTable[x][i] = bmpBitmapTable[0][0];
				bmpBitmapTable[x][i] = Bitmap.createScaledBitmap(bmpBitmapTable[x][i], 150, 150, true);
				/*
				bmpBitmapTable[x][i] = GFXSurface.GetSurface().LoadBitmap(ObjectID, x, i);
				bmpBitmapTable[x][i] = Bitmap.createScaledBitmap(bmpBitmapTable[x][i], 150, 150, true);
				*/
			}
		}

		fWidth = this.bmpBitmapTable[0][0].getWidth();
		fHeight = this.bmpBitmapTable[0][0].getHeight();
	}
	
	private void OnEatTime(long diff)
	{
		if(worm == null)
		{
	    	if(HungryTimer < diff)
	    	{
	    		worm = WormMenager.GetRandomWorm();
	    		if(worm != null && !worm.IsInWormBox())
	    		{
		    		fGoX = worm.GetX();
		    		fGoY = worm.GetY();
	    		}
	    		else
	    		{
	    			Health--;
	    		}
	    		HungryTimer = 20000;
	    	}HungryTimer -= diff;
		}
		else
		{
			if(worm.IsInRange(this))
			{
				//WormMgr.RemoveWorm(worm); //added support in worm struct
				worm.OnRemove();
				worm = null;
				Health++;
				HungryTimer = 8000;
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
		if(fGoX < 0) fGoX = 0;
			//RandomWaypoint();
			//fGoX = (randomX - (randomX - TerrX)) * 1;
		//if(fGoX > TerrX) fGoX = 0;
			//RandomWaypoint();
			//fGoX = TerrX;
			
		//fGoY = (randomY - (randomY - TerrY)) * vectorY;
		
		if(fGoY < 0) fGoY = 0;
			//fGoY = (randomY - (randomY - TerrY)) * 1;
			//RandomWaypoint();
		//if(fGoY > TerrY) fGoY = 0; 
			//RandomWaypoint();
	}
	
	private void OnMove(long diff) 
	{	
		if(MovementFlag == 0)
			return;
		
		if(MovementFlag == 3)
			return;		
		
		/*
		if(worm == null)
		{
	    	if(RandomWaypointTimer < diff)
	    	{
	    		RandomWaypoint();
	    		RandomWaypointTimer = 10000;
	    	}RandomWaypointTimer -= diff;	
		}
		*/
		
	    if(fPosX == fGoX && fPosY == fGoY)
	    {	
	        //StopMove();
	    	//set flag not moving
	        return;
	    }
 
	    vectorX = vectorY = fNewX = fNewY = 0.0f;

	    MoveDirection Move = null;

	    if(fPosX < fGoX && fPosY < fGoY)
	    	Move = MoveDirection.DownRight;
	    if(fPosX > fGoX && fPosY < fGoY)
	    	Move = MoveDirection.DownLeft;
	    if(fPosX < fGoX && fPosY > fGoY)
	    	Move = MoveDirection.UpRight;
	    if(fPosX > fGoX && fPosY > fGoY)
	    	Move = MoveDirection.UpLeft;
	    if(fPosX == fGoX && fPosY > fGoY)
	    	Move = MoveDirection.Up;
	    if(fPosX == fGoX && fPosY < fGoY)
	    	Move = MoveDirection.Down;
	    if(fPosX < fGoX && fPosY == fGoY)
	    	Move = MoveDirection.Right;
	    if(fPosX > fGoX && fPosY == fGoY)
	    	Move = MoveDirection.Left;	    
	        
	    //Prepare orientation
	    //ChangeOrientationDueToWaypoint();
	    
	    if(!CheckOrientation(Move))     	
	    	return;   
	    
	    if(Move != null)
	    {
			switch(Move)
		    {
		        case UpRight:   vectorX = 1.0f;     vectorY = -1.0f; break;
		        case DownRight: vectorX = 1.0f;     vectorY = 1.0f;  break;
		        case DownLeft:  vectorX = -1.0f;    vectorY = 1.0f;  break;
		        case UpLeft:    vectorX = -1.0f;    vectorY = -1.0f; break;
		        case Up: 		vectorY = -1.0f; 	break;
		        case Down: 		vectorY = 1.0f; 	break;
		        case Left: 		vectorX = -1.0f; 	break;
		        case Right: 	vectorX = 1.0f; 	break;
		        default: break;
		    }
	    }

	    fNewX = fPosX;
	    fNewY = fPosY;
	    
	    if(fPosX != fGoX)
	        fNewX = fPosX + (fSpeed*vectorX);

	    if(fPosY != fGoY)
	        fNewY = fPosY + (fSpeed*vectorY); 

        fPosX = fNewX; 
        fPosY = fNewY;	        
	}
	
    boolean CheckOrientation(MoveDirection MoveDir)
    {
    	if(Orientation /*moveDirection*/ == MoveDir.ordinal())
    		return true;
    	
    	//int a = moveDirection.ordinal();
    	int b = MoveDir.ordinal();
    	int c = 0;
    	if(Orientation > b)
    	{
    		c = (Orientation - b);
    	}
    	else
    		c = (b - Orientation);
    	
    	if(c < 4)
    	{
    		++Orientation;
    		if(Orientation == 8)
    			Orientation = 0;
    	}
    	else
    	{
    		--Orientation;
    		if(Orientation == -1)
    			Orientation = 7;
    	}

    	MsgMenager.AddMassage(0,"Orientation: " + Orientation + "");  	
    	
    	return false;
    }	
	
	public void SetUpWaypoint(float GoX, float GoY, float GoZ)
	{		
		fGoX = GoX;
		fGoY = GoY;
		//fGoZ = GoZ;
		worm = null;
	}
		
	public void GetNewSluff()
	{
		++SluffLevel;
		//add scale all bitmaps by sluuf size
		//this.bitmap = Surface.LoadBitmap(ObjectID, SluffLevel);
		SluffTimer = 30000;
	}
	
	@Override	
	public void OnUpdate(long diff)
	{	
		super.OnUpdate(diff);
		
		//if(Health < -5)
			//return;
			//Smierc :D
		
		if(Health <= 0)
			HungryTimer = 2000;
		
    	if(SluffTimer < diff)
    	{
    		GetNewSluff();
    	}SluffTimer -= diff;	
	
		this.OnEatTime(diff);

		//Move and animations calculation Methods
		this.OnMove(diff);
		this.OnAnimate(diff);
	}

	@Override
	public void OnDraw(Canvas canvas) 
	{
		//super.OnDraw(canvas);
		if(bmpBitmapTable[Orientation][AnimationCurrentState] != null)
			GFXSurface.GetSurface().OnDraw(canvas, bmpBitmapTable[Orientation][AnimationCurrentState], fPosX-(fWidth/2), fPosY-(fHeight/2));
	}
}
