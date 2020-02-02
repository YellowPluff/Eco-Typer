package eco.typer.sub_panels;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CComboBox;
import eco.typer.custom_objects.CTextField;
import eco.typer.listeners.SUL;
import eco.typer.pages.PageDisplay;
import eco.typer.pages.PageSelector;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel that controls the window you can add text in
 */

@SuppressWarnings("serial")
public class AddingTextPanel extends CPanel
{
	
	CComboBox pageSelector;
	
	CTextField textLine1,
				textLine2,
				textLine3,
				textLine4,
				textLine5,
				textLine6;
	CTextField[] textLines = new CTextField[6];
	
	CComboBox colorText1,
				colorText2,
				colorText3,
				colorText4,
				colorText5,
				colorText6;
	CComboBox[] colors = new CComboBox[6];
	
	CComboBox effectText1,
				effectText2,
				effectText3,
				effectText4,
				effectText5,
				effectText6;
	CComboBox[] effects = new CComboBox[6];
	
	int[] yValues = new int[6];
	
	public AddingTextPanel()
	{
		super("Text Configurations");
		
		int c = 110;
		for(int i = 0; i < 6; i++)
		{
			yValues[i] = c;
			c += 40;
		}
		
		PageSelector();
		
		TextLine1();
		TextLine2();
		TextLine3();
		TextLine4();
		TextLine5();
		TextLine6();
		AddTextButton();
		
		if(Settings.liteMode)
		{
			pageSelector.setVisible(false);
			
			for(CComboBox com : colors)
			{
				com.setVisible(false);
			}
			for(CComboBox com : effects)
			{
				com.setVisible(false);
			}
			textLine1.setBounds(10, yValues[0], CustomFrame.WORKPANEL_WIDTH - 20, 30);
			textLine2.setBounds(10, yValues[1], CustomFrame.WORKPANEL_WIDTH - 20, 30);
			textLine3.setBounds(10, yValues[2], CustomFrame.WORKPANEL_WIDTH - 20, 30);
			textLine4.setBounds(10, yValues[3], CustomFrame.WORKPANEL_WIDTH - 20, 30);
			textLine5.setBounds(10, yValues[4], CustomFrame.WORKPANEL_WIDTH - 20, 30);
			textLine6.setBounds(10, yValues[5], CustomFrame.WORKPANEL_WIDTH - 20, 30);
		}
		
	}

	private void PageSelector()
	{
		pageSelector = new CComboBox("Page Select", Constants.TEXT_PAGES);
		pageSelector.addMouseListener(new SUL("Use this to quickly change pages."));
		pageSelector.setBounds(CustomFrame.WORKPANEL_WIDTH - 200, 50, 190, 30);
		pageSelector.addActionListener(e -> {
			switch(pageSelector.getSelectedIndex())
			{
			case 0:
				CustomFrame.updateDisplay(new PageSelector());
				break;
			default:
				CustomFrame.updateDisplay(new PageDisplay(pageSelector.getSelectedItem() + ""));
				break;
			}
			pageSelector.setText("Page Select");
		});
		add(pageSelector);
	}

	private void TextLine1()
	{
		colorText1 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText1.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[0], 133, 30);
		colorText1.setMaximumRowCount(12);
		colors[0] = colorText1;
		add(colorText1);
		
