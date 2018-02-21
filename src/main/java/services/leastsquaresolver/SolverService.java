package services.leastsquaresolver;

public class SolverService {


    private final SolverCore solverCore;

    private SolverService(String x, String y, String function,String method,String pivot){

        double[] X = separateStringToVector(x);
        double[] Y = separateStringToVector(y);

        if(X.length!=Y.length){
            throw new IllegalStateException("Debe especificar la misma cantidad de X que de Y.");
        }

        Method METHOD = convertStringToMethod(method);
        Pivot PIVOT = converStringToPivot(pivot);

        solverCore = new SolverCore(X,Y,function,METHOD,PIVOT);
    }

    private double[] separateStringToVector(String string){
        String[] splited = string.split(";");
        int size = splited.length;


        double[] result = new double[size];
        for (int i=0;i<size;i++){
            result[i]=Double.parseDouble(splited[i]);
        }

        return result;

    }

    private Method convertStringToMethod(String string){
        Method method = Method.getMethodFromString(string);
        if (method==null){
            throw new IllegalStateException("Method is illegal.");
        }
        return method;
    }

    private Pivot converStringToPivot(String string){
        Pivot pivot = Pivot.getPivotFromString(string);
        if (pivot==null){
            throw new IllegalStateException("Method is illegal.");
        }
        return pivot;
    }

    public static SolverService createSolverService(String x, String y, String function,String method,String pivot){
        return new SolverService(x,y,function,method,pivot);
    }

    public String getSolution(){
        return solverCore.getSteps();
    }

    public double getError(){
        return solverCore.error();
    }

    public String getCompleteFunction(){
        return solverCore.getFinalExpression();
    }


}
