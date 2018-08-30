package me.brandon.ai.gui.graphics;

import me.brandon.ai.gui.GraphicsWrapper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphicalComponentManager implements MouseMotionListener, MouseListener
{

	private List<GraphicalComponent> components;
	private GraphicalComponent focusedComponent;
	private boolean mouseInWindow;

	public void init(Object comp)
	{
		assert (comp instanceof Component);
		components = new ArrayList<GraphicalComponent>();
		focusedComponent = null;
		((Component) comp).addMouseListener(this);
		((Component) comp).addMouseMotionListener(this);
	}

	public GraphicalComponent addComponent(GraphicalComponent component)
	{
		components.add(component);
		return component;
	}

	public GraphicalComponent addComponent(String title, int px, int py, int width, int height)
	{
		return addComponent(new GraphicalComponent(title, px, py, width, height));
	}

	public GraphicalComponent addComponent(String title, int px, int py, int width, int height, boolean focusable, boolean resizeable, boolean moveable)
	{
		return addComponent(new GraphicalComponent(title, px, py, width, height, focusable, resizeable, moveable));
	}

	public GraphicalComponent getFocusedComponent()
	{
		return focusedComponent;
	}

	public GraphicalComponent getComponent(int i)
	{
		return components.get(i);
	}

	public void draw(GraphicsWrapper g)
	{
		for (GraphicalComponent comp : components)
		{
			comp.draw(g);
		}
	}

	public void mouseClicked(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e)
	{

		focusedComponent = null;
		Point p = e.getPoint();
		for (int i = components.size() - 1; i >= 0; i--)
		{
			GraphicalComponent comp = components.get(i);
			if (comp.click(p, e.getClickCount() == 2, e.isShiftDown()))
			{
				focusedComponent = comp;
				break;
			}
		}

	}

	public void mouseReleased(MouseEvent e)
	{
		if (focusedComponent != null)
			focusedComponent.release(e.getPoint());
		focusedComponent = null;
	}

	public void mouseEntered(MouseEvent e)
	{
		mouseInWindow = true;
	}

	public void mouseExited(MouseEvent e)
	{
		mouseInWindow = false;
	}

	public void mouseDragged(MouseEvent e)
	{
		if (mouseInWindow && focusedComponent != null)
		{
			focusedComponent.move(e.getPoint());
		}
	}

	public void mouseMoved(MouseEvent e)
	{
	}
}
