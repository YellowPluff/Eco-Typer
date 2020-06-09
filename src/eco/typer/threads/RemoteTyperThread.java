package eco.typer.threads;

import java.awt.GraphicsDevice;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
import java.io.File;
//import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.audio.Audio;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_frame.CustomFrameTitleBar;
import eco.typer.sub_panels.CompactModePanel;
import eco.typer.tools.RobotClass;
import eco.typer.tools.TypoSetup;
import eco.typer.tools.Utils;

/**
 * 
 * @author dakota
 * This class is a handful and could probably be cleaned, but IDC. It's clean enough.
 * It gets launched when the typer is started, from the settings panel.
 */

public class RemoteTyperThread implements Runnable
{
	
	private Thread worker;
	public boolean running;
	private RobotClass bot;
	public boolean breakTyperThread;
	//---
	public Audio audio;
	public GameClientChecker clientChecker;
	public RemoteModeWebsiteReader websiteUpdateReader;
	public CompactModePanel compactPanel;
	public Timer backgroundTimer;
	//---
	private int EXEC_TIME;
	private int TEXT_DELAY_BETWEEN_LINES;
	private int TEXT_DELAY_BETWEEN_LINES_RANDOM_MIN;
	private int TEXT_DELAY_BETWEEN_LINES_RANDOM_MAX;
	private int REFRESH_TIME;
	private int BEGINNING_DELAY;
	private boolean SMART_TYPOS;
	private int TYPO_MAX_CHANCE;
	private String SCREENSHOT_1;
	private int SCREENSHOT_2 = 0;
	//---
	private String messageOrder;
	private String[] textsToType;
	private String refereshInterval;
	private String executionTime;
	private String lineBreakTime;
	private int typingSpeed;
	private String finalCommand;
	private String screenshotIntervals;
	private boolean isScreenshotTimestampsOn;
	private String typoChance;
	private boolean isFinishedAlarmOn;
	private String finishedAlarmSound;
	private boolean is24HourClockOn;
	//private String watchClient;

	public RemoteTyperThread(String messageOrderIn,
							 String[] textsToTypeIn,
							 String refereshIntervalIn,
							 String executionTimeIn,
							 String lineBreakTimeIn,
							 int typingSpeedIn,
							 String finalCommandIn,
							 String screenshotIntervalsIn,
							 boolean isScreenshotTimestampsOnIn,
							 String typoChanceIn,
							 boolean isFinishedAlarmOnIn,
							 String finishedAlarmSoundIn,
							 boolean is24HourClockOnIn/*,
							 String watchClientIn*/)
	{
		this.messageOrder = messageOrderIn;
		this.textsToType = textsToTypeIn;
		this.refereshInterval = refereshIntervalIn;
		this.executionTime = executionTimeIn;
		this.lineBreakTime = lineBreakTimeIn;
		this.typingSpeed = typingSpeedIn;
		this.finalCommand = finalCommandIn;
		this.screenshotIntervals = screenshotIntervalsIn;
		this.isScreenshotTimestampsOn = isScreenshotTimestampsOnIn;
		this.typoChance = typoChanceIn;
		this.isFinishedAlarmOn = isFinishedAlarmOnIn;
		this.finishedAlarmSound = finishedAlarmSoundIn;
		this.is24HourClockOn = is24HourClockOnIn;
		//this.watchClient = watchClientIn;
		setDefaults();
	}

	public void start()
	{
		//spawnClientWatcherIfNeccessary();
		checkScreenshot_DIR();
		spawnWebsiteUpdateReader();
		this.bot = new RobotClass();
		this.breakTyperThread = false;
		worker = new Thread(this);
		worker.start();
	}

	public void stop()
	{
		this.running = false;
		this.breakTyperThread = true;
		this.websiteUpdateReader.stop();
		if(this.audio != null)	this.audio.stopAudio();
		if(this.timeRem > 0 && this.audio != null)	this.audio.closeSequencer();
		if(this.clientChecker != null)	this.clientChecker.stop();
	}
	
	public void pause()
	{
		this.backgroundTimer.stop();
		this.running = false;
		this.bot.releaseShift();
		this.BEGINNING_DELAY = 5000;
	}
	
