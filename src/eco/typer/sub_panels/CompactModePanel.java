package eco.typer.sub_panels;

import eco.typer.custom_frame.CPanel;
import eco.typer.custom_objects.CTextField;

/**
 * @author dakota
 * This panel is the display while running
 */

@SuppressWarnings("serial")
public class CompactModePanel extends CPanel
{
	
	public CTextField startTime, endTime, elapsedTime, remainingTime;
	
	public CompactModePanel()
	{
		super("");
		
		TimerBoxes();
	}

	private void TimerBoxes()
	{
		startTime = new CTextField(" Start Time:", "dark", false);
		startTime.setBounds(10, 10, 190, 30);
		startTime.setBorder(null);
		startTime.setEditable(false);
		add(startTime);
		
		endTime = new CTextField(" End Time:", "dark", false);
		endTime.setBounds(210, 10, 190, 30);
		endTime.setBorder(null);
		endTime.setEditable(false);
		add(endTime);
		
		elapsedTime = new CTextField(" Elapsed Time:", "dark", false);
		elapsedTime.setBounds(410, 10, 190, 30);
		elapsedTime.setBorder(null);
		elapsedTime.setEditable(false);
		add(elapsedTime);
		
		remainingTime = new CTextField(" Remaining Time:", "light", false);
		remainingTime.setBounds(410, 50, 190, 30);
		remainingTime.setBorder(null);
		remainingTime.setEditable(false);
		add(remainingTime);
	}

}
