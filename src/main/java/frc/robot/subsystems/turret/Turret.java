package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class Turret {
    private Pivot pivot;
    private Swivel swivel;
    private ExtendRetract extendRetract;

    private static Turret instance;

    private final double PIVOT_SENSITIVITY_THRESHOLD = 2;
    private final double SWIVEL_SENSITIVITY_THRESHOLD = 2;
    private final double EXTENSION_SENSITIVITY_THRESHOLD = 1;

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
        boolean pivot = pivotToAngle(targetState.pivotAngle());
        boolean swivel = swivelToAngle(targetState.swivelAngle());
        boolean extend = extendToPosition(targetState.extensionPosition());
        SmartDashboard.putBoolean("Turret.moveTo.pivot", pivot);
        SmartDashboard.putBoolean("Turret.moveTo.swivel", swivel);
        SmartDashboard.putBoolean("Turret.moveTo.extend", extend);
        return pivot && swivel && extend;
    }

    public void stop() {
        pivot.stop();
        swivel.stop();
        extendRetract.stop();
    }

    public TurretPosition getPosition() {
        return new TurretPosition(pivot.getPositionDegrees(), swivel.getPositionDegrees(),
                extendRetract.getPositionInches());
    }

    public boolean pivotToAngle(double target) {
        double speed;
        double error = target - pivot.getPositionDegrees();
        if (Math.abs(error) < PIVOT_SENSITIVITY_THRESHOLD) {
            speed = 0;
        } else {
            speed = MathUtils.getSign(error);
        }
        speed *= 0.6;
        pivot.move(speed);
        return speed == 0;
    }

    public boolean swivelToAngle(double target) {
        double speed;
        double error = target - swivel.getPositionDegrees();
        if (Math.abs(error) < SWIVEL_SENSITIVITY_THRESHOLD) {
            speed = 0;
        } else {
            speed = MathUtils.getSign(error);
        }
        speed *= 0.6;
        swivel.move(speed);
        return speed == 0;
    }

    public boolean extendToPosition(double target) {
        double speed;
        double error = target - extendRetract.getPositionInches();
        if (Math.abs(error) < EXTENSION_SENSITIVITY_THRESHOLD) {
            speed = 0;
        } else {
            speed = MathUtils.getSign(error);
        }
        speed *= 0.6;
        extendRetract.move(speed);
        return speed == 0;
    }

    public void setLimitOverride(boolean newVal) {
        pivot.setLimitOverride(newVal);
        swivel.setLimitOverride(newVal);
        extendRetract.setLimitOverride(newVal);
    }

    /**
     * Toggles locking the turret, making it impossible to move
     */
    public void lockSwivel(boolean lock) {
        swivel.lockSwivel(lock);
    }

    public void setSlowMode(boolean slow) {
        swivel.setSlowMode(slow);
        pivot.setSlowMode(slow);
    }
}
