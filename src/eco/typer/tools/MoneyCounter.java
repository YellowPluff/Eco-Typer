package eco.typer.tools;

import java.io.Serializable;

/**
 * @author dakota
 * This class is the frame for each money input
 */

@SuppressWarnings("serial")
public class MoneyCounter implements Serializable
{
	
	private double amount;
	private String description;
	private char sign;
	
	public MoneyCounter(char sign,
						double amount,
						String description)
	{
		this.sign = sign;
		this.amount = amount;
		this.description = description;
	}
	
	public char getSign()
	{
		return sign;
	}
	
	public double getAmount()
	{
		return amount;
	}
	
	public String getDescription()
	{
		return description;
	}

}
