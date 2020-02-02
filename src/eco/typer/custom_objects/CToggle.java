package eco.typer.custom_objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.LineMetrics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import eco.typer.Settings.Constants;
import eco.typer.listeners.SettingsTogglesListener;
import eco.typer.listeners.SUL;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * @extends JPanel
 * This class makes the custom toggles on the settings panel
 */

@SuppressWarnings("serial")
public class CToggle extends JPanel
{
	
	IconLabel icon;
	
	public CToggle(String text,
				   boolean b,
				   String statusText)
	{
		setBackground(Constants.BACKGROUND_COLOR);
		setLayout(new BorderLayout());
		addMouseListener(new SUL(statusText));
		
		if(b)
		{
			icon = new IconLabel("\uf205", text, "FontAwesome.ttf", 20);		//Image
		}
		else
		{
			icon = new IconLabel("\uf204", text, "FontAwesome.ttf", 20);
		}
		add(icon, BorderLayout.WEST);
		IconLabel iconLabel = new IconLabel(" " + text, 20);					//Text next to image
		add(iconLabel, BorderLayout.CENTER);
	}

	public boolean isTicked()
	{
		return icon.isTicked();
	}
	
	public void freeze()
	{
		icon.setClickable(false);
		icon.setEnabled(false);
	}
	
	public void unfreeze()
	{
		icon.setClickable(true);
		icon.setEnabled(true);
	}
	
	public void setTick(boolean value)
	{
		icon.setTicked(value);
	}

}


@SuppressWarnings("serial")
class IconLabel extends JLabel implements MouseListener
{

    private int tracking;
    private String action;
    private boolean isAllowedToClick;
    
    public IconLabel(String text,
    				 int fontSize)
    {
    	super(text);
    	this.isAllowedToClick = true;
    	this.tracking = 1;
    	setForeground(Color.WHITE);
    	this.setRightShadow(1, 1, Color.BLACK);
    	Utils.setFont(this, "Neon.ttf", fontSize);
    }
    
    public IconLabel(String text,
    				 String action,
    				 String font,
    				 int fontSize)
    {
    	super(text);
    	this.isAllowedToClick = true;
    	this.tracking = 1;
    	this.action = action;
    	setForeground(Constants.PRIMARY_COLOR);
    	addMouseListener(this);
    	this.setRightShadow(1, 1, Color.BLACK);
    	Utils.setFont(this, font, fontSize);
    }
    
    private int left_x, left_y, right_x, right_y;
    private Color left_color, right_color;
    public void setLeftShadow(int x,
    						  int y,
    						  Color color)
    {
        left_x = x;
        left_y = y;
        left_color = color;
    }
    
    public void setRightShadow(int x,
    						   int y,
    						   Color color)
    {
        right_x = x;
        right_y = y;
        right_color = color;
    }
    
    public Dimension getPreferredSize()
    {
        String text = getText();
        FontMetrics fm = this.getFontMetrics(getFont());
        
        int w = fm.stringWidth(text);
        w += (text.length()-1)*tracking;
        w += left_x + right_x;
        
        int h = fm.getHeight();
        h += left_y + right_y;

        return new Dimension(w,h);
    }
    
    public void paintComponent(Graphics g)
    {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        char[] chars = getText().toCharArray();

        FontMetrics fm = this.getFontMetrics(getFont());
        int h = fm.getAscent();
        @SuppressWarnings("unused")
		LineMetrics lm = fm.getLineMetrics(getText(),g);
        g.setFont(getFont());
        
        int x = 0;
        
        for(int i=0; i<chars.length; i++)
        {
            char ch = chars[i];
            int w = fm.charWidth(ch) + tracking;

            g.setColor(left_color);
            g.drawString(""+chars[i],x-left_x,h-left_y);
            
            g.setColor(right_color);
            g.drawString(""+chars[i],x+right_x,h+right_y);

            g.setColor(getForeground());
            g.drawString(""+chars[i],x,h);

            x+=w;
        }
        
    }
    
    @Override
	public void mouseClicked(MouseEvent me)
    {
    	//Does nothing
    }

	@Override
	public void mouseEntered(MouseEvent me)
	{
		if(isAllowedToClick)
		{
			setForeground(this.getForeground().brighter());
		}
	}

	@Override
	public void mouseExited(MouseEvent me)
	{
		if(isAllowedToClick)
		{
			setForeground(Constants.PRIMARY_COLOR);
		}
	}

	@Override
	public void mousePressed(MouseEvent me)
	{
		//Does nothing
	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
		if(isAllowedToClick)
		{
			if(this.getText().equals("\uf204")) //If toggled OFF
			{
				System.out.println("------ THIS GETS CALLED WHEN TURNING [ ON ] THE TOGGLE ------");
				this.setText("\uf205");
				SettingsTogglesListener.execute(this.action);
			}
			else
			{
				System.out.println("------ THIS GETS CALLED WHEN TURNING [ OFF ] THE TOGGLE ------");
				this.setText("\uf204");
				SettingsTogglesListener.execute(this.action);
			}
		}
	}
	
	public String getAction()
	{
		return action;
	}

	public boolean isTicked()
	{
		if(this.getText().equals("\uf204"))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void setClickable(boolean value)
	{
		this.setText("\uf204");
		this.isAllowedToClick = value;
	}
	
	public void setTicked(boolean value)
	{
		if(value)
		{
			this.setText("\uf205");
		}
		else
		{
			this.setText("\uf204");
		}
	}
	
}