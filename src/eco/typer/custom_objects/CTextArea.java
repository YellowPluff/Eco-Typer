package eco.typer.custom_objects;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import eco.typer.Settings.Constants;

/**
 * @author dakota
 * @extends JTextArea
 * This class makes the custom text area in the tool
 */

@SuppressWarnings("serial")
public class CTextArea extends JTextArea implements KeyListener
{
	
	public CTextArea()
	{
		super();
	}

	public CTextArea(String text)
	{
		super(text);
		setBackground(Constants.BACKGROUND_COLOR);
		setForeground(Constants.PRIMARY_COLOR);
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
	}
	
	public CTextArea(String text,
					 Color white)
	{
		super(text);
		setBackground(Constants.BACKGROUND_COLOR);
		setForeground(white);
		setCaretColor(white);
		setEditable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		//Does nothing
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		//Does nothing
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER || (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && this.getText().substring(this.getCaretPosition() - 1,  this.getCaretPosition()).equals("\n")))
		{
			e.consume();
		}
	}

}
