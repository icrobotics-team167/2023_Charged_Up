package frc.robot.util;

import edu.wpi.first.wpilibj.Timer;

public class PeriodicTimer {

    private double startTime;
    private boolean hasElapsedOnceFlag = false;

    public PeriodicTimer() {
        reset();
    }

    public void reset() {
        startTime = Timer.getFPGATimestamp();
        hasElapsedOnceFlag = false;
    }

    public boolean hasElapsed(double seconds) {
        return get() >= seconds;
    }

    public double get() {
        return Timer.getFPGATimestamp() - startTime;
    }

    public double getAbsoluteTime() {
        return Timer.getFPGATimestamp();
    }

}
