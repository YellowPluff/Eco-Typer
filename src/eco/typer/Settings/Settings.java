package eco.typer.Settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.UIManager;

import eco.typer.custom_frame.CustomFrame;
import eco.typer.tools.FrameProperities;
import eco.typer.tools.TypoSetup;
import eco.typer.tools.Utils;
//import eco.typer.tools.Website;

/**
 * 
 * @author dakota
 * This class is the START of the program
 */

public class Settings
{
	
	public static final String VERSION = "10-01-2019";
	public static boolean liteMode = false;
	
	public static boolean speedUpBoot = false;				//For developing, make false for release
	public static boolean displayBootScreen = true;			//Display loading screen
	public static boolean printWebsiteData = false;			//Toggle for printing data read from website
	public static boolean allowTyping = false;				//Toggle for allowing keyboard to type
	public static boolean displayErrorsInConsole = true;	//Display errors in console rather than .txt to desktop
	
	/**
	 * FOR RELEASE, THE ABOVE 4 VALUES SHOULD BE
	 * false
	 * true
	 * false
	 * true
	 * false
	 * 
	 * Known Issues:
	 * 
	 * - CompactModePanel is instanced for every typer mode, despite all 3 modes using the same style panel. This needs to be refactored to be more effecient
	 * - When the click of a button opens another panel, the button gets stuck on that dark color. A temp fix was put into place, but this is only temp.
	 */
	
