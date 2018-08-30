package me.brandon.ai.network.impl;

import me.brandon.ai.network.Connection;
import me.brandon.ai.network.Node;

public class BasicConnection implements Connection
{

	protected double weight;
	protected BasicNode input;
	protected BasicNode output;

	protected boolean enabled;

	public BasicConnection(BasicNode input, BasicNode output, double weight, boolean enabled)
	{
		this.input = input;
		this.output = output;
		this.weight = weight;
		this.enabled = enabled;
	}

	@Override
	public double weight()
	{
		return weight;
	}

	public double compute()
	{
		return weight * input.value;
	}

	@Override
	public boolean enabled()
	{
		return enabled && input.enabled;
	}

	@Override
	public Node input()
	{
		return input;
	}

	@Override
	public Node output()
	{
		return output;
	}


	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
}
