package eco.typer.custom_frame;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import eco.typer.Settings.*;
import eco.typer.custom_objects.CButton;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This class creates the custom title bar on the frame
 */

@SuppressWarnings("serial")
public class CustomFrameTitleBar extends JPanel
{
	
	public static CButton exit, mini;
	public static JLabel title;
	public static CButton pauseButton, stopButton;
	
	public CustomFrameTitleBar()
	{
		setLayout(null);
		setBounds(0, 0, Constants.FRAME_WIDTH, 25);
		setBackground(new Color(60, 60, 60));
		initializePauseAndMini();
		
		exit = new CButton("X");
		exit.setBackground(Constants.PRIMARY_COLOR);
		exit.setBounds(Constants.FRAME_WIDTH - 50, 0, 50, 25);
		add(exit);
		exit.addActionListener(e ->
		{
			Settings.frame.setVisible(false);
			Constants.OPEN_POINT.setLocation(Settings.frame.getX(), Settings.frame.getY());
			Utils.serializeFrameData();
			System.exit(0);
		});
		
		mini = new CButton("_");
		mini.setBackground(Constants.BACKGROUND_COLOR);
		mini.setBounds(Constants.FRAME_WIDTH - 100, 0, 50, 25);
		add(mini);
		mini.addActionListener(e ->
		{
			Settings.frame.setState(JFrame.ICONIFIED);
		});
		
		title = new JLabel(Constants.FRAME_TITLE);
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
//		Utils.setFont(title, "OpenSans-Regular.ttf", 18);
		Utils.setFont(title, "Neon.ttf", 18);
		title.setBounds(0, 0, Constants.FRAME_WIDTH, 25);
		add(title);
		
		JLabel icon = new JLabel(Utils.getImage("iconS.png"));
		icon.setBounds(3, -2, 24, 28);
		add(icon);
	}

	private void initializePauseAndMini()
	{
		pauseButton = new CButton("Pause");
		pauseButton.setBounds(Constants.FRAME_WIDTH - 210, 0, 100, 25);
		pauseButton.setVisible(false);
		add(pauseButton);
		pauseButton.addActionListener(e ->
		{
			if(pauseButton.getText().equals("Pause"))
			{
				pauseButton.setText("Resume");
				if(CustomFrame.textMode == 0)
				{
					Constants.localTyper.pause();
				}
				else if(CustomFrame.textMode == 1)
				{
					Constants.remoteTyper.pause();
				}
				else
				{
					Constants.sequenceTyper.pause();
				}
			}
			else
			{
				pauseButton.setText("Pause");
				if(CustomFrame.textMode == 0)
				{
					Constants.localTyper.resume();
				}
				else if(CustomFrame.textMode == 1)
				{
					Constants.remoteTyper.resume();
				}
				else
				{
					Constants.sequenceTyper.resume();
				}
			}
		});
		
		stopButton = new CButton("Stop");
		stopButton.setBounds(Constants.FRAME_WIDTH - 100, 0, 100, 25);
		stopButton.setVisible(false);
		add(stopButton);
		stopButton.addActionListener(e ->
		{
			if(CustomFrame.textMode == 0)
			{
				Constants.localTyper.stop();
				Constants.localTyper.unsetCompact();
				Constants.localTyper.audio = null;
				Constants.localTyper.backgroundTimer = null;
				Constants.localTyper.clientChecker = null;
			}
			else if(CustomFrame.textMode == 1)
			{
				Constants.remoteTyper.stop();
				Constants.remoteTyper.unsetCompact();
				Constants.remoteTyper.audio = null;
				Constants.remoteTyper.backgroundTimer = null;
				Constants.remoteTyper.clientChecker = null;
			}
			else
			{
				Constants.sequenceTyper.stop();
				Constants.sequenceTyper.unsetCompact();
				Constants.sequenceTyper.audio = null;
				Constants.sequenceTyper.backgroundTimer = null;
				Constants.sequenceTyper.clientChecker = null;
			}
			CustomFrameTitleBar.pauseButton.setText("Pause");
		});
	}

}
