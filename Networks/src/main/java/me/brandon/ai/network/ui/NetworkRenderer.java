package me.brandon.ai.network.ui;

import me.brandon.ai.gui.Drawable;
import me.brandon.ai.gui.GraphicsWrapper;
import me.brandon.ai.network.Connection;
import me.brandon.ai.network.impl.BasicNetwork;
import me.brandon.ai.network.impl.BasicNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

public class NetworkRenderer implements Drawable
{


	private static final int padX = 10;
	private static final int padY = 10;

	private static final int nodeWidth = 50;
	private static final int nodeHeight = 20;
	private static final Color backgroundColor = Color.darkGray;
	private static final int fgRGB = 120;
	private static final Color textColor = Color.white;

	private static final Color disabledColor = new Color(100, 90, 100);
	private static final Color connHeadColor = Color.cyan;
	private static final int connHeadSize = 6;

	private static final Stroke normStroke = new BasicStroke(1.5f);
	private static final Stroke disabledStroke = new BasicStroke(0.5f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{3f}, 0.0f);


	private BasicNetwork net;

	private boolean recompute;

	private List<NodeData> nodes;
	private List<ConnData> cons;

	private List<TitleData> inputTitles;
	private List<TitleData> outputTitles;

	private int width;
	private int height;


	public NetworkRenderer(BasicNetwork network)
	{
		this.net = network;
		setup();
	}

	public void setInputTitles(String... titles)
	{
		inputTitles = new ArrayList<>();
		for (int i = 0; i < titles.length; i++)
		{
			inputTitles.add(new TitleData(titles[i], nodes.get(i)));
		}
		recompute = true;
	}

	public void setOutputTitles(String... titles)
	{
		outputTitles = new ArrayList<>();
		int oi = nodes.size() - titles.length;
		for (int i = 0; i < titles.length; i++)
		{
			outputTitles.add(new TitleData(titles[i], nodes.get(oi++)));
		}
		recompute = true;
	}

	public void setup()
	{
		nodes = new ArrayList<>();
		cons = new ArrayList<>();

		net.forEachNode((layer, index, node) ->
		{
			nodes.add(new NodeData(node));
		});
		net.forEachNode((layer, index, node) ->
		{
			for (Connection con : node.getInputs())
			{
				NodeData in = nodes.get(((BasicNode) con.input()).getNodeIndex());
				NodeData out = nodes.get(((BasicNode) con.output()).getNodeIndex());
				cons.add(new ConnData(in, out, con.weight(), con.enabled()));
			}
		});


		double maxWeight = Double.MIN_VALUE;
		for (ConnData con : cons)
		{
			double w = Math.abs(con.weight);
			maxWeight = Math.max(maxWeight, w);
		}
		for (ConnData con : cons)
		{
			con.compute(maxWeight);
		}
	}

	public void setPositions(FontMetrics fm, int width, int height)
	{
		this.width = width - padX / 2 - nodeWidth;
		this.height = height - padY / 2;
		int offX = 0;
		int offY = 0;

		if (inputTitles != null)
		{
			int maxSize = 0;

			for (TitleData t : inputTitles)
			{
				t.size = fm.stringWidth(t.title);
				maxSize = Math.max(t.size, maxSize);
			}

			maxSize += padX * 2;

			width -= maxSize;
			offX += maxSize;
		}
		if (outputTitles != null)
		{
			int maxSize = 0;

			for (TitleData t : outputTitles)
			{
				t.size = fm.stringWidth(t.title);
				maxSize = Math.max(t.size, maxSize);
			}

			maxSize += padX * 2;

			width -= maxSize;
		}

		int lcount = net.getLayerCount();

		int sX = (width - nodeWidth - padX * 2) / (lcount - 1);
		int sY;

		Point pos = new Point(offX, offY);
		pos.x -= sX - nodeWidth / 2 - padX;

		int lsize;
		int nodeIndex = 0;
		for (int layer = 0; layer < lcount; layer++)
		{
			lsize = net.getLayerSize(layer);
			sY = height / (lsize);
			pos.x += sX;
			pos.y = sY / 2;

			for (int i = 0; i < lsize; i++)
			{
				nodes.get(nodeIndex++).pos = new Point(pos);
				pos.y += sY;
			}
		}

		if (inputTitles != null)
		{
			for (TitleData t : inputTitles)
			{
				t.pos = new Point(t.node.pos);
				t.pos.x -= t.size + nodeWidth / 2 + padX;
				t.pos.y += 2;
			}
		}
		if (outputTitles != null)
		{
			for (TitleData t : outputTitles)
			{
				t.pos = new Point(t.node.pos);
				t.pos.x += nodeWidth / 2 + padX;
				t.pos.y += 2;
			}
		}

		for (ConnData con : cons)
		{
			con.compute();
		}


		recompute = false;
	}

