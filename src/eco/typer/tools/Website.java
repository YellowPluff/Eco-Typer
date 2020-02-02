package eco.typer.tools;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import eco.typer.Settings.Constants;
import eco.typer.Settings.Settings;

/**
 * @author dakota
 * This class handles all the reading from the website
 */

public class Website
{
	
	private String websiteData;
	
	public Website()
	{
		String status = "Try again";
		int tryCount = 0;
		
		while(status.equals("Try again"))
		{
			if(Settings.speedUpBoot || Settings.liteMode)
			{
				break;
			}
			try
			{
				URL url = new URL(Constants.TEXTFILE_LINK);
			
				BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
				
				String line;
				while((line = reader.readLine()) != null)
				{
					websiteData += " " + line;
				}
				reader.close();
				
				if(!websiteData.contains("#Start Alert"))
				{
					websiteData = "";
				}
				else
				{
					status = "Stop";
				}
			}
			catch (Exception e1)
			{
				Settings.splashWindowText.setText(" Attempting to read data... " + tryCount + "/4");
				tryCount++;
				try
				{
					Thread.sleep(1500);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				if(tryCount >= 4)
				{
					Constants.splashWindow.dispose();
					break;
				}
			}
		}
	}
	
	public int checkForUpdate()
	{
		String websiteVersion = getVersion();
		if(websiteVersion.equals("null"))
		{
			return 0;	//We could not tell
		}
		else if(Settings.VERSION.equals(websiteVersion))
		{
			return -1;	//No Update
		}
		
		String[] websiteVersionArr = websiteVersion.split("-");
		String[] runningVersionArr = Settings.VERSION.split("-");
		
		if(Integer.parseInt(runningVersionArr[2]) > Integer.parseInt(websiteVersionArr[2]))
		{
			return -1;	//No Update
		}
		else if(Integer.parseInt(runningVersionArr[1]) > Integer.parseInt(websiteVersionArr[1]))
		{
			return -1;	//No Update
		}
		else if(Integer.parseInt(runningVersionArr[0]) > Integer.parseInt(websiteVersionArr[0]))
		{
			return -1;	//No Update
		}
		else
		{
			return 1;	//Update
		}
	}
	
	private String getVersion()
	{
		String webString = this.websiteData;
		if(webString == null)
		{
			return "null";		
		}
		
		webString = webString.substring(webString.indexOf("#Start Version"), webString.indexOf("#End Version"));
		webString = webString.substring("#Start Version".length(), webString.length());
		webString = webString.trim();
		
		return webString;
	}
	
	public String getUpdateDesc()
	{
		String webString = this.websiteData;
		if(webString == null)
		{
			return "null";		
		}
		
		webString = webString.substring(webString.indexOf("#Start Update-Description"), webString.indexOf("#End Update-Description"));
		webString = webString.substring("#Start Update-Description".length(), webString.length());
		webString = webString.replaceAll("NL", "\n");
		webString = webString.trim();
				
		return webString;
	}
	
	public String getAlert()
	{
		String webString = this.websiteData;
		if(webString == null)
		{
			return "null";
		}
		
		webString = webString.substring(webString.indexOf("#Start Alert"), webString.indexOf("#End Alert"));
		webString = webString.substring("#Start Alert".length(), webString.length());
		webString = webString.replaceAll("NL", "\n");
		webString = webString.trim();
		
		return webString;
	}

	public String getUpdateLink()
	{
		String webString = this.websiteData;
		if(webString == null)
		{
			return "null";
		}
		
		webString = webString.substring(webString.indexOf("#Start Update Link"), webString.indexOf("#End Update Link"));
		webString = webString.substring("#Start Update Link".length(), webString.length());
		webString = webString.trim();
		
		return webString;
	}

}
