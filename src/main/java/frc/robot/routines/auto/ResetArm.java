package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.turret.*;
import frc.robot.util.PeriodicTimer;

public class ResetArm extends Action {

    boolean done;
    PeriodicTimer timer;

    public ResetArm() {
        this(5);
    }

    public ResetArm(double timeout) {
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
    }

    @Override
    public void periodic() {
        done = Subsystems.turret.moveTo(TurretPosition.INITIAL);
    }

    @Override
    public boolean isDone() {
        return done;
    }

    @Override
    public void done() {
        Subsystems.turret.stop();
    }

}
