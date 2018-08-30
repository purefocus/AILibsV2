package me.brandon.game.test;

import me.brandon.ai.gui.GUIFrame;
import me.brandon.ai.gui.RenderPanel;
import me.brandon.ai.gui.Renderer;
import me.brandon.game.GameBase;

public class TestGame extends GameBase
{

	public TestGame(Renderer renderer)
	{
		super(renderer);
	}

	public static void main(String[] args)
	{
		GUIFrame gui = new GUIFrame();
		gui.setCenterComponent(new RenderPanel(500, 500));
		RenderPanel renderer = gui.getCenterComponent();
		TestGame game = new TestGame(renderer);
		renderer.makeFrame();
		renderer.displayFrame();

		game.start();
	}

	public String name()
	{
		return "test";
	}

	public boolean tick()
	{
		return false;
	}
}