	public static CustomFrame frame;
	public static void main(String[] args)
	{
		
		parseArgs(args);
		
		if(speedUpBoot || !displayBootScreen || printWebsiteData || !allowTyping || displayErrorsInConsole) {
			System.err.println("RELEASE FLAGS ARE NOT SET CORRECTLY!!!");
		}
		
		Constants.splashWindow = loadingCacheFrame();
		//System.out.println(VERSION);
		
		try
		{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch (Exception e)
		{
			Utils.writeErrorReport(e, 120);
		}
		
		checkSerializedData();
		initializeMaps();
		
		UIManager.put("ComboBox.selectionForeground", Constants.PRIMARY_COLOR.brighter());
	    UIManager.put("ComboBox.selectionBackground", Constants.BACKGROUND_COLOR);
		
		frame = new CustomFrame();
		frame.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if((Constants.localTyper != null && Constants.localTyper.running)
					|| (Constants.remoteTyper != null && Constants.remoteTyper.running))
				{
					frame.setSize(Constants.FRAME_WIDTH, 140);
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if((Constants.localTyper != null && Constants.localTyper.running)
					|| (Constants.remoteTyper != null && Constants.remoteTyper.running))
				{
					frame.setSize(Constants.FRAME_WIDTH, 25);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e)
			{
				//Does nothing
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
		});
		
	}

	private static void initializeMaps()
	{
		char[] shiftC = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '{', '}', '|', ':', '<', '>', '?', '+', '"'};
		Constants.shiftCases = new ArrayList<Character>();
		for(char c : shiftC)
		{
			Constants.shiftCases.add(c);
		}
		Constants.mapStrokes = new HashMap<>();
		Constants.mapStrokes.put('a', KeyEvent.VK_A);
		Constants.mapStrokes.put('b',  KeyEvent.VK_B);
		Constants.mapStrokes.put('c',  KeyEvent.VK_C);
		Constants.mapStrokes.put('d',  KeyEvent.VK_D);
		Constants.mapStrokes.put('e',  KeyEvent.VK_E);
		Constants.mapStrokes.put('f',  KeyEvent.VK_F);
		Constants.mapStrokes.put('g',  KeyEvent.VK_G);
		Constants.mapStrokes.put('h',  KeyEvent.VK_H);
		Constants.mapStrokes.put('i',  KeyEvent.VK_I);
		Constants.mapStrokes.put('j',  KeyEvent.VK_J);
		Constants.mapStrokes.put('k',  KeyEvent.VK_K);
		Constants.mapStrokes.put('l',  KeyEvent.VK_L);
		Constants.mapStrokes.put('m',  KeyEvent.VK_M);
		Constants.mapStrokes.put('n',  KeyEvent.VK_N);
		Constants.mapStrokes.put('o',  KeyEvent.VK_O);
		Constants.mapStrokes.put('p',  KeyEvent.VK_P);
		Constants.mapStrokes.put('q',  KeyEvent.VK_Q);
		Constants.mapStrokes.put('r',  KeyEvent.VK_R);
		Constants.mapStrokes.put('s',  KeyEvent.VK_S);
		Constants.mapStrokes.put('t',  KeyEvent.VK_T);
		Constants.mapStrokes.put('u',  KeyEvent.VK_U);
		Constants.mapStrokes.put('v',  KeyEvent.VK_V);
		Constants.mapStrokes.put('w',  KeyEvent.VK_W);
		Constants.mapStrokes.put('x',  KeyEvent.VK_X);
		Constants.mapStrokes.put('y',  KeyEvent.VK_Y);
		Constants.mapStrokes.put('z',  KeyEvent.VK_Z);
		Constants.mapStrokes.put('`', KeyEvent.VK_BACK_QUOTE);
		Constants.mapStrokes.put('0', KeyEvent.VK_0);
		Constants.mapStrokes.put('1', KeyEvent.VK_1);
		Constants.mapStrokes.put('2', KeyEvent.VK_2);
		Constants.mapStrokes.put('3', KeyEvent.VK_3);
		Constants.mapStrokes.put('4', KeyEvent.VK_4);
		Constants.mapStrokes.put('5', KeyEvent.VK_5);
		Constants.mapStrokes.put('6', KeyEvent.VK_6);
		Constants.mapStrokes.put('7', KeyEvent.VK_7);
		Constants.mapStrokes.put('8', KeyEvent.VK_8);
		Constants.mapStrokes.put('9', KeyEvent.VK_9);
		Constants.mapStrokes.put('-', KeyEvent.VK_MINUS);
		Constants.mapStrokes.put('=', KeyEvent.VK_EQUALS);
		Constants.mapStrokes.put('\t', KeyEvent.VK_TAB);
		Constants.mapStrokes.put('\n', KeyEvent.VK_ENTER);
		Constants.mapStrokes.put('[', KeyEvent.VK_OPEN_BRACKET);
		Constants.mapStrokes.put(']', KeyEvent.VK_CLOSE_BRACKET);
		Constants.mapStrokes.put('\\', KeyEvent.VK_BACK_SLASH);
		Constants.mapStrokes.put(';', KeyEvent.VK_SEMICOLON);
		Constants.mapStrokes.put('\'', KeyEvent.VK_QUOTE);
		Constants.mapStrokes.put('\"', KeyEvent.VK_QUOTEDBL);
		Constants.mapStrokes.put(',', KeyEvent.VK_COMMA);
		Constants.mapStrokes.put('.', KeyEvent.VK_PERIOD);
		Constants.mapStrokes.put('/', KeyEvent.VK_SLASH);
		Constants.mapStrokes.put(' ', KeyEvent.VK_SPACE);
		//All the items that require shift are below
		Constants.mapStrokes.put('~', KeyEvent.VK_BACK_QUOTE);
		Constants.mapStrokes.put('!', KeyEvent.VK_1);
		Constants.mapStrokes.put('@', KeyEvent.VK_2);
		Constants.mapStrokes.put('#', KeyEvent.VK_3);
		Constants.mapStrokes.put('$', KeyEvent.VK_4);
		Constants.mapStrokes.put('%', KeyEvent.VK_5);
		Constants.mapStrokes.put('^', KeyEvent.VK_6);
		Constants.mapStrokes.put('&', KeyEvent.VK_7);
		Constants.mapStrokes.put('*', KeyEvent.VK_8);
		Constants.mapStrokes.put('(', KeyEvent.VK_9);
		Constants.mapStrokes.put(')', KeyEvent.VK_0);
		Constants.mapStrokes.put('_', KeyEvent.VK_MINUS);
		Constants.mapStrokes.put('{', KeyEvent.VK_OPEN_BRACKET);
		Constants.mapStrokes.put('}', KeyEvent.VK_CLOSE_BRACKET);
		Constants.mapStrokes.put('|', KeyEvent.VK_BACK_SLASH);
		Constants.mapStrokes.put(':', KeyEvent.VK_SEMICOLON);
		Constants.mapStrokes.put('<', KeyEvent.VK_COMMA);
		Constants.mapStrokes.put('>', KeyEvent.VK_PERIOD);
		Constants.mapStrokes.put('?', KeyEvent.VK_SLASH);
		Constants.mapStrokes.put('+', KeyEvent.VK_EQUALS);
		Constants.mapStrokes.put('"', KeyEvent.VK_QUOTE);
		//-----------------
		TypoSetup.initSmartTypos();
		TypoSetup.initRandomTypos();
	}
	private static void checkDir()
	{
		if(!Constants.SCREENSHOT_DIRECTORY.exists())
		{
			Constants.SCREENSHOT_DIRECTORY.mkdirs();
		}
		if(!Constants.HOST_FILES_DIRECTORY.exists())
		{
			showAlerts();
			Constants.HOST_FILES_DIRECTORY.mkdirs();
//			makeAltarPage();							//Keeping this around for reference
		}
	}
//	public static void makeAltarPage() {
//		String altarFile = Constants.HOST_FILES_DIRECTORY + "/Altar.epg";
//		try {
//			FileWriter writer = new FileWriter(altarFile);
//			writer.write("Name" + "\n");
//			writer.write("[ Name ] Free Perfect Juju Sips [ Name ]" + "\n");
//			writer.write("[ Name ] All The Training Essentials [ Name ]" + "\n");
//			writer.write("[ Name ] ><> Prayer Party <>< [ Name ]" + "\n");
//			writer.write("[ Name ]   <<< .H.O.S.T >>>   [ Name ]" + "\n");
//			writer.write("[ Name ] Come Join My House [ Name ]" + "\n");
//			writer.write("The Current Host Is: Name" + "\n");
//			writer.close();
//		} catch (Exception e) {
//			Utils.writeErrorReport(e, 130);
//		}
//	}