		effectText1 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText1.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[0], 133, 30);
		effects[0] = effectText1;
		add(effectText1);
		
		textLine1 = new CTextField();
		textLine1.setBounds(10, yValues[0], 310, 30);
		//textLine1.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(textLine1, "Neon.ttf", 16);
		textLines[0] = textLine1;
		add(textLine1);
	}
	
	private void TextLine2()
	{
		colorText2 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText2.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[1], 133, 30);
		colorText2.setMaximumRowCount(12);
		colors[1] = colorText2;
		add(colorText2);
		
		effectText2 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText2.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[1], 133, 30);
		effects[1] = effectText2;
		add(effectText2);
		
		textLine2 = new CTextField();
		textLine2.setBounds(10, yValues[1], 310, 30);
		//textLine2.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(textLine2, "Neon.ttf", 16);
		textLines[1] = textLine2;
		add(textLine2);
	}
	
	private void TextLine3()
	{
		colorText3 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText3.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[2], 133, 30);
		colorText3.setMaximumRowCount(12);
		colors[2] = colorText3;
		add(colorText3);
		
		effectText3 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText3.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[2], 133, 30);
		effects[2] = effectText3;
		add(effectText3);
		
		textLine3 = new CTextField();
		textLine3.setBounds(10, yValues[2], 310, 30);
		//textLine3.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(textLine3, "Neon.ttf", 16);
		textLines[2] = textLine3;
		add(textLine3);
	}
	
	private void TextLine4()
	{
		colorText4 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText4.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[3], 133, 30);
		colorText4.setMaximumRowCount(12);
		colors[3] = colorText4;
		add(colorText4);
		
		effectText4 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText4.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[3], 133, 30);
		effects[3] = effectText4;
		add(effectText4);
		
		textLine4 = new CTextField();
		textLine4.setBounds(10, yValues[3], 310, 30);
		//textLine4.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(textLine4, "Neon.ttf", 16);
		textLines[3] = textLine4;
		add(textLine4);
	}
	
	private void TextLine5()
	{
		colorText5 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText5.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[4], 133, 30);
		colorText5.setMaximumRowCount(12);
		colors[4] = colorText5;
		add(colorText5);
		
		effectText5 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText5.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[4], 133, 30);
		effects[4] = effectText5;
		add(effectText5);
		
		textLine5 = new CTextField();
		textLine5.setBounds(10, yValues[4], 310, 30);
		//textLine5.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(textLine5, "Neon.ttf", 16);
		textLines[4] = textLine5;
		add(textLine5);
	}
	
	private void TextLine6()
	{
		colorText6 = new CComboBox("Text Color", Constants.TEXT_COLORS);
		colorText6.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, yValues[5], 133, 30);
		colorText6.setMaximumRowCount(12);
		colors[5] = colorText6;
		add(colorText6);
		
		effectText6 = new CComboBox("Text Effect", Constants.TEXT_EFFECT);
		effectText6.setBounds(CustomFrame.WORKPANEL_WIDTH - 140, yValues[5], 133, 30);
		effects[5] = effectText6;
		add(effectText6);
		
		textLine6 = new CTextField();
		textLine6.setBounds(10, yValues[5], 310, 30);
		//textLine6.setHorizontalAlignment(JLabel.CENTER);
		Utils.setFont(textLine6, "Neon.ttf", 16);
		textLines[5] = textLine6;
		add(textLine6);
	}
	
	private void AddTextButton()
	{
		
		CButton addTextButton = new CButton("Add Text");
		addTextButton.addMouseListener(new SUL("Add Text."));
		addTextButton.setBounds(410, CustomFrame.WORKPANEL_HEIGHT - 30, 190, 20);
		addTextButton.addActionListener(e ->
		{
			for(int i = 0; i < 6; i++)
			{
				if(!textLines[i].getText().equals(""))
				{
					String result = formatTextForRuneScape((String) colors[i].getSelectedItem(), (String) effects[i].getSelectedItem(), textLines[i].getText());
					if(CustomFrame.textMode == 0)
					{
						if(CustomFrame.localDisplay.spamLinesDisplay.getText().equals(""))
						{
							CustomFrame.localDisplay.spamLinesDisplay.setText(result);
						}
						else
						{
							CustomFrame.localDisplay.spamLinesDisplay.append("\n" + result);
						}
					}
					else if(CustomFrame.textMode == 2)
					{
						if(CustomFrame.sequenceDisplay.spamLinesDisplay.getText().equals(""))
						{
							CustomFrame.sequenceDisplay.spamLinesDisplay.setText("Text: " + result);
						}
						else
						{
							CustomFrame.sequenceDisplay.spamLinesDisplay.append("\n" + "Text: " + result);
						}
					}
				}
			}
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		});
		add(addTextButton);
		
	}

	private String formatTextForRuneScape(String color,
										  String effect,
										  String text)
	{
		color = color.replaceAll("\\s+", "");
		effect = effect.replaceAll("\\s+", "");
		String result = color.toLowerCase() + ":" + effect.toLowerCase() + ":" + text;
		result = result.replace("textcolor:", "");
		result = result.replace("texteffect:", "");
		result = result.replaceAll("default:", "");
		return result;
	}

}