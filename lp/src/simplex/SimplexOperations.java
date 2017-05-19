package simplex;

import util.Matrix;

/*
* Classe responsavel por realizar os calculos que definem 
* os elementos necessarios para a operacao do simplex
* @Autores : Cleber Oliveira, Sarah Carneiro
*/
public class SimplexOperations {
	
   /*
   * Metodo responsavel por definir na matriz qual sera a coluna permissivel.   
   * Percorre as linhas para encontrar o primeiro elemento negativo, posteriormente
   * percorre as colunas da mesma linha em busca do primeiro elemento negativo novamente.
   * A coluna desse segundo elemento encontrado sera a permissivel.
   */
   public static void findPermissibleColumnOne(Simplex simplex) {
      for (int i = 1; i <= simplex.getMatrix().getConstraints(); i++) {
         if (simplex.getMatrix().getUpperMatrix()[i][0] < 0) {
            for (int j = 1; j<= simplex.getMatrix().getVariables(); j++) {
               if (simplex.getMatrix().getUpperMatrix()[i][j] < 0) {
                  simplex.setPermissiveColumn(j);
                  j = simplex.getMatrix().getVariables() + 1;
                  i = simplex.getMatrix().getConstraints() + 1;
               }
            }
         }
      }
   }

	/*
   * Metodo responsavel por definir na segunda etapa do simplex, qual sera a coluna permissivel.   
   * Percorre a primeira linha para encontrar o primeiro elemento positivo.
   * A coluna do elemento positivo encontrado sera a proxima coluna permissivel.
   */
   public static void findPermissibleColumnTwo(Simplex simplex) {
      for (int j = 1; j<= simplex.getMatrix().getVariables(); j++) {
         if (simplex.getMatrix().getUpperMatrix()[0][j] > 0) {
            simplex.setPermissiveColumn(j);
            j = simplex.getMatrix().getVariables() + 1;
         }
      }
   }
	
   /*
   * Metodo responsavel por definir elemento permissivel da matriz do simplex.   
   * Realiza a divisao inicialmente dos elementos da coluna permissivel com os elementos do membro livre.
   * A divisao com o menor resultado (lowerQuocient) seu elemento sera definido como o Pivo (elemento permissivel).
   */
   public static double findPivot(Simplex simplex) {
      double lowerQuocient = Double.POSITIVE_INFINITY;
      double temp = 0;
      int row = -1;
   	
      for (int i = 1; i <= simplex.getMatrix().getConstraints(); i++) {
         // A divisao pode ser realizada apenas se os dois elementos possuirem sinais iguais e diferentes de zero.
         // Caso algum dos elemento nao se adequar as restricoes a divisao pode ser descartada.
         if (!((simplex.getMatrix().getUpperMatrix()[i][0] < 0 && simplex.getMatrix().getUpperMatrix()[i][simplex.getPermissiveColumn()] > 0) ||
         	  (simplex.getMatrix().getUpperMatrix()[i][0] > 0 && simplex.getMatrix().getUpperMatrix()[i][simplex.getPermissiveColumn()] < 0) ||
         	  (simplex.getMatrix().getUpperMatrix()[i][simplex.getPermissiveColumn()] == 0))) {
            temp = simplex.getMatrix().getUpperMatrix()[i][0]/simplex.getMatrix().getUpperMatrix()[i][simplex.getPermissiveColumn()];
         	
            // definir o menor quociente entre todas as divisoes
            if (temp < lowerQuocient) {
               lowerQuocient = temp;
               row = i;
            }
         }
      }
   	// define a partir do encontro do elemento permissivel a linha permissivel da matriz
      simplex.setPermissiveRow(row);
   	
      return simplex.getMatrix().getUpperMatrix()[simplex.getPermissiveRow()][simplex.getPermissiveColumn()];
   }
   
