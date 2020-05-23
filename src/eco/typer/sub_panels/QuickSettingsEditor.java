package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.listeners.SUL;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the quick settings diplay editor
 */

@SuppressWarnings("serial")
public class QuickSettingsEditor extends CPanel
{
	
	private String currentLineBreakTime = CustomFrame.settingsPanel.lineBreakTime.getSelectedItem().toString();
	private String currentExectionTime = CustomFrame.settingsPanel.execTime.getSelectedItem().toString();
	private String currentFinalCommand = CustomFrame.settingsPanel.finishCommand.getSelectedItem().toString();
	private String currentMessageOrder = CustomFrame.localDisplay.messageOrder.getSelectedItem().toString();
	private String currentTypingSpeed = CustomFrame.settingsPanel.typeSpeedSlider.getValue() + "ms /char";
	private String currentTypoChance = CustomFrame.settingsPanel.typos.getSelectedItem().toString() + " /char";
	private String currentScreenshots = CustomFrame.settingsPanel.screenshotTime.getSelectedItem().toString().replace("Screenshots: ", "");
	private String currentTimeStamps = CustomFrame.settingsPanel.timeStamps.isTicked() + "";
	private String currentPinToTop = CustomFrame.settingsPanel.pinToTop.isTicked() + "";
	private String currentFinishedAlarm = CustomFrame.settingsPanel.finishedAlarm.isTicked() + "";
	private String currentAlarmSound = CustomFrame.settingsPanel.alarmSelect.getSelectedItem().toString();
	private String currentClockFormat = CustomFrame.settingsPanel.twentyFourClock.isTicked() + "";
	//private String currentWatchClient = CustomFrame.settingsPanel.watchGameClient.getSelectedItem().toString().replace("Watch Client: ", "");
	
	String[] currentSettingsArray = {currentLineBreakTime,
										currentExectionTime,
										currentFinalCommand,
										currentMessageOrder,
										currentTypingSpeed,
										currentTypoChance,
										currentScreenshots,
										currentTimeStamps,
										currentPinToTop,
										currentFinishedAlarm,
										currentAlarmSound,
										currentClockFormat/*,
										currentWatchClient*/};
	
	String[] currentSettingsArrayLabel = {"Line Break Time: ",
											"Execution Time: ",
											"Final Command: ",
											"",						//Message Order
											"Typing Speed: ",
											"",						//Typos
											"Screenshots: ",
											"Time Stamps: ",
											"Pin To Top: ",
											"Finished Alarm: ",
											"Alarm Sound: ",
											"24 Hour Clock: "/*,
											"Watch Game Client: "*/};
	
	public QuickSettingsEditor()
	{
		super("Profile Settings");
		
		SaveButton();
		CurrentSettings();
		FileList();
	}
	
	private void FileList()
	{
		DefaultListModel<String> filesList = new DefaultListModel<String>();
		File[] files = Constants.HOST_FILES_DIRECTORY.listFiles();
		for(File f : files)
		{
			if(f.isFile() && f.toString().contains(".cfg"))
			{
				filesList.add(0, f.getName().replaceAll(".cfg", ""));
			}
		}
		
		JList<String> fileList = new JList<String>(filesList);
		fileList.setBackground(Constants.BACKGROUND_COLOR.brighter());
		fileList.setForeground(Color.WHITE);
		JScrollPane listScroller = new JScrollPane(fileList);
		listScroller.setBounds(CustomFrame.WORKPANEL_WIDTH - 210, 50, 200, CustomFrame.WORKPANEL_HEIGHT - 150);
		add(listScroller);
		
		CButton deleteFile = new CButton("Delete File");
		deleteFile.setBounds(460, CustomFrame.WORKPANEL_HEIGHT - 100, 140, 20);
		deleteFile.addMouseListener(new SUL("Allows you to delete the above selected file."));
		deleteFile.addActionListener(e ->
		{
			if(fileList.getSelectedValue() == null)
			{
				Toolkit.getDefaultToolkit().beep();
			}
			else
			{
				File fileName = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileList.getSelectedValue() + ".cfg");
				fileName.delete();
				CustomFrame.updateDisplay(new QuickSettingsEditor());
			}
		});
		add(deleteFile);
	}

	private void CurrentSettings()
	{
		int y = 50;
		int c = 0;
		for(String s : currentSettingsArray)
		{
			JLabel label = new JLabel(currentSettingsArrayLabel[c] + checkText(s));
			label.setBounds(10, y, 350, 20);
			label.setForeground(checkForeground(s));
			Utils.setFont(label, "Neon.ttf", 16);
			add(label);
			y += 20;
			c++;
		}
	}
	
	private Color checkForeground(String checkingFor)
	{
		if(checkingFor.contains("Line Break Time") || checkingFor.contains("Execution Time"))
		{
			return Color.WHITE;
		}
		else if(checkingFor.contains("Final Command"))
		{
			return Color.WHITE;
		}
		else if(checkingFor.contains("Alarm Sound") && currentFinishedAlarm.equals("false"))
		{
			return Color.WHITE;
		}
		else if(checkingFor.contains("Alarm Sound") && currentFinishedAlarm.equals("true"))
		{
			return Color.YELLOW;
		}
		return Color.WHITE;
	}
	
	private String checkText(String checkingFor)
	{
		if(checkingFor.contains("Line Break Time"))
		{
			return "Default";
		}
		else if(checkingFor.contains("Execution Time"))
		{
			return "Infinite";
		}
		else if(checkingFor.contains("Final Command"))
		{
			return "Default";
		}
		else if(checkingFor.contains("Alarm Sound") && currentFinishedAlarm.equals("false"))
		{
			return "N/A";
		}
		else if(checkingFor.contains("Alarm Sound") && currentFinishedAlarm.equals("true"))
		{
			return "Default";
		}
		return checkingFor;
	}

	private void SaveButton()
	{
		CButton saveConfig = new CButton("Save");
		saveConfig.setBounds(410, CustomFrame.WORKPANEL_HEIGHT - 40, 190, 30);
		saveConfig.addMouseListener(new SUL("Allows you to save this configuration and load it in later."));
		saveConfig.addActionListener(e ->
		{
			String configNameInput = JOptionPane.showInputDialog(Settings.frame, "What would you like to name your profile?");
			if(configNameInput != null && !configNameInput.equals(""))
			{
				File fileName = new File(Constants.HOST_FILES_DIRECTORY + "/" + configNameInput + ".cfg");
				System.out.println(fileName);
				try
				{
					FileWriter writer = new FileWriter(fileName);
					for(int i = this.currentSettingsArray.length - 1; i >= 0; i--)
					{
						writer.write(this.currentSettingsArray[i] + "\n");
					}
					writer.close();
				}
				catch (Exception e1)
				{
					Utils.writeErrorReport(e1, 100);
				}
			}
			
		});
		add(saveConfig);
	}

	public void Title()
	{
		JLabel title = new JLabel("Quick Settings");
		title.setBounds(10, 10, CustomFrame.WORKPANEL_WIDTH - 10, 30);
		title.setForeground(Color.WHITE);
		Utils.setFont(title, "Neon.ttf", 30);
		add(title);
	}

}
