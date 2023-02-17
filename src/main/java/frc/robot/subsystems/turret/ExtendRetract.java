package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Config;

public class ExtendRetract {
    /*
     * Code for extending/retracting the arm.
     * Methods:
     * void move()
     * boolean hasHitFrontLimit()
     * boolean hasHitBackLimit()
     * double getPosition()
     */

    private CANSparkMax extendRetractMotor;
    private RelativeEncoder extendRetractEncoder;

    private double backEncoderLimit;
    private double frontEncoderLimit;

    private DigitalInput extendFrontSwitch;
    private DigitalInput extendBackSwitch;

    private double position;

    /**
     * Constructs a new extension mechanism for the arm.
     */
    public ExtendRetract() {
        // Set up motor
        extendRetractMotor = new CANSparkMax(Config.Ports.Arm.EXTEND_RETRACT, CANSparkMaxLowLevel.MotorType.kBrushless);

        extendRetractMotor.restoreFactoryDefaults();
        extendRetractMotor.setInverted(false);
        extendRetractMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        // Set up encoder
        extendRetractEncoder = extendRetractMotor.getEncoder();

        // Set up limit switches
        extendFrontSwitch = new DigitalInput(Config.Ports.Arm.EXTEND_RETRACT_FRONT);
        extendBackSwitch = new DigitalInput(Config.Ports.Arm.EXTEND_RETRACT_BACK);

        // Set up position (Assuming it's all the way retracted when powered on)
        position = 0.0;
    }

    /**
     * Moves the motor for extending/retracting.
     * Stops the motor if speed tries to move it past a limit switch. (Ex. trying to
     * move forwards while the forwards limit switch is hit)
     * 
     * @param speed Speed value. Positive speed values extend, negative retracts.
     */
    public void move(double speed) {

        if (hasHitFrontLimit()) {
            position = 1.0;
            frontEncoderLimit = extendRetractEncoder.getPosition();
        } else if (hasHitBackLimit()) {
            position = 0.0;
            backEncoderLimit = extendRetractEncoder.getPosition();
        }
        if (hasHitFrontLimit() && speed > 0) {
            extendRetractMotor.stopMotor();
        } else if (hasHitBackLimit() && speed < 0) {
            extendRetractMotor.stopMotor();
        } else {
            extendRetractMotor.set(speed);
            position = calculatePostion(extendRetractEncoder.getPosition());
        }
    }

    /**
     * @return Whether or not it has hit the front limit switch or not
     */
    public boolean hasHitFrontLimit() {
        return !extendFrontSwitch.get();
    }

    /**
     * @return Whether or not it has hit the back limit switch or not
     */
    public boolean hasHitBackLimit() {
        return !extendBackSwitch.get();
    }

    /**
     * Calculates the positon from the encoder positon.
     * 
     * @return The position of the arm's extension/retraction. 0.0 is all the way
     *         in, 1.0 is all the way out
     */
    private double calculatePostion(double encoderPos) {
        return (encoderPos - backEncoderLimit) / frontEncoderLimit;
    }

    /**
     * @return The position of the arm in inches
     */
    public double getPositionInches() {
        // TEMPORARY VALUE
        double extensionLength = 48;
        return position * extensionLength;
    }

    /**
     * Gets how far the arm has extended.
     * 
     * @return The position of the arm. 0.0 is all the way in, 1.0 is all the way
     *         out.
     */
    public double getPosition() {
        return position;
    }

    public void calibrate() {
        /*
         * Calibrates by extending and retracting all the way to read the max and min
         * encoder values
         */
        while (!hasHitFrontLimit()) {
            move(1);
        }
        extendRetractMotor.stopMotor();
        frontEncoderLimit = extendRetractEncoder.getPosition();
        while (!hasHitBackLimit()) {
            move(-1);
        }
        extendRetractMotor.stopMotor();
        backEncoderLimit = extendRetractEncoder.getPosition();
    }
}
