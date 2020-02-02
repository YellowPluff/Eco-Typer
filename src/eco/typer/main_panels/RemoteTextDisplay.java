package eco.typer.main_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CComboBox;
import eco.typer.custom_objects.CTextArea;
import eco.typer.listeners.SUL;

@SuppressWarnings("serial")
public class RemoteTextDisplay extends CPanel
{
	
	public CTextArea spamLinesDisplay;
	public CComboBox messageOrder, refreshInterval;
	
	public RemoteTextDisplay()
	{
		super("Remote Text Display");
		
		ModeSelect();
		
		EnterURL();
		MessageOrder();
		SpamDisplayBox();
		RefreshRate();
	}
	
	private void ModeSelect()
	{
		CButton changeMode = new CButton("Change Mode", new Color(50, 50, 50));
		changeMode.setBounds(CustomFrame.WORKPANEL_WIDTH - 150, 10, 140, 15);
		changeMode.addMouseListener(new SUL("Change Eco Typer Typing Mode"));
		add(changeMode);
		changeMode.addActionListener(e ->
		{
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			
			JLabel text = new JLabel("Mode:");
			mainPanel.add(text, BorderLayout.NORTH);
			
			//String[] modes = {"Local Mode", "Remote Mode", "Sequence Mode"};
			String[] modes = {"Local Mode", "Sequence Mode"};
			JList<String> listModes = new JList<String>(modes);
			listModes.setPreferredSize(new Dimension(Constants.FRAME_WIDTH / 3, Constants.FRAME_HEIGHT / 6));
			mainPanel.add(listModes, BorderLayout.CENTER);
			
			JOptionPane.showMessageDialog(Settings.frame, mainPanel, "URL", JOptionPane.QUESTION_MESSAGE);
			changeMode.setBackground(new Color(50, 50, 50));
			
			if(listModes.getSelectedValue() != null)
			{
				switch(listModes.getSelectedValue())
				{
				case "Local Mode":
					CustomFrame.updateDisplay(CustomFrame.localDisplay);
					CustomFrame.lastVisitedPanel = CustomFrame.localDisplay;
					CustomFrame.textMode = 0;
					break;
				case "Remote Mode":
					CustomFrame.updateDisplay(CustomFrame.remoteDisplay);
					CustomFrame.lastVisitedPanel = CustomFrame.remoteDisplay;
					CustomFrame.textMode = 1;
					break;
				case "Sequence Mode":
					CustomFrame.updateDisplay(CustomFrame.sequenceDisplay);
					CustomFrame.lastVisitedPanel = CustomFrame.sequenceDisplay;
					CustomFrame.textMode = 2;
					break;
				}
			}
		});
	}
	
	private void EnterURL()
	{
		CButton enterUrl = new CButton("Enter URL");
		enterUrl.setBounds(10, 50, 190, 30);
		enterUrl.addActionListener(e ->
		{
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			
			JLabel text = new JLabel("Enter your URL:");
			mainPanel.add(text, BorderLayout.NORTH);
			
			//Example link: http://freetexthost.com/1qhjguunpt
			JTextField inputField = new JTextField("");
			inputField.setPreferredSize(new Dimension(Constants.FRAME_WIDTH / 3, 25));
			mainPanel.add(inputField, BorderLayout.CENTER);
			
			JButton pasteButton = new JButton("Paste");
			mainPanel.add(pasteButton, BorderLayout.EAST);
			pasteButton.addActionListener(e1 ->
			{
				inputField.paste();
			});
			
			JOptionPane.showMessageDialog(Settings.frame, mainPanel, "URL", JOptionPane.QUESTION_MESSAGE);
			
			String urlIn = inputField.getText().trim();
			if(!urlIn.isEmpty() && urlIn.contains("http://freetexthost.com/"))
			{
				this.websiteLink = urlIn;
				readInWebsiteData(true);
			}
		});
		add(enterUrl);
	}
	
	private void MessageOrder()
	{
		messageOrder = new CComboBox(Constants.MESSAGE_ORDERS[0], Constants.MESSAGE_ORDERS);
		messageOrder.addMouseListener(new SUL("What order should the texts be sent in?"));
		messageOrder.setBounds(CustomFrame.WORKPANEL_WIDTH - 400, 50, 390, 30);
		add(messageOrder);
	}
	
