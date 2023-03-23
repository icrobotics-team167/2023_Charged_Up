package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;

/**
 * Tilts the arm up and down
 */
public class Pivot {

    private CANSparkMax pivotMaster;
    private CANSparkMax pivotSlave;
    private RelativeEncoder pivotEncoder;

    private double initialEncoderPosition;

    // private DigitalInput pivotSwitch;
    private static final double MAX_TURN_SPEED = 0.8;
    private static final double INITIAL_PIVOT_ANGLE = TurretPosition.INITIAL.pivotAngle();
    private static final double MAX_PIVOT_ANGLE = 60;
    private static final double MIN_PIVOT_ANGLE = -39;

    private boolean overrideAngleLimits = false;

    private final double SLOW_TURN_MULT = 0.3;
    private boolean slowMode = false;

    // Singleton
    public static Pivot instance;

    private ExtendRetract extendRetract;

    /**
     * Allows only one instance of Pivot to exist at once.
     * 
     * @return An instance of Pivot. Creates a new one if it doesn't exist already.
     */
    public static Pivot getInstance() {
        if (instance == null) {
            instance = new Pivot();
        }
        return instance;
    }

    /**
     * Constructs a new pivot joint for the arm.
     * Assumes the arm is at a set degree angle up relative to the drive base on code
     * boot. See TurretPosition.INITIAL for where that set degree is.
     */
    private Pivot() {
        // Set up motors
        pivotMaster = new CANSparkMax(Config.Ports.Arm.PIVOT_1,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        pivotSlave = new CANSparkMax(Config.Ports.Arm.PIVOT_2,
                CANSparkMaxLowLevel.MotorType.kBrushless);

        pivotMaster.restoreFactoryDefaults();
        pivotSlave.restoreFactoryDefaults();
        pivotSlave.follow(pivotMaster, true);
        pivotMaster.setInverted(false);
        pivotSlave.setInverted(true);
        pivotMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        pivotSlave.setIdleMode(CANSparkMax.IdleMode.kBrake);
        pivotMaster.setSmartCurrentLimit(40);
        pivotMaster.setSecondaryCurrentLimit(60);
        pivotSlave.setSmartCurrentLimit(40);
        pivotSlave.setSecondaryCurrentLimit(60);

        // Set up encoders
        pivotEncoder = pivotMaster.getEncoder();

        initialEncoderPosition = pivotEncoder.getPosition();

        extendRetract = ExtendRetract.getInstance();

        // pivotSwitch = new DigitalInput(Config.Ports.Arm.PIVOT_SWITCH);
    }

    /**
     * Pivots the arm.
     * Stops the motor if speed tries to move it past a limit switch (Ex. trying to
     * pivot up while the top limit switch is hit)
     * 
     * @param speed How fast it should pivot. Positive speed values pivot up,
     *              negative values pivot down.
     */
    public void move(double speed) {
        speed *= extensionSpeedMultiplier();
        if (slowMode) {
            speed *= SLOW_TURN_MULT;
        }
        double motorOutput = MAX_TURN_SPEED * Math.abs(speed);
        // pivotMaster.set(-motorOutput*(Math.abs(speed)/speed));
        if (speed > 0 && !tooFarUp()) {
            pivotMaster.set(-motorOutput);
        } else if (speed < 0 && !tooFarDown()) {
            pivotMaster.set(motorOutput);
        } else {
            pivotMaster.stopMotor();
        }
    }

    /**
     * Returns if the pivot is too far up
     * 
     * @return Whether or not the pivot's angle is greater than or equal to 45
     *         degrees
     */
    public boolean tooFarUp() {
        if (overrideAngleLimits) {
            return false;
        }
        return getPositionDegrees() >= MAX_PIVOT_ANGLE;
    }

    /**
     * Returns if the pivot is too far down
     * 
     * @return Whether or not the pivot's angle is less than or equal to 0 degrees
     */
    public boolean tooFarDown() {
        if (overrideAngleLimits) {
            return false;
        }
        return getPositionDegrees() <= MIN_PIVOT_ANGLE;
    }

    /**
     * @returns The position of the pivot in degrees.
     */
    public double getPositionDegrees() {
        double scalar = -0.9;
        return (pivotEncoder.getPosition() - initialEncoderPosition) * scalar + INITIAL_PIVOT_ANGLE;
    }

    /**
     * Immediately stops the robot from pivoting
     */
    public void stop() {
        move(0);
    }

    public void setLimitOverride(boolean newValue) {
        overrideAngleLimits = newValue;
    }

    public void resetLimit() {
        initialEncoderPosition = pivotEncoder.getPosition();
    }

    private double extensionSpeedMultiplier() {
        double low = 0.25;
        double extensionPosition = extendRetract.getPositionInches();
        double multiplier = -((extensionPosition - ExtendRetract.MIN_EXTENSON)
                / ((ExtendRetract.MAX_EXTENSION - ExtendRetract.MIN_EXTENSON)/(1-low)))
                + (1 + (1) / (ExtendRetract.MAX_EXTENSION - ExtendRetract.MIN_EXTENSON));
        multiplier = MathUtil.clamp(multiplier, low, 1);
        if (overrideAngleLimits) {
            multiplier = 1;
        }
        SmartDashboard.putNumber("Turret.extensionSpeedMultiplier", multiplier);
        return multiplier;
    }

    public void setSlowMode(boolean slow) {
        slowMode = slow;
    }
}
