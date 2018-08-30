package me.brandon.ai.utils;

public class RollingAverage
{

	private double[] values;
	private int pos;

	private double sum;

	private int size;

	private double average;

	public RollingAverage(int sampleSize)
	{
		values = new double[sampleSize];
		sum = 0;
		pos = 0;
		size = 0;
	}

	public double addValue(double value)
	{
		sum -= values[pos];
		sum += value;
		values[pos] = value;
		pos++;
		pos %= values.length;

		if (size < values.length)
			size++;

		average = sum / size;

		return average;
	}

	public double getAverage()
	{
		return average;
	}

	public double getSum()
	{
		return sum;
	}

	public void reset()
	{
		size = 0;
		average = 0;
		values[pos] = 0;
	}

}
