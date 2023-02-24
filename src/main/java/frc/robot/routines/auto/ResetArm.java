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
        if (!extendRetract.tooFarIn()) {
            extendRetract.move(-1);
        }
        if (!pivot.tooFarUp()) {
            pivot.move(1);
        }
        if (swivel.getPositionDegrees() > 0) {
            swivel.move(-0.5);
        } else if (swivel.getPositionDegrees() < 0) {
            swivel.move(0.5);
        }
    }

    @Override
    public boolean isDone() {
        if (extendRetract.tooFarIn()
                && pivot.tooFarUp()
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
