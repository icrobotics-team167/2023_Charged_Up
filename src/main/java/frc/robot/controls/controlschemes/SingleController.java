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
    public boolean intake() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean outtake() {
        return false;
    }

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
    public boolean doUnlockSwivel() {
        return false;
    }

    @Override
    public boolean toggleLimelight() {
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

    @Override
    public int getPositionOffset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean offsetUpdated() {
        // TODO Auto-generated method stub
        return false;
    }
}
