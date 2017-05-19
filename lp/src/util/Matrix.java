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

	public Matrix(int variables, int constraints) {
	    this.variables = variables;
	    this.constraints = constraints;
	    rows = constraints + 1;
	    columns = variables + 1;

	    lowerMatrix = new double[rows][columns];
	    upperMatrix = new double[rows][columns];
    }

    public Matrix(int variables, int constraints, double[][] lowerMatrix, double[][] upperMatrix, int rows, int columns) {
        this.variables = variables;
        this.constraints = constraints;
        this.lowerMatrix = lowerMatrix;
        this.upperMatrix = upperMatrix;
        this.rows = rows;
        this.columns = columns;
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

    public void addConstraintGT(int variable, double value) {
		double[][] newUppermatrix = new double[rows+1][columns];
		double[][] newLowermatrix = new double[rows+1][columns];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				newUppermatrix[i][j] = upperMatrix[i][j];
			}
		}

		newUppermatrix[rows][0] = -value;
		newUppermatrix[rows][variable] = -1;

		constraints++;
		rows++;

		setUpperMatrix(newUppermatrix);
		setLowerMatrix(newLowermatrix);
	}

    public void addConstraintLT(int variable, double value) {
        double[][] newUppermatrix = new double[rows+1][columns];
		double[][] newLowermatrix = new double[rows+1][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newUppermatrix[i][j] = upperMatrix[i][j];
            }
        }

        newUppermatrix[rows][0] = value;
        newUppermatrix[rows][variable] = 1;

        constraints++;
        rows++;

        setUpperMatrix(newUppermatrix);
        setLowerMatrix(newLowermatrix);
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
		
/*		matrix += "==================== Lower Matrix ====================\n";
		for (int i = 0; i <= constraints; i++) {
			for (int j = 0; j <= variables; j++) {
				matrix += nf.format(lowerMatrix[i][j]) + "\t";
			}
			 matrix += "\n";
		}*/
		
		return matrix;
	}

    @Override
    public Matrix clone() {
        return new Matrix(variables,constraints,lowerMatrix,upperMatrix,rows,columns);
    }
}
