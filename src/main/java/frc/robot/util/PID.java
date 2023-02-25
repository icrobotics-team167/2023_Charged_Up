package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PID {

    private double proportionalCoefficient = 0.0;
    private double integralCoeficcient = 0.0;
    private double derivativeCoefficient = 0.0;

    private double initialControlOutput;

    private double lastTime;
    private double lastError;

    private double errorSum;

    private double target = 0.0;

    /**
     * Constructs a new PID controller instance.
     * 
     * @param proportionalCoefficient Proportional value. If you're not at the
     *                                target angle, get there. Higher values make it
     *                                get there faster.
     * @param integralCoefficient     Integral value. The longer you haven't been at
     *                                the target angle, (AKA the bigger the sum
     *                                of the error) get there faster. Higher values
     *                                increase the speed at which it accelerates.
     * @param derivativeCoefficient   Derivative value. If you're getting there too
     *                                fast or too slow, adjust the speed. Higher
     *                                values adjust more aggressively.
     * @param time                    The current time. Used to calulate delta time.
     * @param target                  The target for the PID controller.
     */
    public PID(double proportionalCoefficient, double integralCoefficient, double derivativeCoefficient, double time,
            double target) {
        this(proportionalCoefficient, integralCoefficient, derivativeCoefficient, time, target, 0);
    }

    /**
     * Constructs a new PID controller instance.
     * 
     * @param proportionalCoefficient Proportional value. If you're not at the
     *                                target angle, get there. Higher values make it
     *                                get there faster.
     * @param integralCoefficient     Integral value. The longer you haven't been at
     *                                the target angle, (AKA the bigger the sum
     *                                of the error) get there faster. Higher values
     *                                increase the speed at which it accelerates.
     * @param derivativeCoefficient   Derivative value. If you're getting there too
     *                                fast or too slow, adjust the speed. Higher
     *                                values adjust more aggressively.
     * @param time                    The current time. Used to calulate delta time.
     * @param target                  The target for the PID controller.
     * @param initControlOut          The initial control input when transitioning
     *                                from manual input. Probably shouldn't use.
     */
    public PID(double proportionalCoefficient, double integralCoefficient, double derivativeCoefficient, double time,
            double target, double initControlOut) {
        this.proportionalCoefficient = proportionalCoefficient;
        this.integralCoeficcient = integralCoefficient;
        this.derivativeCoefficient = derivativeCoefficient;
        initialControlOutput = initControlOut;
        lastTime = time;
        lastError = 0.0;
        errorSum = 0.0;
        this.target = target;
    }

    /**
     * Computes a PID output, using the current input value and the current time.
     * 
     * @param currentValue The current input value. Used to calculate error from the
     *                     target and delta error.
     * @param currentTime  The current time. Used to calculate delta time.
     * @return The PID output.
     */
    public double compute(double currentValue, double currentTime) {
        double currentError = target - currentValue;
        double deltaError = lastError - currentError;
        if (Math.abs(deltaError) > 1) {
            deltaError = 0;
        }
        // double deltaTime = currentTime - lastTime;

        // Adds current error to errorSum for integral calculations
        errorSum += currentError;

        // Calculate the values for the proportional, the integral, and the deriative
        double proportional = currentError * proportionalCoefficient;
        double integral = integralCoeficcient * errorSum;
        double derivative = (-1) * derivativeCoefficient * (deltaError);

        double output = proportional + integral + derivative + initialControlOutput;
        SmartDashboard.putNumber("P", proportional);
        SmartDashboard.putNumber("deltaError", deltaError);
        SmartDashboard.putNumber("I", integral);
        SmartDashboard.putNumber("D", derivative);
        lastError = currentError;
        lastTime = currentTime;

        return output;
    }

    /**
     * Sets new PID control parameters.
     * 
     * @param proportional The new proportional coefficient.
     * @param integral     The new integral coefficient.
     * @param derivative   The new derivative coefficient.
     */
    public void setPID(double proportional, double integral, double derivative) {
        proportionalCoefficient = proportional;
        integralCoeficcient = integral;
        derivativeCoefficient = derivative;
    }

    /**
     * Resets the integral sum.
     */
    public void resetIntegralSum() {
        errorSum = 0.0;
    }
}
