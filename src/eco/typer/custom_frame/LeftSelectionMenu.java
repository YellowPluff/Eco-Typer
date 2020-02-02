package eco.typer.custom_frame;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.NoSuchElementException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.listeners.SUL;
import eco.typer.sub_panels.ExtraSettingsPanel;
import eco.typer.sub_panels.NotepadPanel;
import eco.typer.sub_panels.ProfitCounterDisplay;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This class makes the side panel with the icons
 */

@SuppressWarnings("serial")
public class LeftSelectionMenu extends JPanel
{
	
	private int width = 50;
	private int height = Constants.FRAME_HEIGHT - 25;
	
	private ImageContainer spamIcon, settingsIcon, notepadIcon, extraSettings, profitIcon;
	
	public LeftSelectionMenu()
	{
		setBackground(new Color(60, 60, 60));
		setBounds(0, 25, this.width, this.height);
		setLayout(null);
		
		spamIcon = new ImageContainer(Utils.getImage("spamIcon.png"), "Text Display");
		spamIcon.setBounds(0, 20, this.width, 43);
		add(spamIcon);
		
		settingsIcon = new ImageContainer(Utils.getImage("settingsIcon.png"), "Settings");
		settingsIcon.setBounds(0, 70, this.width, 43);
		add(settingsIcon);
		
		notepadIcon = new ImageContainer(Utils.getImage("notepadIcon.png"), "Simple Notepad");
		notepadIcon.setBounds(0, this.height - 150, this.width, 43);
			
		profitIcon = new ImageContainer(Utils.getImage("profitIcon.png"), "Profit Counter");
		profitIcon.setBounds(0, this.height - 100, this.width, 43);
			
		extraSettings = new ImageContainer(Utils.getImage("extraSettingsIcon.png"), "Theme Chooser, ScreenShot Viewer, Eco Typer Uninstaller");
		extraSettings.setBounds(0, this.height - 50, this.width, 43);
		
		if(!Settings.liteMode)
		{
			add(notepadIcon);
			add(profitIcon);
			add(extraSettings);
		}
	}
	
	public void hideAllIcons()
	{
		this.spamIcon.setVisible(false);
		this.settingsIcon.setVisible(false);
		this.notepadIcon.setVisible(false);
		this.profitIcon.setVisible(false);
		this.extraSettings.setVisible(false);
	}
	
	public void unhideAllIcons()
	{
		this.spamIcon.setVisible(true);
		this.settingsIcon.setVisible(true);
		this.notepadIcon.setVisible(true);
		this.profitIcon.setVisible(true);
		this.extraSettings.setVisible(true);
	}

}


@SuppressWarnings("serial")
class ImageContainer extends JPanel implements MouseListener
{
	
	private String objectStatusText;
	private JLabel icon;

	public ImageContainer(ImageIcon image,
						  String statusText)
	{
		setBackground(new Color(60, 60, 60));
		addMouseListener(this);
		this.objectStatusText = statusText;
		
		icon = new JLabel(image);
		add(icon);
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		
		//This acts like SUL
		this.setBackground(this.getBackground().brighter());
		CustomFrame.updateStatus(objectStatusText);
		
		if(Constants.mouseHoverMode == true)
		{
			executePanelOption();
		}
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		this.setBackground(new Color(60, 60, 60));
		CustomFrame.updateStatus(null);
		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		executePanelOption();
	}
	
