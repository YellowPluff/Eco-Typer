package eco.typer.main_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CComboBox;
import eco.typer.custom_objects.CTextArea;
import eco.typer.listeners.SUL;
import eco.typer.sub_panels.AddingTextPanel;
import eco.typer.sub_panels.HostFileEditor;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the main text display
 */

@SuppressWarnings("serial")
public class LocalTextDisplay extends CPanel
{
	
	public CTextArea spamLinesDisplay;
	public CButton addTextButton;
	public CButton editSpamBox;
	public CComboBox messageOrder;
	
	public LocalTextDisplay()
	{
		super("Local Text Display");
		
		if(!Settings.liteMode)
		{
			ModeSelect();
		}
		
		LoadFile();
		MessageOrder();
		SpamDisplayBox();
		EditSpamButton();
		AddSpamButton();
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
	
	private void AddSpamButton()
	{
		addTextButton = new CButton("Add Text");
		addTextButton.setBounds(10, CustomFrame.WORKPANEL_HEIGHT - 35, 190, 20);
		addTextButton.addMouseListener(new SUL("Add text to the spambox."));
		addTextButton.addActionListener(e ->
		{
			CustomFrame.updateDisplay(new AddingTextPanel());
			addTextButton.setBackground(Constants.PRIMARY_COLOR);
							/*
							 * Whenever I click the "Add Text" button it goes dark and never resets, so I'm
							 * resetting it manually with this line. It's probably a bug I don't care to properly fix.
							 * 
							 * It has to do with the mouse never properly leaving the 'bounds' of the button,
							 * therefore never registering to revert back to the original color, this also is true
							 * for the status text of this button, which I fixed by changing mouseRelease in SUL.java
							 */
		});
		add(addTextButton);
	}

	private void EditSpamButton()
	{
		editSpamBox = new CButton("Edit Text Box", Constants.BACKGROUND_COLOR.darker());
		editSpamBox.setBounds(CustomFrame.WORKPANEL_WIDTH - 150, CustomFrame.WORKPANEL_HEIGHT - 75, 140, 20);
		editSpamBox.addMouseListener(new SUL("Allows you to edit the text manually."));
		editSpamBox.addActionListener(e ->
		{
			switch(editSpamBox.getText())
			{
			case "Done Editing":
				addTextButton.setEnabled(true);
				spamLinesDisplay.setText(spamLinesDisplay.getText().trim());
				spamLinesDisplay.setText(spamLinesDisplay.getText().replaceAll("(?m)^\\s", ""));
				spamLinesDisplay.setEditable(false);
				spamLinesDisplay.getCaret().setVisible(false);
				spamLinesDisplay.setBackground(Constants.BACKGROUND_COLOR);
				editSpamBox.addMouseListener(new SUL("Allows you to edit the text manually."));
				editSpamBox.setText("Edit Text Box");
				break;
			case "Edit Text Box":
				if(!spamLinesDisplay.getText().isEmpty())
				{
					addTextButton.setEnabled(false);
					spamLinesDisplay.setEditable(true);
					spamLinesDisplay.getCaret().setVisible(true);
					spamLinesDisplay.setBackground(Constants.BACKGROUND_COLOR.brighter());
					editSpamBox.addMouseListener(new SUL("Finish Editing."));
					editSpamBox.setText("Done Editing");
				}
				else
				{
					Toolkit.getDefaultToolkit().beep();
				}
				break;
			default:
				Settings.frame.dispose();
				JOptionPane.showMessageDialog(null, "Something VERY bad happened and you should NEVER see this.\nPlease tell and show the developer\nPackage: eco.typer.main_panels\nClass: LocalTextDisplay.java");
				System.exit(0);
				break;
			}
		});
		add(editSpamBox);
	}

	private void SpamDisplayBox()
	{
		JPanel border = new JPanel();	//JPanel for the border around textarea
		border.setBounds(10, 90, CustomFrame.WORKPANEL_WIDTH - 20, 240);
		border.setBackground(null);
//		border.setBorder(BorderFactory.createLineBorder(Constants.PRIMARY_COLOR.darker(), 3));
//		border.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		border.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Constants.PRIMARY_COLOR, Constants.PRIMARY_COLOR));
		border.setLayout(null);
		add(border);
		
		spamLinesDisplay = new CTextArea("", Color.WHITE);
		spamLinesDisplay.setBounds(5, 5, border.getWidth() - 10, border.getHeight() - 10);
		border.add(spamLinesDisplay);
	}

	private void LoadFile()
	{
		CComboBox fileSelector = new CComboBox("Quick File Select", Constants.SAVED_LOCAL_FILES);
		fileSelector.addMouseListener(new SUL("Use this to quickly load saved files."));
		fileSelector.setBounds(10, 50, 190, 30);
		fileSelector.addActionListener(e ->
		{
			switch(fileSelector.getSelectedIndex())
			{
			case 0:
				if(!spamLinesDisplay.isEditable())
				{
					CustomFrame.updateDisplay(new HostFileEditor("LocalTextDisplay"));
				}
				break;
			case 1:
				spamLinesDisplay.setText("");
				break;
			default:
				LoadInSelectedFile(fileSelector.getSelectedItem() + "");
				break;
			}
			fileSelector.setText("Quick File Select");
		});
		add(fileSelector);
	}
	
	private void MessageOrder()
	{
		messageOrder = new CComboBox(Constants.MESSAGE_ORDERS[0], Constants.MESSAGE_ORDERS);
		messageOrder.addMouseListener(new SUL("What order should the texts be sent in?"));
		messageOrder.setBounds(CustomFrame.WORKPANEL_WIDTH - 400, 50, 390, 30);
		add(messageOrder);
	}

	private void LoadInSelectedFile(String fileName)
	{
		File file = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileName + ".eco");
		spamLinesDisplay.setText("");
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null)
			{
				spamLinesDisplay.append(line + ",");
			}
			spamLinesDisplay.setText(spamLinesDisplay.getText().substring(0, spamLinesDisplay.getText().length() - 1));
			spamLinesDisplay.setText(spamLinesDisplay.getText().replaceAll(",", "\n"));
			reader.close();
		}
		catch (Exception e1)
		{
			Utils.writeErrorReport(e1, 110);
		}
	}

}
