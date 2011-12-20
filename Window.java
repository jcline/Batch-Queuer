

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
	private JButton add;

	public Window()
	{
		bListener listener = new bListener();

		window = new JFrame();

		topPanel = new JPanel();
		buttons = new JPanel();
		list = new JPanel();

		add = new JButton("Add");
		add.addActionListener(listener);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttons.add(add);

		topPanel.add(list);
		topPanel.add(buttons);

		window.add(topPanel);
		window.pack();
		window.setVisible(true);
	}

	private class bListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			if(e.getSource() == add)
				return;
		}
	}
}
