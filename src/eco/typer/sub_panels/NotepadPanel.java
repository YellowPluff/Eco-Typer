package eco.typer.sub_panels;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import eco.typer.Settings.Constants;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.listeners.SUL;

/**
 * @author dakota
 * This panel is the notepad
 */

@SuppressWarnings("serial")
public class NotepadPanel extends CPanel implements KeyListener
{
	
	private JTextArea notepad;

	public NotepadPanel()
	{
		super("Notepad");
		
		Notepad();
		ClearButton();
	}

	private void ClearButton()
	{
		CButton clearNotepad = new CButton("Quick Clear", Constants.BACKGROUND_COLOR.brighter());
		clearNotepad.setBounds(CustomFrame.WORKPANEL_WIDTH - 150, 30, 140, 20);
		clearNotepad.addMouseListener(new SUL("Quickly resets the notepad."));
		clearNotepad.addActionListener(e ->
		{
			notepad.setText("");
			Constants.NOTEPAD_TEXT = notepad.getText();
		});
		add(clearNotepad);
	}

	private void Notepad()
	{
		notepad = new JTextArea(Constants.NOTEPAD_TEXT);
		notepad.setWrapStyleWord(true);
		notepad.setLineWrap(true);
		notepad.addKeyListener(this);
		notepad.setBackground(Constants.BACKGROUND_COLOR.brighter());
		notepad.setForeground(Color.WHITE);
		notepad.setCaretColor(Color.WHITE);
		JScrollPane sp = new JScrollPane(notepad);
		sp.getVerticalScrollBar().setUI(new CScrollBar());
		sp.setBorder(null);
		sp.setBounds(10, 50, CustomFrame.WORKPANEL_WIDTH - 20, CustomFrame.WORKPANEL_HEIGHT - 60);
		add(sp);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		/* Do Nothing */
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		/* Do Nothing */
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		Constants.NOTEPAD_TEXT = notepad.getText();
	}

}
