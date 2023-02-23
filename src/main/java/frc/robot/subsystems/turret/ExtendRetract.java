package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Config;

/**
 * Extends and retracts the arm
 * Disregard TODOs for now as we will be working with only encoder values for
 * the time being
 * TODO: Find way to correct encoder values based off the limit switch
 * TODO: Find out which limit switch we are hitting.
 * One limit switch is triggered by both ends so we need a method to figure out
 * which one we are hitting.
 */
public class ExtendRetract {

    private CANSparkMax extendRetractMotor;
    private RelativeEncoder extendRetractEncoder;

    private double initialEncoderPosition;

    private static final double MAX_EXTEND_SPEED = 0.3;
    private static final double START_EXTENSION = 3.5;

    private static final double MAX_EXTENSION = 35;
    private static final double MIN_EXTENSION = 3.5;
    private static final double DECEL_DISTANCE = 0.5; // The extension has some interia before it fully stops so this is
                                                      // to account for that

    // private DigitalInput extendRetractSwitch;

    // Singleton
    public static ExtendRetract instance;

    /**
     * Allows only one instance of ExtendRetract to exist at once.
     * 
     * @return An instance of ExtendRetract. Creates a new one if it doesn't exist
     *         already.
     */
    public static ExtendRetract getInstance() {
        if (instance == null) {
            instance = new ExtendRetract();
        }
        return instance;
    }

    /**
     * Constructs a new extension mechanism for the arm.
     * Assumes arm is all the way retracted on code boot.
     */
    private ExtendRetract() {
        // Set up motor
        extendRetractMotor = new CANSparkMax(Config.Ports.Arm.EXTEND_RETRACT, CANSparkMaxLowLevel.MotorType.kBrushless);

        extendRetractMotor.restoreFactoryDefaults();
        extendRetractMotor.setInverted(false);
        extendRetractMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        extendRetractMotor.setSmartCurrentLimit(40);
        extendRetractMotor.setSecondaryCurrentLimit(60);

        // Set up encoder
        extendRetractEncoder = extendRetractMotor.getEncoder();
        initialEncoderPosition = extendRetractEncoder.getPosition();

        // extendRetractSwitch = new
        // DigitalInput(Config.Ports.Arm.EXTEND_RETRACT_SWITCH);
    }

    /**
     * Moves the motor for extending/retracting.
     * Stops the motor if speed tries to move it past a limit switch. (Ex. trying to
     * move forwards while the forwards limit switch is hit)
     * 
     * @param speed Speed value. Positive speed values extend, negative retracts.
     */
    public void move(double speed) {
        double motorOutput = MAX_EXTEND_SPEED * Math.abs(speed);
        if (speed > 0 && !tooFarOut()) {
            extendRetractMotor.set(-motorOutput);
        } else if (speed < 0 && !tooFarIn()) {
            extendRetractMotor.set(motorOutput);
        } else {
            extendRetractMotor.stopMotor();
        }
    }

    /**
     * Gets whether or not the arm is too far in.
     * 
     * @return If the arm is too far in.
     */
    public boolean tooFarIn() {
        if (Config.Settings.OVERRIDE_ARM_ANGLE_LIMITS) {
            return false;
        }
        return getPositionInches() <= MIN_EXTENSION + DECEL_DISTANCE;
    }

    /**
     * Gets whether or not the arm is too far out.
     * 
     * @return If the arm is too far out.
     */
    public boolean tooFarOut() {
        if (Config.Settings.OVERRIDE_ARM_ANGLE_LIMITS) {
            return false;
        }
        return getPositionInches() >= MAX_EXTENSION - DECEL_DISTANCE;
    }

    /**
     * Gets the raw encoder position.
     * 
     * @return The raw encoder position.
     */
    public double getRawPosition() {
        return extendRetractEncoder.getPosition();
    }

    /**
     * Gets the position of the arm's extension in inches.
     * 
     * @return The position in inches.
     */
    public double getPositionInches() {
        double scalar = -0.113;
        return (extendRetractEncoder.getPosition() - initialEncoderPosition) * scalar + START_EXTENSION;
    }

    /*
     * Immediately stops the robot from extending/retracting
     */
    public void stop() {
        move(0);
    }
}
