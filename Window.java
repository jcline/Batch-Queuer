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
		progressPanel,
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
	private JLabel progress;

	public Window()
	{
		files = new ArrayList<File>();

		bListener listener = new bListener();

		window = new JFrame("Batch Uploader");

		topPanel = new JPanel();
		buttonPanel = new JPanel();
		progressPanel = new JPanel();
		listPanel = new JPanel();

		progress = new JLabel("Add files and then press the Upload button.");

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
		// Enable this when it actually works
		//buttonPanel.add(del);
		buttonPanel.add(clr);
		buttonPanel.add(upl);

		fileChooser.setApproveButtonText("Load files");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		scroll.setPreferredSize(new Dimension(480,730));

		listPanel.add(scroll);

		progressPanel.add(progress);

		topPanel.setLayout(new BorderLayout());
		topPanel.add(listPanel, BorderLayout.CENTER); 
		topPanel.add(buttonPanel, BorderLayout.NORTH);
		topPanel.add(progressPanel, BorderLayout.SOUTH);

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setPreferredSize(new Dimension(480, 800));

		window.add(topPanel);
		window.pack();
		window.setVisible(true);
	}

	private void setButtonState(boolean state)
	{
		add.setEnabled(state);
		del.setEnabled(state);
		clr.setEnabled(state);
		upl.setEnabled(state);
	}

	private void run()
	{
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
					files.add(new File(dir + "/" + i));

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
				files.clear();
			}
			else if(e.getSource() == upl)
			{
				setButtonState(false);

				if(BatchUploader.credentials == null)
				{
					String user = JOptionPane.showInputDialog("Email address");
					String pass;
					if(user == null || user.length() == 0)
				 	{
						setButtonState(true);
						return;
					}
					pass = JOptionPane.showInputDialog("Password");
					if(pass == null || pass.length() == 0)
				 	{
						setButtonState(true);
						return;
					}

					Credentials c = new Credentials(user, pass);
					if(c != null)
						BatchUploader.credentials = c;
					else 
					{
						setButtonState(true);
						return;
					}
				}

				int interval = 0;
				while(interval == 0)
				{
					int x;
					try
					{
						x = Integer.parseInt( JOptionPane.showInputDialog("How many minutes between posts?") );
					}
					catch(NumberFormatException ex)
					{
						continue;
					}

					interval = x;
				}

				GregorianCalendar time = new GregorianCalendar();
				int count = 0;
				for(File f : files)
				{
					++count;
					progress.setText("Uploading " + count + "/" + files.size());
					JComponent.paintImmediately(progressPanel.getVisibleRect()); // Ugly hack because we are in the event dispatch thread
					PhotoPost p = new PhotoPost();
					try {
						p.setCredentials(BatchUploader.credentials.user,BatchUploader.credentials.pass);
					}
					catch(Exception ex)
					{
						System.out.println(ex);
						setButtonState(true);
						return;
					}
					try {
						p.setSourceFile(f);
					}
					catch(Exception ex)
					{
						System.out.println(ex);
						setButtonState(true);
						return;
					}

					time.add(GregorianCalendar.MINUTE, interval);
					try {
						p.setPublishOn(time.getTime().toString());
					}
					catch(Exception ex)
					{
						System.out.println(ex);
						setButtonState(true);
						return;
					}
					try {
						int ret = p.postToTumblr();
						if(ret != 201)
						{
							if(ret == 403)
							{
								JOptionPane.showMessageDialog(null, "Failed to upload file(s), invalid email or password.", "information", JOptionPane.INFORMATION_MESSAGE);
								BatchUploader.credentials = null;
							}
							else if(ret == 400)
							{
								JOptionPane.showMessageDialog(null, "Failed to upload file(s), unknown reason.", "information", JOptionPane.INFORMATION_MESSAGE);
							}
							progress.setText("");
							setButtonState(true);
							return;
						}
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(null, "Failed to upload file.\n" + ex.toString(), "information", JOptionPane.INFORMATION_MESSAGE);
						progress.setText("");
						System.out.println(ex);
						setButtonState(true);
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "Files uploaded.", "information", JOptionPane.INFORMATION_MESSAGE);

				setButtonState(true);
				progress.setText("");

				return;
			}
		}
	}
}
