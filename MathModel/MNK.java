public class MNK {
    public static ObservableList<Double> mnkValues = FXCollections.observableArrayList ();

    static double []coefMnk(){
        int n = MainController.list.size ();
        double[][] a = new double[n][5];
        for (int i = 1; i < n; i++) {
            a[i][0] = MainController.list.get (i-1).getPeopleAll ();
            a[i][1] = MainController.list.get (i).getBirth ();
            a[i][2] = MainController.list.get (i).getDeath ();
            a[i][3] = MainController.list.get (i).getPeopleIn ();
            a[i][4] = MainController.list.get (i).getPeopleOut ();
        }
        double[] b = new double[n];
        for (int i = 1; i < n; i++) {
            b[i] = MainController.list.get (i).getPeopleAll ();
        }

        float []res=multVector (multMatrix (inverseMatrix (multMatrix (transposeMatrix (a),a)), transposeMatrix (a)), b);

        double[] coef=new double[5];
        for (int i=0; i<5; i++){
            coef[i]=res[i];
        }
        for (int i = 1; i < n; i++) {
            double allPeople = a[i][0]*coef[0]+a[i][1]*coef[1]+a[i][2]*coef[2]+a[i][3]*coef[3]+a[i][4]*coef[4];
            mnkValues.add (allPeople);
        }
        return coef;
    }

    private static double[][] multMatrix(double[][]A, double[][]B)
    {
        int m=A.length;
        int n=B[0].length;
        int v=B.length;
        double[][] C = new double[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                for (int k=0 ; k<v; k++){
                    C[i][j]+=A[i][k]*B[k][j];
                }
            }
        }
        return C;
    }

    private static float[] multVector(double[][]A, double[]b)
    {
        int m=A.length;
        int n=b.length;

        float[] C = new float[m];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                C[i]+=A[i][j]*b[j];
            }
        }
        return C;

    }

    private static double[][] transposeMatrix(double[][]A)
    {
        int m=A[0].length;
        int n=A.length;
        double[][] B = new double[m][n];
        for (int j=0; j<n; j++){
            for (int i=0; i<m; i++)
            {B[i][j]=A[j][i];}
        }
        return B;
    }

    private static double M[][];
    //определитель матрицы
    private static double det(double A[][]){
        int n=A.length;
        double B;

        if (n == 1) B = A[0][0];
        else if (n == 2) B = A[0][0]*A[1][1] - A[1][0]*A[0][1];
        else{
            B=0;
            for (int j1=0; j1<n; j1++){
                M = mainMinors (A, j1);
                B += Math.pow(-1.0, 1.0+j1+1.0) * A[0][j1] * det(M);
            }
        }
        return B;
    }
    //главные миноры, типа те, которые по первому ряду
    private static double[][] mainMinors(double A[][], int j1){
        int N=A.length;
        M = new double[N-1][];
        for (int k=0; k<(N-1); k++)
            M[k] = new double[N-1];
        for (int i=1; i<N; i++){
            int j2=0;
            for (int j=0; j<N; j++){
                if(j == j1)
                    continue;
                M[i-1][j2] = A[i][j];
                j2++;
            }
        }
        return M;
    }

    public static double[][] Minor (double A[][], int i1, int j1){
        final int n = A.length - 1;
        final int m = A[0].length - 1;

        double[][] B = new double[ n ][ m ];

        for ( int i = 0; i < A.length; i++ ) {
            boolean isRowDeleted = i1 < i;
            int resultRowIndex = isRowDeleted ? i - 1 : i;

            for ( int j = 0; j < A[i].length; j++ ) {
                boolean isColDeleted = j1 < j;
                int resultColIndex = isColDeleted ? j - 1 : j;

                if (i1 != i & j1!= j)
                    B[resultRowIndex][resultColIndex] = A[i][j];
            }
        }
        return B;
    }
    //обратная матрица
    public static double[][] inverseMatrix (double A[][]){
        int N=A.length;
        double d=det(A);
        double[][] Minor = new double[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++){
                Minor[i][j]=det(Minor(A, i, j));
            }
        }
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++){
                if (((i+j)%2)!=0) Minor[i][j]=-Minor[i][j];
            }
        }
        A=transposeMatrix (Minor);
        for (int i=0; i<N; i++){
            for (int j=0; j<N; j++){
                A[i][j]=A[i][j]/d;
            }

        }
        return A;
    }
}
