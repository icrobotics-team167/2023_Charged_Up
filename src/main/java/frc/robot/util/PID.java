package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

    private double lastTime;
    private double lastError;

    private double errorSum;

    private double target = 0.0;

    private double minDerivative;
    private double maxDerivative;

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

    public double compute(double currentValue, double currentTime) {
        double currentError = target - currentValue;
        double deltaError = lastError - currentError;
        double deltaTime = currentTime - lastTime;

        // Adds current error to errorSum for integral calculations
        errorSum += currentError;

        // Calculate the values for the proportional, the integral, and the deriative
        double proportional = currentError * proportionalCoefficient;
        double integral = integralCoeficcient * errorSum;
        double derivative = derivativeCoefficient * (deltaError / deltaTime);

        minDerivative = Math.min(minDerivative, derivative);
        maxDerivative = Math.max(maxDerivative, derivative);

        SmartDashboard.putNumber("PID.minDerivative", minDerivative);
        SmartDashboard.putNumber("PID.maxDerivative", maxDerivative);

        double output = proportional + integral + derivative + initialControlOutput;
        lastError = currentError;
        lastTime = currentTime;

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
