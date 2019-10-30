package xyz.fluxinc.MathsGame.factories;

import xyz.fluxinc.MathsGame.calculations.Calculation;

import java.util.Random;

public abstract class CalculationFactory {

    private int minimumValue;
    private int maximumValue;
    private Random rNG;

    public CalculationFactory(int minimumValue, int maximumValue) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.rNG = new Random();
    }

    public abstract Calculation generateCalculation();


    public int getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }

    public int generateBoundRandomNumber(int minimumValue, int maximumValue) {
        return this.rNG.nextInt(maximumValue) + minimumValue;
    }
}
