package me.brandon.ai.network.impl.builder;

import me.brandon.ai.network.NetworkBuilder;
import me.brandon.ai.network.NodeType;
import me.brandon.ai.network.impl.BasicNetwork;
import me.brandon.ai.network.impl.BasicNode;
import me.brandon.ai.utils.RandUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static me.brandon.ai.utils.RandUtil.chance;
import static me.brandon.ai.utils.RandUtil.rand;

public class BasicNetworkBuilder<T extends BasicNetwork> implements NetworkBuilder<BasicNetwork>
{

	private List<_Node> nodes;
	private List<List<_Node>> layers;
	private List<_Connection> connections;

	protected Class<? extends BasicNode> nodeClass;

	int inputCount;
	int outputCount;

	public BasicNetworkBuilder()
	{
		nodes = new ArrayList<>();
		layers = new ArrayList<>();
		connections = new ArrayList<>();
	}


	@Override
	public NetworkBuilder inputs(int count)
	{
		this.inputCount = count;
		return layer(count, NodeType.Input);
	}

	@Override
	public NetworkBuilder outputs(int count)
	{
		this.outputCount = count;
		return layer(count, NodeType.Output);
	}

	@Override
	public NetworkBuilder layer(int count)
	{
		return layer(count, NodeType.Hidden);
	}

	@Override
	public NetworkBuilder layer(int count, NodeType type)
	{
		List<_Node> layer = new ArrayList<>();
		for (int i = 0; i < count; i++)
		{
			layer.add(new _Node(type, nodes.size(), i, nodeClass));
		}
		layers.add(layer);
		nodes.addAll(layer);
		return this;
	}

	public NetworkBuilder nodeClass(Class<? extends BasicNode> nodeClass)
	{
		this.nodeClass = nodeClass;
		return this;
	}

	@Override
	public NetworkBuilder node(int l, NodeType type)
	{
		List<_Node> layer = layers.get(l);

		_Node node = new _Node(type, l, layer.size(), nodeClass);

		layer.add(node);
		nodes.add(node);
		return this;
	}

	@Override
	public NetworkBuilder addConnection(int in, int out, double weight)
	{
		connections.add(new _Connection(nodes.get(in), nodes.get(out), weight));
		return this;
	}

	public NetworkBuilder addConnection(int inLayer, int inIndex, int outLayer, int outIndex, double weight)
	{
		connections.add(new _Connection(inLayer, inIndex, outLayer, outIndex, weight));
		return this;
	}

	public NetworkBuilder fullyConnect(int inLayer, int outLayer, int weight)
	{
		for (int in = 0; in < layers.get(inLayer).size(); in++)
		{

			for (int out = 0; out < layers.get(outLayer).size(); out++)
			{
				addConnection(inLayer, in, outLayer, out, weight);
			}
		}
		return this;
	}

	public NetworkBuilder randomizeConnectionWeights(double mag)
	{
		for (_Connection con : connections)
		{
			con.weight = rand(mag);
		}
		return this;
	}

	public NetworkBuilder randomizeConnectionEnabled(double chance)
	{
		for (_Connection con : connections)
		{
			con.enabled = chance(chance);
		}
		for (_Node con : nodes)
		{
			con.enabled = chance(chance);
		}
		return this;
	}


	@Override
	public BasicNetwork buildNetwork()
	{
		return buildNetwork(BasicNetwork.class);
	}

	@Override
	public <F extends BasicNetwork> F buildNetwork(Class<F> networkClass)
	{
		F net = createNetwork(networkClass);
		assert net != null;

		net.setLayerCount(layers.size());

		connections.forEach(_Connection::set);

		for (int layer = 0; layer < layers.size(); layer++)
		{
			net.setLayerSize(layer, layers.get(layer).size());
			for (int i = 0; i < layers.get(layer).size(); i++)
			{
				_Node n = layers.get(layer).get(i);
				BasicNode node;
				if (n.nodeClass != null)
				{
					node = net.setNode(layer, i, n.type, n.nodeClass);
				}
				else
				{
					node = net.setNode(layer, i, n.type);
				}
				node.setEnabled(n.enabled);
				node.setConnectionSizes(n.inputCount, n.outputCount);
			}
		}

		connections.forEach(conn -> net.addConnection(conn.inLayer, conn.inIndex, conn.outLayer, conn.outIndex, conn.weight, conn.enabled));

		if (!net.verifyComplete())
		{
			System.out.println("incomplete network!");
		}

		return net;
	}

	private static <Type extends BasicNetwork> Type createNetwork(Class<Type> clz)
	{
		try
		{
			return clz.newInstance();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	class _Node
	{
		int layer;
		int index;
		NodeType type;
		boolean enabled;

		Class<? extends BasicNode> nodeClass;

		int inputCount;
		int outputCount;

		_Node(NodeType type, int layer, int index, Class<? extends BasicNode> nodeClass)
		{
			this.type = type;
			this.layer = layer;
			this.index = index;
			this.inputCount = 0;
			this.outputCount = 0;
			this.nodeClass = nodeClass;
			this.enabled = true;
		}
	}

	class _Connection
	{
		int inLayer;
		int inIndex;

		int outLayer;
		int outIndex;

		double weight;
		boolean enabled;

		_Connection(_Node in, _Node out, double weight)
		{
			this(in.layer, in.index, out.layer, out.index, weight);
		}

		_Connection(int inLayer, int inIndex, int outLayer, int outIndex, double weight)
		{
			this.inLayer = inLayer;
			this.inIndex = inIndex;
			this.outLayer = outLayer;
			this.outIndex = outIndex;

			this.weight = weight;
			this.enabled = true;
		}

		void set()
		{
			layers.get(inLayer).get(inIndex).outputCount++;
			layers.get(outLayer).get(outIndex).inputCount++;
		}
	}

}
