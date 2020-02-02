package eco.typer.pages;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import eco.typer.Settings.Constants;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CComboBox;
import eco.typer.custom_objects.CTextField;
import eco.typer.listeners.SUL;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This package of classes makes up the page capability
 */

@SuppressWarnings("serial")
public class PageDisplay extends CPanel
{
	
	private CTextField replacementInputField;
	private JLabel textLine1, textLine2, textLine3, textLine4, textLine5, textLine6;
	private JLabel[] labels = new JLabel[6];
	private CComboBox colorText1, colorText2, colorText3, colorText4, colorText5, colorText6;
	private CComboBox[] colors = new CComboBox[6];
	private CComboBox effectText1, effectText2, effectText3, effectText4, effectText5, effectText6;
	private CComboBox[] effects = new CComboBox[6];
	
	private File pageFile;
	private String[] values;
	private int[] yValues = new int[6];
	
	public PageDisplay(String pageName)
	{
		super(pageName);
		this.pageFile = new File(Constants.HOST_FILES_DIRECTORY + "/" + pageName + ".epg");
		values = readInFile();
		
		int c = 110;
		for(int i = 0; i < 6; i++)
		{
			yValues[i] = c;
			c += 40;
		}
		
		ReplacementField();
		TextLine1();
		TextLine2();
		TextLine3();
		TextLine4();
		TextLine5();
		TextLine6();
		AddTextButton();
	}

	private void TextLine1()
	{
		colorText1 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText1.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[0], 133, 30);
		colorText1.setMaximumRowCount(12);
		colorText1.setVisible(false);
		colors[0] = colorText1;
		add(colorText1);
		
		effectText1 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText1.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[0], 133, 30);
		effectText1.setVisible(false);
		effects[0] = effectText1;
		add(effectText1);
		
		textLine1 = new JLabel(values[1]);
		textLine1.setBounds(10, yValues[0], 310, 30);
		textLine1.setForeground(Color.DARK_GRAY.brighter());
		//textLine1.setHorizontalAlignment(JLabel.CENTER);
		textLine1.addMouseListener(new AddingTextPanelListener(textLine1, values[1], colorText1, effectText1));
		Utils.setFont(textLine1, "Neon.ttf", 16);
		labels[0] = textLine1;
		add(textLine1);
	}
	
	private void TextLine2()
	{
		colorText2 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText2.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[1], 133, 30);
		colorText2.setMaximumRowCount(12);
		colorText2.setVisible(false);
		colors[1] = colorText2;
		add(colorText2);
		
		effectText2 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText2.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[1], 133, 30);
		effectText2.setVisible(false);
		effects[1] = effectText2;
		add(effectText2);
		
		textLine2 = new JLabel(values[2]);
		textLine2.setBounds(10, yValues[1], 310, 30);
		textLine2.setForeground(Color.DARK_GRAY.brighter());
//		textLine2.setHorizontalAlignment(JLabel.CENTER);
		textLine2.addMouseListener(new AddingTextPanelListener(textLine2, values[2], colorText2, effectText2));
		Utils.setFont(textLine2, "Neon.ttf", 16);
		labels[1] = textLine2;
		add(textLine2);
	}
	
	private void TextLine3()
	{
		colorText3 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText3.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[2], 133, 30);
		colorText3.setMaximumRowCount(12);
		colorText3.setVisible(false);
		colors[2] = colorText3;
		add(colorText3);
		
		effectText3 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText3.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[2], 133, 30);
		effectText3.setVisible(false);
		effects[2] = effectText3;
		add(effectText3);
		
		textLine3 = new JLabel(values[3]);
		textLine3.setBounds(10, yValues[2], 310, 30);
		textLine3.setForeground(Color.DARK_GRAY.brighter());
//		textLine3.setHorizontalAlignment(JLabel.CENTER);
		textLine3.addMouseListener(new AddingTextPanelListener(textLine3, values[3], colorText3, effectText3));
		Utils.setFont(textLine3, "Neon.ttf", 16);
		labels[2] = textLine3;
		add(textLine3);
	}
	
	private void TextLine4()
	{
		colorText4 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText4.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[3], 133, 30);
		colorText4.setMaximumRowCount(12);
		colorText4.setVisible(false);
		colors[3] = colorText4;
		add(colorText4);
		
		effectText4 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText4.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[3], 133, 30);
		effectText4.setVisible(false);
		effects[3] = effectText4;
		add(effectText4);
		
		textLine4 = new JLabel(values[4]);
		textLine4.setBounds(10, yValues[3], 310, 30);
		textLine4.setForeground(Color.DARK_GRAY.brighter());
//		textLine4.setHorizontalAlignment(JLabel.CENTER);
		textLine4.addMouseListener(new AddingTextPanelListener(textLine4, values[4], colorText4, effectText4));
		Utils.setFont(textLine4, "Neon.ttf", 16);
		labels[3] = textLine4;
		add(textLine4);
	}
	
	private void TextLine5()
	{
		colorText5 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText5.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[4], 133, 30);
		colorText5.setMaximumRowCount(12);
		colorText5.setVisible(false);
		colors[4] = colorText5;
		add(colorText5);
		
		effectText5 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText5.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[4], 133, 30);
		effectText5.setVisible(false);
		effects[4] = effectText5;
		add(effectText5);
		
		textLine5 = new JLabel(values[5]);
		textLine5.setBounds(10, yValues[4], 310, 30);
		textLine5.setForeground(Color.DARK_GRAY.brighter());
