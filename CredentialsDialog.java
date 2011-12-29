import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CredentialsDialog
{
	private JDialog dialog;
  private JTextField userField;
	private JPasswordField passField;
	private JButton okButton, cancelButton;
	private JPanel inputPanel, buttonPanel;
	private JLabel userLabel, passLabel;
	public boolean cancel;

	public CredentialsDialog()
	{
		cancel = false;

		dialog = new JDialog();
		userField = new JTextField(25);
		passField = new JPasswordField(25);
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		buttonPanel = new JPanel();
		inputPanel = new JPanel();
		userLabel = new JLabel("Username:");
		passLabel = new JLabel("Password:");

		bListener listener = new bListener();

		dialog.setLayout(new BorderLayout());
		inputPanel.setLayout(new GridLayout(2,2));

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // ?

		passField.setEchoChar('*');

		okButton.addActionListener(listener);
		cancelButton.addActionListener(listener);

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		inputPanel.add(userLabel);
		inputPanel.add(userField);
		inputPanel.add(passLabel);
		inputPanel.add(passField);

		dialog.add(inputPanel, BorderLayout.CENTER);
		dialog.add(buttonPanel, BorderLayout.SOUTH);

		dialog.pack();
		dialog.setVisible(true);
	}

	private class bListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			if(e.getSource() == okButton)
			{
				BatchUploader.credentials = new Credentials(userField.getText(), passField.getPassword());
				System.out.println(BatchUploader.credentials.user + " " +  BatchUploader.credentials.pass);
				dialog.setVisible(false);
				dialog.dispose();
				return;
			}
			else if(e.getSource() == cancelButton)
			{
				cancel = true;
				dialog.setVisible(false);
				dialog.dispose();
				return;
			}
		}
	}
}
