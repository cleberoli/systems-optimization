package util;

import java.text.NumberFormat;

public class Matrix {

	private int variables;
	private int constraints;
	
	private double [][] lowerMatrix;
	private double [][] upperMatrix;
	
	private int rows;
	private int columns;
	
	public Matrix() {
	    variables = 50;
	    constraints = 6;
        rows = constraints + 1;
        columns = variables + 1;
		
		lowerMatrix = new double[rows][columns];
		upperMatrix = new double[rows][columns];
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

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

    public int getVariables() {
        return variables;
    }

    public void setVariables(int variables) {
        this.variables = variables;
    }

    public int getConstraints() {
        return constraints;
    }

    public void setConstraints(int constraints) {
        this.constraints = constraints;
    }

    @Override
	public String toString() {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);            
		nf.setGroupingUsed(false);
		
		String matrix = "==================== Upper Matrix ===================\n";
		for (int i = 0; i <= constraints; i++) {
			for (int j = 0; j <= variables; j++) {
				matrix += nf.format(upperMatrix[i][j]) + "\t";
			}
			 matrix += "\n";
		}
		
		matrix += "==================== Lower Matrix ====================\n";
		for (int i = 0; i <= constraints; i++) {
			for (int j = 0; j <= variables; j++) {
				matrix += nf.format(lowerMatrix[i][j]) + "\t";
			}
			 matrix += "\n";
		}
		
		return matrix;
	}	
	
}
