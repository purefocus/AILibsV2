package me.brandon.ai.utils;

public abstract class SimpleTester implements Runnable
{

	private boolean running;
	protected int tickDelay;
	protected int ticks;
	protected double avgComputeTime;
	RollingAverage avg;

	public SimpleTester()
	{
		avg = new RollingAverage(20);
	}

	public abstract void setup();

	public abstract void tick();

	public void begin()
	{
		running = true;
		new Thread(this).run();
	}

	public void stop()
	{
		running = false;
	}

	@Override
	public void run()
	{
		while (running)
		{
			long start = System.currentTimeMillis();
			tick();
			avgComputeTime = avg.addValue(System.currentTimeMillis() - start);

			ticks++;


			try
			{
				Thread.sleep(tickDelay);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
	}

}