	private void executePanelOption()
	{
		if(this.objectStatusText.equals("Text Display"))
		{
			if(CustomFrame.textMode == 0)
			{
				CustomFrame.updateDisplay(CustomFrame.localDisplay);
				CustomFrame.lastVisitedPanel = CustomFrame.localDisplay;
			}
			else if(CustomFrame.textMode == 1)
			{
				CustomFrame.updateDisplay(CustomFrame.remoteDisplay);
				CustomFrame.lastVisitedPanel = CustomFrame.remoteDisplay;
			}
			else if(CustomFrame.textMode == 2)
			{
				CustomFrame.updateDisplay(CustomFrame.sequenceDisplay);
				CustomFrame.lastVisitedPanel = CustomFrame.sequenceDisplay;
			}
			else
			{
				Utils.writeErrorReport(new NoSuchElementException(), 300);
			}
		}
		else if(this.objectStatusText.equals("Settings"))
		{
			CustomFrame.updateDisplay(CustomFrame.settingsPanel);
			CustomFrame.lastVisitedPanel = CustomFrame.settingsPanel;
		}
		else if(this.objectStatusText.equals("Remote Mode"))
		{
			//TODO
			System.out.println("Remote Mode - NOT coded in yet");
		}
		else if(this.objectStatusText.equals("Simple Notepad"))
		{
			CustomFrame.updateDisplay(new NotepadPanel());
		}
		else if(this.objectStatusText.equals("Theme Chooser, ScreenShot Viewer, Eco Typer Uninstaller"))
		{
			CustomFrame.updateDisplay(new ExtraSettingsPanel());
		}
		else if(this.objectStatusText.equals("Profit Counter"))
		{
			CustomFrame.updateDisplay(new ProfitCounterDisplay());
		}
		checkEditingStatus();
	}

	private void checkEditingStatus() {
		if(CustomFrame.textMode == 0)
		{
			CustomFrame.localDisplay.addTextButton.setEnabled(true);
			CustomFrame.localDisplay.spamLinesDisplay.setText(CustomFrame.localDisplay.spamLinesDisplay.getText().trim());
			CustomFrame.localDisplay.spamLinesDisplay.setText(CustomFrame.localDisplay.spamLinesDisplay.getText().replaceAll("(?m)^\\s", ""));
			CustomFrame.localDisplay.spamLinesDisplay.setEditable(false);
			CustomFrame.localDisplay.spamLinesDisplay.getCaret().setVisible(false);
			CustomFrame.localDisplay.spamLinesDisplay.setBackground(Constants.BACKGROUND_COLOR);
			CustomFrame.localDisplay.editSpamBox.addMouseListener(new SUL("Allows you to edit the text manually."));
			CustomFrame.localDisplay.editSpamBox.setText("Edit Text Box");
		}
		else if(CustomFrame.textMode == 2)
		{
			if(!CustomFrame.sequenceDisplay.spamLinesDisplay.getText().equals(""))
			{
				String[] textsToType = CustomFrame.sequenceDisplay.spamLinesDisplay.getText().split("\n");
				boolean formatIsOkayAndICanCloseEditor = true;
				for(String s : textsToType)
				{
					String temp = s.substring(0, 5);
					if(!temp.equals("Wait:") && !temp.equals("Text:"))
					{
						JOptionPane.showMessageDialog(Settings.frame, "The following line is missing \'Wait: \' or \'Text: \' keyword:\n" + s);
						formatIsOkayAndICanCloseEditor = false;
						break;
					}
				}
				if(formatIsOkayAndICanCloseEditor)
				{
					CustomFrame.sequenceDisplay.addTextButton.setEnabled(true);
					CustomFrame.sequenceDisplay.addWaitButton.setEnabled(true);
					CustomFrame.sequenceDisplay.spamLinesDisplay.setText(CustomFrame.sequenceDisplay.spamLinesDisplay.getText().trim());
					CustomFrame.sequenceDisplay.spamLinesDisplay.setText(CustomFrame.sequenceDisplay.spamLinesDisplay.getText().replaceAll("(?m)^\\s", ""));
					CustomFrame.sequenceDisplay.spamLinesDisplay.setEditable(false);
					CustomFrame.sequenceDisplay.spamLinesDisplay.getCaret().setVisible(false);
					CustomFrame.sequenceDisplay.spamLinesDisplay.setBackground(Constants.BACKGROUND_COLOR);
					CustomFrame.sequenceDisplay.editSpamBox.addMouseListener(new SUL("Allows you to edit the text manually."));
					CustomFrame.sequenceDisplay.editSpamBox.setText("Edit Text Box");
				}
				else
				{
					CustomFrame.updateDisplay(CustomFrame.sequenceDisplay);
					CustomFrame.lastVisitedPanel = CustomFrame.sequenceDisplay;
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		//Does nothing
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		//Does nothing
	}
	
}