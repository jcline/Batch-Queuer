import java.io.File;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window
{
	private JFrame window;
	private JPanel topPanel,
		buttonPanel,
		listPanel;
	private JButton add,
		del,
		clr;
	private JList list;
	private JFileChooser fileChooser;
	private File dir;

	public Window()
	{
		bListener listener = new bListener();

		window = new JFrame("Batch Uploader");

		topPanel = new JPanel();
		buttonPanel = new JPanel();
		listPanel = new JPanel();

		add = new JButton("Add");
		del = new JButton("Delete");
		clr = new JButton("Clear");

		list = new JList();

		fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Load files");

		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		add.addActionListener(listener);
		del.addActionListener(listener);
		clr.addActionListener(listener);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonPanel.add(add);
		buttonPanel.add(del);
		buttonPanel.add(clr);

		listPanel.add(list);

		topPanel.setLayout(new BorderLayout());
		topPanel.add(listPanel, BorderLayout.SOUTH); 
		topPanel.add(buttonPanel, BorderLayout.NORTH);

		window.add(topPanel);
		window.pack();
		window.setVisible(true);
	}

	private class bListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			if(e.getSource() == add)
			{
				fileChooser.showDialog(window, null);
				dir = fileChooser.getSelectedFile();

				list.setListData(dir.listFiles());
			}
			else if(e.getSource() == del)
			{
				return;
			}
			else if(e.getSource() == clr)
			{
				list.setListData(new Object[1]);
			}
		}
	}
}
