package me.brandon.ai.network;

public interface Network
{

	void setInputs(double... inputs);

	void compute();

	double[][] getComputedNetworkValues();

	double[] getOutputs();

	void train(double... desired);

}
