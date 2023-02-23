package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.turret.*;

public class ResetArm extends Action {
    Claw claw;
    ExtendRetract extendRetract;
    Pivot pivot;
    Swivel swivel;

    public ResetArm() {
        claw = Claw.getInstance();
        extendRetract = ExtendRetract.getInstance();
        pivot = Pivot.getInstance();
        swivel = Swivel.getInstance();
    }

    @Override
    public void init() {
    }

    @Override
    public void periodic() {
        claw.closeClaw();
        if (extendRetract.getPositionInches() > ExtendRetract.MIN_EXTENSION) {
            extendRetract.move(-1);
        }
        if (pivot.getPositionDegrees() < Pivot.MAX_PIVOT_ANGLE) {
            pivot.move(1);
        }
        if (swivel.getPositionDegrees() > 0) {
            swivel.move(-1);
        } else if (swivel.getPositionDegrees() < 0) {
            swivel.move(1);
        }
    }

    @Override
    public boolean isDone() {
        if (extendRetract.getPositionInches() <= ExtendRetract.MIN_EXTENSION
                && pivot.getPositionDegrees() >= Pivot.MAX_PIVOT_ANGLE
                && Math.abs(swivel.getPositionDegrees()) <= 0.5) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        extendRetract.stop();
        pivot.stop();
        swivel.stop();
    }

}
