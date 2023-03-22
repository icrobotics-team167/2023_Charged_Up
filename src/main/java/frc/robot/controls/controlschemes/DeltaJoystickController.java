package frc.robot.controls.controlschemes;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Config;
import frc.robot.controls.controllers.Controller;

/**
 * Delta is the fourth Greek letter and we have four joysticks.
 * Q.E.D.
 */
public class DeltaJoystickController extends ControlScheme {

    private Controller primary;
    private Controller secondary;
    private Controller tertiary;
    private Controller quaternary;

    public DeltaJoystickController(Controller primary, Controller secondary, Controller tertiary, Controller quaternary) {
        this.primary = primary;
        this.secondary = secondary;
        this.tertiary = tertiary;
        this.quaternary = quaternary;
    }

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
        return Math.abs(primary.getLeftStickY()) >= Config.Tolerances.PRIMARY_CONTROLLER_DEADZONE_SIZE
                ? primary.getLeftStickY()
                : 0;
    }

    @Override
    public double getArcadeWheel() {
        return -(Math.abs(secondary.getLeftStickX()) >= Config.Tolerances.SECONDARY_CONTROLLER_DEADZONE_SIZE
                ? secondary.getLeftStickX()
                : 0);
    }

    @Override
    public boolean doSwitchLowGear() {
        return primary.getLeftTrigger();
    }

    @Override
    public boolean doSwitchHighGear() {
        return secondary.getLeftTrigger();
    }

    @Override
    public boolean doSlowMode() {

        return false;
    }


    @Override
    public boolean intake() {
        return quaternary.getButtonPressedById(3);
    }

    @Override
    public boolean outtake() {
        return quaternary.getButtonPressedById(4);
    }

    @Override
    public double getArmSwivel() {
        return -(Math.abs(quaternary.getLeftStickX()) >= Config.Tolerances.QUATERNARY_CONTROLLER_DEADZONE_SIZE
                ? quaternary.getLeftStickX()
                : 0);
    }

    @Override
    public double getArmPivot() {
        return (Math.abs(quaternary.getLeftStickY()) >= Config.Tolerances.QUATERNARY_CONTROLLER_DEADZONE_SIZE
                ? quaternary.getLeftStickY()
                : 0);
    }

    @Override
    public double getArmExtend() {
        if (quaternary.getDPadUp()) {
            return 1;
        } else if (quaternary.getDPadDown()) {
            return -1;
        } else {
            return 0;
        }

    }

    @Override
    public boolean doLimitOverride() {

        return false;
    }

    @Override
    public boolean doResetTurret() {
        return quaternary.getButtonPressedById(2);
    }


    @Override
    public boolean doUnlockSwivel() {
        return quaternary.getLeftTrigger();
    }

    @Override
    public boolean doAutoHigh() {
        return quaternary.getButtonPressedById(6);
    }

    @Override
    public boolean doAutoMid() {
        return quaternary.getButtonPressedById(9);
    }

    @Override
    public boolean doAutoPickup() {
        return tertiary.getButtonPressedById(9);
    }

    @Override
    public boolean doPlayerStation() {
        return tertiary.getButtonPressedById(6);
    }


    @Override
    public boolean toggleLimelight() {
        return secondary.getRawButtonPressedById(2);
    }
    @Override
    public boolean doAutoHighLeft() {
        return tertiary.getButtonPressedById(8);
    }

    @Override
    public boolean doAutoMidLeft() {
        return tertiary.getButtonPressedById(7);
    }

    @Override
    public boolean doAutoHighRight() {
        return tertiary.getButtonPressedById(12);
    }

    @Override
    public boolean doAutoMidRight() {
        return tertiary.getButtonPressedById(11);
    }

}
