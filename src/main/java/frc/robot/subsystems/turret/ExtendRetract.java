package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import frc.robot.Config;

public class ExtendRetract {

    private CANSparkMax extendRetractMotor;
    private RelativeEncoder extendRetractEncoder;

    private double initialEncoderPosition;

    private static final double MAX_EXTEND_SPEED = 0.2;

    // Singleton
    public static ExtendRetract instance;

    /**
     * Allows only one instance of ExtendRetract to exist at once.
     * 
     * @return An instance of ExtendRetract. Creates a new one if it doesn't exist already.
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

    private boolean tooFarIn() {
        return false;
    }

    private boolean tooFarOut() {
        return false;
    }

    public double getPositionInches(){
        double scalar = 1;
        return (extendRetractEncoder.getPosition() - initialEncoderPosition) * scalar;
    }
}
