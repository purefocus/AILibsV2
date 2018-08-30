package me.brandon.ai.network;

public interface Node
{

	double compute();

	double getValue();

	void setValue(double value);

	NodeType type();

	boolean enabled();

	Connection[] getInputs();

}
