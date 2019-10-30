package xyz.fluxinc.MathsGame.calculations;

public abstract class Calculation {

    private double variable1;
    private double variable2;

    public Calculation(double variable1, double variable2) {
        this.variable1 = variable1;
        this.variable2 = variable2;
    }


    public double getVariable1() {
        return variable1;
    }

    public void setVariable1(double variable1) {
        this.variable1 = variable1;
    }

    public double getVariable2() {
        return variable2;
    }

    public void setVariable2(double variable2) {
        this.variable2 = variable2;
    }

    public abstract double getResult();

    public String getCalculation() { return this.toString(); }

    public abstract String toString();
}