	@Override
	public void draw(GraphicsWrapper g, int width, int height)
	{
		if (this.width != width || this.height != height)
		{
			recompute = true;
		}

		if (recompute)
			setPositions(g.getFontMetrics(), width, height);

		g.setAntialias();
		g.fillBackground(Color.darkGray);


		g.setColor(Color.white);
		try
		{
			for (ConnData con : cons)
			{
				con.draw(g);
			}
			for (NodeData node : nodes)
			{
				node.draw(g);
			}
			if (inputTitles != null)
			{
				for (TitleData title : inputTitles)
				{
					title.draw(g);
				}
			}
			if (outputTitles != null)
			{
				for (TitleData title : outputTitles)
				{
					title.draw(g);
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private class TitleData
	{
		NodeData node;
		String title;
		Point pos;
		int size;

		TitleData(String title, NodeData node)
		{
			this.title = title;
			this.node = node;
			pos = new Point();
		}

		void draw(GraphicsWrapper g)
		{
			g.setColor(node.node.enabled() ? textColor: disabledColor);
			g.drawString(title, pos);
		}

	}

	private class NodeData
	{
		Point pos;
		BasicNode node;
		boolean enabled;

		NodeData(BasicNode node)
		{
			this.node = node;
			this.enabled = node.enabled();
			pos = new Point(100, 100);
		}

		void draw(GraphicsWrapper g)
		{
			double val = node.getValue();
			g.setStroke(enabled ? normStroke : disabledStroke);

			// background
			g.setColor(backgroundColor);
			g.fillOval(pos, nodeWidth, nodeHeight);

			// background overlay
//			if (enabled)
				g.setColor(new Color(fgRGB, fgRGB, fgRGB, (int) Math.min(255, Math.abs(val * 255))));
//			else
//				g.setColor(disabledColor);
			g.fillOval(pos, nodeWidth, nodeHeight);

			// border
			g.setColor(val > 0 ? Color.green : Color.red);
			g.drawOval(pos, nodeWidth, nodeHeight);

			// text
			g.setColor(textColor);
			g.drawCenteredString(pos, "%2.2f", val);

		}

	}

	private class ConnData
	{
		double weight;
		NodeData in;
		NodeData out;

		Point dirPos;

		boolean enabled;

		Color color;

		ConnData(NodeData in, NodeData out, double weight, boolean enabled)
		{
			this.in = in;
			this.out = out;
			this.weight = weight;
			this.enabled = in.enabled && out.enabled && enabled;
			dirPos = new Point();
		}

		void compute(double max)
		{
			int alpha = (int) (Math.abs(weight) / max * 255);
//			alpha = (int) Math.min(Math.abs(weight) * 255, 255);
			if (weight > 0)
				color = new Color(0, 255, 0, alpha);
			else
				color = new Color(255, 0, 0, alpha);

		}

		void compute()
		{

			double dy = in.pos.y - out.pos.y;
			double dx = in.pos.x - out.pos.x;

			double angle = atan2(dy, dx);

			dirPos = new Point();
			double a = nodeWidth / 2 + connHeadSize / 2;
			double b = nodeHeight / 2 + connHeadSize / 2;
			double sx = b * cos(angle);
			double sy = a * sin(angle);
			dirPos.x = (int) (a * sx / (Math.sqrt(sx * sx + sy * sy))) + out.pos.x + 1;
			dirPos.y = (int) (b * sy / (Math.sqrt(sx * sx + sy * sy))) + out.pos.y + 1;

		}

		void draw(GraphicsWrapper g)
		{


			g.setStroke(enabled ? normStroke : disabledStroke);
//			g.setColor(enabled ? color : disabledColor);
			g.setColor(color);
			g.drawLine(in.pos, out.pos);

//			g.setColor(connHeadColor);
			g.fillCircle(dirPos, connHeadSize);

		}
	}

}
