package simplex;

import util.Matrix;

/*
* Classe responsavel por verificar se ja ainda e primeira fase ou segunda. 
* @Autores : Cleber Oliveira, Sarah Carneiro
*/
public class MatrixPhase {
	
   /*
   * Metodo que verifica se primeira fase ja foi concluida 
   * Se ainda existem elementos da primeira coluna, fora o membro livre, negativos 
   */
   public static boolean isFirstPhase(Matrix matrix) {
      boolean firstPhase = false;
   	
      double [][] upperMatrix = matrix.getUpperMatrix();
   	
   	// Procura um elemento negativo na primeira coluna fora o membro livre
      for (int i = 1; i <= matrix.getConstraints(); i++) {
         if (upperMatrix[i][0] < 0) {
            firstPhase = true;
            i = matrix.getConstraints() + 1;
         }
      }
   	
      return firstPhase;	
   }
	
   /*
   * Metodo que verifica se segunda fase ja foi concluida 
   * Se todos os elementos da primeira coluna, fora o membro livre, sao positivos 
   */
   public static boolean isSecondPhase(Matrix matrix) {
      boolean secondPhase = false;
   	
      if(!isFirstPhase(matrix)) {
         double [][] upperMatrix = matrix.getUpperMatrix();
      	
      	// look for a positive coefficient on the first row, besides the free member
         for (int i = 1; i <= matrix.getVariables(); i++) {
            if (upperMatrix[0][i] >= 0) {
               secondPhase = true;
               i = matrix.getVariables() + 1;
            }
         }
      }
   	
      return secondPhase;
   }

}
