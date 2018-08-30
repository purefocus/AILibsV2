package me.brandon.ai.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class RenderPanel extends Canvas implements ComponentListener, Renderer
{

	private String title;
	private JPanel contentPane;
	private JFrame frame;

	private BufferedImage img;

	public RenderPanel(int width, int height)
	{
		this(width, height, "Render Panel");
	}

	public RenderPanel(int width, int height, String title)
	{
		this.title = title;
		setPreferredSize(new Dimension(width, height));
		addComponentListener(this);
	}

	public void makeFrame()
	{
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		contentPane = new JPanel(new BorderLayout());
		contentPane.add(this, BorderLayout.CENTER);
	}

	public JPanel getContentPane()
	{
		return contentPane;
	}

	public void displayFrame()
	{
		frame.setContentPane(contentPane);
		frame.pack();
		frame.setVisible(true);
	}

	private Drawable drawable;

	public void render(Drawable renderer)
	{
		this.drawable = renderer;
		render();
	}

	public void render()
	{
		try
		{
			if (img == null)
			{
				img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			}

			BufferStrategy bs = this.getBufferStrategy();

			if (bs == null)
			{
				createBufferStrategy(3);
				return;
			}

			Graphics2D g = (Graphics2D) img.getGraphics();

			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (drawable != null)
			{
				drawable.draw(GraphicsWrapper.wrap(g, 0, 0, img.getWidth(), img.getHeight()), img.getWidth(), img.getHeight());
			}

//			repaint();


			Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
			g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);

			g.dispose();
			bs.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g)
	{
		if(img != null)
			g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
	}

	public void componentResized(ComponentEvent e)
	{
//		render();
	}

	public void componentMoved(ComponentEvent e)
	{

	}

	public void componentShown(ComponentEvent e)
	{

	}

	public void componentHidden(ComponentEvent e)
	{

	}
}