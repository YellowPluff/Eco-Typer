package eco.typer.pages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.LineMetrics;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;
import eco.typer.custom_frame.CPanel;
import eco.typer.custom_frame.CustomFrame;
import eco.typer.custom_objects.CButton;
import eco.typer.listeners.SUL;
import eco.typer.tools.Utils;

/**
 * @author dakota
 * This package of classes makes up the page capability
 */

@SuppressWarnings("serial")
public class PageSelector extends CPanel
{
	
	private JList<String>  fileList;
	
	public PageSelector()
	{
		super("Page Editor");
		
		PageFileList();
		MakeNewPage();
		EditSelectedPage();
		DeleteSelectedPage();
		InfoIcon();
	}

	private void PageFileList()
	{
		DefaultListModel<String> filesList = new DefaultListModel<String>();
		File[] files = Constants.HOST_FILES_DIRECTORY.listFiles();
		for(File f : files)
		{
			if(f.isFile() && f.toString().contains(".epg"))
			{
				filesList.add(0, f.getName().replaceAll(".epg", ""));
			}
		}
		
		fileList = new JList<String>(filesList);
		fileList.setBackground(Constants.BACKGROUND_COLOR.brighter());
		fileList.setForeground(Color.WHITE);
		JScrollPane listScroller = new JScrollPane(fileList);
		listScroller.setBounds(10, 50, 200, CustomFrame.WORKPANEL_HEIGHT - 100);
		add(listScroller);
	}

	private void MakeNewPage()
	{
		CButton makeNewPage = new CButton("Make New Page");
		makeNewPage.setBounds(230, 50, 190, 20);
		makeNewPage.addMouseListener(new SUL("Allows you to make a custom page for later use."));
		makeNewPage.addActionListener(e ->
		{
			CustomFrame.updateDisplay(new PageMaker());
		});
		add(makeNewPage);
	}
	
	private void EditSelectedPage()
	{
		CButton editSelPage = new CButton("Edit Selected Page");
		editSelPage.setBounds(230, 80, 190, 20);
		editSelPage.addMouseListener(new SUL("Allows you to edit the currently selected page."));
		editSelPage.addActionListener(e ->
		{
			if(fileList.getSelectedValue() == null)
			{
				Toolkit.getDefaultToolkit().beep();
			}
			else
			{
				CustomFrame.updateDisplay(new PageEditor(fileList.getSelectedValue()));
			}
		});
		add(editSelPage);
	}
	
	private void DeleteSelectedPage()
	{
		CButton deleteSelPage = new CButton("Delete Selected Page");
		deleteSelPage.setBounds(230, 110, 190, 20);
		deleteSelPage.addMouseListener(new SUL("Allows you to remove the currently selected page."));
		deleteSelPage.addActionListener(e ->
		{
			if(fileList.getSelectedValue() == null)
			{
				Toolkit.getDefaultToolkit().beep();
			}
			else
			{
				int result = JOptionPane.showConfirmDialog(Settings.frame, "You're about to delete a page, do you want to continue?", "Page Deletion", JOptionPane.YES_NO_CANCEL_OPTION);
				if(result == 0)
				{
					File fileName = new File(Constants.HOST_FILES_DIRECTORY + "/" + fileList.getSelectedValue() + ".epg");
					fileName.delete();
					CustomFrame.updateDisplay(new PageSelector());
				}
			}
		});
		add(deleteSelPage);
	}
	
	private void InfoIcon()
	{
		IconLabel infoIcon = new IconLabel("\uf05a", "", "FontAwesome.ttf", 30);
		infoIcon.setBounds(CustomFrame.WORKPANEL_WIDTH - 30, CustomFrame.WORKPANEL_HEIGHT - 30, 50, 50);
		infoIcon.addMouseListener(new SUL("Find out how to use the Page System."));
		add(infoIcon);
	}

}

@SuppressWarnings("serial")
class IconLabel extends JLabel implements MouseListener
{

    private int tracking;
    private String action;
    
    public IconLabel(String text,
    				 String action,
    				 String font,
    				 int fontSize)
    {
    	super(text);
    	this.tracking = 1;
    	this.action = action;
    	setForeground(Color.WHITE);
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
		setForeground(this.getForeground().brighter());
	}

	@Override
	public void mouseExited(MouseEvent me)
	{
		setForeground(Color.WHITE);
	}

	@Override
	public void mousePressed(MouseEvent me)
	{
		//Does nothing
	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
		String message = "Eco Typer Pages" + "\n"
				+ "The pages system was designed to replace the legacy community selector on the predecessor." + "\n\n"
				+ "A page is a list of 6 text's that all have a variable that can be changed at once." + "\n"
				+ "For example, when you first clicked \"Add Text\", you were greeted with a page of 6 texts. Each text"
				+ " had a veriable called \"Name\" that you could replace by simply typeing the name once at the top,"
				+ " this is called a page. Pages can be used for a varity of different cases from another community within"
				+ " RuneScape, such as spammers for staking or dungeoneering - or perhaps from an entirely different game"
				+ " like WoW." + "\n\n"
				+ "This is my first iteration of this pages system so please leave feedback on what could be improved.";
		JTextArea textArea = new JTextArea(message);
		textArea.setEditable(false);
		textArea.setBackground(null);
		JScrollPane scrollPane = new JScrollPane(textArea);  
		textArea.setLineWrap(true);  
		textArea.setWrapStyleWord(true); 
		scrollPane.setPreferredSize( new Dimension( 400, 200 ) );
		JOptionPane.showMessageDialog(Settings.frame, scrollPane, "Page System Information", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public String getAction()
	{
		return action;
	}
	
}