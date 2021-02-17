package xyz.fluxinc.MathsGame.factories;

import xyz.fluxinc.MathsGame.MathsGame;
import xyz.fluxinc.MathsGame.calculations.*;
import xyz.fluxinc.MathsGame.errors.LoggingFailedException;

import java.util.ArrayList;

public class HardCalculationFactory extends CalculationFactory {

    private static ArrayList<Integer> validDivisions;
    static {
        validDivisions = new ArrayList<>();
        validDivisions.add(2);
        validDivisions.add(4);
        validDivisions.add(5);
        validDivisions.add(10);
    }

    public HardCalculationFactory(int minimumValue, int maximumValue) {
        super(minimumValue, maximumValue);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Calculation generateCalculation() {
        int value1 = this.generateBoundRandomNumber(this.getMinimumValue(), this.getMaximumValue());
        int value2 = this.generateBoundRandomNumber(this.getMinimumValue(), this.getMaximumValue());

        switch (this.generateBoundRandomNumber(0, 4)) {
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
            case 3:
                return new DivisionCalculation(value1, validDivisions.get(this.generateBoundRandomNumber(0, validDivisions.size()-1)));
            default:
                try { MathsGame.getLogger().error("Reached unexpected default in Case-Switch"); }
                catch (LoggingFailedException e) { e.printStackTrace(); }
        }
        return null;
    }
}
