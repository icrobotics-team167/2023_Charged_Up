package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.util.PeriodicTimer;

public class Wait extends Action {

    private PeriodicTimer timer;
    private double seconds;

    public Wait(double seconds) {
        super();
        timer = new PeriodicTimer();
        this.seconds = seconds;
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(seconds);
    }

    @Override
    public void done() {

    }

}
