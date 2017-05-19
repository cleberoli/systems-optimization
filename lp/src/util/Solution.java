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
		
		for (int i = 1; i <= simplex.getMatrix().getConstraints(); i++) {
			if (indexRows[i].charAt(0) == 'x')
				if (simplex.getMatrix().getUpperMatrix()[i][0] > 0) {
                    double simplexValue = simplex.getMatrix().getUpperMatrix()[i][0];
                    double rounded = Math.round(simplexValue);
                    double newValue = (Math.abs(rounded - simplexValue) <= 0.00001) ? rounded : simplexValue;
					total += newValue*Converter.getPrice(Integer.parseInt(indexRows[i].substring(1)));
					answer += Converter.getBrand(indexRows[i]) + " - " + nf.format(simplex.getMatrix().getUpperMatrix()[i][0]) + " - " + Converter.convert(indexRows[i]) + " - US$" + Converter.getPrice(Integer.parseInt(indexRows[i].substring(1))) + "@";	
				}
		}
		
		answer += "Total: US$" + nf.format(total);
		
		return answer;
	}

	public static double[] getArraySolutionFromMatrix(Simplex simplex) {
        String [] indexRows = simplex.getIndexRows();
	    double[] coefficients = new double[simplex.getColumns()];
        double total = 0;

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(5);
        nf.setGroupingUsed(false);

        for (int i = 1; i <= simplex.getMatrix().getConstraints(); i++) {
            if (indexRows[i].charAt(0) == 'x')
                if (simplex.getMatrix().getUpperMatrix()[i][0] > 0) {
                    String index = indexRows[i];
                    index = index.replace('x', ' ');
                    index = index.trim();
                    int position = Integer.parseInt(index);
                    double simplexValue = simplex.getMatrix().getUpperMatrix()[i][0];
                    double rounded = Math.round(simplexValue);
                    double newValue = (Math.abs(rounded - simplexValue) <= 0.00001) ? rounded : simplexValue;
                    coefficients[position] = Double.parseDouble(nf.format(newValue));
                    total += coefficients[position]*Converter.getPrice(Integer.parseInt(indexRows[i].substring(1)));
                }
        }

        coefficients[0] = total;

	    return coefficients;
    }
}
