package eco.typer.threads;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This class triggers the typer to stop if the client cannot be found
 */

public class GameClientChecker implements Runnable
{

	private Thread worker;
	private boolean running;
	//---
	private int gameClient;
	
	public GameClientChecker(int gameClientID)
	{
		running = false;
		this.gameClient = gameClientID;
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
			try
			{
				Thread.sleep(2000);
			}
			catch (Exception e)
			{
				Utils.writeErrorReport(e, 140);
			}
			if(!running)
			{
				break;
			}
			try
			{
				String line;
				Process p;
				if(Constants.OPERATING_SYSTEM.equals("Windows"))
				{
					p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
				}
				else
				{
					p = Runtime.getRuntime().exec("ps -e");
				}
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				boolean isGameClientRunning = false;
				while ((line = input.readLine()) != null)
				{
					if(!running)
					{
						break;
					}
					if(this.gameClient == 0) //User Selected Rs3 Client
					{
						if(line.contains("rs2client") || line.contains("rs2client.exe") || line.contains("JavaAppLaunch") || line.contains("JagexLauncher.exe"))
						{
							isGameClientRunning = true;
						}
					}
					else if (this.gameClient == 1) //User Selected OSRS Client
					{
						if(line.contains("JavaAppLaunch") || line.contains("JagexLauncher.exe"))
						{
							isGameClientRunning = true;
						}
					}
					else if (this.gameClient == 2) //User Selected RuneLite Client
					{
						if(line.contains("RuneLite") || line.contains("RuneLite.exe"))
						{
							isGameClientRunning = true;
						}
					}
			    }
				if(isGameClientRunning == false)
				{
					if(CustomFrame.textMode == 0)
					{
						Constants.localTyper.stop();
				    	Constants.localTyper.unsetCompact();
						Constants.localTyper.audio = null;
						Constants.localTyper.backgroundTimer = null;
						Constants.localTyper.clientChecker = null;
					}
					else if(CustomFrame.textMode == 1)
					{
						Constants.remoteTyper.stop();
				    	Constants.remoteTyper.unsetCompact();
						Constants.remoteTyper.audio = null;
						Constants.remoteTyper.backgroundTimer = null;
						Constants.remoteTyper.clientChecker = null;
					}
					else
					{
						//TODO FOR SEQUENCE MODE
					}
			    	JOptionPane.showMessageDialog(Settings.frame, "The Game Client was detected as closed.\nTyper was stopped.", "Game Closed", JOptionPane.WARNING_MESSAGE);
			    }
				input.close();
			}
			catch (Exception e)
			{
				Utils.writeErrorReport(e, 150);
			}
		}
	}

}
