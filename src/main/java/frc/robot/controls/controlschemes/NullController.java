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
    public boolean doLimitOverride() {
        return false;
    }

    @Override
    public boolean doResetTurret() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'doResetTurret'");
        return false;
    }

    @Override
    public boolean intake() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean outtake() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSlowTurret() {
        // TODO Auto-generated method stub
        return false;
    }

    // @Override
    // public boolean doLockSwivel() {
    // // TODO Auto-generated method stub
    // return false;
    // }

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
    public boolean doSwivelNorth() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelEast() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelSouth() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doSwivelWest() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getPreset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean doUnlockSwivel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean toggleClaw() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean toggleLimelight() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoHighLeft() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoMidLeft() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoHighRight() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean doAutoMidRight() {
        // TODO Auto-generated method stub
        return false;
    }
}
