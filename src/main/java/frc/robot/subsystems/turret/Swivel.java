package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Config;

/**
 * Turns the turret on the robot, for up to MAX_TURN_ANGLE degrees on both
 * sides.
 */
public class Swivel {

    private CANSparkMax swivelMotor;
    private RelativeEncoder swivelEncoder;

    private double initialEncoderPosition;

    private final double MAX_TURN_ANGLE = 225; //TODO: Switch to 225 degrees
    private final double MAX_TURN_SPEED = 0.8;

    private ExtendRetract extendRetract;

    private DigitalInput swivelSwitch;

    private boolean overrideAngleLimits = false;

    private boolean swivelLocked = false;
    
    private final double SLOW_TURN_MULT = 0.5;
    private boolean slowMode = false;

    // private double fastSpeed = 1;
    // private double slowSpeed = 0.5;
    // private double speedMult = fastSpeed;

    // Singleton
    public static Swivel instance;

    /**
     * Allows only one instance of Swivel to exist at once.
     * 
     * @return An instance of Swivel. Creates a new one if it doesn't exist already.
     */
    public static Swivel getInstance() {
        if (instance == null) {
            instance = new Swivel();
        }
        return instance;
    }

    /**
     * Constructs a new swivel joint for the turret.
     */
    private Swivel() {
        // Set up motor
        swivelMotor = new CANSparkMax(Config.Ports.Arm.SWIVEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        swivelMotor.restoreFactoryDefaults();
        swivelMotor.setInverted(false);
        swivelMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        swivelMotor.setSmartCurrentLimit(60);
        swivelMotor.setSecondaryCurrentLimit(80);

        // Set up encoder
        swivelEncoder = swivelMotor.getEncoder();

        // Set up positon (Assuming it's centered when powered on)
        initialEncoderPosition = swivelEncoder.getPosition();

        extendRetract = ExtendRetract.getInstance();

        swivelSwitch = new DigitalInput(Config.Ports.Arm.SWIVEL_SWITCH);
    }

    /**
     * Swivels the turret.
     * Stops the motor if speed tries to move it more than 180 degrees to prevent
     * twisting wires.
     * 
     * @param speed How fast it should move. Positive speed values swivels
     *              clockwise,
     *              negative values swivels counterclockwise.
     */
    public void move(double speed) {
        if (!swivelSwitch.get()) {
            initialEncoderPosition = swivelEncoder.getPosition();
        }
        if(swivelLocked) {
            return;
        }
        double extensionSpeedMult = extensionSpeedMultiplier();
        // speed *= -extensionSpeedMult * speedMult; // Left-right inputs were backwards
        speed *= extensionSpeedMult;
        if (slowMode) {
            speed *= SLOW_TURN_MULT;
        }
        double motorOutput = MAX_TURN_SPEED * speed;
        if (speed > 0 && !tooFarRight()) {
            swivelMotor.set(motorOutput);
        } else if (speed < 0 && !tooFarLeft()) {
            swivelMotor.set(motorOutput);
        } else {
            swivelMotor.stopMotor();
        }
    }

    /**
     * Gets the positon of the swivel joint in degrees.
     * 
     * @return The position of the joint. 0 degrees is position of the joint on code
     *         boot. (Not neccesarily absolutely centered.) Positive values means
     *         the arm is clockwise of the start position.
     */
    public double getPositionDegrees() {
        double scalar = 0.782;
        double position = (swivelEncoder.getPosition() - initialEncoderPosition) * scalar;
        // return MathUtil.clamp(position, -MAX_TURN_ANGLE, MAX_TURN_ANGLE);
        return position;
    }

    /**
     * Gets if the joint is too far counterclockwise
     * 
     * @return If the joint is more than MAX_TURN_ANGLE degrees counterclockwise
     */
    private boolean tooFarLeft() {
        SmartDashboard.putBoolean("Swivel.tooFarLeft", getPositionDegrees() < -MAX_TURN_ANGLE);
        if (overrideAngleLimits) {
            return false;
        }
        return getPositionDegrees() < -MAX_TURN_ANGLE;
    }

    /**
     * Gets if the joint is too far clockwise
     * 
     * @return If the joint is more than MAX_TURN_ANGLE degrees clockwise
     */
    private boolean tooFarRight() {
        SmartDashboard.putBoolean("Swivel.tooFarRight", getPositionDegrees() > MAX_TURN_ANGLE);
        if (overrideAngleLimits) {
            return false;
        }
        return getPositionDegrees() > MAX_TURN_ANGLE;
    }

    /**
     * Immediately stops the robot from swiveling
     */
    public void stop() {
        move(0);
    }

    public void setLimitOverride(boolean newValue) {
        overrideAngleLimits = newValue;
    }

    // public void setSlowerTurn(boolean slower) {
    //     if (slower) {
    //         speedMult = slowSpeed;
    //     } else {
    //         speedMult = fastSpeed;
    //     }
    // }
    private double extensionSpeedMultiplier() {
        double low = 0.25;
        double extensionPosition = extendRetract.getPositionInches();
        double multiplier = -((extensionPosition - ExtendRetract.MIN_EXTENSON)
                / ((ExtendRetract.MAX_EXTENSION - ExtendRetract.MIN_EXTENSON)/low))
                + (1 + (1) / (ExtendRetract.MAX_EXTENSION - ExtendRetract.MIN_EXTENSON));
        multiplier = MathUtil.clamp(multiplier, low, 1);
        if (overrideAngleLimits) {
            multiplier = 1;
        }
        // SmartDashboard.putNumber("Turret.extensionSpeedMultiplier", multiplier);
        return multiplier;
    }

    public void lockSwivel(boolean lock) {
        swivelLocked = lock;
    }

    public void setSlowMode(boolean slow) {
        slowMode = slow;
    }
}
