package me.brandon.ai.gui;

import javax.swing.*;
import java.awt.*;

public class GUIFrame extends JFrame
{

	private JPanel contentPane;
	private Component centerComponent;


	public GUIFrame()
	{

	}

	public GUIFrame(String title)
	{
		setTitle(title);
	}

	public GUIFrame(Component component)
	{
		setCenterComponent(component);
		initComponents();
	}

	public void showFrame()
	{
		setVisible(true);
	}

	public void setCenterComponent(Component comp)
	{
		this.centerComponent = comp;
		initComponents();
	}

	public void initComponents()
	{
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		contentPane.add(centerComponent, BorderLayout.CENTER);


		pack();
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getCenterComponent()
	{
		return (T) centerComponent;
	}


}
