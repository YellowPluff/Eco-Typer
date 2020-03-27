package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import eco.typer.Settings.Constants;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.listeners.SUL;

/**
 * @author dakota
 * This panel the panel to enter a custom run time or delay
 */

@SuppressWarnings("serial")
public class ExecutionTimeAndDelayTimeEditor extends CPanel
{
	
	//0 = Execution Time (Custom Time)
	//1 = Line Break Time (Custom Delay Time)
	//2 = Wait button (SequenceTextDisplay.java)
	//3 = Random Delay Time
	public ExecutionTimeAndDelayTimeEditor(int i)
	{
		super("Time Controller");
		
//		Title();
		if(i == 0)
		{
			ShowExecutionTime();
		}
		else if(i == 1)
		{
			ShowSpamInterval(i);
		}
		else if(i == 2)
		{
			ShowSpamInterval(i);
		} else if(i == 3)
		{
			ShowRandomLineBreakTime();
		}
	}
	
	private void ShowRandomLineBreakTime()
	{
		SpinnerCircularListModel minSecondsModel = new SpinnerCircularListModel(Constants.TIME_SECONDS);
		JSpinner minWaitSpinner = new JSpinner(minSecondsModel);
		minWaitSpinner.setBounds(10, 50, 200, 30);
		setColors(minWaitSpinner);
		add(minWaitSpinner);
		
		SpinnerCircularListModel maxSecondsModel = new SpinnerCircularListModel(Constants.TIME_SECONDS);
		JSpinner maxWaitSpinner = new JSpinner(maxSecondsModel);
		maxWaitSpinner.setBounds(10, 90, 200, 30);
		setColors(maxWaitSpinner);
		add(maxWaitSpinner);
		
		CButton setTime = new CButton("Set Delay");
		setTime.setBounds(10, 120, 150, 20);
		setTime.addMouseListener(new SUL("How long do you want to RANDOMLY wait between spams?"));
		setTime.addActionListener(e ->
		{
			int minValue = Integer.parseInt(minWaitSpinner.getValue().toString().replace(" Second(s)", ""));
			int maxValue = Integer.parseInt(maxWaitSpinner.getValue().toString().replace(" Second(s)", ""));
			if(minValue <= maxValue)
			{
				CustomFrame.settingsPanel.lineBreakTime.setText(minWaitSpinner.getValue() + " - " + maxWaitSpinner.getValue());
			}
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		});
		add(setTime);
	}

	private void ShowSpamInterval(int i)
	{
		
		SpinnerCircularListModel secondsModel = new SpinnerCircularListModel(Constants.TIME_SECONDS);
		JSpinner secondsSpinner = new JSpinner(secondsModel);
		secondsSpinner.setBounds(10, 50, 200, 30);
		setColors(secondsSpinner);
		add(secondsSpinner);
		
		CButton setDelay = new CButton("Set Delay");
		setDelay.setBounds(10, 80, 150, 20);
		setDelay.addMouseListener(new SUL("How long do you want to wait between spams?"));
		setDelay.addActionListener(e ->
		{
			if(i == 1)
			{
				CustomFrame.settingsPanel.lineBreakTime.setText(secondsSpinner.getValue() + "");
			}
			else if(i == 2)
			{
				CustomFrame.sequenceDisplay.spamLinesDisplay.append("\n" + "Wait: " + secondsSpinner.getValue() + "");
			}
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		});
		add(setDelay);
		
	}

	private void ShowExecutionTime()
	{
		
		SpinnerCircularListModel hoursModel = new SpinnerCircularListModel(Constants.TIME_HOURS);
		JSpinner hoursSpinner = new JSpinner(hoursModel);
		hoursSpinner.setBounds(10, 50, 200, 30);
		setColors(hoursSpinner);
		add(hoursSpinner);
		
		SpinnerCircularListModel minutesModel = new SpinnerCircularListModel(Constants.TIME_MINUTES);
		JSpinner minutesSpinner = new JSpinner(minutesModel);
		minutesSpinner.setBounds(10, 90, 200, 30);
		setColors(minutesSpinner);
		add(minutesSpinner);
		
		CButton setTime = new CButton("Set Time");
		setTime.setBounds(10, 120, 150, 20);
		setTime.addMouseListener(new SUL("How long do you want the typer to run for?"));
		setTime.addActionListener(e ->
		{
			CustomFrame.settingsPanel.execTime.setText(hoursSpinner.getValue() + " : " + minutesSpinner.getValue());
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		});
		add(setTime);
		
	}
	
	private void setColors(JSpinner secondsSpinner)
	{
		JComponent editor = secondsSpinner.getEditor();
		for(int i = 0; i < editor.getComponentCount(); i++)
		{
			Component c = editor.getComponent(i);
			if(c instanceof JTextField)
			{
				((JTextField) c).setEditable(false);
				c.setForeground(Color.WHITE);
				c.setBackground(Constants.BACKGROUND_COLOR);
				((JTextField) c).setBorder(null);
				c.setFont(new Font("Cosmic Sans MS", Font.BOLD, 13));
				((JTextField) c).setHorizontalAlignment(JTextField.CENTER);
			}
		}
	}

}

@SuppressWarnings("serial")
class SpinnerCircularListModel extends SpinnerListModel
{
	public SpinnerCircularListModel(Object[] items)
	{
		super(items);
	}
	
	public Object getNextValue()
	{
		List<?> list = getList();
		int index = list.indexOf(getValue());
		
		index = (index >= list.size() - 1) ? 0 : index + 1;
		return list.get(index);
	}
	
	public Object getPreviousValue()
	{
		List<?> list = getList();
		int index = list.indexOf(getValue());
		
		index = (index <= 0) ? list.size() - 1 : index - 1;
		return list.get(index);
	}
}