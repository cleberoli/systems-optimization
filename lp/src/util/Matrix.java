package util;

import java.text.NumberFormat;

public class Matrix {
	
	public static final int VARIABLES = 50;
	public static final int RESTRICTIONS = 6;
	
	private double [][] lowerMatrix;
	private double [][] upperMatrix;
	
	private int rows;
	private int collumns;
	
	public Matrix() {
		rows = RESTRICTIONS + 1;
		collumns = VARIABLES + 1;
		
		lowerMatrix = new double[rows][collumns];
		upperMatrix = new double[rows][collumns];
	}

	public double[][] getLowerMatrix() {
		return lowerMatrix;
	}

	public void setLowerMatrix(double[][] lowerMatrix) {
		this.lowerMatrix = lowerMatrix;
	}

	public double[][] getUpperMatrix() {
		return upperMatrix;
	}

	public void setUpperMatrix(double[][] upperMatrix) {
		this.upperMatrix = upperMatrix;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCollumns() {
		return collumns;
	}

	public void setCollumns(int collumns) {
		this.collumns = collumns;
	}

	@Override
	public String toString() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);            
		nf.setGroupingUsed(false);
		
		String matrix = "==================== Upper Matrix ===================\n";
		for (int i = 0; i <= Matrix.RESTRICTIONS; i++) {
			for (int j = 0; j <= Matrix.VARIABLES; j++) {
				matrix += nf.format(upperMatrix[i][j]) + "\t";
			}
			 matrix += "\n";
		}
		
		matrix += "==================== Lower Matrix ====================\n";
		for (int i = 0; i <= Matrix.RESTRICTIONS; i++) {
			for (int j = 0; j <= Matrix.VARIABLES; j++) {
				matrix += nf.format(lowerMatrix[i][j]) + "\t";
			}
			 matrix += "\n";
		}
		
		return matrix;
	}	
	
}
