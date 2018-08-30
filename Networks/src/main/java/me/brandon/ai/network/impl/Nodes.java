package me.brandon.ai.network.impl;

import me.brandon.ai.network.NodeType;

public class Nodes
{

	public static class InputNode extends BasicNode
	{

		public InputNode()
		{
			type = NodeType.Input;
		}

		@Override
		public double compute()
		{
			return value;
		}

	}

	public static class OutputNode extends BasicNode
	{
		public OutputNode()
		{
			type = NodeType.Output;
		}

		@Override
		public double compute()
		{
			value = 0;
			for (BasicConnection con : inputs)
			{
				value += con.compute();
			}
			return value;
		}

	}

	public static class StepNode extends BasicNode
	{
		public StepNode()
		{
			type = NodeType.Hidden;
		}

		@Override
		public double compute()
		{
			return value = super.compute() > 0D ? value : 0D;
		}

	}
}