	private static void showAlerts()
	{
		Constants.splashWindow.setVisible(false);
		String welcomeMessage = "Welcome To Eco Typer:" + "\n"
				+ "We're very happy to see you choose Eco Typer for your automated typer." + "\n"
				+ "Unfortunately some great things need to be restricted. Eco Typer is restricted due to Java only supporting an english keyboard layout." + "\n"
				+ "Below are directions for how to set your keyboard for ";
		if(Constants.OPERATING_SYSTEM.equals("Windows"))
		{
			welcomeMessage = welcomeMessage + "Windows" + "\n\n"
					+ "Step 1. Open Settings" + "\n"
					+ "Step 2. Go to Time and Langauge" + "\n"
					+ "Step 3. Scroll down to Region and Language" + "\n"
					+ "Step 4. Make sure Languages is to set English (United States)";
		}
		else if(Constants.OPERATING_SYSTEM.equals("Mac"))
		{
			welcomeMessage = welcomeMessage + "MacOS" + "\n\n"
					+ "Step 1. Open System Preferences" + "\n"
					+ "Step 2. Go to Keyboard" + "\n"
					+ "Step 3. Click on the Dictation tab" + "\n"
					+ "Step 4. Make sure Language is English (United States)";
		}
		else
		{
			welcomeMessage = welcomeMessage + "Linux" + "\n\n"
					+ "We've detected you're running Linux. Unfortunaly we couldn't cover every Linux distro so we suggest googling directions " + "\n"
					+ "for your choice of distro.";
		}
    	JOptionPane.showMessageDialog(Constants.splashWindow, welcomeMessage, "Welcome", JOptionPane.INFORMATION_MESSAGE);
    	
		if(Constants.OPERATING_SYSTEM.equals("Mac"))
		{
	    	String message = "Important:" + "\n"
					+ "For all people running Mac OS, there's an aditional step you need to follow." + "\n\n"
					+ "Step 1. Open System Preferences" + "\n"
					+ "Step 2. Go to Security and Privacy" + "\n"
					+ "Step 3. Scroll down to Accessibility" + "\n"
					+ "Step 4. Make sure \"Jar Launcher\" is ticked" + "\n"
					+ "*note, you might have to click the lock box in the bottom left and enter your password" + "\n\n"
					+ "Why do you have to do this?" + "\n"
					+ "Out of the box Mac OS doesn't allow 3rd party applications to have Accessibility settings turned on, " + "\n"
					+ "but due to the nature of this program, Eco Typer requires it to be enabled.";
	    	JOptionPane.showMessageDialog(Constants.splashWindow, message, "Mac OS", JOptionPane.INFORMATION_MESSAGE);
		}
		Constants.splashWindow.setVisible(true);
	}
	