   /*
   * Metodo responsavel por preencher a nova matriz apos os calculos realizados da matriz anterior.   
   * Se a coluna ou a linha que estiverem sendo percorridas forem as permissiveis a nova matriz recebe elemento da matriz inferior.
   * Caso contrario a matriz recebe a soma do elemento da matriz superior com a inferior na posicao dada.
   */	
   public static void generateNewMatrix(Simplex simplex) {
      fillLowerMatrix(simplex);
   	
      Matrix newMatrix = new Matrix(simplex.getMatrix().getVariables(), simplex.getMatrix().getConstraints());
      double [][] newUpperMatrix = newMatrix.getUpperMatrix();

      for (int i = 0; i <= simplex.getMatrix().getConstraints(); i++) {
         for (int j = 0; j <= simplex.getMatrix().getVariables(); j++) {
            if ((i == simplex.getPermissiveRow()) || (j == simplex.getPermissiveColumn()))
               newUpperMatrix[i][j] = simplex.getMatrix().getLowerMatrix()[i][j];
            else
               newUpperMatrix[i][j] = simplex.getMatrix().getLowerMatrix()[i][j] + simplex.getMatrix().getUpperMatrix()[i][j];
         }
      }
   	
      newMatrix.setUpperMatrix(newUpperMatrix);
      simplex.setMatrix(newMatrix);		
   }
   
   
	/*
   * Metodo responsavel por criar a matriz inferior dos calculos realizados, do preenchimento inicial da matriz no simplex.   
   * Linha da matriz inferior que corresponde a linha permissivel da matriz superior recebera a multiplicacao dos elementos 
   * correspondentes as posicoes pelo inverso do elemento permissivel (pivo).
   * Coluna da matriz inferior que corresponde a coluna permissivel da matriz superior recebera a multiplicacao dos elementos 
   * correspondentes as posicoes pelo inverso negativo do elemento permissivel (pivo).
   * Demais posicoes da matriz inferior receberao a multiplicacao dos elementos da coluna e linha permissivel
   * da propria matriz inferior correspondentes a posicao i, j do elemento analisado.
   */
   private static void fillLowerMatrix(Simplex simplex) {
      double [][] lowerMatrix = simplex.getMatrix().getLowerMatrix();
      double [][] upperMatrix = simplex.getMatrix().getUpperMatrix();
   	
      double pivot = upperMatrix[simplex.getPermissiveRow()][simplex.getPermissiveColumn()];
      double inversePivot = 1/pivot;
   	
      lowerMatrix[simplex.getPermissiveRow()][simplex.getPermissiveColumn()] = inversePivot;
   	
      for (int j = 0; j <= simplex.getMatrix().getVariables(); j++) {
         if (j != simplex.getPermissiveColumn()) {
            // preenchimento da linha permissivel da matriz inferior ( elemento superior * 1/pivo)
            lowerMatrix[simplex.getPermissiveRow()][j] = (upperMatrix[simplex.getPermissiveRow()][j] == 0) ? 0 : upperMatrix[simplex.getPermissiveRow()][j]*inversePivot;
         }
      }
   	
      for (int i = 0; i <= simplex.getMatrix().getConstraints(); i++) {
         if (i != simplex.getPermissiveRow()) {
            // preenchimento da coluna permissivel da matriz inferior (elemento superior * - 1/pivo)
            lowerMatrix[i][simplex.getPermissiveColumn()] = (upperMatrix[i][simplex.getPermissiveColumn()] == 0) ? 0 : -upperMatrix[i][simplex.getPermissiveColumn()]*inversePivot;
         }
      }
   	
      // preenchimento das demais posicoes distintas da coluna ou linha permissivel
      for (int i = 0; i <= simplex.getMatrix().getConstraints(); i++) {
         for (int j = 0; j <= simplex.getMatrix().getVariables(); j++) {
            if ((i != simplex.getPermissiveRow()) && (j != simplex.getPermissiveColumn())) {
               lowerMatrix[i][j] = ((upperMatrix[simplex.getPermissiveRow()][j] == 0) || (lowerMatrix[i][simplex.getPermissiveColumn()] == 0)) ? 0 : upperMatrix[simplex.getPermissiveRow()][j]*lowerMatrix[i][simplex.getPermissiveColumn()];
            }
         }
      }
   	
      simplex.getMatrix().setLowerMatrix(lowerMatrix);
      simplex.getMatrix().setUpperMatrix(upperMatrix);
   	
   }
   
}
