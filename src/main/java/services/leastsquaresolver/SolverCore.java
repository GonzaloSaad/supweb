/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.leastsquaresolver;

import java.text.DecimalFormat;
import services.leastsquaresolver.entryparser.EntryParser;
import services.leastsquaresolver.mathematics.Function;
import services.leastsquaresolver.mathematics.Matrix;
import services.leastsquaresolver.mathematics.GenericVector;
import services.leastsquaresolver.mathematics.Vector;

/**
 *
 * @author Gonzalo
 */
public class SolverCore {

    private final Vector X;
    private final Vector Y;
    private final GenericVector<Function> F;
    private final Vector S;
    private String STEPS;
    private final Matrix A;
    private final Vector B;
    private final GenericVector<String> C;
    private final Method METHOD;
    private final Pivot PIVOT;

    public SolverCore(double x[], double y[], Function functions[], String constants[], Method method, Pivot pivot) {
        X = new Vector(x);
        Y = new Vector(y);
        F = new GenericVector(functions);
        A = new Matrix(F.size());
        B = new Vector(F.size());
        C = new GenericVector(constants);
        S = new Vector(F.size());
        PIVOT = pivot;
        METHOD = method;
        solve();
    }

    public SolverCore(double x[], double y[], String input, Method method, Pivot pivot) {
        EntryParser ep = new EntryParser(input);
        X = new Vector(x);
        Y = new Vector(y);
        F = new GenericVector(ep.getFunctionsArray());
        A = new Matrix(F.size());
        B = new Vector(F.size());
        C = new GenericVector(ep.getConstantsArray());
        S = new Vector(F.size());
        PIVOT = pivot;
        METHOD = method;
        solve();
    }

    private void solve() {
        StringBuilder s = new StringBuilder();
        buildMatrices(s);
        pivot(s);
        reduce(s);
        substitution(s);
        conclude(s);
        STEPS = s.toString();
    }

    private void buildMatrices(StringBuilder sb) {
        int N = F.size();
        int M = X.size();

        Matrix R = new Matrix(M, N);

        for (int i = 0; i < N; i++) {
            R.copyVectorToColumn(i, X.operateFunction(F.getElem(i)));
        }

        for (int i = 0; i < N; i++) {
            B.setElem(i, R.getColumn(i).scalarProduct(Y));
            for (int j = 0; j < N; j++) {
                A.setElem(i, j, R.getColumn(i).scalarProduct(R.getColumn(j)));
            }
        }
        sb.append("Step 1: Building the matrices.\n");
        sb.append(showABC());

    }

    private void pivot(StringBuilder sb) {

        sb.append("Part 2: Pivot");
        sb.append("\nType:\t");

        if (PIVOT.equals(Pivot.PARTIAL)) {

            if (A.needsPartialPivot()) {
                sb.append("Partial Pivot.");
                partialPivot(sb);
            } else {
                sb.append("\nNo partial pivot is needed! One less step.");
            }

        } else if (A.needsTotalPivot()) {
            sb.append("Total pivot.");
            totalPivot(sb);
        } else {
            sb.append("\nNo need for total pivot! One less step!");
        }

    }

    private void partialPivot(StringBuilder sb) {
        int imax = A.getIndexMaxOfColumn(0);
        int jmax = A.getIndexMaxOfRow(0);

        if (A.isSymmetrical()) {
            sb.append("\t\t\t\t\t**You could partial pivot rows or columns, since A is symmetrical.");
        }

        if (A.getElem(0, jmax) > A.getElem(imax, 0)) {

            A.colOperation(0, jmax);
            C.operation(0, jmax);
            F.operation(0, jmax);

            sb.append("\nMaximum value in [").
                    append("1,")
                    .append(jmax + 1)
                    .append("]");
            sb.append("\n\tColumns:");
            sb.append("\n\t\t1)Change column 1 with column ")
                    .append(jmax + 1)
                    .append(" in 'A' matrix.");
            sb.append("\n\t\t2)Change element 1 with element ")
                    .append(jmax + 1)
                    .append(" in 'C' vector.\n");
            sb.append(showAC());

        } else {
            A.rowOperation(0, imax);
            B.operation(0, imax);

            sb.append("\nMaximum value in [").
                    append(imax + 1)
                    .append(",1")
                    .append("]");
            sb.append("\n\tRows:");
            sb.append("\n\t\t1)Change row 1 with row ")
                    .append(imax + 1)
                    .append(" in 'A' matrix.");
            sb.append("\n\t\t2)Change element 1 with element ")
                    .append(imax + 1)
                    .append(" in 'B' vector.\n");
            sb.append(showAB());

        }

    }

    private void totalPivot(StringBuilder sb) {
        int imax, jmax, indexes[];
        indexes = A.getMaxIndex();
        imax = indexes[0];
        jmax = indexes[1];

        sb.append("\nMaximum value in [").
                append(imax + 1)
                .append(",")
                .append(jmax + 1)
                .append("]");
        if (imax != 0) {
            A.rowOperation(0, imax);
            B.operation(0, imax);
            sb.append("\n\tRows:");
            sb.append("\n\t\t1)Change row 1 with row ")
                    .append(imax + 1)
                    .append(" in 'A' matrix.");
            sb.append("\n\t\t2)Change element 1 with element ")
                    .append(imax + 1)
                    .append(" in 'B' vector.\n");

        } else {
            sb.append("No movement required for rows!\n");
        }

        if (jmax != 0) {
            A.colOperation(0, jmax);
            C.operation(0, jmax);
            F.operation(0, jmax);
            sb.append("\n\tColumns:");
            sb.append("\n\t\t1)Change column 1 with column ")
                    .append(jmax + 1)
                    .append(" in 'A' matrix.");
            sb.append("\n\t\t2)Change element 1 with element ")
                    .append(jmax + 1)
                    .append(" in 'C' vector.\n");
        } else {
            sb.append("No movement required for columns!\n");
        }

        sb.append(showABC());

    }

