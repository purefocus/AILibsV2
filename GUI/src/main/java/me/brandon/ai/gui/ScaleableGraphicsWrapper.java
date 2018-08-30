package me.brandon.ai.gui;

import java.awt.*;

public class ScaleableGraphicsWrapper extends GraphicsWrapper
{
	public ScaleableGraphicsWrapper(Graphics g)
	{
		super(g);
	}

	public ScaleableGraphicsWrapper(Graphics g, double offX, double offY)
	{
		super(g, offX, offY);
	}

	public ScaleableGraphicsWrapper(Graphics g, double offX, double offY, double width, double height)
	{
		super(g, offX, offY, width, height);
	}
}
