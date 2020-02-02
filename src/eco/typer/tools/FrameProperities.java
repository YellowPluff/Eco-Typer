package eco.typer.tools;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author dakota
 * This class carries the cache data
 */

@SuppressWarnings("serial")
public class FrameProperities implements Serializable
{
	
	public Color color;
	public double totalProfit;
	public String notepadText;
	public ArrayList<MoneyCounter> profitCounter;
	public String alertMessage;
	public boolean seeAlert;
	public Point openPoint;
	public boolean mouseHoverMode;
	
}

/*
 * Process after adding a value:
 * 1. Go to Utils.loadCache(), towards the bottom set a default value
 * 2. Go to Utils.serializeFrameData() and set the value for serialization
 * 3. Go to Settings.checkSerializedData() and set the Constants value to the loaded in value
 */