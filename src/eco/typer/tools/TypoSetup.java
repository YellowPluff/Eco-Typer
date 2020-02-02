package eco.typer.tools;

import java.util.HashMap;

import eco.typer.Settings.Constants;

/**
 * @author dakota
 * This class init's the typo maps
 */

public class TypoSetup
{
	
	public static char getSmartTypo(char charIn)
	{
		char[] charOptions = Constants.smartTypoMap.get(charIn);
		int length = charOptions.length - 1;
		int option = Utils.getRandomNumberBetweenInclusive(0, length);
		return charOptions[option];
	}
	
	public static char getRandomTypo()
	{
		int length = Constants.randomTypoMap.length - 1;
		return Constants.randomTypoMap[Utils.getRandomNumberBetweenInclusive(0, length)];
	}
	
	public static void initSmartTypos()
	{
		Constants.smartTypoMap = new HashMap<>();
		Constants.smartTypoMap.put('`', "~1!".toCharArray());
		Constants.smartTypoMap.put('1', "`~!2@".toCharArray());
		Constants.smartTypoMap.put('2', "1!@3#".toCharArray());
		Constants.smartTypoMap.put('3', "2@#4$".toCharArray());
		Constants.smartTypoMap.put('4', "#35%$".toCharArray());
		Constants.smartTypoMap.put('5', "4$%6^".toCharArray());
		Constants.smartTypoMap.put('6', "5%^&7".toCharArray());
		Constants.smartTypoMap.put('7', "6^&*8".toCharArray());
		Constants.smartTypoMap.put('8', "7&*(9".toCharArray());
		Constants.smartTypoMap.put('9', "8*()0".toCharArray());
		Constants.smartTypoMap.put('0', "9()_-".toCharArray());
		Constants.smartTypoMap.put('-', "0)_+=".toCharArray());
		Constants.smartTypoMap.put('=', "-_+]}".toCharArray());
		Constants.smartTypoMap.put('q', "1!2@wWQ".toCharArray());
		Constants.smartTypoMap.put('w', "qQ2@3#eEW".toCharArray());
		Constants.smartTypoMap.put('e', "3#4$rRwWE".toCharArray());
		Constants.smartTypoMap.put('r', "4$5%tTeER".toCharArray());
		Constants.smartTypoMap.put('t', "5%6^yYrRT".toCharArray());
		Constants.smartTypoMap.put('y', "6^7&uUYtT".toCharArray());
		Constants.smartTypoMap.put('u', "7&8*iIUYy".toCharArray());
		Constants.smartTypoMap.put('i', "8*9(oOIUu".toCharArray());
		Constants.smartTypoMap.put('o', "9(0)pPOIi".toCharArray());
		Constants.smartTypoMap.put('p', "0)-_[{POo".toCharArray());
		Constants.smartTypoMap.put('[', "-_=+]}{Pp".toCharArray());
		Constants.smartTypoMap.put(']', "=+}\\|[{".toCharArray());
		Constants.smartTypoMap.put('\'', "}]|".toCharArray());
		Constants.smartTypoMap.put('a', "qQwWsSA".toCharArray());
		Constants.smartTypoMap.put('s', "wWeEdDSAa".toCharArray());
		Constants.smartTypoMap.put('d', "eERrfFDsS".toCharArray());
		Constants.smartTypoMap.put('f', "rRTtgGFDd".toCharArray());
		Constants.smartTypoMap.put('g', "tTyYHhGFf".toCharArray());
		Constants.smartTypoMap.put('h', "yYuUjJHGg".toCharArray());
		Constants.smartTypoMap.put('j', "uUiIkKJHh".toCharArray());
		Constants.smartTypoMap.put('k', "iIoOlLKJj".toCharArray());
		Constants.smartTypoMap.put('l', "oOpP;:LKk".toCharArray());
		Constants.smartTypoMap.put(';', "pP[{'\":Ll".toCharArray());
		Constants.smartTypoMap.put('\'', "[{]}\":;".toCharArray());
		Constants.smartTypoMap.put('z', "aAsSxXZ".toCharArray());
		Constants.smartTypoMap.put('x', "sSdDcCXZz".toCharArray());
		Constants.smartTypoMap.put('c', "dDfFvVCXx".toCharArray());
		Constants.smartTypoMap.put('v', "fFgGbBVCc".toCharArray());
		Constants.smartTypoMap.put('b', "gGhHnNBVv".toCharArray());
		Constants.smartTypoMap.put('n', "hHjJmMNBb".toCharArray());
		Constants.smartTypoMap.put('m', "jJkK,<MNn".toCharArray());
		Constants.smartTypoMap.put(',', "kKlL.><Mm".toCharArray());
		Constants.smartTypoMap.put('.', "lL;:/?><,".toCharArray());
		Constants.smartTypoMap.put('/', ";:'\"?>.".toCharArray());
		Constants.smartTypoMap.put('~', "`1!".toCharArray());
		Constants.smartTypoMap.put('!', "`~12@".toCharArray());
		Constants.smartTypoMap.put('@', "1!23#".toCharArray());
		Constants.smartTypoMap.put('#', "2@34$".toCharArray());
		Constants.smartTypoMap.put('$', "3#45%".toCharArray());
		Constants.smartTypoMap.put('%', "4$6^5".toCharArray());
		Constants.smartTypoMap.put('^', "5%7&6".toCharArray());
		Constants.smartTypoMap.put('&', "6^8*7".toCharArray());
		Constants.smartTypoMap.put('*', "7&89(".toCharArray());
		Constants.smartTypoMap.put('(', "8*0)9".toCharArray());
		Constants.smartTypoMap.put(')', "9(0-_".toCharArray());
		Constants.smartTypoMap.put('_', "0)-=+".toCharArray());
		Constants.smartTypoMap.put('+', "-_=".toCharArray());
		Constants.smartTypoMap.put('Q', "1!2@wWq".toCharArray());
		Constants.smartTypoMap.put('W', "qQ2@3#eEw".toCharArray());
		Constants.smartTypoMap.put('E', "3#4$rRwWe".toCharArray());
		Constants.smartTypoMap.put('R', "4$5%tTeEr".toCharArray());
		Constants.smartTypoMap.put('T', "5%6^yYrRt".toCharArray());
		Constants.smartTypoMap.put('Y', "6^7&uUytT".toCharArray());
		Constants.smartTypoMap.put('U', "7&8*iIuYy".toCharArray());
		Constants.smartTypoMap.put('I', "8*9(oOiUu".toCharArray());
		Constants.smartTypoMap.put('O', "9(0)pPoIi".toCharArray());
		Constants.smartTypoMap.put('P', "0)-_[{pOo".toCharArray());
		Constants.smartTypoMap.put('{', "-_=+]}[pP".toCharArray());
		Constants.smartTypoMap.put('}', "=+\\|][{".toCharArray());
		Constants.smartTypoMap.put('|', "\\}]".toCharArray());
		Constants.smartTypoMap.put('A', "qQwWsSa".toCharArray());
		Constants.smartTypoMap.put('S', "wWeEdDsAa".toCharArray());
		Constants.smartTypoMap.put('D', "eERrfFdsS".toCharArray());
		Constants.smartTypoMap.put('F', "rRTtgGfDd".toCharArray());
		Constants.smartTypoMap.put('G', "tTyYHhgFf".toCharArray());
		Constants.smartTypoMap.put('H', "yYuUjJhGg".toCharArray());
		Constants.smartTypoMap.put('J', "uUiIkKjHh".toCharArray());
		Constants.smartTypoMap.put('K', "iIoOlLkJj".toCharArray());
		Constants.smartTypoMap.put('L', "oOpP;:lKk".toCharArray());
		Constants.smartTypoMap.put(':', "pP[{'\";Ll".toCharArray());
		Constants.smartTypoMap.put('"', "[{]}';:".toCharArray());
		Constants.smartTypoMap.put('Z', "aAsSxXz".toCharArray());
		Constants.smartTypoMap.put('X', "sSdDcCxZz".toCharArray());
		Constants.smartTypoMap.put('C', "dDfFvVcXx".toCharArray());
		Constants.smartTypoMap.put('V', "fFgGbBvCc".toCharArray());
		Constants.smartTypoMap.put('B', "gGhHnNbVv".toCharArray());
		Constants.smartTypoMap.put('N', "hHjJmMnBb".toCharArray());
		Constants.smartTypoMap.put('M', "jJkK,<mNn".toCharArray());
		Constants.smartTypoMap.put('<', "kKlL.>,Mm".toCharArray());
		Constants.smartTypoMap.put('>', "lL;:/?.<,".toCharArray());
		Constants.smartTypoMap.put('?', ";:'\"/.>".toCharArray());
	}
	
	public static void initRandomTypos()
	{
		String allKeys = "";
		for(char c : Constants.smartTypoMap.keySet())
		{
			allKeys += c;
		}
		Constants.randomTypoMap = allKeys.toCharArray();
	}

}
