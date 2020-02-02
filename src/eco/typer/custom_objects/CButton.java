package eco.typer.custom_objects;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import eco.typer.Settings.Constants;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * @extends JButton
 * This class makes the custom buttons in the tool
 */

@SuppressWarnings("serial")
public class CButton extends JButton implements MouseListener
{
	
	public CButton(String text)
	{
		super(text);
		setForeground(Color.WHITE);
		setBackground(Constants.PRIMARY_COLOR);
		setBorderPainted(false);
		setFocusable(false);
		Utils.setFont(this, "Neon.ttf", 16);
		addMouseListener(this);
	}
	
	public CButton(String text,
				   Color backgroundColor)
	{
		super(text);
		setForeground(Color.WHITE);
		setBackground(backgroundColor);
		setBorderPainted(false);
		setFocusable(false);
		addMouseListener(this);
	}
	
	public CButton(String text,
				   int fontSize)
	{
		super(text);
		setForeground(Color.WHITE);
		setBackground(Constants.PRIMARY_COLOR);
		setBorderPainted(false);
		setFocusable(false);
		Utils.setFont(this, "LemonMilk.otf", fontSize);
		addMouseListener(this);
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		setBackground(this.getBackground().darker());
		
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		setBackground(this.getBackground().brighter());
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

}
