package eco.typer.listeners;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CustomFrame;

/**
 * 
 * @author dakota
 * This class executes code when the toggles are check from the settings panel
 */

public class SettingsTogglesListener
{
	
	public SettingsTogglesListener()
	{
		//Does nothing
	}
	
	public static void execute(String action)
	{
		switch(action)
		{
		case "Pin to Top":
			if(Settings.frame.isAlwaysOnTop())
			{
				Settings.frame.setAlwaysOnTop(false);
			}
			else
			{
				Settings.frame.setAlwaysOnTop(true);
			}
			break;
		case "Finished Alarm":
			if(CustomFrame.settingsPanel.finishedAlarm.isTicked())
			{
				CustomFrame.settingsPanel.alarmSelect.setVisible(true);
				CustomFrame.settingsPanel.typeSpeedSlider.setBounds(CustomFrame.settingsPanel.typeSpeedSlider.getX(), CustomFrame.settingsPanel.typeSpeedSlider.getY() + 40, CustomFrame.settingsPanel.typeSpeedSlider.getWidth(), CustomFrame.settingsPanel.typeSpeedSlider.getHeight());
			}
			else
			{
				CustomFrame.settingsPanel.alarmSelect.setVisible(false);
				CustomFrame.settingsPanel.typeSpeedSlider.setBounds(CustomFrame.settingsPanel.typeSpeedSlider.getX(), CustomFrame.settingsPanel.typeSpeedSlider.getY() - 40, CustomFrame.settingsPanel.typeSpeedSlider.getWidth(), CustomFrame.settingsPanel.typeSpeedSlider.getHeight());
			}
			break;
//		case "Remote Mode On":
//			CustomFrame.updateDisplay(CustomFrame.remoteDisplay);
//			CustomFrame.lastVisitedPanel = CustomFrame.remoteDisplay;
//			CustomFrame.textMode = 1;
//			break;
//		case "Remote Mode Off":
//			CustomFrame.updateDisplay(CustomFrame.textDisplay);
//			CustomFrame.lastVisitedPanel = CustomFrame.textDisplay;
//			CustomFrame.textMode = 0;
//			CustomFrame.remoteDisplay.remoteMode.setTick(true);
//			break;
		case "Mouse Hover Select":
			if(Constants.mouseHoverMode == true)
			{
				Constants.mouseHoverMode = false;
			}
			else
			{
				Constants.mouseHoverMode = true;
			}
			break;
		default:
			System.err.println("Action \"" + action + "\" isn't coded in yet.\nPlease code it in SettingsTogglesListener.java");
			break;
		}
	}

}
