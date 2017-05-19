package util;

public class Input {
	
	/**
	 * Initializes the first matrix of the Simplex algorithm
	 * if it's a minimization problem the first line of the upper matrix should be multiplied by -1
	 * for the restrictions of the type 'greater than or equal', the line correspondent to said restriction should also be multiplied by -1
	 * @param money restriction 'less than or equal' representing the maximum amount of money available
	 * @param calories restriction 'greater than or equal' representing the minimum amount of calories to be consumed
	 * @param breakfast restriction 'greater than or equal' representing the minimum number of breakfast items desired
	 * @param dessert restriction 'greater than or equal' representing the minimum number of dessert items desired
	 * @param hamburger restriction 'greater than or equal' representing the minimum number of hamburger items desired
	 * @param salad restriction 'greater than or equal' representing the minimum number of salad items desired
	 * @return
	 */
	public static Matrix initializeMatrix(double money, double calories, double breakfast, double dessert, double hamburger, double salad) {
		Matrix initialMatrix = new Matrix();

		double [][] upperMatrix= {{0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{money,14,22,4.5,4.5,4.5,4.8,4.6,2.0,1.3,1.0,1.0,1.0,1.4,2.2,1.3,1.0,1.0,4.2,4.8,1.0,1.7,1.5,4.1,5.6,6.2,2.0,3.3,2.0,2.0,1.0,1.5,1.5,4.3,2.4,2.9,1.7,5.5,5.5,6.8,7.7,7.7,7.7,1.5,1.1,1.0,5.5,4.0,5.5,4.0,1.1},
				{calories,540,300,570,380,180,350,320,15,290,400,460,440,450,640,330,230,200,630,750,270,350,300,540,156,240,120,190,330,80,110,210,180,580,455,470,180,290,330,320,320,310,410,15,40,100,441,450,100,360,150},
				{breakfast,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
				{dessert,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
				{hamburger,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
				{salad,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
		
		// the lines corresponding to the optimization function and the money restriction should not be multiplied
		for (int i = 2; i <= initialMatrix.getConstraints(); i++) {
			for (int j = 0; j <= initialMatrix.getVariables(); j++) {
				upperMatrix[i][j] = (upperMatrix[i][j] == 0) ? 0 : -upperMatrix[i][j];
			}
		}
	
		initialMatrix.setUpperMatrix(upperMatrix);
		
		return initialMatrix;
	}

}
