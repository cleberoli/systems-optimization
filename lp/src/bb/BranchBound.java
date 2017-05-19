package bb;

import simplex.Simplex;
import util.Node;
import util.Solution;

import java.util.Arrays;
import java.util.PriorityQueue;

public class BranchBound {

    private static final int HEIGHT = 5;

    private Simplex simplex;
    private PriorityQueue<Node> queue;

    public BranchBound(Simplex simplex) {
        this.simplex = simplex;
        this.queue = new PriorityQueue<>();
    }

    public Simplex runBranchBound() {
        // salva o simplex original
        Simplex original = this.simplex.clone();
        Simplex s = simplex.runSimplexForSimplex();
        int integerSolution = testForIntegerSolutions(Solution.getArraySolutionFromMatrix(s));
        double bestValue = Solution.getArraySolutionFromMatrix(s)[0];
        Node root = new Node(bestValue, original);
        Node best = root;

        // caso a solução inicial do simplex já seja inteira retorna o modelo, caso contrário executa o BB
        if (integerSolution == 0)
            return s;
        else {
            best.best = Double.NEGATIVE_INFINITY;
            // insere o modelo original na fila de prioridades
            queue.offer(root);

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                original = node.simplex.clone();
                Simplex newSimplex = node.simplex.clone(); // cria um novo objeto para executar o simples
                newSimplex.setIndexRows(node.simplex.getIndexRows()); // adiciona o conjunto de indices adequados
                newSimplex = newSimplex.runSimplexForSimplex(); // executa o simplex
                Simplex newSimplex2, newSimplex3; // cria os dois ramos daquele nó
                int integerIndex = testForIntegerSolutions(Solution.getArraySolutionFromMatrix(newSimplex));

                if (integerIndex == 0) {
                    if (best.best == Double.NEGATIVE_INFINITY) // controla o maior valor inteiro encontrado
                        best = new Node(Solution.getArraySolutionFromMatrix(newSimplex)[0], newSimplex, node.height);
                    else if (Solution.getArraySolutionFromMatrix(newSimplex)[0] > best.best) {
                        best = new Node(Solution.getArraySolutionFromMatrix(newSimplex)[0], newSimplex, node.height);
                    }
                } else {
                    if (node.height < HEIGHT) {
                        // instancio os novos nós a serem inseridos
                        newSimplex3 = original.clone();
                        newSimplex2 = original.clone();
                        // calculo as novas restrições
                        double lower = Math.floor(Solution.getArraySolutionFromMatrix(newSimplex)[integerIndex]);
                        double upper = Math.ceil(Solution.getArraySolutionFromMatrix(newSimplex)[integerIndex]);
                        Node lowerBound = new Node(node, newSimplex3.addConstraintLT(integerIndex, lower));
                        Node upperBound = new Node(node, newSimplex2.addConstraintGT(integerIndex, upper));

                        //insere os novos nós da fila
                        queue.offer(lowerBound);
                        queue.offer(upperBound);
                    }
                }
            }

            if (testForIntegerSolutions(Solution.getArraySolutionFromMatrix(best.simplex)) == 0) {
                System.out.println("Success!!");
                System.out.println(Arrays.toString(Solution.getArraySolutionFromMatrix(best.simplex)));
            } else {
                System.out.println("Not possible");
                System.out.println(Arrays.toString(Solution.getArraySolutionFromMatrix(best.simplex)));
            }
        }

        return best.simplex;
    }

    /*
     * Testa se todos os elementos da solução são inteiros, caso seja retorna 0
     * Caso contrário retorna a primeira variável que não seja inteira
     */
    private static int testForIntegerSolutions(double[] solution) {
        for (int i = 1; i < solution.length; i++) {
            if (!testIntegerSolution(solution[i]))
                return i;
        }

        return 0;
    }

    /*
     * Testa se um determinado valor é inteiro ou não
     */
    private static boolean testIntegerSolution(double solution) {
        double rounded = Math.round(solution);

        return (Math.abs(rounded - solution) <= 0.00001) ? true : false;
    }

}