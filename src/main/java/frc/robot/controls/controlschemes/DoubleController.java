package frc.robot.controls.controlschemes;

import frc.robot.Config;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;

public class DoubleController extends ControlScheme {

    private Controller primary;
    private Controller secondary;

    public DoubleController(Controller primary, Controller secondary) {
        this.primary = primary;
        this.secondary = secondary;
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
        return primary.getRightBumper();
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getRightTrigger();
    }

    @Override
    public boolean doLowerGear() {
        return primary.getLeftTrigger();
    }

    @Override
    public boolean doAutoBalance() {
        return primary.getYButton();
    }

    @Override
    public boolean doOpenClaw() {
        return secondary.getLeftBumper();
    }

    @Override
    public boolean doCloseClaw() {
        return secondary.getRightBumper();
    }

    @Override
    public double getArmSwivel() {
        double swivel = secondary.getLeftStickX();
        if (Config.Settings.SECONDARY_DEADZONE_ENABLED
                && Math.abs(swivel) < Math.abs(Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE)) {
            swivel = 0;
        }
        return swivel;
    }

    @Override
    public double getArmPivot() {
        double pivot = secondary.getLeftStickY();
        if (Config.Settings.SECONDARY_DEADZONE_ENABLED
                && Math.abs(pivot) < Math.abs(Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE)) {
            pivot = 0;
        }
        return pivot;
    }

    @Override
    public double getArmExtend() {
        double extend = secondary.getRightStickY();
        if (Config.Settings.SECONDARY_DEADZONE_ENABLED
                && Math.abs(extend) < Math.abs(Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE)) {
            extend = 0;
        }
        return extend;
    }

    @Override
    public boolean getLimitOverride() {
        return secondary.getBButton();
    }
}