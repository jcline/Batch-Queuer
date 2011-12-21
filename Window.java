import java.io.File;
import java.io.FilenameFilter;

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
		clr,
		del,
		upl;
	private JList list;
	private JFileChooser fileChooser;
	private JScrollPane scroll;
	private File dir;

	public Window()
	{
		bListener listener = new bListener();

		window = new JFrame("Batch Uploader");

		topPanel = new JPanel();
		buttonPanel = new JPanel();
		listPanel = new JPanel();

		add = new JButton("Add");
		clr = new JButton("Clear");
		del = new JButton("Delete");
		upl = new JButton("Upload");

		list = new JList();

		scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		fileChooser = new JFileChooser();

		add.addActionListener(listener);
		clr.addActionListener(listener);
		del.addActionListener(listener);
		upl.addActionListener(listener);

		buttonPanel.add(add);
		buttonPanel.add(del);
		buttonPanel.add(clr);
		buttonPanel.add(upl);

		fileChooser.setApproveButtonText("Load files");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		scroll.setPreferredSize(new Dimension(480,800));

		listPanel.add(scroll);

		topPanel.setLayout(new BorderLayout());
		topPanel.add(listPanel, BorderLayout.CENTER); 
		topPanel.add(buttonPanel, BorderLayout.NORTH);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(new Dimension(480, 800));

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

				list.setListData(dir.list(new FilenameFilter()
					{
						@Override 
						public boolean accept(File dir, String name)
						{
							return (new File(dir, name)).isFile();
						}
					})
				);

			}
			else if(e.getSource() == del)
			{
				return;
			}
			else if(e.getSource() == clr)
			{
				list.setListData(new Object[1]);
			}
			else if(e.getSource() == upl)
			{
				return;
			}
		}
	}
}
