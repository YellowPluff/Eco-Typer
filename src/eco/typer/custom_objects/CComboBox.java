package eco.typer.custom_objects;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;

import eco.typer.Settings.Constants;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * @extends JComboBox<String>
 * This class makes the custom drop down boxes in the tool
 */

@SuppressWarnings("serial")
public class CComboBox extends JComboBox<String> implements FocusListener
{
	
	public CComboBox(String string,
					 String[] options)
	{
		setText(string);
		
		setBackground(Constants.BACKGROUND_COLOR);
		setForeground(Color.WHITE);
		setUI(new BasicComboBoxUI());
		Utils.setFont(this, "Neon.ttf", 16);
		((JLabel) this.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
//		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		setBorder(BorderFactory.createRaisedBevelBorder());
		addFocusListener(this);
		for(Component c : getComponents())
		{
			if(c instanceof JButton)
			{
				remove(c);
			}
		}
		
		for(String s : options)
		{
			addItem(s);
		}
	}
	
	public CComboBox(String item1,
					 String item2)
	{
		setBackground(Constants.BACKGROUND_COLOR);
		setForeground(Color.WHITE);
		setBorder(null);
		
		((JLabel) this.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		
		addItem(item1);
		addItem(item2);
	}

	public CComboBox(String string,
					 ArrayList<String> CONFIGURATIONS)
	{
		setText(string);
		
		setBackground(Constants.BACKGROUND_COLOR);
		setForeground(Color.WHITE);
		setUI(new BasicComboBoxUI());
		Utils.setFont(this, "Neon.ttf", 16);
		((JLabel) this.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
//		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		setBorder(BorderFactory.createRaisedBevelBorder());
		for(Component c : getComponents())
		{
			if(c instanceof JButton)
			{
				remove(c);
			}
		}
		
		for(String s : CONFIGURATIONS)
		{
			addItem(s);
		}
		
	}

	public void setText(String text)
	{
		setEditable(true);
		setSelectedItem(text);
		setEditable(false);
	}

	@Override
	public void focusGained(FocusEvent arg0)
	{
		if(!this.getBackground().equals(new Color(30, 30, 30)))
			setBackground(Constants.BACKGROUND_COLOR);
	}

	@Override
	public void focusLost(FocusEvent arg0)
	{
		/* Does Nothing */
	}

}