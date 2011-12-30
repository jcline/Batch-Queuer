import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.io.File;
import java.io.FilenameFilter;

import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.asplode.tumblr.PhotoPost;

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
	private ArrayList<File> files;

	public Window()
	{
		files = new ArrayList<File>();

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

				String[] l = dir.list(new FilenameFilter()
					{
						@Override 
						public boolean accept(File dir, String name)
						{
							return (new File(dir, name)).isFile();
						}
					}
				);

				for(String i : l)
					files.add(new File(i));

				Collections.sort(files);
				list.setListData(files.toArray());

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
				if(BatchUploader.credentials == null)
				{
					Credentials c = new Credentials(JOptionPane.showInputDialog("Username"), JOptionPane.showInputDialog("Password"));
					if(c != null)
						BatchUploader.credentials = c;
					else
						return;
				}

				GregorianCalendar time = new GregorianCalendar();
				for(File f : files)
				{
					System.out.println("Uploading " + f.toString());
					PhotoPost p = new PhotoPost();
					try {
						p.setCredentials(BatchUploader.credentials.user,BatchUploader.credentials.pass);
					}
					catch(Exception ex)
					{
						System.out.println("Failed");
						System.out.println(ex);
					}
					try {
						p.setSourceFile(f);
					}
					catch(Exception ex)
					{
						System.out.println("Failed2");
						System.out.println(ex);
					}

					time.add(GregorianCalendar.HOUR, 1);
					try {
						p.setPublishOn(time.getTime().toString());
					}
					catch(Exception ex)
					{
						System.out.println("Failed3");
						System.out.println(ex);
					}
					try {
						p.postToTumblr();
					}
					catch(Exception ex)
					{
						System.out.println("Failed4");
						System.out.println(ex);
					}
				}

				return;
			}
		}
	}
}
