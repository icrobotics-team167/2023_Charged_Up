package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;

/**
 * Tilts the arm up and down
 * TODO: Find way to correct encoder values based off the limit switch
 * TODO: Find out which limit switch we are hitting. 
 * One limit switch is triggered by both ends so we need a method to figure out which one we are hitting.
 */
public class Pivot {

    private CANSparkMax pivotMaster;
    private CANSparkMax pivotSlave;
    private RelativeEncoder pivotEncoder;

    private double initialEncoderPosition;

    private DigitalInput pivotSwitch;
    private static final double MAX_TURN_SPEED = 0.3;
    private static final double INITIAL_PIVOT_ANGLE = 65;
    private static final double MAX_PIVOT_ANGLE = 80;
    private static final double MIN_PIVOT_ANGLE = -35;
    private static final boolean OVERRIDE_ANGLE_LIMITS = false;

    // Singleton
    public static Pivot instance;

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
     * Assumes the arm is at a 65 degree angle up relative to the drive base on code boot.
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

        pivotSwitch = new DigitalInput(Config.Ports.Arm.PIVOT_SWITCH);
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
        SmartDashboard.putNumber("Swivel.degrees", getPositionDegrees());
        SmartDashboard.putNumber("Swivel.speed", speed);
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
    private boolean tooFarUp() {
        if (OVERRIDE_ANGLE_LIMITS) {
            return false;
        }
        return !pivotSwitch.get();
    }

    /**
     * Returns if the pivot is too far down
     * 
     * @return Whether or not the pivot's angle is less than or equal to 0 degrees
     */
    private boolean tooFarDown() {
        if (OVERRIDE_ANGLE_LIMITS) {
            return false;
        }
        return !pivotSwitch.get();
    }

    /**
     * @returns The position of the pivot in degrees.
     */
    public double getPositionDegrees() {
        double scalar = -0.9;
        return (pivotEncoder.getPosition() - initialEncoderPosition) * scalar + INITIAL_PIVOT_ANGLE;
    }

    /*
     * Immediately stops the robot from pivoting
     */
    public void stop()
    {
        move(0);
    }
}
