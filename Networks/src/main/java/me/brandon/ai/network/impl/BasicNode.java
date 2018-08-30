package me.brandon.ai.network.impl;

import me.brandon.ai.network.Connection;
import me.brandon.ai.network.Node;
import me.brandon.ai.network.NodeType;

public class BasicNode implements Node
{

	protected int layer;
	protected int index;
	protected int nodeIndex;

	protected NodeType type;

	protected boolean enabled;
	protected double value;
	protected BasicConnection[] inputs;
	protected BasicConnection[] outputs;

	public BasicNode()
	{
		enabled = true;
	}

	public BasicNode(NodeType type)
	{
		this();
		this.type = type;
	}

	@Override
	public double compute()
	{
		if (enabled)
		{
			value = 0;
			for (BasicConnection con : inputs)
			{
				if (con.enabled())
				{
					value += con.compute();
				}
			}
		}

		return value;
	}

	@Override
	public double getValue()
	{
		return value;
	}

	@Override
	public void setValue(double value)
	{
		this.value = value;
	}

	@Override
	public NodeType type()
	{
		return type;
	}

	@Override
	public boolean enabled()
	{
		return enabled;
	}

	@Override
	public Connection[] getInputs()
	{
		return inputs;
	}

	// ----------- Utility QoL Methods -----------
	public int getLayer()
	{
		return layer;
	}

	public int getIndex()
	{
		return index;
	}

	public int getNodeIndex()
	{
		return nodeIndex;
	}

	// ----------- Network Creation Methods -----------
	public void setConnectionSizes(int inSize, int outSize)
	{
		inputs = new BasicConnection[inSize];
		outputs = new BasicConnection[outSize];
	}

	public void addConnection(BasicConnection connection)
	{
		if (connection.input.equals(this))
		{
			for (int i = 0; i < inputs.length; i++)
			{
				if (inputs[i] == null)
				{
					inputs[i] = connection;
					return;
				}
			}
		}
		else if (connection.output.equals(this))
		{
			for (int i = 0; i < inputs.length; i++)
			{
				if (inputs[i] == null)
				{
					inputs[i] = connection;
					return;
				}
			}
		}
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}
