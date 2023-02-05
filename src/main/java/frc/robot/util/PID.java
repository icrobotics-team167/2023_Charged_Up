package frc.robot.util;

import java.util.ArrayList;

public class PID {

    private double P = 0.0;
    private double I = 0.0;
    private double D = 0.0;

    private double lastTime;
    private double lastError;

    private double errorSum;

    private double target = 0.0;

    public PID (double p, double i, double d, double t) {
        P = p;
        I = i;
        D = d;
        lastTime = t;
        lastError = 0.0;
    }

    public PID (double p, double i, double d, double t, double target) {
        P = p;
        I = i;
        D = d;
        lastTime = t;
        lastError = 0.0;
        this.target = target;
    }

    public double compute (double currentValue, double currentTime) {
        double error = target - currentValue;
        double dT = currentTime - lastTime;
        
        //Calculate the values for the proportional, the integral, and the deriative
        double proportional = error * P;
        double integral = errorSum * I * dT;
        double derivative = (error - lastError) * D * dT;

        double output = 0.0;

        return 0.0;
    }

    public void setPID (double p, double i, double d) {
        P = p;
        I = i;
        D = d;
    }

    public void resetIntegralSum() {
        errorSum = 0.0;
    }
}
