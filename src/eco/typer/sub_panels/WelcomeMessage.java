package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
//import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicScrollBarUI;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_frame.CustomFrameTitleBar;
import eco.typer.custom_objects.*;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the welcome screen, the very first screen the user sees
 */

@SuppressWarnings("serial")
public class WelcomeMessage extends CPanel
{
	
	@SuppressWarnings("unused")
	public WelcomeMessage()
	{
		super("");	//This is the only exeption
		
		JLabel icon = new JLabel(Utils.getImage("eco.png"));
		icon.setBackground(Color.WHITE);
		icon.setBounds(10, 10, 100, 100);
		add(icon);
		
		JLabel title = new JLabel(Constants.FRAME_TITLE);
		title.setBounds(130, 10, CustomFrame.WORKPANEL_WIDTH - 130, 50);
		title.setForeground(Color.WHITE);
		Utils.setFont(title, "Neon.ttf", 50);
		add(title);
		
		if(Constants.isUpdate == 1)
		{
			Constants.FRAME_TITLE = "Eco Typer(Update Available)";
			CustomFrameTitleBar.title.setText(Constants.FRAME_TITLE);
			CButton updateButton = new CButton("Update Available - Click to Download", Color.RED);
			updateButton.setBounds(130, 70, 300, 30);
			updateButton.addActionListener(e ->
			{
				Settings.frame.dispose();
				Utils.openWebpage(Constants.updateLink);
				System.exit(0);
			});
			add(updateButton);
			
			JTextArea updateDescription = new JTextArea(Constants.UPDATE_DESCRIPITION);
			updateDescription.setBackground(Constants.BACKGROUND_COLOR);
			updateDescription.setForeground(Color.WHITE);
			updateDescription.setEditable(false);
			updateDescription.setHighlighter(null);
			updateDescription.setWrapStyleWord(true);
			updateDescription.setLineWrap(true);
			JScrollPane sp = new JScrollPane(updateDescription);
			sp.getVerticalScrollBar().setUI(new CScrollBar());
			sp.setBorder(null);
			sp.setBounds(10, 110, CustomFrame.WORKPANEL_WIDTH - 20, CustomFrame.WORKPANEL_HEIGHT - 110);
			add(sp);
		}
		else if(Constants.isUpdate == -1)
		{
			JLabel noUpdateText = new JLabel("You're running the most up to date version");
			noUpdateText.setForeground(Color.WHITE);
			noUpdateText.setBounds(130, 70, CustomFrame.WORKPANEL_WIDTH - 130, 30);
			add(noUpdateText);
			
//			CButton donateLabel = new CButton("Click to Donate", Constants.BACKGROUND_COLOR.darker());
//			donateLabel.setBounds(CustomFrame.WORKPANEL_WIDTH - ((CustomFrame.WORKPANEL_WIDTH - 65) / 3) - 5, CustomFrame.WORKPANEL_HEIGHT - 15, (CustomFrame.WORKPANEL_WIDTH - 65) / 3, 15);
//			donateLabel.addActionListener(e -> {
//				Utils.openWebpage("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=FAY4BFVHFGH7A&source=url");
//			});
//			add(donateLabel);
			
			if(Constants.SHOW_ALERT || Constants.isAlert)
			{
				JLabel alertIcon = new JLabel(Utils.getImage("alert.png"));
				alertIcon.setBounds(10, 110, 32, 32);
				add(alertIcon);
				
				JTextArea updateDescription = new JTextArea(Constants.ALERT_MESSAGE);
				updateDescription.setBackground(Constants.BACKGROUND_COLOR.brighter());
				updateDescription.setForeground(Color.WHITE);
				updateDescription.setEditable(false);
				updateDescription.setHighlighter(null);
				updateDescription.setWrapStyleWord(true);
				updateDescription.setLineWrap(true);
				JScrollPane sp = new JScrollPane(updateDescription);
				sp.getVerticalScrollBar().setUI(new CScrollBar());
				sp.setBorder(null);
				sp.setBounds(55, 110, CustomFrame.WORKPANEL_WIDTH - 65, CustomFrame.WORKPANEL_HEIGHT - 150);
				add(sp);
				
//				JCheckBox doNotShowAlertAgainBox = new JCheckBox("Do not show this alert again");
//				doNotShowAlertAgainBox.setBounds(55, CustomFrame.WORKPANEL_HEIGHT - 30, (CustomFrame.WORKPANEL_WIDTH - 65) / 2, 30);
//				doNotShowAlertAgainBox.setForeground(Color.WHITE);
//				doNotShowAlertAgainBox.setBackground(Constants.BACKGROUND_COLOR);
//				add(doNotShowAlertAgainBox);
//				Constants.SHOW_ALERT = true;	//This is important because we need to default it to true if there is a new alert
//				doNotShowAlertAgainBox.addActionListener(e ->
//				{
//					if(doNotShowAlertAgainBox.isSelected())
//					{
//						Constants.SHOW_ALERT = false;
//					}
//					else
//					{
//						Constants.SHOW_ALERT = true;
//					}
//				});
			}
		}
		else
		{
			JLabel noUpdateText;
			if(Constants.isUpdate == 0 && Settings.speedUpBoot == true)
			{
				noUpdateText = new JLabel("YOU ARE IN DEVELOPER MODE, SPEEDUP IS ENABLED - TURN OFF FOR RELEASE");
				noUpdateText.setForeground(Color.CYAN);
			}
			else if(Settings.liteMode)
			{
				noUpdateText = new JLabel("Welcome to Eco Typer!");
				noUpdateText.setForeground(Utils.getRandColor());
			}
			else
			{
				noUpdateText = new JLabel("We could not determine if there is an update");
				noUpdateText.setForeground(Color.ORANGE);
			}
			noUpdateText.setBounds(130, 70, CustomFrame.WORKPANEL_WIDTH - 130, 30);
			add(noUpdateText);
		}
	}

}

class CScrollBar extends BasicScrollBarUI
{
	private final Dimension d = new Dimension();

	@SuppressWarnings("serial")
	@Override
	protected JButton createDecreaseButton(int orientation)
	{
		return new JButton()
		{
			@Override
			public Dimension getPreferredSize()
			{
				return d;
			}
		};
	}

	@SuppressWarnings("serial")
	@Override
	protected JButton createIncreaseButton(int orientation)
	{
		return new JButton()
		{
			@Override
			public Dimension getPreferredSize()
			{
				return d;
			}
		};
	}

	@Override
	protected void paintTrack(Graphics g,
							  JComponent c,
							  Rectangle r)
	{
		g.setColor(Constants.BACKGROUND_COLOR.brighter());
		g.fillRect(r.x, r.y, r.width, r.height);
	}

	@Override
	protected void paintThumb(Graphics g,
							  JComponent c,
							  Rectangle r)
	{
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color color = null;
		JScrollBar sb = (JScrollBar) c;
		if (!sb.isEnabled() || r.width > r.height)
		{
			return;
		}
		else if (isDragging)
		{
			color = Color.DARK_GRAY;
		}
		else if (isThumbRollover())
		{
			color = Color.LIGHT_GRAY;
		}
		else
		{
			color = Color.GRAY;
		}
		g2.setPaint(color);
		g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
		g2.setPaint(Constants.PRIMARY_COLOR);
		g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
		g2.dispose();
	}

	@Override
	protected void setThumbBounds(int x,
								  int y,
								  int width,
								  int height)
	{
		super.setThumbBounds(x, y, width, height);
		scrollbar.repaint();
	}
	
}