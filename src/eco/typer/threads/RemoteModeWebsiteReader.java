package eco.typer.threads;

import javax.swing.JOptionPane;

import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_frame.CustomFrameTitleBar;
import eco.typer.tools.Utils;

public class RemoteModeWebsiteReader implements Runnable
{

	private Thread worker;
	private boolean running;
	//---
	private int refereshInterval;
	//---
	
	public RemoteModeWebsiteReader(int refereshIntervalIn)
	{
		running = false;
		this.refereshInterval = refereshIntervalIn;
	}
	
	public void start()
	{
		worker = new Thread(this);
		worker.start();
	}
	
	public void stop()
	{
		running = false;
	}

	@Override
	public void run()
	{
		running = true;
		while(running)
		{
			sleep(this.refereshInterval * 1000);
			//---
			System.out.println("Updating from website..");
			//---
			CustomFrame.remoteDisplay.readInWebsiteData(false);
			if(CustomFrame.remoteDisplay.getKeepTyping() == false)
			{
				CustomFrameTitleBar.stopButton.doClick();
				JOptionPane.showMessageDialog(Settings.frame, "Eco Typer was remotely stopped", "Eco Typer", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	private void sleep(int i)
	{
		try
		{
			Thread.sleep(i);
		}
		catch (Exception e)
		{
			Thread.currentThread().interrupt();
			Utils.writeErrorReport(e, 320);
		}
	}
	
}
