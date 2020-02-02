package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CTextArea;
import eco.typer.custom_objects.CTextField;
import eco.typer.listeners.SUL;
import eco.typer.tools.MoneyCounter;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the profit/loss display
 */

@SuppressWarnings("serial")
public class ProfitCounterDisplay extends CPanel
{
	
	public ProfitCounterDisplay()
	{
		super("Profit Counter");
		
		InputFields();
		ShowValues();
		ShowTotal();
	}
	
	private void ShowTotal()
	{
		JLabel profitLabel = new JLabel();
		profitLabel.setHorizontalAlignment(JLabel.CENTER);
		profitLabel.setBounds(10, CustomFrame.WORKPANEL_HEIGHT - 40, 270, 30);
		profitLabel.setFont(new Font("Cosmic Sans MS", Font.BOLD, 20));
		if(Constants.totalProfit > 0)
		{
			profitLabel.setForeground(Color.GREEN);
			profitLabel.setText("+" + String.format("%.2f", Constants.totalProfit) + "m");
			profitLabel.setBorder(BorderFactory.createEtchedBorder(Color.GREEN.darker(), Constants.BACKGROUND_COLOR));
		}
		else if(Constants.totalProfit < 0)
		{
			profitLabel.setForeground(Color.RED);
			profitLabel.setText(String.format("%.2f", Constants.totalProfit) + "m");
			profitLabel.setBorder(BorderFactory.createEtchedBorder(Color.RED.darker(), Constants.BACKGROUND_COLOR));
		}
		else
		{
			profitLabel.setForeground(Color.WHITE);
			profitLabel.setText(String.format("%.2f", Constants.totalProfit) + "m");
			profitLabel.setBorder(BorderFactory.createEtchedBorder(Color.WHITE.darker(), Constants.BACKGROUND_COLOR));
		}
		add(profitLabel);
	}

	private void ShowValues()
	{
		CTextArea addTextArea = new CTextArea(getValue());
		addTextArea.setBackground(Constants.BACKGROUND_COLOR.brighter());
		addTextArea.setForeground(Color.WHITE);
		addTextArea.setEditable(false);
		addTextArea.setHighlighter(null);
		addTextArea.setBorder(BorderFactory.createRaisedBevelBorder());
		JScrollPane addTextAreaScroller = new JScrollPane(addTextArea);
		addTextAreaScroller.getVerticalScrollBar().setUI(new CScrollBar());
		addTextAreaScroller.setBounds(CustomFrame.WORKPANEL_WIDTH - 280, 50, 270, CustomFrame.WORKPANEL_HEIGHT - 80);
		add(addTextAreaScroller);
		
		CButton clearProfitCounter = new CButton("Delete Logs", Constants.BACKGROUND_COLOR.brighter());
		clearProfitCounter.setBounds(CustomFrame.WORKPANEL_WIDTH - 150, CustomFrame.WORKPANEL_HEIGHT - 30, 140, 20);
		clearProfitCounter.addMouseListener(new SUL("Would you like to delete your profit log history?"));
		clearProfitCounter.addActionListener(e ->
		{
			//Settings.frame.setState(JFrame.ICONIFIED);
			int confirmDelete = JOptionPane.showConfirmDialog(Settings.frame, "Would you like to delete your profit log history?", "Profit Logs", JOptionPane.YES_NO_OPTION);
			//Settings.frame.setState(JFrame.NORMAL);
			if(confirmDelete == 0)
			{
				Constants.totalProfit = 0;
				Constants.profitCounter = new ArrayList<MoneyCounter>();
				CustomFrame.updateDisplay(new ProfitCounterDisplay());
				Utils.serializeFrameData();
			}
		});
		add(clearProfitCounter);
	}

	private String getValue()
	{
		String returnValue = "";
		for(MoneyCounter c : Constants.profitCounter)
		{
			returnValue += c.getSign() + " " + c.getAmount() + "m -> " + c.getDescription() + "\n";
		}
		return returnValue;
	}

	private void InputFields()
	{
		JLabel amountLabel = new JLabel("Amount (in millions)");
		amountLabel.setBounds(10, 50, 200, 30);
		Utils.setFont(amountLabel, "Neon.ttf", 18);
		amountLabel.setForeground(Constants.PRIMARY_COLOR.brighter());
		add(amountLabel);
		
		CTextField addAmountInput = new CTextField();
		addAmountInput.setBounds(10, 80, 270, 30);
		addAmountInput.setForeground(Color.WHITE);
		addAmountInput.addMouseListener(new SUL("How much money(in millions) are you adding or losing?"));
		doubleFormatField(addAmountInput);
		add(addAmountInput);
		
		JLabel descriptionLabel = new JLabel("Description");
		descriptionLabel.setBounds(10, 130, 100, 30);
		Utils.setFont(descriptionLabel, "Neon.ttf", 18);
		descriptionLabel.setForeground(Constants.PRIMARY_COLOR.brighter());
		add(descriptionLabel);
		
		CTextField addDescriptionInput = new CTextField();
		addDescriptionInput.setBounds(10, 160, 270, 30);
		addDescriptionInput.setForeground(Color.WHITE);
		addDescriptionInput.addMouseListener(new SUL("Why are you adding or losing this money?"));
		add(addDescriptionInput);
		
		CButton addProfitButton = new CButton("Add(+) Profit", Color.GREEN.darker());
		addProfitButton.setBounds(10, 200, 150, 20);
		addProfitButton.addMouseListener(new SUL("Add this money to your profits."));
		addProfitButton.addActionListener(e ->
		{
			if(!addAmountInput.getText().isEmpty() && !addDescriptionInput.getText().isEmpty())
			{
				changeProfit('+', Double.parseDouble(addAmountInput.getText()), addDescriptionInput.getText());
			}
			else
			{
				Toolkit.getDefaultToolkit().beep();
			}
		});
		add(addProfitButton);
		
		CButton subtractProfitButton = new CButton("Subtract(-) Profit", Color.RED.darker());
		subtractProfitButton.setBounds(10, 225, 150, 20);
		subtractProfitButton.addMouseListener(new SUL("Subtract this money from your profits."));
		subtractProfitButton.addActionListener(e ->
		{
			if(!addAmountInput.getText().isEmpty() && !addDescriptionInput.getText().isEmpty())
			{
				changeProfit('-', Double.parseDouble(addAmountInput.getText()), addDescriptionInput.getText());
			}
			else
			{
				Toolkit.getDefaultToolkit().beep();
			}
		});
		add(subtractProfitButton);
	}

	private void changeProfit(char sign,
							  double amount,
							  String desc)
	{
		Constants.profitCounter.add(new MoneyCounter(sign, amount, desc));
		if(sign == '+')
		{
			Constants.totalProfit += amount;
		}
		else
		{
			Constants.totalProfit -= amount;
		}
		Utils.serializeFrameData();
		CustomFrame.updateDisplay(new ProfitCounterDisplay());
	}

	public void Title()
	{
		JLabel title = new JLabel("Profit Counter");
		title.setBounds(10, 10, CustomFrame.WORKPANEL_WIDTH - 10, 30);
		title.setForeground(Color.WHITE);
		Utils.setFont(title, "Neon.ttf", 30);
		add(title);
	}
	
	private void doubleFormatField(CTextField field)
	{
		field.addKeyListener(new KeyAdapter()
		{
            public void keyTyped(KeyEvent e)
            {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9')) && character != '.')
                {
                	e.consume();
                }
            }
        });
	}

}
