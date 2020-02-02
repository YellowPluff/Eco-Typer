package eco.typer.main_panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicSliderUI;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.*;
import eco.typer.listeners.SUL;
import eco.typer.sub_panels.ExecutionTimeAndDelayTimeEditor;
import eco.typer.sub_panels.QuickSettingsEditor;
import eco.typer.threads.LocalTyperThread;
import eco.typer.threads.RemoteTyperThread;
import eco.typer.threads.SequenceTyperThread;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the main settings display
 */

@SuppressWarnings("serial")
public class SettingsDisplay extends CPanel
{
	
	//public CComboBox alarmSelect, finishCommand, lineBreakTime, execTime, screenshotTime, typos, configurations, watchGameClient;
	public CComboBox alarmSelect, finishCommand, lineBreakTime, execTime, screenshotTime, typos, configurations;
	public CToggle pinToTop, timeStamps, finishedAlarm, twentyFourClock;
	public JSlider typeSpeedSlider;
	public CButton startButton;
	
	public SettingsDisplay()
	{
		super("Settings");
		
		//WaterMark();
		ComboBoxes();
		Toggles();
		
		if(Settings.liteMode)
		{
			screenshotTime.setVisible(false);
			typos.setVisible(false);
			timeStamps.setVisible(false);
			finishedAlarm.setVisible(false);
			//watchGameClient.setVisible(false);
			configurations.setVisible(false);
			lineBreakTime.setVisible(false);
			finishCommand.setVisible(false);
			twentyFourClock.setBounds(10, 50, 180, 25);
			pinToTop.setBounds(10, 90, 150, 25);
			typeSpeedSlider.setBounds(210, 90, 390, 110);
		}
	}
	
	public void WaterMark()
	{
		JLabel watermarkLabel = new JLabel("Eco Typer is freeware - www.ecotyper.com");
		watermarkLabel.setForeground(Color.WHITE);
		watermarkLabel.setBounds(0, CustomFrame.WORKPANEL_HEIGHT - 20, 270, 20);
		add(watermarkLabel);
	}

