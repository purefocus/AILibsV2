package me.brandon.ai.gui;

import me.brandon.ai.util.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicsWrapper
{

	public static GraphicsWrapper wrap(Graphics g)
	{
		return new GraphicsWrapper(g);
	}

	public static GraphicsWrapper wrap(Graphics g, double offX, double offY)
	{
		return new GraphicsWrapper(g, offX, offY);
	}

	public static GraphicsWrapper wrap(Graphics g, double offX, double offY, double width, double height)
	{
		return new GraphicsWrapper(g, offX, offY, width, height);
	}

	public static boolean allowAntiAliasing = true;

	private final Graphics g;
	public final double offX;
	public final double offY;
	public final double width;
	public final double height;
	public final Rectangle bounds;


	public GraphicsWrapper(Graphics g)
	{
		this(g, 0, 0);
	}

	public GraphicsWrapper(Graphics g, double offX, double offY)
	{
		this(g, offX, offY, 500, 500);
	}

	public GraphicsWrapper(Graphics g, double offX, double offY, double width, double height)
	{
		this.g = g;
		this.offX = offX;
		this.offY = offY;
		this.width = width;
		this.height = height;
		this.bounds = new Rectangle((int) offX, (int) offY, (int) width, (int) height);
	}

	public Graphics getGraphics()
	{
		return g;
	}

	public GraphicsWrapper wrap(double offX, double offY)
	{
		return wrap(g, offX, offY);
	}

	public GraphicsWrapper wrap(Point off)
	{
		return wrap(g, off.x, off.y);
	}

	public GraphicsWrapper wrap(double offX, double offY, double width, double height)
	{
		return wrap(g, offX, offY, width, height);
	}

	public GraphicsWrapper wrap(Rectangle bounds)
	{
		return wrap(g, bounds.x, bounds.y, bounds.width, bounds.height);
	}

	public void setAntialias()
	{
		if (allowAntiAliasing)
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void dispose()
	{
		g.dispose();
	}

	public void setColor(Color c)
	{
		g.setColor(c);
	}

	public void fillBackground(Color color)
	{
		setColor(color);
		fillRect(0, 0, width, height);
	}

	public void drawLine(double x1, double y1, double x2, double y2)
	{
		g.drawLine((int) (offX + x1), (int) (offY + y1), (int) (offX + x2), (int) (offY + y2));
	}

	public void drawLine(Vector v1, Vector v2)
	{
		g.drawLine((int) (offX + v1.x), (int) (offY + v1.y), (int) (offX + v2.x), (int) (offY + v2.y));
	}

	public void drawLine(Point v1, Point v2)
	{
		g.drawLine((int) (offX + v1.x), (int) (offY + v1.y), (int) (offX + v2.x), (int) (offY + v2.y));
	}

	public void drawRect(Rectangle rect)
	{
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}

	public void fillRect(Rectangle rect)
	{
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}

	public void drawRect(double x, double y, double w, double h)
	{
		g.drawRect((int) (offX + x), (int) (offY + y), (int) w, (int) h);
	}

	public void drawRect(Vector pos, double w, double h)
	{
		g.drawRect((int) (offX + pos.x), (int) (offY + pos.y), (int) w, (int) h);
	}

	public void drawRect(Point pos, double w, double h)
	{
		g.drawRect((int) (offX + pos.x), (int) (offY + pos.y), (int) w, (int) h);
	}

	public void fillRect(double x, double y, double w, double h)
	{
		g.fillRect((int) (offX + x), (int) (offY + y), (int) w, (int) h);
	}

	public void fillRect(Vector pos, double w, double h)
	{
		g.fillRect((int) (offX + pos.x), (int) (offY + pos.y), (int) w, (int) h);
	}

	public void fillRect(Point pos, double w, double h)
	{
		g.fillRect((int) (offX + pos.x), (int) (offY + pos.y), (int) w, (int) h);
	}

	public void drawOval(double x, double y, double w, double h)
	{
		g.drawOval((int) (offX + x - w / 2 - 1), (int) (offY + y - h / 2 - 1), (int) w, (int) h);
	}

	public void drawOval(Vector pos, double w, double h)
	{
		g.drawOval((int) (offX + pos.x - w / 2 - 1), (int) (offY + pos.y - h / 2 - 1), (int) w, (int) h);
	}

	public void drawOval(Point pos, double w, double h)
	{
		g.drawOval((int) (offX + pos.x - w / 2 - 1), (int) (offY + pos.y - h / 2 - 1), (int) w, (int) h);
	}

	public void fillOval(double x, double y, double w, double h)
	{
		g.fillOval((int) (offX + x - w / 2 - 1), (int) (offY + y - h / 2 - 1), (int) w, (int) h);
	}

	public void fillOval(Vector pos, double w, double h)
	{
		g.fillOval((int) (offX + pos.x - w / 2 - 1), (int) (offY + pos.y - h / 2 - 1), (int) w, (int) h);
	}

	public void fillOval(Point pos, double w, double h)
	{
		g.fillOval((int) (offX + pos.x - w / 2 - 1), (int) (offY + pos.y - h / 2 - 1), (int) w, (int) h);
	}

	public void drawCircle(double x, double y, double r)
	{
		g.drawOval((int) (offX + x - r / 2 - 1), (int) (offY + y - r / 2 - 1), (int) r, (int) r);
	}

	public void drawCircle(Vector pos, double r)
	{
		g.drawOval((int) (offX + pos.x - r / 2 - 1), (int) (offY + pos.y - r / 2 - 1), (int) r, (int) r);
	}

	public void drawCircle(Point pos, double r)
	{
		g.drawOval((int) (offX + pos.x - r / 2 - 1), (int) (offY + pos.y - r / 2 - 1), (int) r, (int) r);
	}

	public void fillCircle(double x, double y, double r)
	{
		g.fillOval((int) (offX + x - r / 2 - 1), (int) (offY + y - r / 2 - 1), (int) r, (int) r);
	}

	public void fillCircle(Vector pos, double r)
	{
		g.fillOval((int) (offX + pos.x - r / 2 - 1), (int) (offY + pos.y - r / 2 - 1), (int) r, (int) r);
	}

	public void fillCircle(Point pos, double r)
	{
		g.fillOval((int) (offX + pos.x - r / 2 - 1), (int) (offY + pos.y - r / 2 - 1), (int) r, (int) r);
	}

	public void drawPolygon(Polygon poly)
	{
		poly.translate((int) offX, (int) (offY));
		g.drawPolygon(poly);
	}

	public void drawString(String str, int x, int y)
	{
		g.drawString(str, (int) offX + x, (int) offY + y);
	}

	public void drawString(String str, Point pos)
	{
		g.drawString(str, (int) offX + pos.x, (int) offY + pos.y);
	}

	public void drawString(int x, int y, String format, Object... obj)
	{
		g.drawString(String.format(format, obj), (int) offX + x, (int) offY + y);
	}

	public void drawString(Point pos, String format, Object... obj)
	{
		g.drawString(String.format(format, obj), (int) offX + pos.x, (int) offY + pos.y);
	}

	public void drawCenteredString(String str, int x, int y)
	{
		FontMetrics metrics = g.getFontMetrics();
		int w = metrics.stringWidth(str);
		int h = metrics.getHeight();
		g.drawString(str, x - w / 2, y + h / 2 - 2);
	}

	public void drawCenteredString(String str, Point pos)
	{
		drawCenteredString(str, pos.x, pos.y);
	}

	public void drawCenteredString(Point pos, String format, Object... obj)
	{
		drawCenteredString(String.format(format, obj), pos.x, pos.y);
	}

	public void drawImage(BufferedImage img, int x, int y, int w, int h)
	{
		g.drawImage(img, x, y, w, h, null);
	}


	public void drawImage(BufferedImage img, int x, int y)
	{
		g.drawImage(img, x, y, img.getWidth(), img.getHeight(), null);
	}

	public FontMetrics getFontMetrics()
	{
		return g.getFontMetrics();
	}

	public int stringWidth(String str)
	{
		return getFontMetrics().stringWidth(str);
	}

	public void setStroke(int width)
	{
		setStroke(new BasicStroke(width));
	}

	public void setStroke(Stroke stroke)
	{
		((Graphics2D) g).setStroke(stroke);
	}

}
