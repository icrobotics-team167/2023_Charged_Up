package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;

public class SingleController extends ControlScheme {

    private Controller primary;

    public SingleController(Controller controller) {
        primary = controller;
    }

    // Drive

    @Override
    public double getTankLeftSpeed() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getTankRightSpeed() {
        double speed = primary.getRightStickY();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getArcadeThrottle() {
        double speed = primary.getLeftStickY();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(speed) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            speed = 0;
        }
        return speed;
    }

    @Override
    public double getArcadeWheel() {
        double wheel = primary.getRightStickX();
        if (Config.Settings.PRIMARY_DEADZONE_ENABLED
                && Math.abs(wheel) < Math.abs(Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE)) {
            wheel = 0;
        }
        return wheel;
    }

    @Override
    public boolean doSwitchHighGear() {
        return primary.getRightTrigger();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getRightBumper();
    }

    @Override
    public boolean doSlowMode() {
        return primary.getLeftTrigger();
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
    public boolean doLimitOverride() {
        return false;
    }

    @Override
    public boolean doResetTurret() {

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