	public void resume()
	{
		this.backgroundTimer.start();
		this.running = true;
	}

	@Override
	public void run()
	{
		running = true;
		startTimer();
		int spamLineCounter = 0;
		if(this.messageOrder.equals(Constants.MESSAGE_ORDERS[1]))
		{
			spamLineCounter = this.textsToType.length - 1;
		}
		else
		{
			spamLineCounter = 0;
		}
		while(true)
		{
			System.out.println("Running: " + running);	/* THIS LINE IS ABSOLUTELY VITAL!
														Apparently Java is smart enough to break out of the loop if nothing happens...
														So when user presses PAUSE and running goes to false, break out of loop and then
														pressing RESUME would do nothing */
			if(running)
			{
				if(this.BEGINNING_DELAY != 0)
				{
					sleep(this.BEGINNING_DELAY);
					this.BEGINNING_DELAY = 0;
				}
				
				boolean isNewTexts = false;
				String[] oldTexts = this.textsToType;
				this.textsToType = CustomFrame.remoteDisplay.spamLinesDisplay.getText().split("\n");
				if(oldTexts.length != this.textsToType.length)
				{
					isNewTexts = true;
				}
				
				String line;
				
				if(isNewTexts == true)
				{
					line = this.textsToType[0];
				}
				else
				{
					line = this.textsToType[spamLineCounter];
				}
				
				if(this.messageOrder.equals(Constants.MESSAGE_ORDERS[1]))
				{
					if(isNewTexts == true)
					{
						spamLineCounter = this.textsToType.length - 1;
					}
					else
					{
						spamLineCounter--;
						if(spamLineCounter == -1)
						{
							spamLineCounter = this.textsToType.length - 1;
						}
					}
				}
				else
				{
					if(isNewTexts == true && spamLineCounter != 0)
					{
						spamLineCounter = 0;
					}
					else
					{
						spamLineCounter++;
						if(spamLineCounter == this.textsToType.length)
						{
							spamLineCounter = 0;
							if(this.messageOrder.equals(Constants.MESSAGE_ORDERS[2]))
							{
								Random rgen = new Random();
								for(int i = 0; i < this.textsToType.length; i++)
								{
									int randomPosition = rgen.nextInt(this.textsToType.length);
									String temp = this.textsToType[i];
									this.textsToType[i] = this.textsToType[randomPosition];
									this.textsToType[randomPosition] = temp;
								}
							}
						}
					}
				}
				for(char c : line.toCharArray())
				{
					if(running)
					{
						//User wants a typo
						if(this.TYPO_MAX_CHANCE != -1
							&& Utils.getRandomNumberBetweenInclusive(0, 100) < this.TYPO_MAX_CHANCE
							&& c != ' ')
						{
							if(this.SMART_TYPOS)
							{
								if(Settings.allowTyping == true)
								{
									this.bot.typeCharacter(TypoSetup.getSmartTypo(c));
								}
							}
							else
							{
								if(Settings.allowTyping == true)
								{
									this.bot.typeCharacter(TypoSetup.getRandomTypo());
								}
							}
						}
						//User does not want a typo
						else
						{
							if(Settings.allowTyping == true)
							{
								this.bot.typeCharacter(c);
							}
						}
						sleep(this.typingSpeed);
					}
				}
				if(running && Settings.allowTyping == true)
				{
					this.bot.pressEnter();
				}
				if(this.TEXT_DELAY_BETWEEN_LINES == -1)
				{
					int timeToSleep = Utils.getRandomNumberBetweenInclusive(this.TEXT_DELAY_BETWEEN_LINES_RANDOM_MIN, this.TEXT_DELAY_BETWEEN_LINES_RANDOM_MAX);
					sleep(timeToSleep);
				} else
				{
					sleep(this.TEXT_DELAY_BETWEEN_LINES);
				}
			}
			if(this.breakTyperThread) //Prevents this thread from multiplying
			{
				break;
			}
		}
	}
	
