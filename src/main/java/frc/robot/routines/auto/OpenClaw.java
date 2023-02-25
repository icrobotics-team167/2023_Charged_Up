package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class OpenClaw extends Action {

    private final double WAIT_TIME = 0.25;

    private PeriodicTimer timer;

    public OpenClaw() {
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
        Subsystems.claw.closeClaw();
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(WAIT_TIME);
    }

    @Override
    public void done() {
        
    }
}