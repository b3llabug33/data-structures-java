package edu.ser222.m01_02;
/**
 * An implementation of the Matrix ADT. Provides four basic operations over an immutable type.
 * 
 * Last updated 7/31/2021.
 * 
 * @author Bella Sheridan, Ruben Acuna
 * @version visual studio 
 */

public class CompletedMatrix implements Matrix {

        //immutable 
        private final int[][] data;
        //rows and columns
        private final int r;
        private final int c;

    public CompletedMatrix(int[][] matrix) {
        //if matrix is null throw an exception 
        if(matrix == null){
            throw new IllegalArgumentException("matrix is null :(");
        }
        //record dimensions
        this.r = matrix.length;
        this.c = (r == 0) ? 0 : matrix[0].length;
        //make new array so the object is immutable 
        this.data = new int[r][c];

        //copy over 
        for(int i = 0; i < r; i++){ 
            for(int j = 0; j < c; j++){
                this.data[i][j] = matrix[i][j];
            }
        }

    }

    
    public int getElement(int y, int x) {
        return data[y][x];
    }

    
    public int getRows() {
        return r;
    }

    
    public int getColumns() {
        return c;
    }

    
    public Matrix scale(int scalar) {
        
        //new array for scaled matrix 
        int[][] scaleR = new int[r][c];

        //go through and multiply every element by the scalar 
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                scaleR[i][j] = data[i][j] * scalar;
            }
        }
        return new CompletedMatrix(scaleR);
    }

    @Override
    public Matrix plus(Matrix other) {
        //make sure other matrix exists and that the dimesions are the same 
        if(other == null){
            throw new IllegalArgumentException("other matrix is null :(");
        }
        if(r != other.getRows() || c != other.getColumns()){
            throw new RuntimeException("dimensions do not match :(");
        }
        //new array for result 
        int[][] plusR = new int[r][c];

        //add matrix with other matrix for new matrix 
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                plusR[i][j] = data[i][j] + other.getElement(i,j);
            }
        }

        return new CompletedMatrix(plusR);
    }

    @Override
    public Matrix minus(Matrix other) {
        //make sure other matrix exists and that the dimesions are the same 
        if(other == null){
            throw new IllegalArgumentException("other matrix is null :(");
        }
        if(r != other.getRows() || c != other.getColumns()){
            throw new RuntimeException("dimensions do not match :(");
        }
        //new array for result 
        int[][] minusR = new int[r][c];

        //subtract every element of matrixes
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                minusR[i][j] = data[i][j] - other.getElement(i,j);
            }
        }

        return new CompletedMatrix(minusR);
    }

    @Override
    public Matrix multiply(Matrix other) {
        //check if other matrix is null or if columns dont equal other rows
        if(other == null){
            throw new IllegalArgumentException("other matrix is null :(");
        }
        if(c != other.getRows()){
            throw new RuntimeException("dimesions aren't compatible");
        }

        //new array for result 
        int[][] multiplyR = new int[r][other.getColumns()];

        //triple loop for matrix multiplication 
        for(int i = 0; i < r; i++){
            for(int j = 0; j < other.getColumns(); j++){
                for(int x = 0; x < c; x++){
                    multiplyR[i][j] += this.data[i][x] * other.getElement(x, j);
                }
            }
        }
        return new CompletedMatrix(multiplyR);
    }

    public boolean equals(Object other){
        //check for null
        if(other == null){
            return false;
        }
        //check for right "class"
        if (!(other instanceof Matrix)) {
            return false;
        }

        Matrix otherMatrix = (Matrix) other;

        //check member variables 
        if(r != otherMatrix.getRows() || c != otherMatrix.getColumns()){
            return false;
        }

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                if(data[i][j] != otherMatrix.getElement(i,j)){
                    return false;
                }
            }
        }
        return true;
    }

    public String toString(){
        //make string row by row 
        String result = "";

        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                result += data[i][j];
                if(j< c - 1){
                    result += " ";
                }
            }
            if(i < r - 1) {
                result += "\n";
            }
        }
        return result;
    }

    /**
     * Entry point for matrix testing.
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //These tests show sample usage of the matrix, and some basic ideas for testing. They are not comprehensive.

        int[][] data1 = new int[0][0];
        int[][] data2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] data3 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data4 = {{1, 4, 7}, {2, 5, 8}, {3, 6, 9}};
        int[][] data5 = {{1, 4, 7}, {2, 5, 8}};

        Matrix m1 = new CompletedMatrix(data1);
        Matrix m2 = new CompletedMatrix(data2);
        Matrix m3 = new CompletedMatrix(data3);
        Matrix m4 = new CompletedMatrix(data4);
        Matrix m5 = new CompletedMatrix(data5);

        System.out.println("m1 --> Rows: " + m1.getRows() + " Columns: " + m1.getColumns());
        System.out.println("m2 --> Rows: " + m2.getRows() + " Columns: " + m2.getColumns());
        System.out.println("m3 --> Rows: " + m3.getRows() + " Columns: " + m3.getColumns());

        //check for reference issues
        System.out.println("m2 -->\n" + m2);
        data2[1][1] = 101;
        System.out.println("m2 -->\n" + m2);

        //test equals
        System.out.println("m2==null: " + m2.equals(null));             //false
        System.out.println("m3==\"MATRIX\": " + m2.equals("MATRIX"));   //false
        System.out.println("m2==m1: " + m2.equals(m1));                 //false
        System.out.println("m2==m2: " + m2.equals(m2));                 //true
        System.out.println("m2==m3: " + m2.equals(m3));                 //false
        System.out.println("m3==m4: " + m3.equals(m4));                 //true

        //test operations (valid)
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("m1 + m1:\n" + m1.plus(m1));
        System.out.println("2 * m2:\n" + m2.scale(2));
        System.out.println("m2 + m3:\n" + m2.plus(m3));
        System.out.println("m2 - m3:\n" + m2.minus(m3));
        System.out.println("3 * m5:\n" + m5.scale(3));

        //not tested... multiply(). you know what to do.

        //test operations (invalid)
        //System.out.println("m1 + m2" + m1.plus(m2));
        //System.out.println("m1 + m5" + m1.plus(m5));
        //System.out.println("m1 - m2" + m1.minus(m2));
    }
}