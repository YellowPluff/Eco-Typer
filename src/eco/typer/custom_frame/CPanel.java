package eco.typer.custom_frame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import eco.typer.Settings.Constants;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This class is some basic settings for a JPanel
 */

@SuppressWarnings("serial")
public class CPanel extends JPanel
{
	
	public CPanel(String title)
	{
		Title(title);
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout(null);
	}

	private void Title(String title)
	{
		JLabel titleLabel = new JLabel(title);
		titleLabel.setBounds(10, 10, CustomFrame.WORKPANEL_WIDTH - 10, 30);
		titleLabel.setForeground(Color.WHITE);
		Utils.setFont(titleLabel, "Neon.ttf", 30);
		add(titleLabel);
	}

}
