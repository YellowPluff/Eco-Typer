package eco.typer.pages;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
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
public class PageMaker extends CPanel 
{
	
	private CTextField replacementInputField;
	private JLabel warningLabel;
	
	private CTextField textLine1, textLine2, textLine3, textLine4, textLine5, textLine6;
	private int[] yValues = new int[6];
	
	public PageMaker()
	{
		super("New Template");
		
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
		MakePageButton();
	}

	private void MakePageButton()
	{
		CButton makePageButton = new CButton("Make Template");
		makePageButton.addMouseListener(new SUL("Make a new template."));
		makePageButton.setBounds(410, CustomFrame.WORKPANEL_HEIGHT - 30, 190, 20);
		makePageButton.addActionListener(e ->
		{
			String fileName = JOptionPane.showInputDialog(Settings.frame, "What would you like to name this template?");
			if(fileName != null && !fileName.equals("") && !replacementInputField.getText().equals(""))
			{
				File file = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileName + ".epg");
				try
				{
					FileWriter writer = new FileWriter(file);
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
					Utils.writeErrorReport(e1, 60);
				}
			}
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		});
		add(makePageButton);
	}

	private void TextLine1()
	{
		textLine1 = new CTextField();
		textLine1.setBounds(10, yValues[0], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine1);
	}
	
	private void TextLine2()
	{
		textLine2 = new CTextField();
		textLine2.setBounds(10, yValues[1], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine2);
	}
	
	private void TextLine3()
	{
		textLine3 = new CTextField();
		textLine3.setBounds(10, yValues[2], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine3);
	}
	
	private void TextLine4()
	{
		textLine4 = new CTextField();
		textLine4.setBounds(10, yValues[3], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine4);
	}
	
	private void TextLine5()
	{
		textLine5 = new CTextField();
		textLine5.setBounds(10, yValues[4], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine5);
	}
	
	private void TextLine6()
	{
		textLine6 = new CTextField();
		textLine6.setBounds(10, yValues[5], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		add(textLine6);
	}

	private void WarningLabel()
	{
		warningLabel = new JLabel("Make sure to include \"Replacement Input\".");
		warningLabel.setBounds(10, CustomFrame.WORKPANEL_HEIGHT - 30, CustomFrame.WORKPANEL_WIDTH - 200, 20);
		warningLabel.setForeground(Color.RED);
		warningLabel.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(warningLabel, "Neon.ttf", 16);
		add(warningLabel);
	}

	private void ReplacementTextField()
	{
		replacementInputField = new CTextField("Replacement Input", BorderFactory.createLineBorder(Constants.PRIMARY_COLOR.darker(), 1));
		replacementInputField.setBounds(10, 50, 250, 30);
		replacementInputField.setBackground(null);
		replacementInputField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyReleased(KeyEvent e) {
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
		
		replacementInputField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if(replacementInputField.getText().equals("Replacement Input"))
				{
					replacementInputField.setText(null);
					replacementInputField.setForeground(Color.WHITE);
					warningLabel.setText("Make sure to include \"\".");
				}
			}
			@Override
			public void focusLost(FocusEvent e) {}
		});
		add(replacementInputField);
	}

}
