package com.example.spiderapi;

import java.util.Random;

public class Spider extends Animal
{	
	private int SluffLevel = 0;//wylinka ze slownika :D
	private int SluffTimer = 10000;

	//Moving Variables	
	private float vectorX, vectorY;
	private int RandomWaypointTimer = 5000;
	
	//Pointers
	private Worm worm = null;	

	public int GetSluffLevel() { return SluffLevel; }
	
	public Spider(int objectID)
	{
		ObjectID = objectID;
		this.OnCreate();
	}	
	
	@Override
	protected void OnCreate() 
	{
		MsgMenager.AddLoadingInfo(0, "Loading Spider Bitmap");
		
		super.OnCreate();
	
		//Initialize Variable
		MovementFlag = 1; //super
		fSpeed = 0.5f;

		this.SetPosition(300,300);
		this.SetupStats();
	}
	
	@Override
	public void OnDelete() 
	{
		worm = null;
		super.OnDelete();
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
	    		if(worm != null && !worm.IsInWormBox())
	    		{
		    		GoX = worm.GetX();
		    		GoY = worm.GetY();
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
		if(GoX < 0) GoX = 0;
			//RandomWaypoint();
			//fGoX = (randomX - (randomX - TerrX)) * 1;
		//if(fGoX > TerrX) fGoX = 0;
			//RandomWaypoint();
			//fGoX = TerrX;
			
		//fGoY = (randomY - (randomY - TerrY)) * vectorY;
		
		if(GoY < 0) GoY = 0;
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
		
	    if(PositionX == GoX && PositionY == GoY)
	    {	
	        //StopMove();
	    	//set flag not moving
	        return;
	    }
 
		int fNewX, fNewY;	
	    
	    vectorX = vectorY = fNewX = fNewY = 0;

	    MoveDirection Move = null;

	    if(PositionX < GoX && PositionY < GoY)
	    	Move = MoveDirection.DownRight;
	    if(PositionX > GoX && PositionY < GoY)
	    	Move = MoveDirection.DownLeft;
	    if(PositionX < GoX && PositionY > GoY)
	    	Move = MoveDirection.UpRight;
	    if(PositionX > GoX && PositionY > GoY)
	    	Move = MoveDirection.UpLeft;
	    if(PositionX == GoX && PositionY > GoY)
	    	Move = MoveDirection.Up;
	    if(PositionX == GoX && PositionY < GoY)
	    	Move = MoveDirection.Down;
	    if(PositionX < GoX && PositionY == GoY)
	    	Move = MoveDirection.Right;
	    if(PositionX > GoX && PositionY == GoY)
	    	Move = MoveDirection.Left;	    
	        
	    //Prepare orientation
	    //ChangeOrientationDueToWaypoint();
	    //if(!CheckOrientation(Move))     	
	    //	return;   
	    
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

	    fNewX = PositionX;
	    fNewY = PositionY;
	    
	    if(PositionX != GoX)
	    {
	    	fNewX = (int) (PositionX + (fSpeed*vectorX));
	    }

	    if(PositionY != GoY)
	    {
	    	fNewY = (int) (PositionY + (fSpeed*vectorY)); 
	    }

	    SetPosition(fNewX, fNewY);
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
    		--Orientation;
    		if(Orientation == -1)
    			Orientation = 7;
    	}
    	else
    	{
    		++Orientation;
    		if(Orientation == 8)
    			Orientation = 0;  		
    	}

    	return false;
    }	
	
	public void SetUpWaypoint(float GoXPoint, float GoYPoint, float GoZPoint)
	{		
		GoX = (int) GoXPoint;
		GoY = (int) GoYPoint;
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
}