    private void reduce(StringBuilder sb) {
        sb.append("\nPart 3: Reduce system.")
                .append("\nMethod:\t");
        if (METHOD.equals(Method.GAUSS)) {
            sb.append("Gauss");
            gauss(sb);
        } else {
            sb.append("Gauss-Jordan");
            gaussJordan(sb);
        }
    }

    private void gauss(StringBuilder sb) {
        int N = F.size();
        for (int k = 0; k < N - 1; k++) {
            sb.append("\nStep: ")
                    .append(k + 1);
            for (int i = k + 1; i < N; i++) {
                sb.append("\n\tRow: ").
                        append(i + 1);
                double m = -(A.getElem(i, k) / A.getElem(k, k));
                sb.append("\n\tm")
                        .append(k + 1)
                        .append(i + 1)
                        .append("=")
                        .append(m);
                B.operation(i, k, m);
                A.rowOperation(i, k, m);
                sb.append(showAB());

            }
        }
    }

    private void gaussJordan(StringBuilder sb) {
        int N = F.size();

        for (int k = 0; k < N - 1; k++) {
            sb.append("\nStep: ")
                    .append(k + 1);
            for (int i = k + 1; i < N; i++) {
                sb.append("\n\tRow: ").
                        append(i + 1);
                double m = -(A.getElem(i, k) / A.getElem(k, k));
                sb.append("\n\tm")
                        .append(k + 1)
                        .append(i + 1)
                        .append("=")
                        .append(m);
                B.operation(i, k, m);
                A.rowOperation(i, k, m);
                sb.append(showAB());

            }

        }
        for (int k = N - 1; k > 0; k--) {
            sb.append("\nStep: ")
                    .append(N - 1 + (N - k));
            for (int i = k - 1; i >= 0; i--) {
                sb.append("\n\tRow: ").
                        append(i + 1);
                double m = -(A.getElem(i, k) / A.getElem(k, k));
                sb.append("\n\tm")
                        .append(N - 1 + (N - k))
                        .append(i + 1)
                        .append("=")
                        .append(m);
                B.operation(i, k, m);
                A.rowOperation(i, k, m);
                sb.append(showAB());
            }

        }
    }

    private void substitution(StringBuilder sb) {
        sb.append("Part 4: Substitution");
        if (METHOD.equals(Method.GAUSS)) {
            gaussSubstitution(sb);
        } else {
            gaussJordanSubstitution(sb);
        }
    }

    private void gaussSubstitution(StringBuilder sb) {
        int N = F.size();

        for (int i = N - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = N - 1; j > i; j--) {
                sum += S.getElem(j) * A.getElem(i, j);
            }
            S.setElem(i, (B.getElem(i) - sum) / A.getElem(i, i));
        }
        sb.append(C.showEqualed(S));
    }

    private void gaussJordanSubstitution(StringBuilder sb) {
        int N = F.size();
        for (int i = 0; i < N; i++) {
            S.setElem(i, B.getElem(i) / A.getElem(i, i));
        }
        sb.append(C.showEqualed(S));
    }

    private void conclude(StringBuilder sb) {
        sb.append("\nError:\t")
                .append(this.error());
        sb.append("\nFinal expression:\t")
                .append(this.getFinalExpression());
    }

    public double error() {
        double s = 0;
        Function f = this.buildCompleteFunction();
        for (int i = 0; i < X.size(); i++) {
            s += Math.pow(f.apply(new double[]{X.getElem(i)}) - Y.getElem(i), 2);
        }
        return s;
    }

    private Function buildCompleteFunction() {
        StringBuilder sb = new StringBuilder("F(x)=");
        for (int i = 0; i < F.size(); i++) {
            sb.append(S.getElem(i))
                    .append("*")
                    .append(F.getElem(i).getExpression());
            if (i != F.size() - 1) {
                sb.append("+");
            }

        }
        return new Function(sb.toString());
    }

    public String getFinalExpression() {
        DecimalFormat df = new DecimalFormat("#.####");
        StringBuilder sb = new StringBuilder("F(x) = ");
        for (int i = 0; i < F.size(); i++) {
            sb.append(df.format(S.getElem(i)))
                    .append(" * ")
                    .append(F.getElem(i).getExpression());
            if (i != F.size() - 1) {
                sb.append(" + ");
            }

        }
        return sb.toString().replace("+ -", "- ");
    }

    private String showABC() {
        StringBuilder s = new StringBuilder();
        s.append(showAB());
        s.append("C:\n");
        s.append(C);
        return s.toString();
    }

    private String showAB() {
        StringBuilder s = new StringBuilder();
        s.append("\nA:\n");
        s.append(A);
        s.append("B:\n");
        s.append(B);
        return s.toString();
    }

    private String showAC() {
        StringBuilder s = new StringBuilder();
        s.append("\nA:\n");
        s.append(A);
        s.append("C:\n");
        s.append(C);
        return s.toString();
    }

    public String getSteps() {
        return STEPS;
    }

}
