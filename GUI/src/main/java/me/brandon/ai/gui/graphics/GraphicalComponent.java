package me.brandon.ai.gui.graphics;

import me.brandon.ai.gui.Drawable;
import me.brandon.ai.gui.GraphicsWrapper;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicalComponent
{

	private Drawable drawable;

	private static final Color backgroundColor = Color.darkGray;
	private static final Color borderColor = Color.lightGray;
	private static final Color hiddenBackgroundColor = Color.red;
	private static final Color hiddenBorderColor = Color.lightGray;
	private static final Color hiddenTextColor = Color.black;

	private boolean focusable;
	private boolean focused;
	private boolean visible;

	protected BufferedImage img;

	private int px;
	private int py;
	private int width;
	private int height;

	private String title;
	private int hiddenWidth;
	private int hiddenHeight;

	private boolean moveable;
	private boolean resizeable;
	private boolean adjWidth;
	private boolean adjHeight;
	private boolean adjPos;

	private int lmx;
	private int lmy;

	public GraphicalComponent(String title, int px, int py, int width, int height)
	{
		this(title, px, py, width, height, true, true, true);
	}

	public GraphicalComponent(String title, int px, int py, int width, int height, boolean focusable, boolean resizeable, boolean moveable)
	{
		this.focusable = focusable;
		this.px = px;
		this.py = py;
		this.width = width;
		this.height = height;
		this.visible = true;
		this.title = title;
		this.resizeable = resizeable;

		this.moveable = moveable;
	}

	public void setDrawable(Drawable drawable)
	{
		this.drawable = drawable;
	}

	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}

	public void setFocusable(boolean focusable)
	{
		this.focusable = focusable;
	}

	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	public void setVisible(boolean flag)
	{
		this.visible = flag;
	}


	public void setResizeable(boolean resizeable)
	{
		this.resizeable = resizeable;
	}

	public String getTitle()
	{
		return title;
	}

	public boolean contains(Point p)
	{
		if (visible)
			return p.x > px && p.y > py && p.x < px + width && p.y < py + height;

		return p.x > px && p.y > py && p.x < px + hiddenWidth && p.y < py + hiddenHeight;
	}

	public boolean click(Point p, boolean doubleClicked, boolean lock)
	{
		if (contains(p))
		{
			if (lock)
			{
				focusable = !focusable;
				return true;
			}
			if (!focusable)
			{
				return false;
			}
			if (doubleClicked)
			{
				visible = !visible;

				if (px < 0)
					px = 0;
				if (py < 0)
					py = 0;

				return true;
			}
			focused = true;
			lmx = p.x;
			lmy = p.y;

			if (resizeable)
			{
				adjWidth = p.x > px + width - 10;
				adjHeight = p.y > py + height - 10;
				adjPos = !(adjWidth || adjHeight);
			}
			else
			{
				adjPos = true;
			}

		}
		else
		{
			focused = false;
		}

		return focused;
	}

	public void move(Point p)
	{
		if (focused && moveable)
		{
			int dx = p.x - lmx;
			int dy = p.y - lmy;
			if (adjPos)
			{
				px += dx;
				py += dy;
			}
			else
			{
				if (adjWidth)
					width += dx;
				if (adjHeight)
					height += dy;

				if (width < 10) width = 10;
				if (height < 10) height = 10;

				onResize();
			}

			lmx = p.x;
			lmy = p.y;
		}
	}

	public void release(Point p)
	{
		if (focused)
		{
			focused = false;
		}
	}

	public GraphicsWrapper wrap(GraphicsWrapper g)
	{
		return g.wrap(px, py, width, height);
	}

	public GraphicsWrapper wrap(Graphics g)
	{
		return GraphicsWrapper.wrap(g, px, py, width, height);
	}

	protected void onResize()
	{

	}

	public void draw(GraphicsWrapper g)
	{
		g = wrap(g);
		if (visible)
		{

			if (drawable != null)
			{
				drawable.draw(g, width, height);
			}
			else
			{
				g.setColor(backgroundColor);
				g.fillRect(0, 0, width, height);
			}

			if (focusable)
			{
				g.setColor(borderColor);
				g.drawRect(0, 0, width, height);
			}
		}
		else
		{
			if (hiddenWidth == 0)
			{
				FontMetrics m = g.getFontMetrics();
				hiddenWidth = m.stringWidth(title) + 4;
				hiddenHeight = m.getHeight() + 4;
			}
			g.setColor(hiddenBackgroundColor);
			g.fillRect(0, 0, hiddenWidth, hiddenHeight);
			g.setColor(hiddenBorderColor);
			g.drawRect(0, 0, hiddenWidth, hiddenHeight);
			g.setColor(hiddenTextColor);
			g.drawString(title, 2, hiddenHeight / 2 + 4);
		}
	}
}
