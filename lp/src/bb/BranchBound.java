package bb;

import simplex.Simplex;
import util.Matrix;
import util.Node;
import util.Solution;

import java.util.Arrays;
import java.util.PriorityQueue;

public class BranchBound {

    private static final int HEIGHT = 5;

    private Matrix matrix;
    private Simplex simplex;
    private PriorityQueue<Node> queue;

    public BranchBound(Simplex simplex) {
        this.simplex = simplex;
        this.matrix = simplex.getMatrix();
        this.queue = new PriorityQueue<Node>();
    }

    public Simplex runBranchBound() {
        Simplex s = simplex.runSimplexForSimplex();
        int integerSolution = testForIntegerSolutions(Solution.getArraySolutionFromMatrix(s));
        double bestValue = Solution.getArraySolutionFromMatrix(s)[0];

        if (integerSolution == 0)
            System.out.println("sucesso");
        else {
            Node root = new Node(bestValue, s);
            Node best = root;
            queue.offer(root);
            System.out.println(root.simplex.getMatrix());

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                Simplex newSimplex = node.simplex.runSimplexForSimplex();
                Simplex newSimplex2 = newSimplex.clone();
                int integerIndex = testForIntegerSolutions(Solution.getArraySolutionFromMatrix(newSimplex));

                if (integerIndex == 0) {
                    if (Solution.getArraySolutionFromMatrix(newSimplex)[0] > best.best)
                        best = new Node(Solution.getArraySolutionFromMatrix(newSimplex)[0], newSimplex, node.height);
                } else {
                    if (node.height < HEIGHT) {
                        double lower = Math.floor(Solution.getArraySolutionFromMatrix(newSimplex)[integerIndex]);
                        double upper = Math.ceil(Solution.getArraySolutionFromMatrix(newSimplex)[integerIndex]);
                        Node lowerBound = new Node(node, newSimplex.addConstraintLT(integerIndex, lower));
                        Node upperBound = new Node(node, newSimplex2.addConstraintGT(integerIndex, upper));

                        queue.offer(lowerBound);
                        System.out.println(lowerBound.simplex.getMatrix());
                        queue.offer(upperBound);
                        System.out.println(upperBound.simplex.getMatrix());
                    }
                }
            }

            if (testForIntegerSolutions(Solution.getArraySolutionFromMatrix(best.simplex)) == 0) {
                System.out.println("sucesso");
                System.out.println(Arrays.toString(Solution.getArraySolutionFromMatrix(best.simplex)));
            } else {
                System.out.println("BB nao deu");
                System.out.println(Arrays.toString(Solution.getArraySolutionFromMatrix(best.simplex)));
            }
        }


        return null;
    }

    private static int testForIntegerSolutions(double[] solution) {
        for (int i = 1; i < solution.length; i++) {
            if (!testIntegerSolution(solution[i]))
                return i;
        }

        return 0;
    }

    private static boolean testIntegerSolution(double solution) {
        double rounded = Math.round(solution);

        return (Math.abs(rounded - solution) <= 0.00001) ? true : false;
    }

}
