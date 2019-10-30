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

    public String getVariable1String() {
        if (this.variable1 == (int)this.variable1) { return String.valueOf((int)this.getVariable1()); }
        else { return String.valueOf(this.getVariable1()); }
    }

    public String getVariable2String() {
        if (this.variable2 == (int)this.variable2) { return String.valueOf((int)this.getVariable2()); }
        else { return String.valueOf(this.getVariable2()); }
    }

    public double getVariable2() {
        return variable2;
    }

    public void setVariable2(double variable2) {
        this.variable2 = variable2;
    }

    public abstract double getResult();

    public String getFullCalculation() {
        String resultString;
        if (this.getResult() == (int)this.getResult()) { resultString = String.valueOf((int)this.getResult()); }
        else { resultString = String.valueOf(this.getResult()); }
        return this.toString() + " = " + resultString;
    }

    public abstract String toString();
}
