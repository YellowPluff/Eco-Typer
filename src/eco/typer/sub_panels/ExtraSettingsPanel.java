package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.custom_objects.CToggle;
import eco.typer.listeners.SUL;
import eco.typer.main_panels.LocalTextDisplay;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This panel is the extra settings display
 */

@SuppressWarnings("serial")
public class ExtraSettingsPanel extends CPanel
{
	
	public ExtraSettingsPanel()
	{
		super("Extra Settings");
		
		ThemeChooser();
		HoverMode();
		OpenScreenshots();
		DeleteEcoTyper();
	}

	private void ThemeChooser()
	{
		CButton blue = new CButton(null, Color.BLUE);
		blue.setActionCommand("Blue");
		blue.setBounds(5, 255, 160, 40);
		blue.addActionListener(e ->
		{
			changeColor(Color.blue.darker());
		});
		add(blue);
		CButton green = new CButton(null, Color.GREEN);
		green.setActionCommand("Green");
		green.setBounds(5, 90, 160, 160);
		green.addActionListener(e ->
		{
			changeColor(Color.GREEN.darker());
		});
		add(green);
		CButton yellow = new CButton(null, Color.YELLOW);
		yellow.setActionCommand("Yellow");
		yellow.setBounds(170, 90, 125, 90);
		yellow.addActionListener(e ->
		{
			changeColor(Color.YELLOW.darker());
		});
		add(yellow);
		CButton pink = new CButton(null, Color.PINK);
		pink.setActionCommand("Pink");
		pink.setBounds(170, 185, 160, 65);
		pink.addActionListener(e ->
		{
			changeColor(Color.PINK.darker());
		});
		add(pink);
		CButton cyan = new CButton(null, Color.CYAN);
		cyan.setActionCommand("Cyan");
		cyan.setBounds(170, 255, 100, 40);
		cyan.addActionListener(e ->
		{
			changeColor(Color.CYAN.darker());
		});
		add(cyan);
		CButton red = new CButton(null, Color.RED);
		red.setActionCommand("Red");
		red.setBounds(275, 255, 55, 40);
		red.addActionListener(e ->
		{
			changeColor(Color.RED.darker());
		});
		add(red);
		CButton orange = new CButton(null, Color.ORANGE);
		orange.setActionCommand("Orange");
		orange.setBounds(335, 185, 85, 110);
		orange.addActionListener(e ->
		{
			changeColor(Color.ORANGE.darker());
		});
		add(orange);
		CButton magenta = new CButton(null, Color.MAGENTA);
		magenta.setActionCommand("Magenta");
		magenta.setBounds(425, 185, 40, 80);
		magenta.addActionListener(e ->
		{
			changeColor(Color.MAGENTA.darker());
		});
		add(magenta);
		CButton custom1 = new CButton(null, new Color(11, 117, 115));
		custom1.setActionCommand("Custom 1");
		custom1.setBounds(300, 90, 165, 90);
		custom1.addActionListener(e ->
		{
			changeColor(new Color(11, 117, 115).darker());
		});
		add(custom1);
		CButton custom2 = new CButton(null, new Color(30, 144, 255));
		custom2.setActionCommand("Custom 2");
		custom2.setBounds(425, 270, 145, 25);
		custom2.addActionListener(e ->
		{
			changeColor(new Color(30, 144, 255).darker());
		});
		add(custom2);
		CButton custom3 = new CButton(null, new Color(105, 139, 34));
		custom3.setActionCommand("Custom 3");
		custom3.setBounds(470, 155, 100, 110);
		custom3.addActionListener(e ->
		{
			changeColor(new Color(105, 139, 34).darker());
		});
		add(custom3);
		CButton custom4 = new CButton(null, new Color(72, 61, 139));
		custom4.setActionCommand("Custom 4");
		custom4.setBounds(575, 155, 27, 140);
		custom4.addActionListener(e ->
		{
			changeColor(new Color(72, 61, 139).darker());
		});
		add(custom4);
		CButton custom5 = new CButton(null, new Color(238, 64, 0));
		custom5.setActionCommand("Custom 5");
		custom5.setBounds(470, 90, 133, 60);
		custom5.addActionListener(e ->
		{
			changeColor(new Color(238, 64, 0).darker());
		});
		add(custom5);
	}
	
	private void changeColor(Color c)
	{
		Constants.PRIMARY_COLOR_NEW = c;
		Utils.serializeFrameData();
		JOptionPane.showMessageDialog(Settings.frame, "This theme will take effect when you reload Eco Typer.");
		try
		{
			CustomFrame.updateDisplay(CustomFrame.lastVisitedPanel);
		}
		catch (Exception e) //Tries to go to last visited panel, otherwise the text display
		{
			CustomFrame.updateDisplay(new LocalTextDisplay());
		}
	}

	private void HoverMode()
	{
		CToggle mouseHoverMode = new CToggle("Mouse Hover Select", Constants.mouseHoverMode, "Would you like mouse hover to be on/off?");
		mouseHoverMode.setBounds(10, CustomFrame.WORKPANEL_HEIGHT - 60, 250, 25);
		add(mouseHoverMode);
	}
	
	private void OpenScreenshots()
	{
		CButton viewScreenshots = new CButton("View Screenshots");
		viewScreenshots.setBounds(10, CustomFrame.WORKPANEL_HEIGHT - 35, 190, 20);
		viewScreenshots.addMouseListener(new SUL("This will open the folder where all of your screenshots are stored."));
		viewScreenshots.addActionListener(e ->
		{
			try
			{
				Desktop.getDesktop().open(Constants.SCREENSHOT_DIRECTORY);
			}
			catch (Exception e1)
			{
				eco.typer.tools.Utils.writeErrorReport(e1, 70);
			}
		});
		add(viewScreenshots);
	}

	private void DeleteEcoTyper()
	{
		CButton deleteEcoTyper = new CButton("Delete Eco Typer");
		deleteEcoTyper.setBounds(CustomFrame.WORKPANEL_WIDTH - 200, CustomFrame.WORKPANEL_HEIGHT - 35, 190, 20);
		deleteEcoTyper.addMouseListener(new SUL("This will remove Eco Typer from your computer, completely."));
		deleteEcoTyper.addActionListener(e ->
		{
			int answer = JOptionPane.showConfirmDialog(Settings.frame, "This will completely remove Eco Typer from your computer.\nDo you want to continue?", "Eco Typer Uninstall", JOptionPane.YES_NO_CANCEL_OPTION);
			if(answer == 0)
			{
				try
				{
					Files.walk(Paths.get(System.getProperty("user.home") + "/Eco Typer/")).map(Path::toFile)
							.sorted((o1, o2) -> -o1.compareTo(o2)).forEach(File::delete);
				}
				catch (Exception e1)
				{
					Utils.writeErrorReport(e1, 80);
				}
				JOptionPane.showMessageDialog(Settings.frame, "All Eco Typer files have been removed.\nEco Typer will now close and you need to manually delete Eco Typer.jar", "Eco Typer Uninstall", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
		add(deleteEcoTyper);
	}

}
