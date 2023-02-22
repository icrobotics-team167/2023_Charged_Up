package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class PeriodicTimer {

    private double startTime;

    /**
     * Constructs a new timer for use in periodic functions.
     */
    public PeriodicTimer() {
        reset();
    }

    /**
     * Resets the timer.
     */
    public void reset() {
        startTime = Timer.getFPGATimestamp();
    }

    /**
     * Checks if a certain amount of time has passed
     * 
     * @param seconds The amount of time to check for
     * @return Whether or not that amount of time has passed
     */
    public boolean hasElapsed(double seconds) {
        return get() >= seconds;
    }

    /**
     * Gets the current time value, relative to when the timer was last reset.
     * 
     * @return The time value.
     */
    public double get() {
        return Timer.getFPGATimestamp() - startTime;
    }

    /**
     * Gets the absolute time value, relative to when the RoboRIO was powered on.
     * 
     * @return The absolute time value.
     */
    public double getAbsoluteTime() {
        return Timer.getFPGATimestamp();
    }

}
