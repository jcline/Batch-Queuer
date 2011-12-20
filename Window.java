

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window
{
	private JFrame window;
	private JPanel topPanel,
					       buttons,
								 list;

	public Window()
	{
		window = new JFrame();

		topPanel = new JPanel();
		buttons = new JPanel();
		list = new JPanel();

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		topPanel.add(list);
		topPanel.add(buttons);

		window.add(topPanel);
		window.pack();
		window.setVisible(true);
	}
}
