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

    public void move(double speed) {
        /*
         * Moves the motor for extending/retracting.
         * Takes a parameter speed for how fast it should move.
         * Positive speed values extend, negative retracts.
         * Stops the motor if speed tries to move it past a limit switch. (Ex. trying to
         * move forwards while the forwards limit switch is hit)
         */
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

    public boolean hasHitFrontLimit() {
        /*
         * Returns whether or not it has hit the front limit switch.
         */
        return !extendFrontSwitch.get();
    }

    public boolean hasHitBackLimit() {
        /*
         * Returns whether or not it has hit the back limit switch.
         */
        return !extendBackSwitch.get();
    }

    private double calculatePostion(double encoderPos) {
        /*
         * Calculates the positon from the encoder positon.
         * frontEncoderLimit is 1.0
         * backEncoderLimit is 0.0
         */
        return (encoderPos - backEncoderLimit) / frontEncoderLimit;
    }

    public double getPositionInches() {
        /*
         * Converts the current position to inches.
         */
        // TEMPORARY VALUE
        double extensionLength = 48;
        return position * extensionLength;
    }

    public double getPosition() {
        /*
         * Gets how far the arm has extended.
         * Upper bound: (Fully extended) 1.0
         * Lower bound: (Fully retracted) 0.0
         */
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
