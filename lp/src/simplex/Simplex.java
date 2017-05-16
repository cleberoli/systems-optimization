package simplex;

import util.Matrix;
import util.Solution;

/*
* Classe responsavel por unir os calculos das demais classes para executar o simplex. 
* Responsavel tambem por classificar as matrizes como Solucao Otima, Impossivel, Multiplas Solucoes e Ilimitada.
* @Autores : Cleber Oliveira, Sarah Carneiro
*/
public class Simplex {
	
	private Matrix matrix;
	
	private int rows;
	private int columns;
	private int permissiveRow = -1;
	private int permissiveColumn = -1;
	
   // arrays para armazenarem as variaveis que realizarao a troca na matriz.
	private String [] indexColumns = {"ML", "x1", "x2", "x3", "x4", "x5", "x6", "x7", "x8", "x9", "x10", "x11", "x12", "x13", "x14", "x15", "x16", "x17", "x18", "x19","x20", "x21", "x22", "x23", "x24", "x25", "x26", "x27", "x28", "x29", "x30", "x31", "x32", "x33", "x34", "x35", "x36", "x37", "x38", "x39", "x40", "x41", "x42", "x43", "x44", "x45", "x46", "x47","x48", "x49", "x50"};
	private String [] indexRows = {"fx", "y1", "y2", "y3", "y4", "y5", "y6"};
	
	public Simplex(Matrix matrix) {
		this.matrix = matrix;
		this.rows = matrix.getRows();
		this.columns = matrix.getColumns();
	}
	
   /*
   * Metodo que executa todas as partes separadas formando o simplex
   * Faz verificacao inicial se matriz e impossivel. Se nao, executa primeira parte do simplex,
   * identifica se a matriz gerada na primeira etapa do simplex ja possui algum tipo de classificacao. 
   * Caso nao, executa a segunda parte do algoritmo do simplex procurando novamente a classificacao da matriz fina.
   */
	public String runSimplex() {		
		if (MatrixSolution.isImpossibleSolution(matrix))
			return "Impossible Solution";
		else {
			phaseOne();
			
			if (MatrixSolution.isImpossibleSolution(matrix))
				return "Impossible Solution";
			
			if (MatrixSolution.isMultipleSolutions(matrix))
				return "Multiple Solutions@" + Solution.getSolutionFromMatrix(this);
			
			if (MatrixSolution.isOptimalSolution(matrix))
				return "Optimal Solution@" + Solution.getSolutionFromMatrix(this);
			
			if (MatrixSolution.isUnlimitedSolution(matrix))
				return "Unlimited Solution";
			
			phaseTwo();
			
			if (MatrixSolution.isMultipleSolutions(matrix))
				return "Multiple Solutions@" + Solution.getSolutionFromMatrix(this);
			
			if (MatrixSolution.isOptimalSolution(matrix))
				return "Optimal Solution@" +  Solution.getSolutionFromMatrix(this);
			
			if (MatrixSolution.isUnlimitedSolution(matrix))
				return "Unlimited Solution";
		}
		
		return "Error!";
	}
	
   /*
   * Metodo que executa a primeira parte do algoritmo do simplex : apos o preenchimento da matriz encontrar coluna, linha 
   * e elemento permissivel (pivo). Posteriormente calcular e gerar a nova matriz. Repetir esse primeiro passo enquanto 
   * a matriz nova gerada nao possuir todos os elementos da primeira coluna, excluindo o elemento (0,0), positivos. 
   */
	private void phaseOne() {
		while(MatrixPhase.isFirstPhase(matrix) && !MatrixSolution.isImpossibleSolution(matrix)) {
			for (int i = 0; i < matrix.getColumns(); i++) {
				if (SimplexOperations.isPermissibleColumnOne(this, i)) {
					SimplexOperations.findPivot(this);
					SimplexOperations.generateNewMatrix(this);
					this.switchVaribales();
				}
			}
		}
	}
	
   /*
   * Metodo que executa a segunda parte do algoritmo do simplex : apos o a matriz conseguir ter todos seus elementos da primeira
   * coluna positivos, verifica se a primeira linha da matriz possui apenas elementos positivos. 
   * Definir linha, coluna e elemento permissivel novamente. Executar o algoritmo da troca e preencher nova matriz enquanto todos os
   * elementos da primeira linha nao forem negativos ou condizentes com as demais classificacoes que nao sao a solucao otima.
   */
	private void phaseTwo() {
		while(MatrixPhase.isSecondPhase(matrix) && !MatrixSolution.isMultipleSolutions(matrix) && !MatrixSolution.isOptimalSolution(matrix) && !MatrixSolution.isUnlimitedSolution(matrix)) {
			for (int i = 0; i < matrix.getColumns(); i++) {
				if (SimplexOperations.isPermissibleColumnTwo(this, i)) {
					SimplexOperations.findPivot(this);
					SimplexOperations.generateNewMatrix(this);
					this.switchVaribales();
				}
			}
		}
	}
	
   /*
   * Metodo do algoritmo da troca das variaveis, referentes a linha e coluna do elemento permissivel 
   * da matriz superior, de posicao (entre si).
   */
	private void switchVaribales() {
		String temp = indexColumns[permissiveColumn];
		indexColumns[permissiveColumn] = indexRows[permissiveRow];
	    indexRows[permissiveRow] = temp;
	}


   // metodos construtores da matriz 
	public Matrix getMatrix() {
		return matrix;
	}

	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getPermissiveRow() {
		return permissiveRow;
	}

	public void setPermissiveRow(int permissiveRow) {
		this.permissiveRow = permissiveRow;
	}

	public int getPermissiveColumn() {
		return permissiveColumn;
	}

	public void setPermissiveColumn(int permissiveColumn) {
		this.permissiveColumn = permissiveColumn;
	}

	public String[] getIndexColumns() {
		return indexColumns;
	}

	public void setIndexColumns(String[] indexColumns) {
		this.indexColumns = indexColumns;
	}

	public String[] getIndexRows() {
		return indexRows;
	}

	public void setIndexRows(String[] indexRows) {
		this.indexRows = indexRows;
	}

}
