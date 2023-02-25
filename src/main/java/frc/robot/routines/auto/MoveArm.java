package frc.robot.routines.auto;

import frc.robot.util.PeriodicTimer;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.turret.*;

public class MoveArm extends Action {

    private TurretPosition targetState;
    private double timeoutSeconds;

    private PeriodicTimer timer;

    private boolean doneMoving = false;

    public MoveArm(TurretPosition targetState, double timeoutSeconds) {
        this.targetState = targetState;
        this.timeoutSeconds = timeoutSeconds;
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
        doneMoving = Subsystems.turret.moveTo(targetState);
    }

    @Override
    public boolean isDone() {
        return doneMoving || timer.hasElapsed(timeoutSeconds);
    }

    @Override
    public void done() {
        Subsystems.turret.stop();
    }

}
