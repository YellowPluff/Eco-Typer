package eco.typer.Settings;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JWindow;

import eco.typer.threads.LocalTyperThread;
import eco.typer.threads.RemoteTyperThread;
import eco.typer.threads.SequenceTyperThread;
import eco.typer.tools.MoneyCounter;

/**
 * 
 * @author dakota
 * This class contains values that I constantly edit/refer to
 */

public class Constants
{
	
//	public static String[] TEXTFILE_LINKS = {"http://freetexthost.com/xfktm3c4rq",
//												"http://freetexthost.com/3xnxw6wbxu",
//												"http://freetexthost.com/x0dglwcd4h",
//												"http://freetexthost.com/dh33w3upw5",
//												"http://freetexthost.com/dvobfykk2k"};
	
	public static String TEXTFILE_LINK = "https://textuploader.com/10biz/raw";
	
	public static final int FRAME_WIDTH = 660; //610 655
	public static final int FRAME_HEIGHT = 450; //350
	public static final Color BORDER_COLOR = new Color(0, 0, 0);
	public static final Color BACKGROUND_COLOR = new Color(30, 30, 30);
	public static Color PRIMARY_COLOR = new Color(0, 178, 0);
	public static Color PRIMARY_COLOR_NEW;
	public static Point OPEN_POINT = new Point(50, 50);
	public static String FRAME_TITLE = "Eco Typer";
	public static String UPDATE_DESCRIPITION, WELCOME_MESSAGE, OPERATING_SYSTEM, ALERT_MESSAGE;
	public static boolean isAlert, SHOW_ALERT;
	public static int isUpdate;
	public static String updateLink;
	public static boolean mouseHoverMode;
	
	public static String HOST_NAME;
	public static final File SCREENSHOT_DIRECTORY = new File(System.getProperty("user.home") + "/Eco Typer/Typer Screenshots/");
	public static final File HOST_FILES_DIRECTORY = new File(System.getProperty("user.home") + "/Eco Typer/Host Files/");
	public static final int MAX_REGULAR_CHARACTERS = 68;
	public static final int MAX_NAME_CHARACTERS = 12;
	public static int SPAM_DELAY, EXECUTION_TIME, EXECUTION_HOUR, EXECUTION_MINUTE;
	