	int timeRem, timeElap;
	private void startTimer()
	{
		timeRem = this.EXEC_TIME;
		timeElap = 0;
		if(this.is24HourClockOn)
		{
			Constants.remoteTyper.compactPanel.startTime.setText(" Start Time: " + (DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now())));
			Constants.remoteTyper.compactPanel.endTime.setText(" End Time: " + (DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now().plusSeconds(this.EXEC_TIME))));
		}
		else
		{
			String am_pm = "";
			
			Calendar cal = Calendar.getInstance();
			//result = testCondition ? trueValue : falseValue
			am_pm = cal.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
			Constants.localTyper.compactPanel.startTime.setText(" Start Time: " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + " " + am_pm);
			
			cal.add(Calendar.SECOND, this.EXEC_TIME);
			am_pm = cal.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
			Constants.localTyper.compactPanel.endTime.setText(" End Time: " + cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + " " + am_pm);
		}
		backgroundTimer = new Timer(1000, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(!running)
				{
					((Timer)e.getSource()).stop();
				}
				if(timeRem <= 0)
				{
					Constants.remoteTyper.stop();
					if(Constants.remoteTyper.clientChecker != null)
					{
						Constants.remoteTyper.clientChecker.stop();
					}
					unsetCompact();
					executeFinalCommand();
					((Timer)e.getSource()).stop();
					if(Constants.remoteTyper.audio != null)
					{
						Constants.remoteTyper.audio.playAudio();
						JOptionPane.showMessageDialog(Settings.frame, "Eco Typer has finished.", "Alarm", JOptionPane.WARNING_MESSAGE);
						Constants.remoteTyper.audio.stopAudio();
						Constants.remoteTyper.audio.closeSequencer();
					}
				}
				Constants.remoteTyper.compactPanel.remainingTime.setText(" Remaining Time: " + Integer.toString(timeRem / 3600) + " : " + Integer.toString((timeRem % 3600) / 60) + " : " + Integer.toString(timeRem % 60));
				Constants.remoteTyper.compactPanel.elapsedTime.setText(" Elapsed Time: " + Integer.toString(timeElap / 3600) + " : " + Integer.toString((timeElap % 3600) / 60) + " : " + Integer.toString(timeElap % 60));
				CustomFrameTitleBar.title.setText("Eco Typer" + " [ " + Integer.toString(timeRem / 3600) + " : " + Integer.toString((timeRem % 3600) / 60) + " : " + Integer.toString(timeRem % 60) + " ]");
				if(!running)
				{
					CustomFrameTitleBar.title.setText(Constants.FRAME_TITLE);
				}
				timeRem--;
				timeElap++;
				checkScreenshot();
			}

			private void checkScreenshot()
			{
				switch(Constants.remoteTyper.screenshotIntervals)
				{
				case "Screenshots: 15 Minutes":
					if(timeRem % 900 == 0)
						takeScreenshot();
					break;
				case "Screenshots: 30 Minutes":
					if(timeRem % 1800 == 0)
						takeScreenshot();
					break;
				case "Screenshots: 45 Minutes":
					if(timeRem % 2700 == 0)
						takeScreenshot();
					break;
				case "Screenshots: 1 Hour":
					if(timeRem % 3600 == 0)
						takeScreenshot();
					break;
				}
			}

			private void executeFinalCommand()
			{
				switch(Constants.OPERATING_SYSTEM)
				{
				case "Windows":
					switch(Constants.remoteTyper.finalCommand)
					{
					case "When Done:  Do Nothing":
						break;
					case "When Done:  Close Eco Typer":
						System.exit(0);
						break;
//					case "Close Selected Game Client":
//						if(!Constants.remoteTyper.watchClient.equals("Watch Client: Off"))
//							CloseSelectedGameClient();
//						break;
//					case "Close Selected Game Client and Eco":
//						if(!Constants.remoteTyper.watchClient.equals("Watch Client: Off"))
//							CloseSelectedGameClient();
//						System.exit(0);
//						break;
					case "When Done:  Logoff Computer":
						try
						{
							Process proc = Runtime.getRuntime().exec("shutdown -l -t 0");
							proc.waitFor();
						}
						catch (Exception e)
						{
							Utils.writeErrorReport(e, 250);
							System.exit(0);
						}
						break;
					case "When Done:  Restart Computer":
						try
						{
							Process proc = Runtime.getRuntime().exec("shutdown -r -t 0");
							proc.waitFor();
						}
						catch (Exception e)
						{
							Utils.writeErrorReport(e, 260);
							System.exit(0);
						}
						break;
					case "When Done:  Shutdown Computer":
						try
						{
							Process proc = Runtime.getRuntime().exec("shutdown -s -t 0");
							proc.waitFor();
						}
						catch (Exception e)
						{
							Utils.writeErrorReport(e, 270);
							System.exit(0);
						}
						break;
					}
					break;
				case "Mac":
				case "Linux":
					switch(Constants.remoteTyper.finalCommand)
					{
					case "When Done:  Do Nothing":
						break;
					case "When Done:  Close Eco Typer":
						System.exit(0);
						break;
//					case "Close Selected Game Client":
//						if(!Constants.remoteTyper.watchClient.equals("Watch Client: Off"))
//							CloseSelectedGameClient();
//						break;
//					case "Close Selected Game Client and Eco":
//						if(!Constants.remoteTyper.watchClient.equals("Watch Client: Off"))
//							CloseSelectedGameClient();
//						System.exit(0);
//						break;
					}
					break;
				default:
						
					break;
				}
			}

