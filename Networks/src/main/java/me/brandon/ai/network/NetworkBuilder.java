package me.brandon.ai.network;

import me.brandon.ai.network.impl.BasicNode;

@SuppressWarnings("UnusedReturnValue")
public interface NetworkBuilder<T extends Network>
{

	NetworkBuilder inputs(int count);

	NetworkBuilder outputs(int count);

	NetworkBuilder layer(int count);

	NetworkBuilder layer(int count, NodeType type);

	NetworkBuilder nodeClass(Class<? extends BasicNode> nodeClass);

	NetworkBuilder node(int layer, NodeType type);

	NetworkBuilder addConnection(int in, int out, double weight);

	NetworkBuilder addConnection(int inLayer, int inIndex, int outLayer, int outIndex, double weight);

	NetworkBuilder fullyConnect(int inLayer, int outLayer, int weight);

	NetworkBuilder randomizeConnectionWeights(double mag);

	NetworkBuilder randomizeConnectionEnabled(double chance);

	T buildNetwork();

	<F extends T> F buildNetwork(Class<F> networkClass);

}
