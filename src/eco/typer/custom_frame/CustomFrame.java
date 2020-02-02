package eco.typer.custom_frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.main_panels.LocalTextDisplay;
import eco.typer.main_panels.RemoteTextDisplay;
import eco.typer.main_panels.SequenceTextDisplay;
import eco.typer.main_panels.SettingsDisplay;
import eco.typer.sub_panels.*;
import eco.typer.tools.*;

/**
 * @author dakota
 * This is called from settings and is the "start" of the typer
 */

@SuppressWarnings("serial")
public class CustomFrame extends JFrame
{
	
	private static int posX, posY;
	public static int WORKPANEL_WIDTH = Constants.FRAME_WIDTH - 50;
	public static int WORKPANEL_HEIGHT = Constants.FRAME_HEIGHT - 45;
	public static SettingsDisplay settingsPanel;
	public static LocalTextDisplay localDisplay;
	public static RemoteTextDisplay remoteDisplay;
	public static SequenceTextDisplay sequenceDisplay;
	public static int textMode;	//0=local, 1=remote, 2=sequence
	public static JPanel lastVisitedPanel;
	
	
	public static LeftSelectionMenu leftSelectionMenu;
	public static JPanel workPanel;
	public static JLabel statusLabel;
	
	public CustomFrame()
	{
		settingsPanel = new SettingsDisplay();
		localDisplay = new LocalTextDisplay();
		remoteDisplay = new RemoteTextDisplay();
		sequenceDisplay = new SequenceTextDisplay();
		textMode = 0;
		lastVisitedPanel = null;
		getContentPane().setBackground(Constants.BACKGROUND_COLOR);
		setUndecorated(true);
		setTitle(Constants.FRAME_TITLE);
		setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		Constants.splashWindow.dispose();
		setVisible(true);
		setLocation(Constants.OPEN_POINT);
		TitleBar_StatusField();
		MouseListener();
		setIconImage(Utils.getImage("iconB2.png").getImage());
		setLayout(null);
		setAlwaysOnTop(true);
		getRootPane().setBorder(new LineBorder(Color.BLACK));
		LeftSelectionPanel();
		WelcomeMessage();
		
		revalidate();
		repaint();
	}

	private void WelcomeMessage()
	{
		WelcomeMessage wm = new WelcomeMessage();
		workPanel.add(wm, BorderLayout.CENTER);
	}

	private void TitleBar_StatusField()
	{
		CustomFrameTitleBar titleBar = new CustomFrameTitleBar();
		add(titleBar);
		
		//Actual Space I'll have to work
		workPanel = new JPanel();
		workPanel.setBounds(50, 25, WORKPANEL_WIDTH, WORKPANEL_HEIGHT);
		workPanel.setLayout(new BorderLayout());
		workPanel.setBackground(Constants.BACKGROUND_COLOR);
		
		add(workPanel);
		
		//Status Label on bottom
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Constants.BACKGROUND_COLOR);
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBounds(50, Constants.FRAME_HEIGHT - 20, Constants.FRAME_WIDTH - 55, 20);
		
		JLabel osName = new JLabel("Eco Typer for " + Constants.OPERATING_SYSTEM);
		osName.setForeground(Color.LIGHT_GRAY.darker());
		statusPanel.add(osName, BorderLayout.WEST);
		
		statusLabel = new JLabel("");
		statusLabel.setHorizontalAlignment(JLabel.RIGHT);
		statusLabel.setForeground(Color.WHITE);
		statusPanel.add(statusLabel, BorderLayout.CENTER);
		
		add(statusPanel);
	}
	
	private void LeftSelectionPanel()
	{
		leftSelectionMenu = new LeftSelectionMenu();
		add(leftSelectionMenu);
	}
	
	public static void updateStatus(String text)
	{
		statusLabel.setText(text);
	}
	
	public static void updateDisplay(JPanel panel)
	{
		CustomFrame.workPanel.removeAll();
		CustomFrame.workPanel.add(panel, BorderLayout.CENTER);
		CustomFrame.workPanel.revalidate();
		CustomFrame.workPanel.repaint();
	}
	
	private void MouseListener()
	{
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				posX = e.getX();
				posY = e.getY();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if(posX >= 0 && posX <= Constants.FRAME_WIDTH && posY >= 0 && posY <= 25)
				{
					Rectangle rectangle = Settings.frame.getBounds();
					Settings.frame.setBounds(e.getXOnScreen() - posX, e.getYOnScreen() - posY, rectangle.width, rectangle.height);
				}
			}
		});
	}

}
