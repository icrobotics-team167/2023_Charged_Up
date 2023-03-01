package frc.robot.routines.auto;

import frc.robot.util.PeriodicTimer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.turret.*;

public class MoveArm extends Action {

    private TurretPosition targetState;
    private double timeOutSeconds = 0;

    private PeriodicTimer timer;

    private boolean doneMoving = false;

    public MoveArm(TurretPosition targetState, double timeOutSeconds) {
        this.targetState = targetState;
        this.timeOutSeconds = timeOutSeconds;
        timer = new PeriodicTimer();
    }

    public MoveArm(TurretPosition targetState) {
        this(targetState, 0);
    }

    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
        doneMoving = Subsystems.turret.moveTo(targetState);
        SmartDashboard.putBoolean("MoveArm.doneMoving", doneMoving);
    }

    @Override
    public boolean isDone() {
        if (timeOutSeconds != 0) {
            return doneMoving || timer.hasElapsed(timeOutSeconds);
        } else {
            return doneMoving;
        }
    }

    @Override
    public void done() {
        Subsystems.turret.stop();
    }

}
