package neuralNetwork;

public class Matrix {
	public double[][] matrix;
	public int rows;
	public int colums;

	public Matrix(int rows, int colums) {
		this.rows = rows;
		this.colums = colums;
		matrix = new double[rows][colums];
	}
	
	public void randomize() {
		for(int i = 0;i<this.rows;i++) {
			for(int j = 0;j<this.colums;j++) {
				this.matrix[i][j] = (Math.random()*2)-1;
			}
		}
	}
	
	public static Matrix addOne(Matrix m,double n) {
		for(int i = 0;i<m.rows;i++) {
			for(int j = 0;j<m.colums;j++) {
				m.matrix[i][j] += n;
			}
		}
		return m;
	}
	
	public void addMatrix(Matrix m2) {
		for(int i = 0;i<this.rows;i++) {
			for(int j = 0;j<this.colums;j++) {
				this.matrix[i][j] += m2.matrix[i][j];
			}
		}
	}
	public static Matrix subtractMatrix(Matrix m1,Matrix m2) {
		Matrix resultingMatrix = new Matrix(m1.rows, m2.colums);
		
		for(int i = 0;i<resultingMatrix.rows;i++) {
			for(int j = 0;j<resultingMatrix.colums;j++) {
				resultingMatrix.matrix[i][j] = m1.matrix[i][j] - m2.matrix[i][j];
			}
		}
		return resultingMatrix;
	}
	
	public void multiplyOne(double n) {
		for(int i = 0;i<this.rows;i++) {
			for(int j = 0;j<this.colums;j++) {
				this.matrix[i][j] *= n;
			}
		}
	}
	
	public static Matrix multiplyOne1(Matrix m,double n) {
		Matrix resultingMatrix = new Matrix(m.rows, m.colums);
		for(int i = 0;i<m.rows;i++) {
			for(int j = 0;j<m.colums;j++) {
				resultingMatrix.matrix[i][j] = m.matrix[i][j] * n;
			}
		}
		return resultingMatrix;
	}
	
	public void multiplyMatrix(Matrix m2) {
		for(int i = 0;i<this.rows;i++) {
			for(int j = 0;j<this.colums;j++) {
				this.matrix[i][j] *= m2.matrix[i][j];
			}
		}
	}
	
	public static Matrix matrixProdukt(Matrix m1,Matrix m2) {
		if(m1.colums != m2.rows) {
			System.out.println("ERROR: colums of A and rows of B do not match!!");
			return null;
		}
		Matrix resultingMatrix = new Matrix(m1.rows, m2.colums);
		for(int i = 0;i<resultingMatrix.rows;i++ ) {
			for(int j = 0;j<resultingMatrix.colums;j++ ) {
				// Dot Produkt of values in col
				double sum = 0;
				for(int k = 0;k<m1.colums;k++) {
					sum += m1.matrix[i][k] * m2.matrix[k][j];
				}
				resultingMatrix.matrix[i][j] = sum;
			}
		}
		return resultingMatrix;
	}
	
	public static Matrix transpose(Matrix m) {
		Matrix resultingMatrix = new Matrix(m.colums, m.rows);
		for(int i = 0;i<m.rows;i++) {
			for(int j = 0;j<m.colums;j++) {
				resultingMatrix.matrix[j][i] = m.matrix[i][j];
			}
		}
		return resultingMatrix;
	}
	
	public void showMatrix() {
		for(int i = 0;i<rows;i++) {
			for(int j = 0;j<colums;j++) {
				System.out.print(Math.round(matrix[i][j]*1000.0)/1000.0+"|");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
}