	private void SpamDisplayBox()
	{
		JPanel border = new JPanel();	//JPanel for the border around textarea
		border.setBounds(10, 90, CustomFrame.WORKPANEL_WIDTH - 20, 240);
		border.setBackground(null);
		border.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Constants.PRIMARY_COLOR, Constants.PRIMARY_COLOR));
		border.setLayout(null);
		add(border);
		
		spamLinesDisplay = new CTextArea("", Color.WHITE);
		spamLinesDisplay.setBounds(5, 5, border.getWidth() - 10, border.getHeight() - 10);
		border.add(spamLinesDisplay);
	}
	
	private void RefreshRate()
	{
		refreshInterval = new CComboBox("Refresh Interval", Constants.REFRESH_RATES);
		refreshInterval.addMouseListener(new SUL("How often do you want to refresh from the website?"));
		refreshInterval.setBounds(CustomFrame.WORKPANEL_WIDTH - 160, CustomFrame.WORKPANEL_HEIGHT - 65, 150, 30);
		add(refreshInterval);
	}
	
	private String websiteLink;
	private String websiteData;
	private String websiteDataBackup;
	//Get text array of all texts
	private void setTextDisplay()
	{
		this.spamLinesDisplay.setText("");
		
		String webString = this.websiteData;
		
		webString = webString.substring(webString.indexOf("#Start Text"), webString.indexOf("#End Text"));
		webString = webString.substring("#Start Text".length() + 7, webString.length());
		webString = webString.replaceAll("<br />", ",");
		webString = webString.trim();
		webString = webString.substring(0, webString.length() - 1);
		webString = webString.replace("&quot;", "\"");
		webString = webString.replace("&amp;", "&");
		webString = webString.replace("&lt;", "<");
		webString = webString.replace("&gt;", ">");
		
		String[] webStringArr = webString.split(", ");
		
		for(String s : webStringArr)
		{
			this.spamLinesDisplay.append(s + "\n");
		}
	}
	
	//User can toggle remotely if they want to stop the typing.
	public boolean getKeepTyping()
	{
		try
		{
			String webString = this.websiteData;
			
			webString = webString.substring(webString.indexOf("#Start TyperRunning"), webString.indexOf("#End TyperRunning"));
			webString = webString.substring("#Start TyperRunning".length() + 7, webString.length());
			webString = webString.replaceAll("<br />", ",");
			webString = webString.trim();
			webString = webString.substring(0, webString.length() - 1);
			
			//I do it this way incase they mispell true/false
			if(webString.toLowerCase().contains("tr"))
			{
				return true;
			}
			return false;
		}
		catch(StringIndexOutOfBoundsException e)
		{
			System.out.println("Exception: Can't connect to website to determine if we should stop");
			return true;
		}
		
	}
	
	public void readInWebsiteData(boolean tryAgain)
	{	
		String status = "Try again";
		int tryCount = 0;
		
		websiteDataBackup = websiteData;
		websiteData = "";
		
		while(status.equals("Try again"))
		{
			try
			{
				URL url = new URL(this.websiteLink);
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				while((line = reader.readLine()) != null)
				{
					websiteData += " " + line;
				}
				reader.close();
				if(!websiteData.contains("#Start Text") || !websiteData.contains("#End Text") || !websiteData.contains("#Start TyperRunning") || !websiteData.contains("#End TyperRunning"))
				{
					websiteData = websiteDataBackup;
					websiteData.substring(1, 2); //Throw exception on purpose
				}
				setTextDisplay();
				status = "done";
				
			}
			catch(Exception e)
			{
				if(tryAgain == true)
				{
					tryCount++;
					try
					{
						Thread.sleep(1500);
					}
					catch (Exception e1)
					{
						e1.printStackTrace();
					}
					if(tryCount >= 4)
					{
						int option = JOptionPane.showConfirmDialog(Settings.frame, "Something went wrong.\nMake sure the link is valid and follows the correct format.\nA guide can be found on the website.\nWould you like to try again?", "Oops!", JOptionPane.YES_NO_OPTION);
						if(option == 0)
						{
							readInWebsiteData(true);
						}
						break;
					}
				}
				else
				{
					System.out.println("Exception: Can't connect to website to update texts");
					status = "done";
				}
				
			}
		}
	}

}