	public void ComboBoxes()
	{
		typeSpeedSlider = new JSlider(10, 50);
		typeSpeedSlider.addMouseListener(new SUL("How fast would you like each character to type?"));
		typeSpeedSlider.setLabelTable(labelTable());
		typeSpeedSlider.setUI(new CustomSliderUI(typeSpeedSlider));
		TitledBorder sliderBorder = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Typing Speed");
		sliderBorder.setTitleColor(Color.WHITE);
		typeSpeedSlider.setBorder(sliderBorder);
		typeSpeedSlider.setSnapToTicks(true);
		typeSpeedSlider.setFocusable(false);
		typeSpeedSlider.setBounds(210, 170, 390, 110);
		typeSpeedSlider.setBackground(null);
		typeSpeedSlider.setPaintTicks(true);
		typeSpeedSlider.setPaintLabels(true);
		typeSpeedSlider.setMajorTickSpacing(20);
		typeSpeedSlider.setMinorTickSpacing(5);
		add(typeSpeedSlider);
		
		startButton = new CButton("Start");
		startButton.addMouseListener(new SUL("Start Typing."));
		startButton.setBounds(410, CustomFrame.WORKPANEL_HEIGHT - 30, 190, 20);
		startButton.addActionListener(e ->
		{
			String executionTime = CustomFrame.settingsPanel.execTime.getSelectedItem().toString();
			String lineBreakTime = CustomFrame.settingsPanel.lineBreakTime.getSelectedItem().toString();
			int typingSpeed = CustomFrame.settingsPanel.typeSpeedSlider.getValue();
			String finalCommand = CustomFrame.settingsPanel.finishCommand.getSelectedItem().toString();
			String screenshotIntervals = CustomFrame.settingsPanel.screenshotTime.getSelectedItem().toString();
			boolean isScreenshotTimestampsOn = CustomFrame.settingsPanel.timeStamps.isTicked();
			String typoChance = CustomFrame.settingsPanel.typos.getSelectedItem().toString();
			boolean isFinishedAlarmOn = CustomFrame.settingsPanel.finishedAlarm.isTicked();
			String finishedAlarmSound = CustomFrame.settingsPanel.alarmSelect.getSelectedItem().toString();
			boolean is24HourClockOn = CustomFrame.settingsPanel.twentyFourClock.isTicked();
			//String watchClient = CustomFrame.settingsPanel.watchGameClient.getSelectedItem().toString();
			
			if (CustomFrame.textMode == 0) //Local
			{
				String messageOrder = CustomFrame.localDisplay.messageOrder.getSelectedItem().toString();
				String[] textsToType = CustomFrame.localDisplay.spamLinesDisplay.getText().split("\n");
				
				if(textsToType[0].equals(""))
				{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(Settings.frame, "Text Display is empty - please add text before continuing.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else	/** If everything is filled out properly, I can continue 
				 			Defaults are set in the typer class						*/
				{
					Constants.localTyper = new LocalTyperThread(messageOrder,
																textsToType,
																executionTime,
																lineBreakTime,
																typingSpeed,
																finalCommand,
																screenshotIntervals,
																isScreenshotTimestampsOn,
																typoChance,
																isFinishedAlarmOn,
																finishedAlarmSound,
																is24HourClockOn/*,
																watchClient*/);
					Constants.localTyper.start();
				}
			}
			else if (CustomFrame.textMode == 1) //Remote
			{
				String messageOrder = CustomFrame.remoteDisplay.messageOrder.getSelectedItem().toString();
				String[] textsToType = CustomFrame.remoteDisplay.spamLinesDisplay.getText().split("\n");
				String refereshInterval = CustomFrame.remoteDisplay.refreshInterval.getSelectedItem().toString();
				
				if(textsToType[0].equals(""))
				{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(Settings.frame, "Text Display is empty - please add text before continuing.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else	/** If everything is filled out properly, I can continue 
				 			Defaults are set in the typer class						*/
				{
					Constants.remoteTyper = new RemoteTyperThread(messageOrder,
																  textsToType,
																  refereshInterval,
																  executionTime,
																  lineBreakTime,
																  typingSpeed,
																  finalCommand,
																  screenshotIntervals,
																  isScreenshotTimestampsOn,
																  typoChance,
																  isFinishedAlarmOn,
																  finishedAlarmSound,
																  is24HourClockOn/*,
																  watchClient*/);
					Constants.remoteTyper.start();
				}
			}
			else if (CustomFrame.textMode == 2) //Sequence
			{
				String[] textsToType = CustomFrame.sequenceDisplay.spamLinesDisplay.getText().split("\n");
				
				if(textsToType[0].equals(""))
				{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(Settings.frame, "Text Display is empty - please add text before continuing.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else	/** If everything is filled out properly, I can continue 
				 			Defaults are set in the typer class						*/
				{
					Constants.sequenceTyper = new SequenceTyperThread(textsToType,
																	  executionTime,
																      //lineBreakTime, //This isn't used in sequence configuration
																      typingSpeed,
																      finalCommand,
																      screenshotIntervals,
																      isScreenshotTimestampsOn,
																      typoChance,
																      isFinishedAlarmOn,
																      finishedAlarmSound,
																      is24HourClockOn/*,
																      watchClient*/);
					Constants.sequenceTyper.start();
				}
			}
		});
		add(startButton);
		
		configurations = new CComboBox("Quick Settings", Constants.CONFIGURATIONS);
		configurations.addMouseListener(new SUL("Use this to quickly load settings."));
		configurations.setBounds(410, 50, 190, 30);
		configurations.addActionListener(e ->
		{
			if(configurations.getSelectedIndex() == 0)
			{
				CustomFrame.updateDisplay(new QuickSettingsEditor());
			}
			else
			{
				Utils.updateSettings((String) configurations.getSelectedItem());
			}
			
			configurations.setText("Quick Settings");
		});
		add(configurations);
		
		execTime = new CComboBox("Execution Time", Constants.EXEC_TIME_OPTIONS);
		execTime.addMouseListener(new SUL("How long do you want the typer to run for?"));
		execTime.setBounds(210, 50, 190, 30);
		execTime.addActionListener(e ->
		{
			if(execTime.getSelectedItem().toString().equals("Custom Time"))
			{
				execTime.setText("Execution Time");
				CustomFrame.updateDisplay(new ExecutionTimeAndDelayTimeEditor(0));	//0 = Execution Time
			}
		});
		add(execTime);
		
		lineBreakTime = new CComboBox("Line Break Time", Constants.SPAM_SPEED_OPTIONS);
		lineBreakTime.addMouseListener(new SUL("How frequent do you want texts to be sent?"));
		lineBreakTime.setBounds(210, 90, 190, 30);
		lineBreakTime.addActionListener(e ->
		{
			if(lineBreakTime.getSelectedItem().toString().equals("Custom Delay Time"))
			{
				lineBreakTime.setText("Line Break Time");
				CustomFrame.updateDisplay(new ExecutionTimeAndDelayTimeEditor(1));	//1 = Line Break Time (Formally: Spam Interval)
			}
		});
		add(lineBreakTime);
		
		alarmSelect = new CComboBox("Alarm Sound", Constants.AUDIO_FILES);
		alarmSelect.addMouseListener(new SUL("What alarm do you want to sound?"));
		alarmSelect.setBounds(210, 170, 390, 30);
		alarmSelect.setVisible(false);	//This gets toggled
		add(alarmSelect);
		
		finishCommand = new CComboBox("Final Command", Constants.FINISHING_COMMAND_OPTIONS);
		finishCommand.addMouseListener(new SUL("What do you want to happen when the timer finishes?"));
		finishCommand.setBounds(210, 130, 390, 30);
		add(finishCommand);
		
		//watchGameClient = new CComboBox("Watch Client: Off", Constants.GAME_CLIENTS);
		//watchGameClient.addMouseListener(new SUL("Would you like the typer to stop if your Game Client is closed?"));
		//watchGameClient.setBounds(10, 290, 200, 25);
		//add(watchGameClient);
	}
	
	@SuppressWarnings("rawtypes")
	private Dictionary labelTable()
	{
		JLabel fastLabel = new JLabel("Fastest");
		fastLabel.setForeground(Color.WHITE);
		JLabel slowLabel = new JLabel("Slowest");
		slowLabel.setForeground(Color.WHITE);
		Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
		labelTable.put(10, fastLabel);
		labelTable.put(50, slowLabel);
		return labelTable;
	}
	
	public void Toggles()
	{
		screenshotTime = new CComboBox("Screenshots: Off", Constants.SCREENSHOTS_TIME_INTERVALS);
		screenshotTime.addMouseListener(new SUL("How often would you like to take a screenshot?"));
		screenshotTime.setBounds(10, 50, 190, 30);
		screenshotTime.addActionListener(e -> {
			if(screenshotTime.getSelectedItem().toString().equals("Screenshots: Off"))
			{
				timeStamps.freeze();
			}
			else
			{
				timeStamps.unfreeze();
			}
		});
		add(screenshotTime);
		
		typos = new CComboBox("Typos: Off", Constants.TYPO_PERCENTAGES);
		typos.addMouseListener(new SUL("How often would you like a typo to happen?"));
		typos.setBounds(10, 90, 190, 30);
		add(typos);
		
		timeStamps = new CToggle("Time Stamps", false, "Would you like your pictures to have time stamps?");
		timeStamps.freeze();
		timeStamps.setBounds(10, 130, 180, 25);
		add(timeStamps);
		
		finishedAlarm = new CToggle("Finished Alarm", false, "Would you like an alarm to go off to when the timer finishes?");
		finishedAlarm.setBounds(10, 170, 190, 25);
		add(finishedAlarm);
		
		twentyFourClock = new CToggle("24 Hour Clock", true, "Would you like to see the 24 hour clock?");
		twentyFourClock.setBounds(10, 210, 180, 25);
		add(twentyFourClock);
		
		pinToTop = new CToggle("Pin to Top", true, "Would you like to keep Eco Typer as the top-most window?");
		pinToTop.setBounds(10, 250, 150, 25);
		add(pinToTop);
		
	}

}

class CustomSliderUI extends BasicSliderUI
{

	private Color majorTickColor = Constants.PRIMARY_COLOR.brighter();
	private Color minorTickColor = Constants.PRIMARY_COLOR;
    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND, 
            BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);

    public CustomSliderUI(JSlider b)
    {
        super(b);
    }

    @Override
    public void paint(Graphics g, JComponent c)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize()
    {
    	return new Dimension(14, 18);
    }

    @Override
    public void paintTrack(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Stroke old = g2d.getStroke();
        g2d.setStroke(stroke);
        g2d.setPaint(Color.WHITE.darker()); //TODO
        if (slider.getOrientation() == SwingConstants.HORIZONTAL)
        {
            g2d.drawLine(trackRect.x, trackRect.y + trackRect.height / 2, 
                    trackRect.x + trackRect.width, trackRect.y + trackRect.height / 2);
        }
        else
        {
            g2d.drawLine(trackRect.x + trackRect.width / 2, trackRect.y, 
                    trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }
        g2d.setStroke(old);
    }
    
    @Override
    public void paintMajorTickForHorizSlider(Graphics g,
    										 Rectangle tickBounds,
    										 int x)
    {
    	int coordinateX = x;
    	if(slider.getOrientation()==JSlider.HORIZONTAL)
    	{   
    		g.setColor(majorTickColor);    
    		g.drawLine(coordinateX, 0, coordinateX, tickBounds.height);  //Draw Major TICK
    	}  
    }
    
    @Override
    public void paintMinorTickForHorizSlider(Graphics g,
    										 Rectangle tickBounds,
    										 int x)
    {  
    	int coordinateX = x;  
    	if(slider.getOrientation()==JSlider.HORIZONTAL)
    	{  
    		g.setColor(minorTickColor);  
    		g.drawLine(coordinateX, 0, coordinateX, tickBounds.height / 2);  //Draw Minor TICK
    	}  
    }

    @Override
    public void paintThumb(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x + 2;
        int x2 = thumbRect.x + thumbRect.width - 2;
        int width = thumbRect.width - 4;
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3;
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        shape.moveTo(x1, topY);
        shape.lineTo(x2, topY);
        shape.lineTo((x1 + x2) / 2, topY + width);
        shape.closePath();
        //g2d.setPaint(new Color(81, 83, 186)); //Inner Color
        g2d.setPaint(Color.LIGHT_GRAY);
        g2d.fill(shape);
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2f));
        //g2d.setPaint(new Color(131, 127, 211));	//Outer Color
        g2d.setPaint(Color.WHITE);
        g2d.draw(shape);
        g2d.setStroke(old);
    }
    
}