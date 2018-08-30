package me.brandon.ai.network;

import me.brandon.ai.gui.RenderPanel;
import me.brandon.ai.gui.util.GUIUtil;
import me.brandon.ai.network.impl.BasicNetwork;
import me.brandon.ai.network.impl.BasicNode;
import me.brandon.ai.network.impl.Nodes;
import me.brandon.ai.network.impl.builder.BasicNetworkBuilder;
import me.brandon.ai.network.ui.NetworkRenderer;

public class Test
{

	public static void main(String[] args)
	{
		BasicNetworkBuilder builder = new BasicNetworkBuilder();
		builder.inputs(4)
				.nodeClass(BasicNode.class)
				.layer(2)
				.nodeClass(Nodes.StepNode.class)
				.layer(4)
				.nodeClass(BasicNode.class)
				.layer(3)
				.outputs(4)
				.fullyConnect(0, 1, 1)
				.fullyConnect(1, 2, 1)
				.fullyConnect(2, 3, 1)
				.fullyConnect(3, 4, 1)
				.randomizeConnectionWeights(1.0D);
		Network net = builder.buildNetwork();

		net.setInputs(1, 1);
		net.compute();
		double out[][] = net.getComputedNetworkValues();

		new Thread(() ->
		{

			double[] outp = {1, 1, 1, 1};
			while (true)
			{

				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				net.setInputs(outp);
				net.compute();
				outp = net.getOutputs();
				outp[0] = Math.random();
			}

		}).start();


		NetworkRenderer renderer = new NetworkRenderer((BasicNetwork) net);
		RenderPanel rp = GUIUtil.createRenderGUI(renderer, 500, 500);
		GUIUtil.createRenderLoop(rp, renderer);

		renderer.setInputTitles("input value 1", "input value 2", "input value 3", "input value 4");
		renderer.setOutputTitles("output value 1", "out 2", "out 3", "out 4");

	}
}
