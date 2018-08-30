package me.brandon.game;

import me.brandon.ai.gui.Drawable;
import me.brandon.ai.gui.GraphicsWrapper;

public abstract class GamePlayer implements Drawable
{

	protected boolean dead;

	public abstract void tick();

	public abstract void draw(GraphicsWrapper g, int width, int height);
}
