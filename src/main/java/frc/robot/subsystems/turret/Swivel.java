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

    /**
     * Constructs a new swivel joint for the turret.
     */
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

    /**
     * Swivels the turret.
     * Stops the motor if speed tries to move it more than 180 degrees to prevent
     * twisting wires.
     * 
     * @param speed How fast it should move. Positive speed valuess swivels right,
     *              negative values swivels left.
     */
    public void move(double speed) {
        if (hasHitCenterLimit()) { // If it's at the center limit switch, (0 degrees)
            position = 0.0; // Set position to 0
        } else if (hasHitEndLimit()) { // If it hits the back limit switch
            if (position < 0) { // If it's trying to go left
                position = -1; // Then it's all the way left
                leftEncoderLimit = swivelEncoder.getPosition(); // Calibrate encoders
            } else {
                position = 1; // Otherwise it's all the way right
                rightEncoderLimit = swivelEncoder.getPosition(); // Calibrate encoders.
            }
        }
        if (hasHitEndLimit()) {
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

    /**
     * @return Whether or not the arm has hit the center limit switch
     */
    public boolean hasHitEndLimit() {
        return false;
        // return !swivelEndsSwitch.get();
    }

    /**
     * @return Whether or not the arm has hit the ends limit switch
     */
    public boolean hasHitCenterLimit() {

        return false;
        // return !swivelCenterSwitch.get();
    }

    /**
     * Calculates the positon of the arm.
     * 
     * @return The positon of the arm. -1 is all the way left, 1 is all the way
     *         right.
     */
    private double calculatePostion(double encoderPos) {
        return (encoderPos - leftEncoderLimit) / rightEncoderLimit - 1;
    }

    /**
     * Returns the position of the arm.
     * 
     * @return The positon of the arm. -1 is all the way left, 1 is all the way
     *         right.
     */
    public double getPosition() {
        return position;
    }
}
