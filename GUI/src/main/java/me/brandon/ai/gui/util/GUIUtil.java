package me.brandon.ai.gui.util;

import me.brandon.ai.gui.Drawable;
import me.brandon.ai.gui.GUIFrame;
import me.brandon.ai.gui.RenderPanel;

public class GUIUtil
{

	public static RenderPanel createRenderGUI(Drawable drawable, int width, int height)
	{
		GUIFrame gui = new GUIFrame();
		RenderPanel panel = new RenderPanel(width, height);
		gui.setCenterComponent(panel);
		panel.render(drawable);

		gui.showFrame();

		return panel;
	}

	public static void createRenderLoop(RenderPanel panel, Drawable drawable)
	{
		createRenderLoop(panel, drawable, 10, -1);
	}

	public static void createRenderLoop(RenderPanel panel, Drawable drawable, int tickDelay)
	{
		createRenderLoop(panel, drawable, tickDelay, -1);
	}

	public static void createRenderLoop(RenderPanel panel, Drawable drawable, int tickDelay, int numTimes)
	{

		new Thread(() ->
		{
			for (int i = numTimes; i != 0; i--)
			{
				panel.render(drawable);
				try
				{
					Thread.sleep(tickDelay);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

}