	private static void checkSerializedData()
	{
		//Website webReader = new Website();
		//Constants.UPDATE_DESCRIPITION = webReader.getUpdateDesc();
		//Constants.isUpdate = webReader.checkForUpdate();
		Constants.isUpdate = -1;	//This FORCES welcome screen to say "Running Most Up To Date Version"
		//Constants.updateLink = webReader.getUpdateLink();
		Constants.OPERATING_SYSTEM = Utils.getOperatingSystem();
		if(Constants.OPERATING_SYSTEM.equals("Windows")) //This works because Utils.getOperatingSystem(); returns "Windows"
		{
			//Constants.FINISHING_COMMAND_OPTIONS = new String[] {"Do Nothing", "Close Eco Typer", "Close Selected Game Client", "Close Selected Game Client and Eco", "Logoff Computer", "Restart Computer", "Shutdown Computer"};
			Constants.FINISHING_COMMAND_OPTIONS = new String[] {"Do Nothing", "Close Eco Typer", "Logoff Computer", "Restart Computer", "Shutdown Computer"};
		}
		else
		{
			//Constants.FINISHING_COMMAND_OPTIONS = new String[] {"Do Nothing", "Close Eco Typer", "Close Selected Game Client", "Close Selected Game Client and Eco"};
			Constants.FINISHING_COMMAND_OPTIONS = new String[] {"Do Nothing", "Close Eco Typer"};
		}
		//Constants.ALERT_MESSAGE = webReader.getAlert();
		Constants.ALERT_MESSAGE = "Hello everyone,\n\nFirstly, I would like to thank everyone who has supported this project over the last four years. However, my time with it is coming to an end. Eco Typer started as a small project aimed at adding a timer to Garys Hood Auto Typer, and a hobby, but has outgrown me. Throughout development of Eco Typer I have learned a lot and had a lot of fun doing so. Tons of time and excitement went into working on it, and as a developer I will always cherish this work. Still, it is time to let it go. It will always hold a special place in my heart as my first big project. I'm glad that my work was able to help those who used it and it's been an honor working with those who gave me feedback on how to improve it. Thank you everyone for your support. With that, this is goodbye to Eco Typer.\n\nThank you,\nDakota\n\n\r\n" + 
				"As of January 1, 2020, Eco Typer is no longer in active development. Eco Typer is hosted on Github @ https://github.com/YellowPluff/Eco-Typer\n\nIn an effort to keep this running as long as possible, the following was changed:\n1. Any code connecting it to the internet was removed, which includes:\n   - Checking for new update on launch\n   - Remote mode\n2. Removed freeware watermark in Settings. Eco Typer will always be free.\n3. Removed watch client feature";
		
		if(Settings.printWebsiteData)
		{
			System.out.println("Website Link => " + Constants.TEXTFILE_LINK);
			System.out.println("Update Description. => " + Constants.UPDATE_DESCRIPITION);
			System.out.println("Is there an update? => " + Constants.isUpdate);
			System.out.println("Update Link => " + Constants.updateLink);
			System.out.println("Operating System => " + Constants.OPERATING_SYSTEM);
			System.out.println("Alert Message => " + Constants.ALERT_MESSAGE);
			System.out.println("Is there a new Alert? => " + Constants.isAlert);
			System.out.println();
		}
		
		checkDir();
		FrameProperities fp = Utils.loadCache();
		Constants.PRIMARY_COLOR = fp.color;
		Constants.PRIMARY_COLOR_NEW = Constants.PRIMARY_COLOR;
		Constants.totalProfit = fp.totalProfit;
		Constants.NOTEPAD_TEXT = fp.notepadText;
		Constants.profitCounter = fp.profitCounter;
		Constants.mouseHoverMode = fp.mouseHoverMode;
//		if(!Constants.ALERT_MESSAGE.equals(fp.alertMessage))
//		{
//			Constants.isAlert = true;
//		}
//		else
//		{
//			Constants.isAlert = false;
//		}
		Constants.SHOW_ALERT = fp.seeAlert;
		Constants.OPEN_POINT = fp.openPoint;
		if(Settings.liteMode)
		{
			Constants.mouseHoverMode = false;
			Constants.FRAME_TITLE = "Eco Typer Lite";
		}
	}
	
