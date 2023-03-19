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

        return false;
    }

    // @Override
    public boolean doLimitOverride() {
        return false;
    }

    @Override
    public boolean doResetTurret() {

        // throw new UnsupportedOperationException("Unimplemented method
        // 'doResetTurret'");
        return false;
    }

    @Override
    public boolean openClaw() {

        return false;
    }

    @Override
    public boolean closeClaw() {

        return false;
    }

    @Override
    public boolean doSlowTurret() {

        return false;
    }

    // @Override
    // public boolean doLockSwivel() {
    //
    // return false;
    // }

    @Override
    public boolean doAutoHigh() {

        return false;
    }

    @Override
    public boolean doAutoMid() {

        return false;
    }

    @Override
    public boolean doAutoPickup() {

        return false;
    }

    @Override
    public boolean doPlayerStation() {

        return false;
    }

    @Override
    public boolean doSwivelNorth() {

        return false;
    }

    @Override
    public boolean doSwivelEast() {

        return false;
    }

    @Override
    public boolean doSwivelSouth() {

        return false;
    }

    @Override
    public boolean doSwivelWest() {

        return false;
    }

    @Override
    public double getPreset() {

        return 0;
    }

    @Override
    public boolean doUnlockSwivel() {

        return false;
    }

    @Override
    public boolean toggleClaw() {

        return false;
    }

    @Override
    public boolean toggleLimelight() {

        return false;
    }

    @Override
    public boolean doAutoHighLeft() {

        return false;
    }

    @Override
    public boolean doAutoMidLeft() {

        return false;
    }

    @Override
    public boolean doAutoHighRight() {

        return false;
    }

    @Override
    public boolean doAutoMidRight() {

        return false;
    }
}
