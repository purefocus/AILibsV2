package me.brandon.game;

import me.brandon.ai.gui.Drawable;
import me.brandon.ai.gui.GraphicsWrapper;
import me.brandon.ai.gui.Renderer;
import me.brandon.ai.gui.graphics.GraphicalComponent;
import me.brandon.ai.gui.graphics.GraphicalComponentManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameBase implements Runnable, Drawable
{

	protected GraphicalComponent statusComp;
	protected GraphicalComponent gameComp;

	protected List<GamePlayer> players;

	protected GamePlayer focusedPlayer;

	protected Renderer renderer;

	protected boolean renderSinglePlayer;
	protected boolean running;
	protected boolean render;

	protected long lastRunTime;
	protected int ticks;

	protected boolean done;

	protected GraphicalComponentManager gcm;

	public GameBase(Renderer renderer)
	{
		this.renderer = renderer;
		players = new ArrayList<>();

		render = true;

		gcm = new GraphicalComponentManager();
		gcm.init(renderer);

		gameComp = gcm.addComponent("Game", 0, 0, renderer.getWidth(), renderer.getHeight());
		statusComp = gcm.addComponent("Status", 10, 10, 300, 120);

		init();
		reset();
	}

	private void init()
	{
		statusComp.setDrawable((g, w, h) ->
		{
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, w, h);
			g.setColor(Color.white);
			g.drawString(10, 15, "Ticks: %d", ticks);
		});

		gameComp.setDrawable((g, w, h) ->
		{

			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, w, h);
			if (GameConfig.renderAllPlayers)
			{
				for (GamePlayer player : players)
				{
					player.draw(g, w, h);
				}
			}
			else
			{
				if (focusedPlayer != null)
				{
					focusedPlayer.draw(g, w, h);
				}
			}
		});
	}

	/**
	 * @return the name of the game
	 */
	public abstract String name();

	public void reset()
	{
		ticks = 0;
		done = false;
	}

	/**
	 * starts the game
	 */
	public void start()
	{
		if (!running)
		{
			running = true;
			new Thread(this).start();
		}
	}

	/**
	 * stops/pauses the game
	 */
	public void stop()
	{
		running = false;
	}

	/**
	 * method runs every tick, ticks frequencies is dependant on tickDelay variable
	 */
	public abstract boolean tick();

	public void tickPlayers()
	{
		for (GamePlayer player : players)
		{
			player.tick();
		}
	}

	/**
	 * method called by a thread
	 */
	public void run()
	{
		while (running)
		{
			ticks++;
			if (tick())
			{
				done = true;
				running = false;
			}

			if (render)
			{
				render();
			}

			delay();
		}
	}

	protected void drawStatus(GraphicsWrapper g)
	{


	}

	public void draw(GraphicsWrapper g, int width, int height)
	{
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, width, height);

		if (GameConfig.renderGame)
			gameComp.draw(g);

		if (GameConfig.renderStatus)
			statusComp.draw(g);

	}

	public void render()
	{
		if (renderer != null)
		{
			renderer.render(this);
		}
	}

	public boolean isDone()
	{
		return done;
	}

	private void delay()
	{
		try
		{
			long delayTime = GameConfig.tickDelay - (System.currentTimeMillis() - lastRunTime);
			if (delayTime > 0)
			{
				Thread.sleep(delayTime);
			}
			lastRunTime = System.currentTimeMillis();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
