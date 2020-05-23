package eco.typer.tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CustomFrame;

/**
 * @author dakota
 * This class contains functions that don't belong anywhere else.
 * Note* This is a bad habit and should be cleaned up.
 */

public class Utils
{
	
	public static String getOperatingSystem()
	{
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.contains("window"))
		{
			return "Windows";
		}
		else if(osName.contains("mac"))
		{
			return "Mac";
		}
		else
		{
			return "Linux";
		}
	}
	
	public static int getRandomNumberBetweenInclusive(int min,
													  int max)
	{
		return new Random().nextInt(max - min + 1) + min;
	}
	
	public static void openWebpage(String url)
	{
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
	    {
	        try
	        {
	            desktop.browse(new URL(url).toURI());
	        }
	        catch (Exception e)
	        {
	        	Utils.writeErrorReport(e, 360);
	        }
	    }
	}
	
	public static void setFont(Component c,
							   String fontName,
							   float size)
	{
		try
		{
			Font font = Font.createFont(0,  Utils.class.getResource("/data/fonts/" + fontName).openStream());
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			font = font.deriveFont(size);
			c.setFont(font);
		}
		catch(Exception e)
		{
			Utils.writeErrorReport(e, 370);
		}
	}

	public static ImageIcon getImage(String imageName)
	{
		return new ImageIcon(Utils.class.getResource("/data/img/" + imageName));
	}

	@SuppressWarnings("unused")
	public static void writeErrorReport(Exception e, int errorCode)
	{
		if(Settings.displayErrorsInConsole == true)
		{
			e.printStackTrace();
		}
		else
		{
			if(errorCode == 500) {
				File[] files = Constants.HOST_FILES_DIRECTORY.listFiles();
				for(File f : files)
				{
					if(f.isFile() && f.toString().contains(".cfg"))
					{
						f.delete();
					}
				}
				JOptionPane.showMessageDialog(Settings.frame, "Due to an incompatability in the new release, all your configs were outdated and deleted.", "Eco Typer Error", JOptionPane.ERROR_MESSAGE);
			}
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String sStackTrace = sw.toString(); // stack trace as a string
			
			File desktopFile = new File(System.getProperty("user.home") + "/Desktop/Eco Typer Error " + System.currentTimeMillis() + ".txt");
			try
			{
				PrintWriter writer = new PrintWriter(desktopFile, "UTF-8");
				writer.println("Error Code: " + errorCode);
				writer.println(sStackTrace);
				writer.close();
				JOptionPane.showMessageDialog(Settings.frame, "An error file has been generated on your desktop.\nPlease send it to the developer ASAP.", "Eco Typer Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
				JOptionPane.showMessageDialog(Settings.frame, "Writing to file error" + desktopFile, "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
	}
	
	public static FrameProperities loadCache()
	{
		if(!Constants.CONFIGURATIONS.contains("---Edit Profiles---"))
		{
			Constants.CONFIGURATIONS.add("---Edit Profiles---");
		}
		if(!Constants.SAVED_LOCAL_FILES.contains("---Edit Files---"))
		{
			Constants.SAVED_LOCAL_FILES.add("---Save/Delete Files---");
			Constants.SAVED_LOCAL_FILES.add("---Clear Text Box---");
		}
		if(!Constants.SAVED_SEQUENCE_FILES.contains("---Edit Files---"))
		{
			Constants.SAVED_SEQUENCE_FILES.add("---Save/Delete Files---");
			Constants.SAVED_SEQUENCE_FILES.add("---Clear Text Box---");
		}
		if(!Constants.TEXT_PAGES.contains("---Edit Pages---"))
		{
			Constants.TEXT_PAGES.add("---Edit Pages---");
		}
		
		File[] files = Constants.HOST_FILES_DIRECTORY.listFiles();
		for(File f : files)
		{
			if(f.isFile() && f.toString().contains(".cfg"))
			{
				Constants.CONFIGURATIONS.add(f.getName().replace(".cfg", ""));
			}
			else if(f.isFile() && f.toString().contains(".eco"))
			{
				Constants.SAVED_LOCAL_FILES.add(f.getName().replaceAll(".eco", ""));
			}
			else if(f.isFile() && f.toString().contains(".epg"))
			{
				Constants.TEXT_PAGES.add(f.getName().replaceAll(".epg", ""));
			}
			else if(f.isFile() && f.toString().contains(".seq"))
			{
				Constants.SAVED_SEQUENCE_FILES.add(f.getName().replaceAll(".seq", ""));
			}
		}
		
		FrameProperities fp = null;
		if(new File(System.getProperty("user.home") + "/Eco Typer/Cache").exists())
		{
			fp = deserializeData(fp);
		}
		else
		{
			fp = new FrameProperities();
			fp.color = Color.GREEN.darker();
			fp.totalProfit = 0.0;
			fp.notepadText = "";
			fp.profitCounter = new ArrayList<MoneyCounter>();
			fp.alertMessage = Constants.ALERT_MESSAGE;
			fp.seeAlert = true;
			fp.openPoint = new Point(50, 50);
			fp.mouseHoverMode = true;
		}
		return fp;
	}
	
	public static void serializeFrameData()
	{
		FrameProperities fp = new FrameProperities();
		fp.color = Constants.PRIMARY_COLOR_NEW;
		fp.totalProfit = Constants.totalProfit;
		fp.notepadText = Constants.NOTEPAD_TEXT;
		fp.profitCounter = Constants.profitCounter;
		fp.alertMessage = Constants.ALERT_MESSAGE;
		fp.seeAlert = Constants.SHOW_ALERT;
		fp.openPoint = Constants.OPEN_POINT;
		fp.mouseHoverMode = Constants.mouseHoverMode;
		
		try
		{
			FileOutputStream outStream = new FileOutputStream(System.getProperty("user.home") + "/Eco Typer/Cache");
			ObjectOutputStream out = new ObjectOutputStream(outStream);
			out.writeObject(fp);
			out.close();
			outStream.close();
			System.out.println("Serialized Data now stored in " + System.getProperty("user.home") + "/Eco Typer/Cache");
		}
		catch (Exception e)
		{
			Utils.writeErrorReport(e, 380);
		}
	}
	
	private static FrameProperities deserializeData(FrameProperities fp)
	{
		FileInputStream fileIn = null;
		ObjectInputStream in = null;
		try
		{
			fileIn = new FileInputStream(System.getProperty("user.home") + "/Eco Typer/Cache");
			in = new ObjectInputStream(fileIn);
			fp = (FrameProperities) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (Exception e)
		{
			try //All streams should be closed prior to data manipulation
			{
				in.close();
				fileIn.close();
			}
			catch (Exception e1)
			{
				Utils.writeErrorReport(e1, 390);
			}
			Constants.splashWindow.dispose();
			File eco_typer_folder = new File(System.getProperty("user.home") + "/Eco Typer/");
			deleteDirectory(eco_typer_folder);
			JOptionPane.showMessageDialog(null, "We're sorry, but the newest version of Eco Typer isn't compatiable with the cache of the older version.\nThe older version will now be deleted. Please re-start Eco Typer.", "Cache Reading Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		return fp;
	}

	//I have no idea how this function works.. but it looks cool.
	private static void deleteDirectory(File eco_typer_folder)
	{
		try
		{
			Files.walk(Paths.get(System.getProperty("user.home") + "/Eco Typer/")).map(Path::toFile)
					.sorted((o1, o2) -> -o1.compareTo(o2)).forEach(File::delete);
		}
		catch (Exception e1)
		{
			Utils.writeErrorReport(e1, 400);
		}
	}

	public static void updateSettings(String selected)
	{
		File fileName = new File(Constants.HOST_FILES_DIRECTORY + "/" + selected + ".cfg");
		Stack<String> settings = new Stack<String>();
		BufferedReader reader;
		try
		{
			reader = new BufferedReader(new FileReader(fileName));
			String line;
			while((line = reader.readLine()) != null)
			{
				settings.push(line);
			}
			updateSettingsBasedOnLoadedConfiguration(settings);
			reader.close();
		}
		catch (Exception e1)
		{
			Utils.writeErrorReport(e1, 410);
		}
	
	}

	private static void updateSettingsBasedOnLoadedConfiguration(Stack<String> settings)
	{
		//I'm able to pop them in order because I wrote them backwards when initially saved.
		CustomFrame.settingsPanel.lineBreakTime.setText(settings.pop());
		CustomFrame.settingsPanel.execTime.setText(settings.pop());
		CustomFrame.settingsPanel.finishCommand.setText(settings.pop());
		CustomFrame.localDisplay.messageOrder.setText(settings.pop());
		CustomFrame.settingsPanel.typeSpeedSlider.setValue(Integer.parseInt(settings.pop().substring(0, 2)));
		CustomFrame.settingsPanel.typos.setText(settings.pop().replace(" /char", ""));
		CustomFrame.settingsPanel.screenshotTime.setText("Screenshots: " + settings.pop());
		CustomFrame.settingsPanel.timeStamps.setTick(Boolean.parseBoolean(settings.pop()));
		CustomFrame.settingsPanel.pinToTop.setTick(Boolean.parseBoolean(settings.pop()));
		CustomFrame.settingsPanel.finishedAlarm.setTick(Boolean.parseBoolean(settings.pop()));
		CustomFrame.settingsPanel.alarmSelect.setText(settings.pop());
		CustomFrame.settingsPanel.twentyFourClock.setTick(Boolean.parseBoolean(settings.pop()));
		//CustomFrame.settingsPanel.watchGameClient.setText("Watch Client: " + settings.pop());
		
		if(CustomFrame.settingsPanel.finishedAlarm.isTicked())
		{
			CustomFrame.settingsPanel.alarmSelect.setVisible(true);
			CustomFrame.settingsPanel.typeSpeedSlider.setBounds(210, 210, 390, 110);
		}
		else
		{
			CustomFrame.settingsPanel.alarmSelect.setVisible(false);
			CustomFrame.settingsPanel.typeSpeedSlider.setBounds(210, 170, 390, 110);
		}
		/*
		 * I use hard coded values here because I can't assume that the alarm started as ticked to OFF
		 */
		
	}

	public static void TimeStampScreenshot(String fileName)
	{
		try
		{
			Robot robot = null;
			try
			{
				robot = new Robot();
			}
			catch (Exception e)
			{
				//This shouldn't be caught.. but just incase.
				Utils.writeErrorReport(e, 420);
			}
			Rectangle screenRect = new Rectangle(0, 0, 0, 0);
			for(GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
			{
				screenRect = screenRect.union(gd.getDefaultConfiguration().getBounds());
			}
			DateFormat dateForm = new SimpleDateFormat("HH:mm:ss");
			BufferedImage bi = robot.createScreenCapture(screenRect);
			Graphics2D graphics = bi.createGraphics();
			Font font = new Font("Sans Serif", Font.BOLD, 60);
			graphics.setFont(font);
			graphics.setColor(Constants.PRIMARY_COLOR);
			graphics.drawString("Eco Typer - Made By Dakota", 10, (int) ((screenRect.getHeight() / 2) - 50));
			graphics.drawString("Time - " + dateForm.format(new Date()), 10, (int) (screenRect.getHeight() / 2));
			bi.flush();
			ImageIO.write(bi, "png", new File(fileName));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.err.println("Something went wrong when trying to take a Time Stamp");
		}
	}
	
	public static Color getRandColor()
	{
		Color[] cols = {Color.GREEN, Color.LIGHT_GRAY, Color.PINK, Color.BLUE, Color.MAGENTA, Color.WHITE, Color.YELLOW};
		return cols[Utils.getRandomNumberBetweenInclusive(0, cols.length - 1)];
	}
	
}