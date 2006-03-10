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

import java.awt.*;
import java.awt.event.*;
import java.net.URL;


import java.io.*;
import java.net.*;

import javax.swing.*;


/**
 * Summary description for LoginGUI
 *
 */
public class StendhalFirstScreen extends JFrame {
    private static final long serialVersionUID = -7825572598938892220L;

    // Variables declaration
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton exitButton;
    private JPanel contentPane;
    // End of variables declaration
    private StendhalClient client;
    private Image background;
    
    
    public StendhalFirstScreen(StendhalClient client) {
        super();
        this.client=client;
        
        URL url = this.getClass().getClassLoader().getResource("data/gui/StendhalSplash.jpg");
        ImageIcon imageIcon = new ImageIcon(url);
        background=imageIcon.getImage();
        
        initializeComponent();
        
        this.setVisible(true);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always regenerated
     * by the Windows Form Designer. Otherwise, retrieving design might not work properly.
     * Tip: If you must revise this method, please backup this GUI file for JFrameBuilder
     * to retrieve your design properly in future, before revising this method.
     */
    private void initializeComponent() {
        loginButton = new JButton();
        createAccountButton = new JButton();
        exitButton = new JButton();
        this.setContentPane(new JPanel() {
            {
                setOpaque(false);
                this.setPreferredSize(new Dimension(640,480));
            }
            public void paint(Graphics g) {
                g.drawImage(background,0,0,this);
                super.paint(g);
            }
            private static final long serialVersionUID = -4272347652159225492L;
            
        });
        contentPane = (JPanel)this.getContentPane();
        
        //
        // loginButton
        //
        loginButton.setText("Login to Stendhal");
        loginButton.setToolTipText("Press this button to Login to a Stendhal server");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try
                  {                  
                  URL url = new URL(stendhal.VERSION_LOCATION);
                  HttpURLConnection.setFollowRedirects(false);
                  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                      
                  BufferedReader br=new BufferedReader(new InputStreamReader( connection.getInputStream()));
                  
                  String version=br.readLine();
                  
                  if(!version.equals(stendhal.VERSION))
                    {            
                    //custom title, warning icon
                    JOptionPane.showMessageDialog(null,
                        "Your client is out of date. Latest version is "+version+".\nDownload from http://arianne.sourceforge.net",
                        "Client out of date",
                        JOptionPane.WARNING_MESSAGE);
                    }                  
                  }
                catch(Exception ex)
                  {
                  }
          
                new LoginDialog(StendhalFirstScreen.this, client);
            }
            
        });
        //
        // createAccountButton
        //
        createAccountButton.setText("Create an account");
        createAccountButton.setToolTipText("Press this button to create an account on a stendhal server.");
        createAccountButton.setEnabled(true);
        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try
                  {                  
                  URL url = new URL(stendhal.VERSION_LOCATION);
                  HttpURLConnection.setFollowRedirects(false);
                  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                      
                  BufferedReader br=new BufferedReader(new InputStreamReader( connection.getInputStream()));
                  
                  String version=br.readLine();
                  
                  if(!version.equals(stendhal.VERSION))
                    {            
                    //custom title, warning icon
                    JOptionPane.showMessageDialog(null,
                        "Your client is out of date. Latest version is "+version+".\nDownload from http://arianne.sourceforge.net",
                        "Client out of date",
                        JOptionPane.WARNING_MESSAGE);
                    }                  
                  }
                catch(Exception ex)
                  {
                  }
          
                new CreateAccountDialog(StendhalFirstScreen.this, client);
                //JOptionPane.showMessageDialog(StendhalFirstScreen.this, "To create an account for Stendhal please visit \n http://stendhal.ombres.ambre.net" ,"Create account",JOptionPane.INFORMATION_MESSAGE);
            }
            
        });
        //
        // exitButton
        //
        exitButton.setText("Exit");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        //
        // contentPane
        //
        contentPane.setLayout(null);
        addComponent(contentPane, loginButton, 220,340,200,32);
        addComponent(contentPane, createAccountButton, 220,380,200,32);
        addComponent(contentPane, exitButton, 220,420,200,32);
        
        
        //
        // LoginGUI
        //
        setTitle("Stendhal "+stendhal.VERSION+" - a multiplayer online game using Arianne");
        this.setLocation(new Point(100, 100));
        
        URL url = this.getClass().getClassLoader().getResource("data/gui/StendhalIcon.png");
        this.setIconImage(new ImageIcon(url).getImage());
        pack();
    }


    
    /** Add Component Without a Layout Manager (Absolute Positioning) */
    private void addComponent(Container container,Component c,int x,int y,int width,int height) {
        c.setBounds(x,y,width,height);
        container.add(c);
    }
    
}
