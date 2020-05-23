package eco.typer.tools;

import eco.typer.Settings.Constants;
import eco.typer.custom_frame.CustomFrame;

public class ReportSettings {
	
	public static String operatingSystem = "";
	
	//Text Display settings
	public static String typerMode = "";
	public static String typerText = "";
	public static String messageOrder = "";
	
	//Settings display settings
	public static String screenshots = "";
	public static String executionTime = "";
	public static String typos = "";
	public static String lineBreakTime = "";
	public static String finalCommand = "";
	public static String timeStamps = "";
	public static String finishedAlarm = "";
	public static String finishedAlarmFile = "";
	public static String twoFourHourClock = "";
	public static String pinToTop = "";
	public static String typingSpeed = "";
	
	public static void setCurrentSettingsForReporting()
	{
		operatingSystem = Constants.OPERATING_SYSTEM;
		if(CustomFrame.textMode == 0) //Local
		{
			typerMode = "Local";
			messageOrder = CustomFrame.localDisplay.messageOrder.getSelectedItem().toString();
			typerText = CustomFrame.localDisplay.spamLinesDisplay.getText();
		} else if(CustomFrame.textMode == 1) //Remote
		{
			
		} else { //Sequence
			typerMode = "Sequence";
			messageOrder = "N/A";
			typerText = CustomFrame.sequenceDisplay.spamLinesDisplay.getText();
		}
		screenshots = CustomFrame.settingsPanel.screenshotTime.getSelectedItem().toString();
		executionTime = CustomFrame.settingsPanel.execTime.getSelectedItem().toString();;
		typos = CustomFrame.settingsPanel.typos.getSelectedItem().toString();
		lineBreakTime = CustomFrame.settingsPanel.lineBreakTime.getSelectedItem().toString();
		finalCommand = CustomFrame.settingsPanel.finishCommand.getSelectedItem().toString();
		timeStamps = CustomFrame.settingsPanel.timeStamps.isTicked() + "";
		finishedAlarm = CustomFrame.settingsPanel.finishedAlarm.isTicked() + "";
		finishedAlarmFile = CustomFrame.settingsPanel.alarmSelect.getSelectedItem().toString();
		twoFourHourClock = CustomFrame.settingsPanel.twentyFourClock.isTicked() + "";
		pinToTop = CustomFrame.settingsPanel.pinToTop.isTicked() + "";
		typingSpeed = CustomFrame.settingsPanel.typeSpeedSlider.getValue() + "ms";
	}
	
	public static String getCurrentSettingsForReporting()
	{
		String returnStr = "Operating System: " + operatingSystem + "\nTyper Mode: " + typerMode + "\nMessage Order: " + messageOrder + "\n";
		returnStr += "Typer Text: " + typerText + "\nScreenshots: " + screenshots + "\nExecution Time: " + executionTime + "\nTypos: " + typos + "\n";
		returnStr += "Line Break Time: " + lineBreakTime + "\nFinal Command: " + finalCommand + "\nTime Stamps: " + timeStamps + "\nFinished Alarm: " + finishedAlarm + "\n";
		returnStr += "Finished Alarm File: " + finishedAlarmFile + "\n24 Hour Clock: " + twoFourHourClock + "\nPin To Top: " + pinToTop + "\nTyping Speed: " + typingSpeed;
		return returnStr;
	}

}
