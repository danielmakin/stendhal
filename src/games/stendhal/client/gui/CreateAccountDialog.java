/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.gui;

import games.stendhal.client.StendhalClient;
import games.stendhal.client.stendhal;
import games.stendhal.client.update.ClientGameConfiguration;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import marauroa.client.BannedAddressException;
import marauroa.client.LoginFailedException;
import marauroa.client.TimeoutException;
import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.AccountResult;
import marauroa.common.net.InvalidVersionException;

/**
 * Summary description for CreateAccountDialog
 *
 */
public class CreateAccountDialog extends JDialog {

	private static final long serialVersionUID = 4436228792112530975L;
	private static final Logger logger = Log4J.getLogger(CreateAccountDialog.class);


	// Variables declaration
	private JLabel usernameLabel;

	private JLabel serverLabel;

	private JLabel serverPortLabel;

	private JLabel passwordLabel;

	private JLabel passwordretypeLabel;

	private JLabel emailLabel;

	private JTextField usernameField;

	private JPasswordField passwordField;

	private JPasswordField passwordretypeField;

	private JTextField emailField;

	private JTextField serverField;

	private JTextField serverPortField;

	private JButton createAccountButton;

	private JPanel contentPane;

	// End of variables declaration
	private StendhalClient client;

	private Frame owner;

