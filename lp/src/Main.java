import bb.BranchBound;
import simplex.Simplex;
import util.Input;
import util.Matrix;

public class Main {

    public static void transfer(double money, double calories, double breakfast, double dessert, double hamburger, double salad) {
        Matrix matrix = Input.initializeMatrix(money, calories, breakfast, dessert, hamburger, salad);
        double[][] um = {{0,-1,-2},{-16,-8,-2},{6,1,1},{-28,-2,-7},{-2,-1,0}};
        double[][] dois = {{0,14,22},{250,2,4},{-460,-5,-8},{40,1,0}};
        Matrix m = new Matrix(dois);
        Simplex s = new Simplex(matrix);
        //System.out.println(s.runSimplexForSimplex().getMatrix());

        BranchBound bb = new BranchBound(s);
        bb.runBranchBound();


/*        String[] solution = s.runSimplexForResult().split("@");
        int solutionSize = solution.length;

        for (int i = 0; i < solutionSize; i++) {
            System.out.println(solution[i]);
        }*/
    }

    public static void main(String[] args) {
        transfer(500,500,2,3,5,1);
    }
}
