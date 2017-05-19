import bb.BranchBound;
import simplex.Simplex;
import util.Input;
import util.Matrix;

public class Main {

    public static void transfer(double money, double calories, double breakfast, double dessert, double hamburger, double salad) {
        Matrix matrix = Input.initializeMatrix(money, calories, breakfast, dessert, hamburger, salad);
        Simplex s = new Simplex(matrix);

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
