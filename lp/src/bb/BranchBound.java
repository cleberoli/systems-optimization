package bb;

import simplex.Simplex;
import util.Matrix;
import util.Node;
import util.Solution;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.PriorityQueue;

public class BranchBound {

    private static final int HEIGHT = 2;

    private Matrix matrix;
    private Simplex simplex;
    private PriorityQueue<Node> queue;

    public BranchBound(Simplex simplex) {
        this.simplex = simplex;
        this.matrix = simplex.getMatrix();
        this.queue = new PriorityQueue<Node>();
    }

    public Simplex runBranchBound() {
        Simplex original = this.simplex.clone();
        System.out.println(this.simplex.getMatrix());
        Simplex s = simplex.runSimplexForSimplex();
        int integerSolution = testForIntegerSolutions(Solution.getArraySolutionFromMatrix(s));
        double bestValue = Solution.getArraySolutionFromMatrix(s)[0];

        if (integerSolution == 0)
            System.out.println("sucesso");
        else {
            Node root = new Node(bestValue, original);
            Node best = root;
            best.best = Double.NEGATIVE_INFINITY;
            queue.offer(root);

            while (!queue.isEmpty()) {
                System.out.println("inicio do while " + queue.size());
                Node node = queue.poll();
                original = node.simplex.clone();
                System.out.println("acabei de tirar");
                System.out.println(original.getMatrix());
                Simplex newSimplex = original.clone();
                System.out.println("clonei");
                newSimplex.setIndexRows(node.simplex.getIndexRows());
                System.out.println(Arrays.toString(node.simplex.getIndexRows()));
                newSimplex = newSimplex.runSimplexForSimplex();
                System.out.println("newSimplexIndex "+Arrays.toString(newSimplex.getIndexRows()));
                System.out.println("fiz simplex");
                System.out.println(newSimplex.getMatrix());
                Simplex newSimplex2, newSimplex3;
                int integerIndex = testForIntegerSolutions(Solution.getArraySolutionFromMatrix(newSimplex));

                System.out.println("index "+integerIndex);
                if (integerIndex == 0) {
                    System.out.println(Solution.getArraySolutionFromMatrix(newSimplex)[0]);
                    System.out.println(best.best);
                    if (best.best == Double.NEGATIVE_INFINITY)
                        best = new Node(Solution.getArraySolutionFromMatrix(newSimplex)[0], newSimplex, node.height);
                    else if (Solution.getArraySolutionFromMatrix(newSimplex)[0] > best.best) {
                        System.out.println("BEST");
                        System.out.println(newSimplex.getMatrix());
                        best = new Node(Solution.getArraySolutionFromMatrix(newSimplex)[0], newSimplex, node.height);
                    }
                } else {
                    System.out.println("ELSE");
                    if (node.height < HEIGHT) {
                        System.out.println("HEIGHT");
                        newSimplex3 = original.clone();
                        newSimplex2 = original.clone();
                        double lower = Math.floor(Solution.getArraySolutionFromMatrix(newSimplex)[integerIndex]);
                        double upper = Math.ceil(Solution.getArraySolutionFromMatrix(newSimplex)[integerIndex]);
                        Node lowerBound = new Node(node, newSimplex3.addConstraintLT(integerIndex, lower));
                        Node upperBound = new Node(node, newSimplex2.addConstraintGT(integerIndex, upper));

                        queue.offer(lowerBound);
                        System.out.println(lowerBound.simplex.getMatrix());
                        System.out.println(Arrays.toString(lowerBound.simplex.getIndexRows()));
                        queue.offer(upperBound);
                        System.out.println(upperBound.simplex.getMatrix());
                        System.out.println(Arrays.toString(upperBound.simplex.getIndexRows()));
                    }
                }

                System.out.println("fim do while " + queue.size());
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
