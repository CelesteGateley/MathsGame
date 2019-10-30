package xyz.fluxinc.MathsGame.factories;

import xyz.fluxinc.MathsGame.calculations.AdditionCalculation;
import xyz.fluxinc.MathsGame.calculations.Calculation;
import xyz.fluxinc.MathsGame.calculations.MultiplicationCalculation;
import xyz.fluxinc.MathsGame.calculations.SubtractionCalculation;

public class EasyCalculationFactory extends CalculationFactory {

    public EasyCalculationFactory(int minimumValue, int maximumValue) {
        super(minimumValue, maximumValue);
    }

    @Override
    public Calculation generateCalculation() {
        int value1 = this.generateBoundRandomNumber(this.getMinimumValue(), this.getMaximumValue());
        int value2 = this.generateBoundRandomNumber(this.getMinimumValue(), this.getMaximumValue());

        switch (this.generateBoundRandomNumber(0, 3)) {
            case 0:
                return new AdditionCalculation(value1, value2);
            case 1:
                if (value1 > value2) {
                    return new SubtractionCalculation(value1, value2);
                } else {
                    return new SubtractionCalculation(value2, value1);
                }
            case 2:
                return new MultiplicationCalculation(value1, value2);
            default:
                System.out.println("This should not be reached!!");
        }
        return null;
    }
}