	public static JLabel splashWindowText;
	private static JWindow loadingCacheFrame()
	{
		JWindow window = new JWindow();
		window.setLayout(new BorderLayout());
		window.setSize(610, 350);
		window.setBackground(new Color(0,0,0,0));
		JLabel bg = new JLabel(Utils.getImage("Eco_Typer_Splash_2.png"));
		bg.setLayout(null);
		splashWindowText = new JLabel();
		splashWindowText.setForeground(Color.BLACK);
		splashWindowText.setBounds(0, window.getHeight() - 15, window.getWidth(), 15);
		bg.add(splashWindowText);
		window.add(bg);
		window.setLocationRelativeTo(null);
		if(!Settings.speedUpBoot && Settings.displayBootScreen)	//If speedUpBoot is false
		{
			window.setVisible(true);
		}
		window.setAlwaysOnTop(true);
		return window;
	}
	
	private static void parseArgs(String[] args) {
		if(args.length > 0)
		{
			ArrayList<String> flags = new ArrayList<String>();
			for(String s : args)
			{
				if(!s.equals("-h") && !s.equals("-light") && !s.equals("-speed") && !s.equals("-splash") && !s.equals("-website") && !s.equals("-typing") && !s.equals("-errors"))
				{
					System.out.println();
					System.out.println(s + " >> invalid flag. Use -h for more information.");
					System.exit(0);
				}
				flags.add(s);
			}
			
			if(flags.contains("-h"))
			{
				System.out.println();
				System.out.println("-light : Will enable light mode");
				System.out.println("-speed : Will enable speed up boot");
				System.out.println("-splash : Will disable initial loading screen");
				System.out.println("-website : Will print website data to console");
				System.out.println("-typing : Will disable typer from using the keyboard");
				System.out.println("-errors : Will display errors in console instead of .txt on desktop");
				System.exit(0);
			}
			if(flags.contains("-light"))
			{
				Settings.liteMode = true;
			}
			if(flags.contains("-speed"))
			{
				Settings.speedUpBoot = true;
			}
			if(flags.contains("-splash"))
			{
				Settings.displayBootScreen = false;
			}
			if(flags.contains("-website"))
			{
				Settings.printWebsiteData = true;
			}
			if(flags.contains("-typing"))
			{
				Settings.allowTyping = false;
			}
			if(flags.contains("-errors"))
			{
				Settings.displayErrorsInConsole = true;
			}
		}
	}
}
