package eco.typer.custom_objects;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import eco.typer.Settings.Constants;

/**
 * @author dakota
 * @extends JTextField
 * This class makes the custom input fields in the tool
 */

@SuppressWarnings("serial")
public class CTextField extends JTextField implements MouseListener
{
	
	public CTextField()
	{
		super();
		setBackground(Constants.BACKGROUND_COLOR);
		setCaretColor(Constants.PRIMARY_COLOR);
		setForeground(Color.WHITE.darker());
		//setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
		addMouseListener(this);
		LineBorder bord = (LineBorder) this.getBorder();
		BorderColorListener(this, bord.getLineColor(), bord.getThickness());
	}
	
	public CTextField(String text)
	{
		super(text);
		setBackground(Constants.BACKGROUND_COLOR);
		setCaretColor(Constants.PRIMARY_COLOR);
		setForeground(Color.LIGHT_GRAY);
		//setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
		addMouseListener(this);
		LineBorder bord = (LineBorder) this.getBorder();
		BorderColorListener(this, bord.getLineColor(), bord.getThickness());
	}
	
	public CTextField(String text,
					 int shade) //1 = light, 2 = dark, 0 = normal
	{
		super(text);
		if(shade == 2)
		{
			setBackground(Constants.BACKGROUND_COLOR.darker());
		}
		else if(shade == 1)
		{
			setBackground(Constants.BACKGROUND_COLOR);
		}
		setForeground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
		setEditable(false);
		addMouseListener(this);
		LineBorder bord = (LineBorder) this.getBorder();
		BorderColorListener(this, bord.getLineColor(), bord.getThickness());
	}
	
	public CTextField(String text,
					 Border border)
	{
		super(text);
		setBackground(Constants.BACKGROUND_COLOR);
		setCaretColor(Constants.PRIMARY_COLOR);
		setForeground(Color.LIGHT_GRAY);
		//setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(border);
		addMouseListener(this);
		LineBorder bord = (LineBorder) this.getBorder();
		BorderColorListener(this, bord.getLineColor(), bord.getThickness());
	}
	
	public CTextField(String text,
					  String shade,
					  boolean bool)
	{
		super(text);
		if(shade.equals("light"))
		{
			setBackground(Constants.BACKGROUND_COLOR.brighter());
		}
		else if(shade.equals("dark"))
		{
			setBackground(Constants.BACKGROUND_COLOR.darker());
		}
		else
		{
			setBackground(Constants.BACKGROUND_COLOR);
		}
		
		setForeground(Color.WHITE);
		if(bool)
		{
			setCaretColor(Constants.PRIMARY_COLOR);
			//setHorizontalAlignment(SwingConstants.CENTER);
		}
		setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1));
		addMouseListener(this);
		LineBorder bord = (LineBorder) this.getBorder();
		BorderColorListener(this, bord.getLineColor(), bord.getThickness());
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(super.getText().equals("Enter Text Here"))
		{
			super.setText(null);
			super.setForeground(Color.WHITE);
		}
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

	@Override
	public void mouseEntered(MouseEvent e)
	{
		//Does nothing
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		//Does nothing
	}
	
	private void BorderColorListener(CTextField field,
									 Color borderColor,
									 int borderSize)
	{
		field.addKeyListener(new KeyListener()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if(field.getText().isEmpty())
				{
					field.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				}
				else
				{
					field.setBorder(BorderFactory.createLineBorder(borderColor, borderSize));
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				//Does nothing
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				//Does nothing
			}
		});
	}

}
