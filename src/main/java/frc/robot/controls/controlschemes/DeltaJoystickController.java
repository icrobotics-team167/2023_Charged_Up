package frc.robot.controls.controlschemes;

import edu.wpi.first.math.MathUtil;
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
        return quaternary.getButtonById(3);
    }

    @Override
    public boolean outtake() {
        return quaternary.getButtonById(4);
    }

    @Override
    public double getArmSwivel() {
        return -(Math.abs(quaternary.getLeftStickX()) >= Config.Tolerances.QUATERNARY_CONTROLLER_DEADZONE_SIZE
                ? quaternary.getLeftStickX()
                : 0);
    }

    @Override
    public double getArmPivot() {
        return -(Math.abs(quaternary.getLeftStickY()) >= Config.Tolerances.QUATERNARY_CONTROLLER_DEADZONE_SIZE
                ? quaternary.getLeftStickY()
                : 0);
    }

    @Override
    public double getArmExtend() {
        return tertiary.getLeftStickY();

    }

    @Override
    public boolean doLimitOverride() {
        return tertiary.getButtonById(3);
    }

    @Override
    public boolean resetLimits() {
        return tertiary.getButtonReleasedById(3);
    }
    
    @Override
    public boolean doResetTurret() {
        return quaternary.getButtonPressedById(2);
    }

    int offset = 0;
    @Override
    public int getPositionOffset() {
        if (quaternary.getDPadUp()) {
            offset = 0;
        // } else if (quaternary.getDPadLeft()) {
        //     offset = -90;
        // } else if (quaternary.getDPadRight()) {
        //     offset = 90;
        } else if (quaternary.getDPadDown()) {
            offset = -180;
        }
        return offset;
    }

    @Override
    public boolean offsetUpdated() {
        return quaternary.getDPadUp() ||
            // quaternary.getDPadLeft() ||
            // quaternary.getDPadRight() ||
            quaternary.getDPadDown();
    }


    @Override
    public boolean doUnlockSwivel() {
        return quaternary.getLeftTrigger();
    }

    @Override
    public boolean doAutoPickup() {
        return tertiary.getButtonById(2);
    }

    @Override
    public boolean doPlayerStation() {
        return tertiary.getButtonById(4);
    }


    @Override
    public boolean toggleLimelight() {
        return secondary.getButtonPressedById(2);
    }

    @Override
    public boolean doAutoHigh() {
        return tertiary.getButtonById(15) || quaternary.getButtonById(9);
    }

    @Override
    public boolean doAutoMid() {
        return tertiary.getButtonById(12) || quaternary.getButtonById(6);
    }

    @Override
    public boolean doAutoHighLeft() {
        return tertiary.getButtonById(14) || quaternary.getButtonById(8);
    }

    @Override
    public boolean doAutoMidLeft() {
        return tertiary.getButtonById(13) || quaternary.getButtonById(7);
    }

    @Override
    public boolean doAutoHighRight() {
        return tertiary.getButtonById(16) || quaternary.getButtonById(10);
    }

    @Override
    public boolean doAutoMidRight() {
        return tertiary.getButtonById(11) || quaternary.getButtonById(5);
    }

    @Override
    public double getSpeed() {
        return ((-primary.getLeftStickZ() + 1) / 2);
    }
}
