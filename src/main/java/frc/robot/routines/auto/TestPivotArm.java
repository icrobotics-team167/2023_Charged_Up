package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.turret.Pivot;

public class TestPivotArm extends Action {

    Pivot pivot;
    double pivotSpeed;
    double pivotAngle;
    public TestPivotArm(double speed, double angle) {
        pivotSpeed = speed;
        pivotAngle = angle;
    }

    @Override
    public void init() {
        pivot = Pivot.getInstance();
    }

    @Override
    public void periodic() {
        pivot.move(pivotSpeed);
        
    }

    @Override
    public boolean isDone() {
        if (pivotAngle > 0 && pivot.getPositionDegrees() >= pivotAngle) {
            return true;
        } else if (pivotAngle <= 0 && pivot.getPositionDegrees() <= pivotAngle) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        pivot.stop();
        
    }
    
}
