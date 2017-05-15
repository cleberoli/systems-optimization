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
				{money,3.99,1.00,4.39,4.39,4.49,4.79,4.59,2.00,1.29,1.19,1.19,1.00,1.39,2.19,1.29,1.00,1.00,4.19,4.80,1.00,1.69,1.49,4.09,5.59,6.19,1.99,3.29,2.00,2.00,1.00,1.50,1.49,4.29,2.38,2.89,1.69,5.50,5.50,6.75,7.75,7.75,7.75,1.50,1.10,1.00,5.50,4.00,5.50,4.00,1.10},
				{calories,540,300,570,380,180,350,320,15,290,400,460,440,450,640,330,230,200,630,750,270,350,300,540,156,240,120,190,330,80,110,210,180,580,455,470,180,290,330,320,320,310,410,15,40,100,441,450,100,360,150},
				{breakfast,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
				{dessert,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0},
				{hamburger,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0},
				{salad,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
		
		// the lines corresponding to the optimization function and the money restriction should not be multiplied
		for (int i = 2; i <= Matrix.RESTRICTIONS; i++) {
			for (int j = 0; j <= Matrix.VARIABLES; j++) {
				upperMatrix[i][j] = (upperMatrix[i][j] == 0) ? 0 : -upperMatrix[i][j];
			}
		}
	
		initialMatrix.setUpperMatrix(upperMatrix);
		
		return initialMatrix;
	}

}
