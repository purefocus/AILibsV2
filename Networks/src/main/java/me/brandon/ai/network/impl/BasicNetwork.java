package me.brandon.ai.network.impl;

import me.brandon.ai.network.Network;
import me.brandon.ai.network.NodeType;

import java.lang.reflect.Constructor;

public class BasicNetwork implements Network
{
	protected BasicNode[][] network;

	protected int outputCount;
	protected int outputLayer;

	protected int nodeCount;
	protected int connectionCount;

	protected boolean populated;

	@Override
	public void setInputs(double... inputs)
	{
		for (int i = 0; i < inputs.length; i++)
		{
			network[0][i].setValue(inputs[i]);
		}
	}

	@Override
	public void compute()
	{
		for (int layer = 1; layer < network.length; layer++)
		{
			for (int node = 0; node < network[layer].length; node++)
			{
				network[layer][node].compute();
			}
		}
	}

	@Override
	public double[] getOutputs()
	{
		double output[] = new double[outputCount];
		for (int i = 0; i < outputCount; i++)
		{
			output[i] = network[outputLayer][i].getValue();
		}
		return output;
	}

	@Override
	public void train(double... desired)
	{

	}

	public double[][] getComputedNetworkValues()
	{

		double[][] out = new double[network.length][];
		for (int layer = 0; layer < network.length; layer++)
		{
			out[layer] = new double[network[layer].length];
			for (int i = 0; i < network[layer].length; i++)
			{
				out[layer][i] = network[layer][i].getValue();
			}
		}

		return out;

	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int layer = 0; layer < network.length; layer++)
		{
			sb.append("\nLayer ").append(layer).append(": ");
			for (int i = 0; i < network[layer].length; i++)
			{
				BasicNode node = network[layer][i];
				sb.append("[").append(node.type).append(", ").append(node.value).append(", ").append(node.enabled).append("], ");
			}
		}

		return sb.toString();
	}

	// ---------------- Utility QoL methods ----------------
	public void forEachNode(NetworkIterator.NodeIter<BasicNode> iter)
	{
		for (int layer = 0; layer < network.length; layer++)
		{
			for (int i = 0; i < network[layer].length; i++)
			{
				iter.node(layer, i, network[layer][i]);
			}
		}
	}

	public void forEachConnection(NetworkIterator.NodeIter<BasicNode> iter)
	{
		for (int layer = 0; layer < network.length; layer++)
		{
			for (int i = 0; i < network[layer].length; i++)
			{
				iter.node(layer, i, network[layer][i]);
			}
		}
	}

	public int getNodeCount()
	{
		return nodeCount;
	}

	public int getConnectionCount()
	{
		return connectionCount;
	}

	public int getLayerCount()
	{
		return network.length;
	}

	public int getLayerSize(int layer)
	{
		return network[layer].length;
	}

	@SuppressWarnings("unchecked")
	public <T extends BasicNode> T getNodeAt(int layer, int index)
	{
		return (T) network[layer][index];
	}


	// ---------------- Network creation methods ----------------

	public void setLayerCount(int layers)
	{
		if (populated)
		{
			expandNetworkLayers(layers);
		}
		else
		{
			network = new BasicNode[layers][];
		}
	}

	public void setLayerSize(int layer, int size)
	{
		if (layer == network.length - 1)
		{
			outputLayer = layer;
			outputCount = size;
		}
		if (network[layer] == null)
		{
			network[layer] = new BasicNode[size];
		}
		else
		{
			expandLayerSize(layer, size);
		}
	}

	public BasicNode addNode(int layer, NodeType type)
	{
		for (int i = 0; i < network[layer].length; i++)
		{
			if (network[layer][i] == null)
			{
				return setNode(layer, i, type);
			}
		}
		return null;
	}

	public BasicNode setNode(int layer, int index, NodeType type)
	{
		switch (type)
		{
			case Input:
				return setNode(layer, index, type, Nodes.InputNode.class);
			case Output:
				return setNode(layer, index, type, Nodes.OutputNode.class);
			case Hidden:
				return setNode(layer, index, type, Nodes.StepNode.class);
		}
		return null;
	}

	public BasicNode setNode(int layer, int index, NodeType type, Class<? extends BasicNode> nodeClz)
	{
		try
		{
			BasicNode node;
			Constructor<? extends BasicNode> constructor = null;
			try
			{
				constructor = nodeClz.getConstructor(NodeType.class);
			} catch (Exception e)
			{

			}
			if (constructor == null)
			{
				node = nodeClz.newInstance();
			}
			else
			{
				node = constructor.newInstance(type);
			}
			return setNode(layer, index, node);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public BasicNode setNode(int layer, int index, BasicNode node)
	{
		node.layer = layer;
		node.index = index;
		nodeCount++;
		return network[layer][index] = node;
	}

	public void addConnection(int inLayer, int inIndex, int outLayer, int outIndex, double weight)
	{
		addConnection(inLayer, inIndex, outLayer, outIndex, weight, true);
	}

	public void addConnection(int inLayer, int inIndex, int outLayer, int outIndex, double weight, boolean enabled)
	{
		BasicNode in = network[inLayer][inIndex];
		BasicNode out = network[outLayer][outIndex];
		BasicConnection connection = new BasicConnection(in, out, weight, enabled);
		in.addConnection(connection);
		out.addConnection(connection);

		connectionCount++;
	}

	public boolean verifyComplete()
	{
		int nodeIndex = 0;
		for (int layer = 0; layer < network.length; layer++)
		{
			for (int i = 0; i < network[layer].length; i++)
			{
				if (network[layer][i] == null)
					return false;
				network[layer][i].nodeIndex = nodeIndex++;
			}
		}
		return true;
	}

	private void expandNetworkLayers(int newSize)
	{
		assert network != null && newSize <= network.length;
		BasicNode newNet[][] = new BasicNode[newSize][];

		System.arraycopy(network, 0, newNet, 0, network.length);

		network = newNet;
	}

	private void expandLayerSize(int layer, int newSize)
	{
		assert network[layer] != null && newSize <= network[layer].length;
		BasicNode newLayer[] = new BasicNode[newSize];

		System.arraycopy(network[layer], 0, newLayer, 0, network.length);

		network[layer] = newLayer;
	}
}