//		textLine5.setHorizontalAlignment(JLabel.CENTER);
		textLine5.addMouseListener(new AddingTextPanelListener(textLine5, values[5], colorText5, effectText5));
		Utils.setFont(textLine5, "Neon.ttf", 16);
		labels[4] = textLine5;
		add(textLine5);
	}
	
	private void TextLine6()
	{
		colorText6 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText6.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[5], 133, 30);
		colorText6.setMaximumRowCount(12);
		colorText6.setVisible(false);
		colors[5] = colorText6;
		add(colorText6);
		
		effectText6 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText6.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[5], 133, 30);
		effectText6.setVisible(false);
		effects[5] = effectText6;
		add(effectText6);
		
		textLine6 = new JLabel(values[6]);
		textLine6.setBounds(10, yValues[5], 310, 30);
		textLine6.setForeground(Color.DARK_GRAY.brighter());
//		textLine6.setHorizontalAlignment(JLabel.CENTER);
		textLine6.addMouseListener(new AddingTextPanelListener(textLine6, values[6], colorText6, effectText6));
		Utils.setFont(textLine6, "Neon.ttf", 16);
		labels[5] = textLine6;
		add(textLine6);
	}

	private void ReplacementField()
	{
		replacementInputField = new CTextField(values[0], BorderFactory.createLineBorder(Constants.PRIMARY_COLOR.darker(), 1));
		replacementInputField.setBounds(10, 50, 250, 30);
		replacementInputField.setBackground(null);
		replacementInputField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if(replacementInputField.getText().equals(values[0]))
				{
					replacementInputField.setText(null);
					replacementInputField.setForeground(Color.WHITE);
				}
			}
			
			@Override
			public void focusLost(FocusEvent e)
			{
				//Does nothing
			}
		});
		add(replacementInputField);
	}

	private void AddTextButton()
	{
		CButton addTextButton = new CButton("Add Text");
		addTextButton.addMouseListener(new SUL("Add Text."));
		addTextButton.setBounds(410, CustomFrame.WORKPANEL_HEIGHT - 30, 190, 20);
		addTextButton.addActionListener(e -> {
			if(replacementInputField.getText().equals("") || replacementInputField.getText().equals(values[0]))
			{
				Toolkit.getDefaultToolkit().beep();
			}
			else
			{
				for(int i = 0; i < 6; i++)
				{
					if(labels[i].getForeground().getRed() == 255)
					{
						String result = formatTextForRuneScape((String) colors[i].getSelectedItem(), (String) effects[i].getSelectedItem(), labels[i].getText(), replacementInputField.getText());
						if(CustomFrame.localDisplay.spamLinesDisplay.getText().equals(""))
						{
							CustomFrame.localDisplay.spamLinesDisplay.setText(result);
						}
						else
						{
							CustomFrame.localDisplay.spamLinesDisplay.append("\n" + result);
						}
					}
				}
				CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
			}
		});
		add(addTextButton);
	}
	
	private String formatTextForRuneScape(String color,
										  String effect,
										  String text,
										  String replacementText)
	{
		color = color.replaceAll("\\s+", "");
		effect = effect.replaceAll("\\s+", "");
		String result = color.toLowerCase() + ":" + effect.toLowerCase() + ":" + text;
		result = result.replace("textcolor:", "");
		result = result.replace("texteffect:", "");
		result = result.replaceAll("default:", "");
		result = result.replaceAll("(?i)" + values[0], replacementText);
		return result;
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
			Utils.writeErrorReport(e1, 30);
		}
		return vals;
	}

}

class AddingTextPanelListener implements MouseListener
{

	JLabel label;
	String sulText;
	CComboBox effectBox;
	CComboBox colorBox;
	boolean isTicked;
	
	public AddingTextPanelListener(JLabel label,
								   String text,
								   CComboBox colorBox,
								   CComboBox effectBox)
	{
		this.label = label;
		this.sulText = text;
		this.effectBox = effectBox;
		this.colorBox = colorBox;
		this.isTicked = false;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(this.isTicked)
		{
			this.isTicked = false;
			this.effectBox.setVisible(false);
			this.colorBox.setVisible(false);
			this.label.setForeground(Color.DARK_GRAY.brighter());
		}
		else
		{
			this.isTicked = true;
			this.effectBox.setVisible(true);
			this.colorBox.setVisible(true);
			this.label.setForeground(Color.WHITE);
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

	@Override
	public void mouseEntered(MouseEvent e)
	{
		if(this.isTicked == false)
		{
			this.label.setForeground(Color.WHITE.darker());
		}
		CustomFrame.updateStatus(sulText);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if(this.isTicked == false)
		{
			this.label.setForeground(Color.DARK_GRAY.brighter());
		}
		CustomFrame.updateStatus("");
	}
	
}