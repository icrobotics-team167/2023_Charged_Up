package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Config;

public class Swivel {

    private CANSparkMax swivelMotor;
    private RelativeEncoder swivelEncoder;

    private DigitalInput swivelCenterSwitch;
    private DigitalInput swivelEndsSwitch;

    private double position;
    private double leftEncoderLimit;
    private double rightEncoderLimit;

    public Swivel() {
        // Set up motor
        swivelMotor = new CANSparkMax(Config.Ports.Arm.SWIVEL, CANSparkMaxLowLevel.MotorType.kBrushless);

        // Set up encoder
        swivelEncoder = swivelMotor.getEncoder();

        // Set up limit switches
        swivelCenterSwitch = new DigitalInput(Config.Ports.Arm.SWIVEL_CENTER);
        swivelEndsSwitch = new DigitalInput(Config.Ports.Arm.SWIVEL_ENDS);

        // Set up positon (Assuming it's centered when powered on)
        position = 0.0;
    }

    public void move(double speed) {
        /*
         * Swivels the turret.
         * Takes a parameter speed for how fast it should swivel.
         * Positive speed values swivel right, negative swivels left.
         * Stops the motor if speed tries to move it more than 180 degrees to prevent
         * twisting wires.
         */
        if (hasHitCenterLimit()) {
            position = 0.0;
        } else if (hasHitLimit()) {
            if (position < 0) {
                position = -1;
                leftEncoderLimit = swivelEncoder.getPosition();
            } else {
                position = 1;
                rightEncoderLimit = swivelEncoder.getPosition();
            }
        }
        if (hasHitLimit()) {
            if (position < 0 && speed < 0) {
                swivelMotor.stopMotor();
            } else if (position > 0 && speed > 0) {
                swivelMotor.stopMotor();
            } else {
                swivelMotor.set(speed);
            }
        } else {
            swivelMotor.set(speed);
            position = calculatePostion(swivelEncoder.getPosition());
        }
    }

    public boolean hasHitLimit() {
        /*
         * Returns whether or not the arm has hit the center limit switch
         */
        return false;
        // return !swivelEndsSwitch.get();
    }

    public boolean hasHitCenterLimit() {
        /*
         * Returns whether or not the arm has hit the ends limit switch
         */
        return false;
        // return !swivelCenterSwitch.get();
    }

    private double calculatePostion(double encoderPos) {
        return (encoderPos - leftEncoderLimit) / rightEncoderLimit - 1;
    }

    public double getPosition() {
        /*
         * Returns the position of the swivel.
         * Upper bound: (All the way right) 1.0
         * Lower bound: (All the way left) -1.0
         */
        return position;
    }
}
