package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CTextArea;
import eco.typer.listeners.SUL;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the load in file display
 */

@SuppressWarnings("serial")
public class HostFileEditor extends CPanel
{
	
	private String type;
	
	public HostFileEditor(String type) //type = LocalTextDisplay OR SequenceTextDisplay
	{
		super("File Editor");
		this.type = type;
		
		switch(type)
		{
		case "LocalTextDisplay":
			if(!CustomFrame.localDisplay.spamLinesDisplay.getText().equals(""))
			{
				CurrentViewAndSave(CustomFrame.localDisplay.spamLinesDisplay.getText());
				FileList(true);	//Short
			}
			else
			{
				FileList(false);	//Long
			}
			break;
		case "SequenceTextDisplay":
			if(!CustomFrame.sequenceDisplay.spamLinesDisplay.getText().equals(""))
			{
				CurrentViewAndSave(CustomFrame.sequenceDisplay.spamLinesDisplay.getText());
				FileList(true);	//Short
			}
			else
			{
				FileList(false);	//Long
			}
			break;
		}
	}

	private void FileList(boolean heightOfFileSelector)
	{
		DefaultListModel<String> filesList = new DefaultListModel<String>();
		File[] files = Constants.HOST_FILES_DIRECTORY.listFiles();
		for(File f : files)
		{
			switch(this.type)
			{
			case "LocalTextDisplay":
				if(f.isFile() && f.toString().contains(".eco"))
				{
					filesList.add(0, f.getName().replaceAll(".eco", ""));
				}
				break;
			case "SequenceTextDisplay":
				if(f.isFile() && f.toString().contains(".seq"))
				{
					filesList.add(0, f.getName().replaceAll(".seq", ""));
				}
				break;
			}
		}
		
		JList<String> fileList = new JList<String>(filesList);
		fileList.setBackground(Constants.BACKGROUND_COLOR.brighter());
		fileList.setForeground(Color.WHITE);
		JScrollPane listScroller = new JScrollPane(fileList);
		listScroller.setBounds(CustomFrame.WORKPANEL_WIDTH - 210, 50, 200, CustomFrame.WORKPANEL_HEIGHT - 100);
		if(heightOfFileSelector) //Short
		{
			listScroller.setBounds(CustomFrame.WORKPANEL_WIDTH - 210, 195, 200, CustomFrame.WORKPANEL_HEIGHT - 245);
		}
		add(listScroller);
		
		CButton deleteFile = new CButton("Delete File");
		deleteFile.setBounds(460, CustomFrame.WORKPANEL_HEIGHT - 50, 140, 20);
		deleteFile.addMouseListener(new SUL("Allows you to delete the above selected file."));
		deleteFile.addActionListener(e ->
		{
			if(fileList.getSelectedValue() == null)
			{
				Toolkit.getDefaultToolkit().beep();
			}
			else
			{
				File fileName = null;
				switch(this.type)
				{
				case "LocalTextDisplay":
					fileName = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileList.getSelectedValue() + ".eco");
					break;
				case "SequenceTextDisplay":
					fileName = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileList.getSelectedValue() + ".seq");
					break;
				}
				fileName.delete();
				CustomFrame.updateDisplay(new HostFileEditor(this.type));
			}
		});
		add(deleteFile);
	}

	private void CurrentViewAndSave(String text)
	{
		JPanel border = new JPanel();	//JPanel for the border around textarea
		border.setBounds(10, 50, CustomFrame.WORKPANEL_WIDTH - 20, 120);
		border.setBackground(null);
		border.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Constants.PRIMARY_COLOR, Constants.PRIMARY_COLOR));
		border.setLayout(null);
		add(border);
		
		CTextArea spamLinesDisplay = new CTextArea(text, Color.WHITE);
		spamLinesDisplay.setBounds(5, 5, border.getWidth() - 10, border.getHeight() - 10);
		spamLinesDisplay.setHighlighter(null);
		border.add(spamLinesDisplay);
		
		CButton saveFile = new CButton("Save Text", Constants.BACKGROUND_COLOR.brighter());
		saveFile.setBounds(CustomFrame.WORKPANEL_WIDTH - 150, border.getHeight() + 50, 140, 20);
		saveFile.addMouseListener(new SUL("Allows you to save this text and load it in later."));
		saveFile.addActionListener(e ->
		{
			String fileName = JOptionPane.showInputDialog(Settings.frame, "What would you like to name this file?");
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
			if(fileName != null && !fileName.equals(""))
			{
				File file = null;
				switch(this.type)
				{
				case "LocalTextDisplay":
					file = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileName + ".eco");
					break;
				case "SequenceTextDisplay":
					file = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileName + ".seq");
					break;
				}
				
				try
				{
					FileWriter writer = new FileWriter(file);
					writer.write(text);
					writer.close();
				}
				catch (Exception e1)
				{
					Utils.writeErrorReport(e1, 90);
				}
			}
		});
		add(saveFile);
		
	}

}
