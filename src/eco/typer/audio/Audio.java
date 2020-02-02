package eco.typer.audio;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;

import eco.typer.tools.Utils;

/**
 * 
 * @author dakota
 * This class launches the alarm if the user requests it
 */

public class Audio
{
	
	private static Sequencer player;
	private String midiFile;
	
	public Audio(String file)
	{
		this.midiFile = file;
		try
		{
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Sequencer Being Initialized ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			player = MidiSystem.getSequencer();
			player.open();
		}
		catch (Exception e)
		{
			Utils.writeErrorReport(e, 10);
		}
	}
	
	public void playAudio()
	{
		if (player == null || !player.isOpen() || player.isRunning())
		{
			return;
		}
		try
		{
			player.setSequence(getClass().getResourceAsStream(midiFile));
			player.start();
		}
		catch(Exception e)
		{
			Utils.writeErrorReport(e, 20);
		}
	}
	
	public void stopAudio()
	{
		if (player == null)
		{
			return;
		}
		player.stop();
	}
	
	public void closeSequencer()
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Sequencer Being Closed ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		player.close();
	}

}
