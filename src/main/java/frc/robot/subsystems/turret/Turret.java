package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class Turret {
    private Pivot pivot;
    private Swivel swivel;
    private ExtendRetract extendRetract;
    private Claw claw;

    private static Turret instance;

    private final double PIVOT_SENSITIVITY_THRESHOLD = 2;
    private final double SWIVEL_SENSITIVITY_THRESHOLD = 2;
    private final double EXTENSION_SENSITIVITY_THRESHOLD = 1;

    private DigitalInput retractSwitch;
    private DigitalInput swivelSwitch;

    public static Turret getinstance() {
        if (instance == null) {
            instance = new Turret();
        }
        return instance;
    }

    private Turret() {
        pivot = Pivot.getInstance();
        swivel = Swivel.getInstance();
        extendRetract = ExtendRetract.getInstance();
        claw = Claw.getInstance();

        retractSwitch = new DigitalInput(Config.Ports.Arm.EXTEND_RETRACT_SWITCH);
        swivelSwitch = new DigitalInput(Config.Ports.Arm.SWIVEL_SWITCH);
    }

    /**
     * Moves multiple parts of the arm at the same time.
     * 
     * @param pivotSpeed         The pivot speed
     * @param swivelSpeed        The swivel speed
     * @param extendRetractSpeed The extension speed
     */
    public void move(double pivotSpeed, double swivelSpeed, double extendRetractSpeed) {
        pivot.move(pivotSpeed);
        swivel.move(swivelSpeed);
        extendRetract.move(extendRetractSpeed);
    }

    /**
     * Moves multiple parts of the turret at the same time.
     * 
     * @param pivotTarget     The target pivot angle
     * @param swivelTarget    The target swivel angle
     * @param extensionTarget The target extension position
     * 
     * @return If it has finished moving (AKA when all the targets are met)
     */
    public boolean moveTo(TurretPosition targetState) {
        return pivotToAngle(targetState.pivotAngle()) &&
                swivelToAngle(targetState.swivelAngle()) &&
                extendToPosition(targetState.extensionPosition());
    }

    public void stop() {
        pivot.stop();
        swivel.stop();
        extendRetract.stop();
    }

    /**
     * Gets the positions of the turret's systems in an array.
     * [0]: The pivot position in degrees.
     * [1]: The swivel position in degrees.
     * [2]: The extension position in inches.
     * 
     * @return An array containing the positions.
     */
    public TurretPosition getPositions() {
        return new TurretPosition(pivot.getPositionDegrees(), swivel.getPositionDegrees(),
                extendRetract.getPositionInches());
    }

    public boolean pivotToAngle(double target) {
        double speed;
        double error = Math.abs(target - pivot.getPositionDegrees());
        if (error < PIVOT_SENSITIVITY_THRESHOLD) {
            speed = 0;
        } else {
            speed = MathUtils.getSign(error);
        }
        pivot.move(speed);
        return speed == 0;
    }

    public boolean swivelToAngle(double target) {
        double speed;
        double error = Math.abs(target - swivel.getPositionDegrees());
        if (error < SWIVEL_SENSITIVITY_THRESHOLD) {
            speed = 0;
        } else {
            speed = MathUtils.getSign(error);
        }
        swivel.move(speed);
        return speed == 0;
    }

    public boolean extendToPosition(double target) {
        double speed;
        double error = Math.abs(target - extendRetract.getPositionInches());
        if (error < EXTENSION_SENSITIVITY_THRESHOLD) {
            speed = 0;
        } else {
            speed = MathUtils.getSign(error);
        }
        extendRetract.move(speed);
        return speed == 0;
    }

    public void setLimitOverride(boolean newVal) {
        pivot.setLimitOverride(newVal);
        swivel.setLimitOverride(newVal);
        extendRetract.setLimitOverride(newVal);
    }

    public boolean isSwivelCentered() {
        return !swivelSwitch.get();
    }

    public boolean isFullyRetracted() {
        return !retractSwitch.get();
    }
}
