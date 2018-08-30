package me.brandon.ai.utils;

import java.util.Random;

public class RandUtil
{

	private static Random rand = new Random(10);

	public static void seed(long seed)
	{
		rand = new Random(seed);
	}

	public static boolean chance(double chance)
	{
		return rand.nextDouble() <= chance;
	}

	public static double rand()
	{
		return (rand.nextDouble() - 0.5) * 2;
	}

	public static double rand(double mag)
	{
		return rand() * mag;
	}

	public static double rand(double max, double min)
	{
		return (rand() - min) * (max - min) + min;
	}


}
