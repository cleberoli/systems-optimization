package simplex;

import util.Matrix;

/*
* Classe responsavel por realizar a analize que definem 
* as classificacoes de Solucao Otima, Multiplas Solucoes, Ilimitadas e Impossivel
* @Autores : Cleber Oliveira, Sarah Carneiro
*/
public class MatrixSolution {


   /*
   * Metodo responsavel por realizar a analize da solucao Impossivel 
   * Condicao: possuir elementos da coluna 0 da matriz como negativos
   * porem, nas linhas dos elementos negativos, nao existirem outros elementos negativos.
   */
   public static boolean isImpossibleSolution(Matrix matrix) {
      boolean impossibleSolution = false;
   	
      double [][] upperMatrix = matrix.getUpperMatrix();
   	
   	// Procura por outros elementos negativos, fora o elemento do F(x) e membro livre, na primeira coluna.
      for (int i = 1; i <= matrix.getConstraints(); i++) {
      	
         impossibleSolution = false;
      	
         if (upperMatrix[i][0] < 0) {
         	
            impossibleSolution = true;
         	
         	// Ao encontrar o negativo, verificar se os demais elementos da mesma linha sao todos positivos.
            for (int j = 1; j <= matrix.getVariables(); j++) {
               if (upperMatrix[i][j] < 0) {
                  impossibleSolution = false;
                  j = matrix.getVariables() + 1;
                  i = matrix.getConstraints() + 1;
               	
               }
            	
            }
         	
            // finalizar for 
            if(impossibleSolution) 
               i = matrix.getConstraints() + 1;
         	
         }
      	
      }
   	
      return impossibleSolution;
   }
	
   /*
   * Metodo responsavel por realizar a analize da solucao Multipla 
   */
   public static boolean isMultipleSolutions(Matrix matrix) {
      boolean multipleSolutions = true;
   	
      double [][] upperMatrix = matrix.getUpperMatrix();
   	
   	// Verificar se todos os coeficientes na primeira coluna, além do membro livre, são positivos
      for (int i = 1; i <= matrix.getConstraints(); i++) {
         if (upperMatrix[i][0] < 0) {
            multipleSolutions = false;
            i = matrix.getConstraints() + 1;
         }
      }
   	
   	// Verificar se todos os coeficientes na primeira fila, além do membro livre, são não positivos
      if (multipleSolutions) {
      	
         for (int i = 1; i <= matrix.getVariables(); i++) {
            if (upperMatrix[0][i] > 0) {
               multipleSolutions = false;
               i = matrix.getVariables() + 1;
            }
         }
      }
   	
   	// Verificar se existe um coeficiente 0 na primeira fila, além do membro livre
      if (multipleSolutions) {
         multipleSolutions = false;
      	
         for (int i = 1; i <= matrix.getVariables(); i++) {
            if (upperMatrix[0][i] == 0) {
               multipleSolutions = true;
               i = matrix.getVariables() + 1;
            }
         }
      }
   	
   	
      return multipleSolutions;
   }
	
   /*
   * Metodo responsavel por realizar a analize da solucao Otima 
   * Condicao: possuir elementos da coluna 0 da matriz como positivos
   * e todos os elementos da primeira linha como negativos.
   */
   public static boolean isOptimalSolution(Matrix matrix) {
      boolean optimalSolution = true;
   	
   	//System.out.println(matrix);
   	
      double [][] upperMatrix = matrix.getUpperMatrix();
   	
   	// Verificar se todos os coeficientes na primeira coluna, menos do membro livre, são positivos 
      // se nao, acusar que nao e otima
      for (int i = 1; i <= matrix.getConstraints(); i++) {
         if (upperMatrix[i][0] < 0) {
            optimalSolution = false;
            i = matrix.getConstraints() + 1;
         }
      }
   	
   	// Verificar se todos os coeficientes da primeira linha, além do membro livre, são negativos
      // para acusar que nao e otima
      if (optimalSolution) {
         for (int i = 1; i <= matrix.getVariables(); i++) {
            if (upperMatrix[0][i] >= 0) {
               optimalSolution = false;
               i = matrix.getVariables() + 1;
            }
         }
      }
   	
      return optimalSolution;
   }
	
  /*
   * Metodo responsavel por realizar a analize da solucao Ilimitada 
   */
   public static boolean isUnlimitedSolution(Matrix matrix) {
      boolean unlimitedSolution = true;
   	
      double [][] upperMatrix = matrix.getUpperMatrix();
   	
   	// Verificar se todos os coeficientes na primeira coluna, além do membro livre, são positivos
      for (int i = 1; i <= matrix.getConstraints(); i++) {
         if (upperMatrix[i][0] < 0) {
            unlimitedSolution = false;
            i = matrix.getConstraints() + 1;
         }
      }
   	
      if (unlimitedSolution) {
      	
      	// Procura um coeficiente positivo na primeira fila, além do membro livre
         for (int i = 1; i <= matrix.getVariables(); i++) {
            unlimitedSolution = false;
         	
            if (upperMatrix[0][i] >= 0) {
               unlimitedSolution = true;
            	
            	// Verificar se todos os outros coeficientes dessa coluna são negativos
               for (int j = 1; j <= matrix.getVariables(); j++) {
                  if (upperMatrix[j][i] >= 0) {
                     unlimitedSolution = false;
                     j = matrix.getVariables() + 1;
                     i = matrix.getVariables() + 1;
                  }
               }
            	
               if (unlimitedSolution) 
                  i = matrix.getVariables() + 1;
            	
            }
         }
      }
   	
      return unlimitedSolution;
   }
	
}