	public static ArrayList<String> CONFIGURATIONS = new ArrayList<String>();
	public static ArrayList<String> SAVED_LOCAL_FILES = new ArrayList<String>();
	public static ArrayList<String> SAVED_SEQUENCE_FILES = new ArrayList<String>();
	public static ArrayList<String> TEXT_PAGES = new ArrayList<String>();
	public static String[] TEXT_COLORS = {"Default",
											"Red",
											"White",
											"Green",
											"Cyan",
											"Purple",
											"Glow 1",
											"Glow 2", 
											"Glow 3",
											"Flash 1",
											"Flash 2",
											"Flash 3"};
	public static String[] TEXT_EFFECT = {"Default",
											"Scroll",
											"Wave",
											"Wave 2",
											"Shake",
											"Slide"};
	public static String[] SPAM_SPEED_OPTIONS = {"0 Second(s)",
													"1 Second(s)",
													"2 Second(s)",
													"4 Second(s)",
													"7 Second(s)",
													"Custom Delay Time",
													"Random Delay Time"};
	public static String[] EXEC_TIME_OPTIONS = {"1 Minute(s)",
													"15 Minute(s)",
													"30 Minute(s)",
													"45 Minute(s)",
													"1 Hour(s)",
													"2 Hour(s)",
													"Infinite",
													"Custom Time"};
	public static String[] FINISHING_COMMAND_OPTIONS;	//Gets set in Settings.java
	public static String[] SCREENSHOTS_TIME_INTERVALS = {"Screenshots: 15 Minutes",
															"Screenshots: 30 Minutes",
															"Screenshots: 45 Minutes",
															"Screenshots: 1 Hour",
															"Screenshots: Off"};
	public static String[] TYPO_PERCENTAGES = {"Smart Typos: 1%",
												"Smart Typos: 2%",
												"Smart Typos: 3%",
												"Typos: Off",
												"Random Typos: 1%",
												"Random Typos: 2%",
												"Random Typos: 3%"};
	public static String[] TIME_SECONDS = {"01 Second(s)", "02 Second(s)", "03 Second(s)",
											"04 Second(s)", "05 Second(s)", "06 Second(s)",
											"07 Second(s)", "08 Second(s)", "09 Second(s)",
											"10 Second(s)", "11 Second(s)", "12 Second(s)",
											"13 Second(s)", "14 Second(s)", "15 Second(s)",
											"16 Second(s)", "17 Second(s)", "18 Second(s)",
											"19 Second(s)", "20 Second(s)", "21 Second(s)",
											"22 Second(s)", "23 Second(s)", "24 Second(s)",
											"25 Second(s)", "26 Second(s)", "27 Second(s)",
											"28 Second(s)", "29 Second(s)", "30 Second(s)",
											"31 Second(s)", "32 Second(s)", "33 Second(s)",
											"34 Second(s)", "35 Second(s)", "36 Second(s)",
											"37 Second(s)", "38 Second(s)", "39 Second(s)",
											"40 Second(s)", "41 Second(s)", "42 Second(s)",
											"43 Second(s)", "44 Second(s)", "45 Second(s)",
											"46 Second(s)", "47 Second(s)", "48 Second(s)",
											"49 Second(s)", "50 Second(s)", "51 Second(s)",
											"52 Second(s)", "53 Second(s)", "54 Second(s)",
											"55 Second(s)", "56 Second(s)", "57 Second(s)",
											"58 Second(s)", "59 Second(s)"};
	public static String[] TIME_MINUTES_ZERO_SECOND = {"00 Minute(s)", "01 Minute(s)", "02 Minute(s)",
														"03 Minute(s)", "04 Minute(s)", "05 Minute(s)",
														"06 Minute(s)", "07 Minute(s)", "08 Minute(s)",
														"09 Minute(s)", "10 Minute(s)", "11 Minute(s)",
														"12 Minute(s)", "13 Minute(s)", "14 Minute(s)",
														"15 Minute(s)", "16 Minute(s)", "17 Minute(s)",
														"18 Minute(s)", "19 Minute(s)", "20 Minute(s)",
														"21 Minute(s)", "22 Minute(s)", "23 Minute(s)",
														"24 Minute(s)", "25 Minute(s)", "26 Minute(s)",
														"27 Minute(s)", "28 Minute(s)", "29 Minute(s)",
														"30 Minute(s)", "31 Minute(s)", "32 Minute(s)",
														"33 Minute(s)", "34 Minute(s)", "35 Minute(s)",
														"36 Minute(s)", "37 Minute(s)", "38 Minute(s)",
														"39 Minute(s)", "40 Minute(s)", "41 Minute(s)",
														"42 Minute(s)", "43 Minute(s)", "44 Minute(s)",
														"45 Minute(s)", "46 Minute(s)", "47 Minute(s)",
														"48 Minute(s)", "49 Minute(s)", "50 Minute(s)",
														"51 Minute(s)", "52 Minute(s)", "53 Minute(s)",
														"54 Minute(s)", "55 Minute(s)", "56 Minute(s)",
														"57 Minute(s)", "58 Minute(s)", "59 Minute(s)"};
	public static String[] TIME_MINUTES = {"01 Minute(s)", "02 Minute(s)", "03 Minute(s)",
											"04 Minute(s)", "05 Minute(s)", "06 Minute(s)",
											"07 Minute(s)", "08 Minute(s)", "09 Minute(s)",
											"10 Minute(s)", "11 Minute(s)", "12 Minute(s)",
											"13 Minute(s)", "14 Minute(s)", "15 Minute(s)",
											"16 Minute(s)", "17 Minute(s)", "18 Minute(s)",
											"19 Minute(s)", "20 Minute(s)", "21 Minute(s)",
											"22 Minute(s)", "23 Minute(s)", "24 Minute(s)",
											"25 Minute(s)", "26 Minute(s)", "27 Minute(s)",
											"28 Minute(s)", "29 Minute(s)", "30 Minute(s)",
											"31 Minute(s)", "32 Minute(s)", "33 Minute(s)",
											"34 Minute(s)", "35 Minute(s)", "36 Minute(s)",
											"37 Minute(s)", "38 Minute(s)", "39 Minute(s)",
											"40 Minute(s)", "41 Minute(s)", "42 Minute(s)",
											"43 Minute(s)", "44 Minute(s)", "45 Minute(s)",
											"46 Minute(s)", "47 Minute(s)", "48 Minute(s)",
											"49 Minute(s)", "50 Minute(s)", "51 Minute(s)",
											"52 Minute(s)", "53 Minute(s)", "54 Minute(s)",
											"55 Minute(s)", "56 Minute(s)", "57 Minute(s)",
											"58 Minute(s)", "59 Minute(s)"};
	public static String[] TIME_HOURS = {"00 Hour(s)", "01 Hour(s)",
											"02 Hour(s)", "03 Hour(s)",
											"04 Hour(s)", "05 Hour(s)",
											"06 Hour(s)", "07 Hour(s)",
											"08 Hour(s)", "09 Hour(s)",
											"10 Hour(s)", "11 Hour(s)",
											"12 Hour(s)", "13 Hour(s)",
											"14 Hour(s)", "15 Hour(s)",
											"16 Hour(s)", "17 Hour(s)",
											"18 Hour(s)", "19 Hour(s)",
											"20 Hour(s)", "21 Hour(s)",
											"22 Hour(s)", "23 Hour(s)"};
	public static String[] GAME_CLIENTS = {"Watch Client: Rs3",
												"Watch Client: OSRS",
												"Watch Client: RuneLite",
												"Watch Client: Off"};
	public static String[] AUDIO_FILES = {"Simpsons",
											"Canon",
											"Country",
											"Hemeroidy",
											"Popcorn",
											"Sea Shanty 2",
											"RuneScape Theme"};
	public static String[] MESSAGE_ORDERS = {"Messages will be sent top to bottom",
												"Messages will be sent bottom to top",
												"Messages will be sent in a random order"};
	public static String[] REFRESH_RATES = {"15 Seconds",
												"30 Seconds",
												"45 Seconds",
												"1 Minute"};
	public static ArrayList<Character> shiftCases;
	public static Map<Character, Integer> mapStrokes;
	public static Map<Character, char[]> smartTypoMap;
	public static char[] randomTypoMap;
	
	public static double totalProfit;
	public static ArrayList<MoneyCounter> profitCounter;
	
	public static LocalTyperThread localTyper;
	public static RemoteTyperThread remoteTyper;
	public static SequenceTyperThread sequenceTyper;
	public static String NOTEPAD_TEXT;
	public static JWindow splashWindow;
	

}