//			private void CloseSelectedGameClient()
//			{
//				try
//				{
//					Process proc;
//					if(Constants.OPERATING_SYSTEM.equals("Windows"))
//					{
//						proc = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
//					}
//					else
//					{
//						proc = Runtime.getRuntime().exec("ps -e");
//					}
//					BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
//					String line;
//					while ((line = input.readLine()) != null)
//					{
//						if(Constants.remoteTyper.watchClient.equals(Constants.GAME_CLIENTS[0])) //Send Command - Close Runescape 3
//						{
//							if(line.contains("rs2client") || line.contains("rs2client.exe") || line.contains("JavaAppLaunch") || line.contains("JagexLauncher.exe"))
//							{
//								killFoundProcess(line);
//							}
//						}
//						else if(Constants.remoteTyper.watchClient.equals(Constants.GAME_CLIENTS[1])) //Send Command - Close OSRS
//						{
//							if(line.contains("JavaAppLaunch") || line.contains("JagexLauncher.exe"))
//							{
//								killFoundProcess(line);
//							}
//						}
//						else if (Constants.remoteTyper.watchClient.equals(Constants.GAME_CLIENTS[2])) //Send Command - Close RuneLite
//						{
//							if(line.contains("RuneLite") || line.contains("RuneLite.exe"))
//							{
//								killFoundProcess(line);
//							}
//						}
//					}
//				}
//				catch (Exception e)
//				{
//					Utils.writeErrorReport(e, 280);
//				}
//			}

