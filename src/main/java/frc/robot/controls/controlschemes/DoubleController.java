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
        double swivel = secondary.getRightStickX();
        if (Config.Settings.SECONDARY_DEADZONE_ENABLED
                && Math.abs(swivel) < Math.abs(Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE)) {
            swivel = 0;
        }
        return swivel;
    }

    @Override
    public double getArmPivot() {
        double pivot = secondary.getRightStickY();
        if (Config.Settings.SECONDARY_DEADZONE_ENABLED
                && Math.abs(pivot) < Math.abs(Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE)) {
            pivot = 0;
        }
        return pivot;
    }

    @Override
    public double getArmExtend() {
        double extend = secondary.getLeftStickY();
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
        return secondary.getRightTrigger();
    }

    @Override
    public boolean doUnlockSwivel() {
        return secondary.getLeftTrigger();
    }

    @Override
    public boolean doAutoHigh() {
        return getPreset() == 0 && secondary.getYButton();
    }

    @Override
    public boolean doAutoMid() {
        return getPreset() == 0 && secondary.getBButton();
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
    public boolean doSwivelNorth() {
        return secondary.getDPadUp();
    }

    @Override
    public boolean doSwivelEast() {
        return secondary.getDPadRight();
    }

    @Override
    public boolean doSwivelSouth() {
        return secondary.getDPadDown();
    }

    @Override
    public boolean doSwivelWest() {
        return secondary.getDPadLeft();
    }

    @Override
    public double getPreset() {
        if (secondary.getLeftStickX() == -1) {
            return -1;
        } else if (secondary.getLeftStickX() == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean toggleClaw() {

        return false;
    }

    @Override
    public boolean toggleLimelight() {
        return primary.getRightBumperToggled();
    }

    @Override
    public boolean doAutoHighLeft() {
        return getPreset() == -1 && secondary.getYButton();
    }

    @Override
    public boolean doAutoMidLeft() {
        return getPreset() == -1 && secondary.getBButton();
    }

    @Override
    public boolean doAutoHighRight() {
        return getPreset() == 1 && secondary.getYButton();
    }

    @Override
    public boolean doAutoMidRight() {
        return getPreset() == 1 && secondary.getBButton();
    }
}