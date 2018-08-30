package me.brandon.game;

public class GameConfig
{

	/**
	 * should the status of the game be rendered each tick
	 */
	public static boolean renderStatus = true;

	/**
	 * should the game itself be rendered each tick
	 */
	public static boolean renderGame = true;
	/**
	 * should the game attempt to render all players (true)
	 * or just the focused player (false)?
	 */
	public static boolean renderAllPlayers = false;

	/**
	 * Time between each tick in milliseconds
	 */
	public static int tickDelay = 10; // milliseconds

	public static void renderAll()
	{
		renderStatus = true;
	}
}
