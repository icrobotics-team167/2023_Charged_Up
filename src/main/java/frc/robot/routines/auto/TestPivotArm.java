package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.turret.Pivot;

public class TestPivotArm extends Action {

    Pivot pivot;
    double pivotSpeed;
    double pivotAngle;

    /**
     * Constructs a new TestPivotArm auto routine.
     * @param speed The speed to pivot the arm at.
     * @param angle The target angle.
     */
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
        if (pivotSpeed == 0) {
            return true;
        }
        if (pivotSpeed > 0 && pivot.getPositionDegrees() >= pivotAngle) {
            return true;
        } else if (pivotSpeed < 0 && pivot.getPositionDegrees() <= pivotAngle) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        pivot.stop();
        
    }
    
}
