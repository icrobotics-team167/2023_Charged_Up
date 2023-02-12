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

    public void move(double speed) {
        /*
         * Pivots the arm.
         * Takes a parameter speed for how fast it should pivot.
         * Positive speed values pivot up, negative values pivot down.
         * Stops the motor if speed tries to move it past a limit switch (Ex. trying to
         * pivot up while the top limit switch is hit)
         */
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

    public boolean hasHitTopLimit() {
        /*
         * Returns whether or not the arm has hit the top limit switch
         */
        return !pivotTopSwitch.get();
    }

    public boolean hasHitBottomLimit() {
        /*
         * Returns whether or not the arm has hit the bottom limit switch.
         */
        return !pivotBottomSwitch.get();
    }

    private double calculatePostion(double encoderPos) {
        return (encoderPos - bottomEncoderLimit) / topEncoderLimit;
    }

    public double getPositionDegrees() {
        /*
         * Returns the position of the pivot in degrees.
         */
        // TEMPORARY VALUE
        double pivotUpperBound = 45;
        double pivotLowerBound = 0;
        return position * (pivotUpperBound - pivotLowerBound) + pivotLowerBound;
    }

    public double getPosition() {
        /*
         * Returns the positon of the pivot.
         * Upper bound: (All the way up) 1.0
         * Lower bound: (All the way down) 0.0
         */
        return position;
    }

    public void calibrate() {
        /*
         * Calibrates by pivoting all the way up and down to read the max and min
         * encoder values
         */
        while (!hasHitTopLimit()) {
            move(1);
        }
        pivotMaster.stopMotor();
        topEncoderLimit = pivotEncoder.getPosition();
        while (!hasHitBottomLimit()) {
            move(-1);
        }
        pivotMaster.stopMotor();
        bottomEncoderLimit = pivotEncoder.getPosition();
    }
}
