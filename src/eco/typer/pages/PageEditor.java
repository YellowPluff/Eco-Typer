package eco.typer.pages;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import eco.typer.Settings.Constants;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CTextField;
import eco.typer.listeners.SUL;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This package of classes makes up the page capability
 */

@SuppressWarnings("serial")
public class PageEditor extends CPanel
{
	
	private CTextField replacementInputField;
	private JLabel warningLabel;
	
	private File pageFile;
	private String[] values;
	
	private CTextField textLine1, textLine2, textLine3, textLine4, textLine5, textLine6;
	private int[] yValues = new int[6];
	
	public PageEditor(String pageName)
	{
		super("Template Editor - " + pageName);
		this.pageFile = new File(Constants.HOST_FILES_DIRECTORY + "/" + pageName + ".epg");
		values = readInFile();
		
		int c = 110;
		for(int i = 0; i < 6; i++)
		{
			yValues[i] = c;
			c += 40;
		}
		
		ReplacementTextField();
		WarningLabel();
		TextLine1();
		TextLine2();
		TextLine3();
		TextLine4();
		TextLine5();
		TextLine6();
		SavePageButton();
		
	}

	private void TextLine1()
	{
		textLine1 = new CTextField(values[1]);
		textLine1.setBounds(10, yValues[0], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine1);
	}
	
	private void TextLine2()
	{
		textLine2 = new CTextField(values[2]);
		textLine2.setBounds(10, yValues[1], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine2);
	}
	
	private void TextLine3()
	{
		textLine3 = new CTextField(values[3]);
		textLine3.setBounds(10, yValues[2], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine3);
	}
	
	private void TextLine4()
	{
		textLine4 = new CTextField(values[4]);
		textLine4.setBounds(10, yValues[3], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine4);
	}
	
	private void TextLine5()
	{
		textLine5 = new CTextField(values[5]);
		textLine5.setBounds(10, yValues[4], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine5);
	}
	
	private void TextLine6()
	{
		textLine6 = new CTextField(values[6]);
		textLine6.setBounds(10, yValues[5], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine6);
	}

	private void ReplacementTextField()
	{
		replacementInputField = new CTextField(values[0], BorderFactory.createLineBorder(Constants.PRIMARY_COLOR.darker(), 1));
		replacementInputField.setBounds(10, 50, 250, 30);
		replacementInputField.setBackground(null);
		replacementInputField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				warningLabel.setText("Make sure to include \"" + replacementInputField.getText() + "\".");
			}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				//Does nothing
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				//Does nothing
			}
		});
		add(replacementInputField);
	}
	
	private void WarningLabel()
	{
		warningLabel = new JLabel("Make sure to include \"" + values[0] + "\".");
		warningLabel.setBounds(10, CustomFrame.WORKPANEL_HEIGHT - 30, CustomFrame.WORKPANEL_WIDTH - 200, 20);
		warningLabel.setForeground(Color.RED);
		warningLabel.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(warningLabel, "Neon.ttf", 16);
		add(warningLabel);
	}

	private void SavePageButton()
	{
		CButton savePageButton = new CButton("Save Changes");
		savePageButton.addMouseListener(new SUL("Save the changes to this template."));
		savePageButton.setBounds(410, CustomFrame.WORKPANEL_HEIGHT - 30, 190, 20);
		savePageButton.addActionListener(e ->
		{
			if(!replacementInputField.getText().equals(""))
			{
				pageFile.delete();
				try
				{
					FileWriter writer = new FileWriter(pageFile);
					writer.write(this.replacementInputField.getText() + "\n");
					if(!textLine1.getText().equals("")) writer.write(textLine1.getText() + "\n");
					if(!textLine2.getText().equals("")) writer.write(textLine2.getText() + "\n");
					if(!textLine3.getText().equals("")) writer.write(textLine3.getText() + "\n");
					if(!textLine4.getText().equals("")) writer.write(textLine4.getText() + "\n");
					if(!textLine5.getText().equals("")) writer.write(textLine5.getText() + "\n");
					if(!textLine6.getText().equals("")) writer.write(textLine6.getText());
					writer.close();
				}
				catch (Exception e1)
				{
					Utils.writeErrorReport(e1, 40);
				}
			}
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		});
		add(savePageButton);
	}
	
	private String[] readInFile()
	{
		String[] vals = new String[7];
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(pageFile));
			String line;
			int count = 0;
			while((line = reader.readLine()) != null)
			{
				vals[count] = line;
				count++;
			}
			reader.close();
		}
		catch (Exception e1)
		{
			Utils.writeErrorReport(e1, 50);
		}
		return vals;
	}

}
