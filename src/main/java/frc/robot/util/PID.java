package frc.robot.util;

import java.util.ArrayList;

public class PID {

    // PID tuning values
    // If you're not at the target angle, get there. Higher values make it get there
    // faster.
    private double proportionalCoefficient = 0.0;
    // The longer you haven't been at the target angle, (AKA the bigger the sum of
    // the error) get there faster. Higher values increase the speed at which it
    // accelerates.
    private double integralCoeficcient = 0.0;
    // If you're getting there too fast or too slow, adjust the speed. Higher values
    // adjust more aggressively.
    private double derivativeCoefficient = 0.0;

    private double initialControlOutput;

    private double initTime;
    private double lastTime;
    private double lastError;

    private double errorSum;

    private double target = 0.0;

    public PID(double proportionalCoefficient, double integralCoefficient, double derivativeCoefficient, double time, double target, double initControlOut) {
        this.proportionalCoefficient = proportionalCoefficient;
        this.integralCoeficcient = integralCoefficient;
        this.derivativeCoefficient = derivativeCoefficient;
        initialControlOutput = initControlOut;
        lastTime = time;
        initTime = time;
        lastError = 0.0;
        errorSum = 0.0;
        this.target = target;
    }

    public double compute(double currentValue, double currentTime) {
        double error = target - currentValue;
        double totalElapsedTime = currentTime - initTime;

        // Adds current error to errorSum for integral calculations
        errorSum += error;

        // Calculate the values for the proportional, the integral, and the deriative
        double proportional = error * proportionalCoefficient;
        double integral = errorSum * (integralCoeficcient * 50.0/totalElapsedTime);
        double derivative = (error - lastError) * (derivativeCoefficient * totalElapsedTime/50.0);

        double output = proportional + integral + derivative + initialControlOutput;

        return output;
    }

    public void setPID(double p, double i, double d) {
        proportionalCoefficient = p;
        integralCoeficcient = i;
        derivativeCoefficient = d;
    }

    public void resetIntegralSum() {
        errorSum = 0.0;
    }
}
