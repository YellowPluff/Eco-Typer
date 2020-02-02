package eco.typer.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import eco.typer.custom_frame.CustomFrame;

/**
 * 
 * @author dakota
 * This class is responsible for updating the status message for every object
 */

public class SUL implements MouseListener
{
	
	private String displayText;
	
	public SUL(String text)
	{
		this.displayText = text;
	}

    @Override
    public void mouseEntered(MouseEvent e)
    {
        CustomFrame.updateStatus(displayText);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        CustomFrame.updateStatus("");
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
    	CustomFrame.updateStatus("");
    }

}