	public CreateAccountDialog(Frame owner, StendhalClient client) {
		super(owner, true);
		this.client = client;
		this.owner = owner;
		initializeComponent();

		this.setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Windows Form Designer. Otherwise, retrieving design
	 * might not work properly. Tip: If you must revise this method, please
	 * backup this GUI file for JFrameBuilder to retrieve your design properly
	 * in future, before revising this method.
	 */
	private void initializeComponent() {
		serverLabel = new JLabel("Server name");
		serverField = new JTextField(ClientGameConfiguration.get("DEFAULT_SERVER"));
		serverField.setEditable(true);
		serverPortLabel = new JLabel("Server port");
		serverPortField = new JTextField(ClientGameConfiguration.get("DEFAULT_PORT"));

		usernameLabel = new JLabel("Choose a username");
		usernameField = new JTextField();
		usernameField.setDocument(new LowerCaseLetterDocument());

		passwordLabel = new JLabel("Choose a password");
		passwordField = new JPasswordField();

		passwordretypeLabel = new JLabel("Retype password");
		passwordretypeField = new JPasswordField();

		emailLabel = new JLabel("E-mail address");
		emailField = new JTextField();

		createAccountButton = new JButton();
		contentPane = (JPanel) this.getContentPane();


		// createAccountButton
		//
		createAccountButton.setText("Create Account");
		createAccountButton.setMnemonic(KeyEvent.VK_C);
		createAccountButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				createAccountButton_actionPerformed(e, false);
			}
		});
		//
		// contentPane
		//
		contentPane.setLayout(new GridBagLayout());
		contentPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		GridBagConstraints c = new GridBagConstraints();

		//row 0
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(4, 4, 4, 4);
		c.gridx = 0;//column
		c.gridy = 0;//row
		c.fill = GridBagConstraints.NONE;
		contentPane.add(serverLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(serverField, c);

		//row 1
		c.insets = new Insets(4, 4, 4, 4);
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.NONE;
		contentPane.add(serverPortLabel, c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(4, 4, 4, 4);
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(serverPortField, c);

		//row 2
		c.insets = new Insets(4, 4, 4, 4);
		c.gridx = 0;
		c.gridy = 2;
		contentPane.add(usernameLabel, c);
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(usernameField, c);
		// TODO: put the caret into the username field, does not work?!
		usernameField.requestFocusInWindow();

		//row 3
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.NONE;
		contentPane.add(passwordLabel, c);
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(passwordField, c);

		//row 4
		c.gridx = 0;
		c.gridy = 4;
		c.fill = GridBagConstraints.NONE;
		contentPane.add(passwordretypeLabel, c);
		c.gridx = 1;
		c.gridy = 4;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(passwordretypeField, c);

		//row 5
		c.gridx = 0;
		c.gridy = 5;
		c.fill = GridBagConstraints.NONE;
		contentPane.add(emailLabel, c);
		c.gridx = 1;
		c.gridy = 5;
		c.fill = GridBagConstraints.BOTH;
		contentPane.add(emailField, c);

		//row 6
		c.gridx = 1;
		c.gridy = 6;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(15, 4, 4, 4);
		contentPane.add(createAccountButton, c);

		// CreateAccountDialog
		this.setTitle("Create New Account");
		this.setResizable(false);
		this.setSize(new Dimension(350, 275));
		this.setLocationRelativeTo(owner);

	}

	private void createAccountButton_actionPerformed(ActionEvent e, boolean saveLoginBoxStatus) {
		final String accountUsername = usernameField.getText();
		final String password = new String(passwordField.getPassword());
		final String passwordretype = new String(passwordretypeField.getPassword());

		if (!password.equals(passwordretype)) {
			JOptionPane.showMessageDialog(owner, "The passwords do not match. Please retype both.",
			        "Password Mismatch", JOptionPane.WARNING_MESSAGE);
			return;
		}

		final String email = emailField.getText();
		final String server = serverField.getText();
		int port = 32160;

		final int finalPort;//port couldnt be accessed from inner class
		final ProgressBar progressBar = new ProgressBar(owner);

		try {
			port = Integer.parseInt(serverPortField.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(owner, "That is not a valid port number. Please try again.", "Invalid Port",
			        JOptionPane.WARNING_MESSAGE);
			return;
		}
		finalPort = port;

		/*seprate thread for connection proccess added by TheGeneral*/
		//run the connection procces in separate thread
		Thread m_connectionThread = new Thread() {

			@Override
			public void run() {
				progressBar.start();//intialize progress bar
				setEnabled(false);//disable this screen when attempting to connect

				try {
					client.connect(server, finalPort);
					progressBar.step();//for each major connection milestone call step()
				} catch (Exception ex) {
					progressBar.cancel();//if something goes horribly just cancel the progressbar
					setEnabled(true);
					JOptionPane.showMessageDialog(owner,
						"Stendhal cannot connect to the Internet. Please check that your connection is set up and active, then try again.");
					
					logger.error(ex, ex);

					return;
				}

				try {
                	AccountResult result = client.createAccount(accountUsername, password, email);
                	if (result.failed()) {
                		/*
                		 * If the account can't be created, show an error message and don't continue.
                		 */
						progressBar.cancel();
    					setEnabled(true);
    					JOptionPane.showMessageDialog(owner, 
    							result.getResult().getText(),
    							"Create account failed",
						        JOptionPane.ERROR_MESSAGE);
					} else {

						/*
						 * Print username returned by server, as server can modify it at will
						 * to match account names rules. 
						 */

						progressBar.step();
						progressBar.finish();

						// TODO: Check mental conflict bewteen username and account name.
						// Be sure to fix all the variable names.
						client.setAccountUsername(accountUsername);

						/*
						 * Once the account is created, login into server.
						 */
						client.login(accountUsername, password);
						progressBar.step();
						progressBar.finish();

						setEnabled(false);
						owner.setVisible(false);

						stendhal.doLogin = true;
					}
                } catch (TimeoutException e) {
					progressBar.cancel();
					setEnabled(true);
					JOptionPane.showMessageDialog(
						owner,
						"Unable to connect to server to create your account. The server may be down or, if you are using a custom server, you may have entered its name and port number incorrectly.",
						"Error Creating Account", 
						JOptionPane.ERROR_MESSAGE);
                } catch (InvalidVersionException e) {
					progressBar.cancel();
					setEnabled(true);
					JOptionPane.showMessageDialog(owner, 
							"You are running an incompatible version of Stendhal. Please update", 
							"Invalid version",
							JOptionPane.ERROR_MESSAGE);
                } catch (BannedAddressException e) {
					progressBar.cancel();
					setEnabled(true);
					JOptionPane.showMessageDialog(owner, 
							"You IP is banned. If you think this is not right. Please send a Support request to http://sourceforge.net/tracker/?func=add&group_id=1111&atid=201111",
							"IP Banned",
							JOptionPane.ERROR_MESSAGE);
                } catch(LoginFailedException e) {
					progressBar.cancel();
					setEnabled(true);
					JOptionPane.showMessageDialog(owner, 
							e.getMessage(), 
							"Login failed", 
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		m_connectionThread.start();
	}

	private static class LowerCaseLetterDocument extends PlainDocument {
		private static final long serialVersionUID = -5123268875802709841L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			String lower = str.toLowerCase();
			boolean ok = true;
			for (int i = 0; i < lower.length(); i++) {
				char chr = str.charAt(i);
				if (chr < 'a' || chr > 'z') {
					ok = false;
					break;
				}
			}
			if (ok) {
				super.insertString(offs, lower, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}
