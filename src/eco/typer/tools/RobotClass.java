package eco.typer.tools;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import eco.typer.Settings.Constants;

public class RobotClass {

	private Robot bot;
	
	public RobotClass()
	{
		try
		{
			this.bot = new Robot();
		}
		catch (Exception e)
		{
			Utils.writeErrorReport(e, 340);
		}
	}
	
	public void typeCharacter(char c)
	{
		if (Constants.shiftCases.contains(c))
		{
			bot.keyPress(KeyEvent.VK_SHIFT);
			bot.delay(35);
		}
		int keyStroke = Constants.mapStrokes.get(Character.toLowerCase(c));
		try
		{
		    bot.keyPress(keyStroke);
		    bot.keyRelease(keyStroke);
		}
		catch (Exception e1)
		{
			Utils.writeErrorReport(e1, 350);
		}
		if (Constants.shiftCases.contains(c))
		{
			bot.delay(35);
			bot.keyRelease(KeyEvent.VK_SHIFT);
		}
	}
	
	public void pressEnter()
	{
		bot.keyPress(KeyEvent.VK_ENTER);
		bot.delay(35);
		bot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public void releaseShift()
	{
		bot.keyRelease(KeyEvent.VK_SHIFT);
	}
	
}
