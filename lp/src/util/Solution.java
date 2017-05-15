package util;

import java.text.NumberFormat;

import simplex.Simplex;

public class Solution {
	
	public static String getSolutionFromMatrix(Simplex simplex) {
		String [] indexRows = simplex.getIndexRows();
		String answer = "";
		double total = 0;
		
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);            
		nf.setGroupingUsed(false);
		
		for (int i = 1; i <= Matrix.RESTRICTIONS; i++) {
			if (indexRows[i].charAt(0) == 'x')
				if (simplex.getMatrix().getUpperMatrix()[i][0] > 0) {
					total += simplex.getMatrix().getUpperMatrix()[i][0]*Converter.getPrice(Integer.parseInt(indexRows[i].substring(1)));
					answer += Converter.getBrand(indexRows[i]) + " - " + nf.format(simplex.getMatrix().getUpperMatrix()[i][0]) + " - " + Converter.convert(indexRows[i]) + " - US$" + Converter.getPrice(Integer.parseInt(indexRows[i].substring(1))) + "@";	
				}
		}
		
		answer += "Total: US$" + nf.format(total);
		
		return answer;
	}
}
