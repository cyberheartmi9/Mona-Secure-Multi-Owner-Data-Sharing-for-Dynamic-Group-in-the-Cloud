package com.mona.key;
import java.util.Random;
public class SourceAndPort 
{
	Random rr=new Random();
	String str="";
	public String getSource()
	{
		str="";
		str="BSMR"+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10));
		return str;
	}
	public int getPort()
	{
		String str="";
		str=String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10))+String.valueOf(rr.nextInt(10));
		return Integer.parseInt(str);
	}
	public int getDistance()
	{
		String str="";
		str=String.valueOf(rr.nextInt(100));
		return Integer.parseInt(str);
	}
}