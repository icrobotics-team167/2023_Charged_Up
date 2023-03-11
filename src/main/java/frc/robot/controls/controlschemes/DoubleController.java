package frc.robot.controls.controlschemes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.controls.controllers.Controller;

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
    public boolean openClaw() {
        return secondary.getRightBumper();
    }

    @Override
    public boolean closeClaw() {
        return secondary.getLeftBumper();
    }

    @Override
    public double getArmSwivel() {
        double swivel;
        if (secondary.getRightTriggerValue() > secondary.getLeftTriggerValue()) {
            swivel = secondary.getRightTriggerValue();
        } else {
            swivel = -secondary.getLeftTriggerValue();
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
    public boolean doLimitOverride() {
        return secondary.getDPadRight();
    }

    @Override
    public boolean doResetTurret() {
        return secondary.getStartButton();
    }

    @Override
    public boolean doSlowTurret() {
        return secondary.getBButton();
    }

    @Override
    public boolean doLockSwivel() {
        return secondary.getLeftTrigger();
    }

    @Override
    public boolean doAutoHigh() {
        return false;
    }

    @Override
    public boolean doAutoMid() {
        return secondary.getYButton();
    }

    @Override
    public boolean doAutoPickup() {
        return secondary.getAButton();
    }

    @Override
    public boolean doPlayerStation() {
        return secondary.getXButton();
    }

    @Override
    public boolean doSwivel180() {
        return secondary.getBackButton();
    }
}