package com.example.spiderapi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class MsgMenager 
{
	private static String stringtable[] = { null, null, null, null };
	static private String X = "";
	static private String Y = "";
	static private String Z = "";
	private static int fpsnumber[] = { 0,0,0,0 };

	private static String LoadingInformation = "Loading ... ";
	
	static public void OnUpdate(long timeDiff)
	{

	}
	
	public static void OnDraw(Canvas canvas)
	{
		//Text Testing
		Paint paint = new Paint(); 
		paint.setColor(Color.WHITE);
		paint.setTextSize(20); 
		
		switch(GameCore.GetCurrentGameState())
		{
		    case LoadingScreen:
		    {
				canvas.drawText(LoadingInformation, 20, 550, paint);		
		    	break;
		    }
			case Game:
			{						
				break;
			}
			case InGameMenu:
			{
				break;
			}
			
			case InGameSpiderStat:
			{
				String temp1 = "Spider Health: " + Z;
				canvas.drawText(temp1, 20, 550, paint);		
				
				String temp = "Spider Position: " + X + " : " + Y;
				canvas.drawText(temp, 20, 575, paint);
				
				break;
			}
			case MainMenu:
			{
				break;
			}
			default: break;
		}	
	}
	
	static public void AddMssage(int msgtype, int Fps)
	{
		fpsnumber[msgtype] = Fps;
	}	

	public static void AddMassage(int msgtype, String string) 
	{
		switch(msgtype)
		{
			case 0: LoadingInformation = string;
			default: break;
		}
		
		stringtable[msgtype] = string;
	}
}