//			private void killFoundProcess(String line)
//			{
//				try
//				{
//					line = line.trim();
//					if(Constants.OPERATING_SYSTEM.equals("Windows"))
//					{
//						line = line.substring(line.indexOf(" "), line.length());
//						line = line.trim();
//					}
//					int pid = Integer.parseInt(line.substring(0, line.indexOf(" ")));
//					Process p;
//					if(Constants.OPERATING_SYSTEM.equals("Windows"))
//					{
//						p = Runtime.getRuntime().exec("taskkill /F /PID " + pid);
//					}
//					else
//					{
//						p = Runtime.getRuntime().exec("kill " + pid);
//					}
//					p.waitFor();
//				}
//				catch (Exception e)
//				{
//					Utils.writeErrorReport(e, 290);
//				}
//			}
    	});
		backgroundTimer.start();
	}
	
	private void checkScreenshot_DIR()
	{
		if(!Constants.remoteTyper.screenshotIntervals.equals("Screenshots: Off"))
		{
			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy  (HH mm ss)");
			SCREENSHOT_1 = dateFormat.format(new Date());
			File screenshotFolder = new File(Constants.SCREENSHOT_DIRECTORY + "/" + SCREENSHOT_1);
			screenshotFolder.mkdir();
		}
	}
	
	private void takeScreenshot()
	{
		while(new File(Constants.SCREENSHOT_DIRECTORY + "/" + SCREENSHOT_1 + "/" + SCREENSHOT_2 + ".png").exists())
		{
			SCREENSHOT_2++;
		}
		String fileName = Constants.SCREENSHOT_DIRECTORY + "/" + SCREENSHOT_1 + "/" + SCREENSHOT_2 + ".png";
		if(!Constants.remoteTyper.isScreenshotTimestampsOn)
		{
			Robot robot = null;
			try
			{
				robot = new Robot();
			}
			catch (Exception e)
			{
				Utils.writeErrorReport(e, 300);
			}
			
			Rectangle screenRect = new Rectangle(0, 0, 0, 0);
			for(GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
			{
				screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
			}
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			try
			{
				ImageIO.write(screenFullImage, "png", new File(fileName));
			}
			catch (Exception e)
			{
				Utils.writeErrorReport(e, 310);
			}
		}
		else
		{
			Utils.TimeStampScreenshot(fileName);
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
	
	private void setDefaults()
	{
		this.BEGINNING_DELAY = 5000;
		this.compactPanel = new CompactModePanel();
		formatExecutionTime();
		formatSpamInterval();
		formatTypoChance();
		formatRefreshInterval();
		setCompact();
		if(this.isFinishedAlarmOn == true)
		{
			if(this.finishedAlarmSound.equals("Alarm Sound"))
			{
				this.audio = new Audio("/data/audio/Simpsons.mid");
			}
			else
			{
				this.audio = new Audio("/data/audio/" + this.finishedAlarmSound + ".mid");
			}
		}
		CustomFrame.settingsPanel.startButton.setBackground(Constants.PRIMARY_COLOR);
	}
	
	private void setCompact()
	{
		if(Constants.OPERATING_SYSTEM.equals("Windows"))
		{
			Settings.frame.setLocation(0, 0);
		}
		else
		{
			Settings.frame.setLocation((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() - Constants.FRAME_WIDTH, 25);
		}
		Settings.frame.setSize(Constants.FRAME_WIDTH, 25);
		CustomFrame.updateDisplay(compactPanel);
		CustomFrame.leftSelectionMenu.hideAllIcons();
		CustomFrameTitleBar.mini.setVisible(false);
		CustomFrameTitleBar.exit.setVisible(false);
		CustomFrameTitleBar.pauseButton.setVisible(true);
		CustomFrameTitleBar.stopButton.setVisible(true);
	}
	
	public void unsetCompact()
	{
		Settings.frame.setSize(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		CustomFrameTitleBar.title.setText(Constants.FRAME_TITLE);
		CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		CustomFrame.leftSelectionMenu.unhideAllIcons();
		CustomFrameTitleBar.mini.setVisible(true);
		CustomFrameTitleBar.exit.setVisible(true);
		CustomFrameTitleBar.pauseButton.setVisible(false);
		CustomFrameTitleBar.stopButton.setVisible(false);
	}

//	private void spawnClientWatcherIfNeccessary()
//	{
//		checkScreenshot_DIR();
//		switch(this.watchClient)
//		{
//		case "Watch Client: Rs3":
//			this.clientChecker = new GameClientChecker(0);
//			this.clientChecker.start();
//			break;
//		case "Watch Client: OSRS":
//			this.clientChecker = new GameClientChecker(1);
//			this.clientChecker.start();
//			break;
//		case "Watch Client: RuneLite":
//			this.clientChecker = new GameClientChecker(2);
//			this.clientChecker.start();
//			break;
//		case "Watch Client: Off":
//			break;
//		default:
//			Utils.writeErrorReport(new IllegalStateException(), 330);
//			break;
//		}
//	}
	
	private void spawnWebsiteUpdateReader()
	{
		this.websiteUpdateReader = new RemoteModeWebsiteReader(this.REFRESH_TIME);
		this.websiteUpdateReader.start();
	}
	
	private void formatExecutionTime()
	{
		switch(this.executionTime) //Set in Seconds
		{
		case "1 Minute(s)":
			this.EXEC_TIME = 60 + 1;
			break;
		case "15 Minute(s)":
			this.EXEC_TIME = 900 + 1;
			break;
		case "30 Minute(s)":
			this.EXEC_TIME = 1800 + 1;
			break;
		case "45 Minute(s)":
			this.EXEC_TIME = 2700 + 1;
			break;
		case "1 Hour(s)":
			this.EXEC_TIME = 3600 + 1;
			break;
		case "2 Hour(s)":
			this.EXEC_TIME = 7200 + 1;
			break;
		case "Execution Time":
		case "Infinite":
			this.EXEC_TIME = Integer.MAX_VALUE;
			break;
		default:
			int hours = Integer.parseInt(this.executionTime.substring(0, 2));
			int minutes = Integer.parseInt(this.executionTime.substring(13, 15));
			this.EXEC_TIME = (60 * (60 * hours) + 60 * minutes) + 1;
			break;
		}
	}
	
	private void formatSpamInterval()
	{
		switch(this.lineBreakTime)
		{
		case "Line Break Time":
			this.TEXT_DELAY_BETWEEN_LINES = -1;
			this.TEXT_DELAY_BETWEEN_LINES_RANDOM_MIN = 3500;
			this.TEXT_DELAY_BETWEEN_LINES_RANDOM_MAX = 4500;
			break;
		case "0 Second(s)":
			this.TEXT_DELAY_BETWEEN_LINES = 100;
			break;
		case "1 Second(s)":
			this.TEXT_DELAY_BETWEEN_LINES = 1000;
			break;
		case "2 Second(s)":
			this.TEXT_DELAY_BETWEEN_LINES = 2000;
			break;
		case "4 Second(s)":
			this.TEXT_DELAY_BETWEEN_LINES = 4000;
			break;
		case "7 Second(s)":
			this.TEXT_DELAY_BETWEEN_LINES = 7000;
			break;
		default:
			String timeDelay = this.lineBreakTime.replace(" Second(s)", "");
			if(timeDelay.contains("-"))
			{
				this.TEXT_DELAY_BETWEEN_LINES = -1;
				this.TEXT_DELAY_BETWEEN_LINES_RANDOM_MIN = 1000 * Integer.parseInt(timeDelay.substring(0, 2));
				this.TEXT_DELAY_BETWEEN_LINES_RANDOM_MAX = 1000 * Integer.parseInt(timeDelay.substring(timeDelay.length() - 2, timeDelay.length()));
			} else
			{
				this.TEXT_DELAY_BETWEEN_LINES = 1000 * Integer.parseInt(timeDelay);
			}
			break;

		}
	}
	
	private void formatTypoChance()
	{
		switch(this.typoChance)
		{
		case "Smart Typos: 1%":
			this.SMART_TYPOS = true;
			this.TYPO_MAX_CHANCE = 1;
			break;
		case "Smart Typos: 2%":
			this.SMART_TYPOS = true;
			this.TYPO_MAX_CHANCE = 2;
			break;
		case "Smart Typos: 3%":
			this.SMART_TYPOS = true;
			this.TYPO_MAX_CHANCE = 3;
			break;
		case "Random Typos: 1%":
			this.SMART_TYPOS = false;
			this.TYPO_MAX_CHANCE = 1;
			break;
		case "Random Typos: 2%":
			this.SMART_TYPOS = false;
			this.TYPO_MAX_CHANCE = 2;
			break;
		case "Random Typos: 3%":
			this.SMART_TYPOS = false;
			this.TYPO_MAX_CHANCE = 3;
			break;
		default:
			this.SMART_TYPOS = false;
			this.TYPO_MAX_CHANCE = -1;
			break;
		}
	}
	
	private void formatRefreshInterval()
	{
		switch(this.refereshInterval)
		{
		case "15 Seconds":
			this.REFRESH_TIME = 15;
			break;
		case "30 Seconds":
			this.REFRESH_TIME = 30;
			break;
		default:
		case "45 Seconds":
			this.REFRESH_TIME = 45;
			break;
		case "1 Minute":
			this.REFRESH_TIME = 60;
			break;
		}
	}

}
