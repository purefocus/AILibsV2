package me.brandon.ai.network.impl;

import me.brandon.ai.network.Connection;
import me.brandon.ai.network.Node;

public class NetworkIterator
{
	public interface NodeIter<T extends Node>
	{
		void node(int layer, int index, T node);
	}

	public interface ConnectionIter<T extends Connection>
	{
		void connection(T con);
	}
}
