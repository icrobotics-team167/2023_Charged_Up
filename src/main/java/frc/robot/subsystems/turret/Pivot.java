package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Config;

public class Pivot {
    /*
     * Code for pivoting the arm.
     * Methods:
     * void move()
     * boolean hasHitTopLimit()
     * boolean hasHitBottomLimit()
     * double getPositon()
     */

    private CANSparkMax pivotMaster;
    private CANSparkMax pivotSlave;
    private RelativeEncoder pivotEncoder;

    private DigitalInput pivotTopSwitch;
    private DigitalInput pivotBottomSwitch;

    private double position;
    private double topEncoderLimit;
    private double bottomEncoderLimit;

    /**
     * Constructs a new pivot joint for the arm.
     */
    public Pivot() {
        // Set up motors
        pivotMaster = new CANSparkMax(Config.Ports.Arm.PIVOT_1,
                CANSparkMaxLowLevel.MotorType.kBrushless);
        pivotSlave = new CANSparkMax(Config.Ports.Arm.PIVOT_2,
                CANSparkMaxLowLevel.MotorType.kBrushless);

        pivotSlave.follow(pivotMaster, true);

        // Set up encoders
        pivotEncoder = pivotMaster.getEncoder();

        // Set up limit switches
        pivotTopSwitch = new DigitalInput(Config.Ports.Arm.PIVOT_TOP);
        pivotBottomSwitch = new DigitalInput(Config.Ports.Arm.PIVOT_BOTTOM);

        // Set up initial position (Assuming it's all the way down when powered on)
        position = 0;
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
        if (hasHitTopLimit()) {
            position = 1.0;
            topEncoderLimit = pivotEncoder.getPosition();
        } else if (hasHitBottomLimit()) {
            position = 0.0;
            bottomEncoderLimit = pivotEncoder.getPosition();
        }
        if (hasHitTopLimit() && speed > 0) {
            pivotMaster.stopMotor();
        } else if (hasHitBottomLimit() && speed < 0) {
            pivotMaster.stopMotor();
        } else {
            pivotMaster.set(speed);
            position = calculatePostion(pivotEncoder.getPosition());
        }
    }

    /**
     * @returns Whether or not the arm has hit the top limit switch
     */
    public boolean hasHitTopLimit() {
        return !pivotTopSwitch.get();
    }

    /**
     * @returns Whether or not the arm has hit the bottom limit switch.
     */
    public boolean hasHitBottomLimit() {
        return !pivotBottomSwitch.get();
    }

    /**
     * Calculates the position of the pivot.
     * @param encoderPos The current position of the encoder.
     * @return The positon of the pivot. 0 is all the way down, 1 is all the way up.
     */
    private double calculatePostion(double encoderPos) {
        return (encoderPos - bottomEncoderLimit) / (topEncoderLimit - bottomEncoderLimit);
    }

    /**
     * @returns The position of the pivot in degrees.
     */
    public double getPositionDegrees() {
        // TEMPORARY VALUE
        double pivotUpperBound = 45;
        double pivotLowerBound = 0;
        return position * (pivotUpperBound - pivotLowerBound) + pivotLowerBound;
    }

    /**
     * @returns The position of the pivot. 0 is all the way down, 1 is all the way up.
     */
    public double getPosition() {
        return position;
    }
}
