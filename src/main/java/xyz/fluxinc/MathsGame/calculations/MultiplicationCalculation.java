package xyz.fluxinc.MathsGame.calculations;

public class MultiplicationCalculation extends Calculation {

    public MultiplicationCalculation(double variable1, double variable2) {
        super(variable1, variable2);
    }

    @Override
    public double getResult() {
        return this.getVariable1() * this.getVariable2();
    }

    @Override
    public String toString() {
        return this.getVariable1() +  " X " + this.getVariable2();
    }
}
