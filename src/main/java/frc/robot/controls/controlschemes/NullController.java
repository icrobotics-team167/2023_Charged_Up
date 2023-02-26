package frc.robot.controls.controlschemes;

public class NullController extends ControlScheme {

    // Drive

    @Override
    public double getTankLeftSpeed() {
        return 0;
    }

    @Override
    public double getTankRightSpeed() {
        return 0;
    }

    @Override
    public double getArcadeThrottle() {
        return 0;
    }

    @Override
    public double getArcadeWheel() {
        return 0;
    }

    @Override
    public boolean doSwitchHighGear() {
        return false;
    }

    @Override
    public boolean doSwitchLowGear() {
        return false;
    }

    @Override
    public double getArmSwivel() {
        return 0;
    }

    @Override
    public double getArmPivot() {
        return 0;
    }

    @Override
    public double getArmExtend() {
        return 0;
    }

    @Override
    public boolean doSlowMode() {
        // TODO Auto-generated method stub
        return false;
    }

    // @Override
    // public boolean doLimitOverride() {
    //     // TODO Auto-generated method stub
    //     // throw new UnsupportedOperationException("Unimplemented method 'getLimitOverride'");
    //     return false;
    // }

    @Override
    public boolean doResetTurret() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'doResetTurret'");
        return false;
    }

    @Override
    public boolean openClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean closeClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    // @Override
    // public boolean slowDownTurn() {
    //     // TODO Auto-generated method stub
    //     return false;
    // }

    @Override
    public boolean doSlowTurret() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doLockSwivel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoHigh() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoMid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoPickup() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doPlayerStation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivel180() {
        // TODO Auto-generated method stub
        return false;
    }
}
