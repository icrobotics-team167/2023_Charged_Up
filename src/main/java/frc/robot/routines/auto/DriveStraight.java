package frc.robot.routines.auto;

import edu.wpi.first.math.util.Units;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Action {

    private double meters;
    private double speed;
    private double timeoutSeconds;
    private double leftEncoderInitialPosition;
    private double rightEncoderInitialPosition;
    private double minSpeed;
    private double maxSpeed;
    private double offset;
    // private double speedRange;
    private double accelerationMeters;
    private PeriodicTimer timer;
    private double startAngle;
    private boolean initTickIsDone;
    private AHRS ahrs;

    /** 
     * Constructs a new DriveStraight auto routine.
     * 
     * @param inches Distance to drive in inches
     * @param speed How fast to drive
     */
    public DriveStraight(double inches, double speed) {
        this(inches, speed, -1);
    }

    /**
     * Constructs a new DriveStraight auto routine.
     * 
     * @param inches Distance to drive in inches
     * @param speed How fast to drive
     * @param timeoutSeconds How many seconds before it times out and gives up on trying to reach the target distance. -1 for no timeout.
     */
    public DriveStraight(double inches, double speed, double timeoutSeconds) {
        super();
        meters = Units.inchesToMeters(inches);
        this.speed = speed;
        this.timeoutSeconds = timeoutSeconds;
        leftEncoderInitialPosition = 0;
        rightEncoderInitialPosition = 0;

        speed = Math.abs(speed);

        minSpeed = 0.2 * speed;
        if (minSpeed < 0.1) {
            minSpeed = 0.1;
        }
        if (minSpeed > speed) {
            minSpeed = speed;
        }

        accelerationMeters = Units.feetToMeters(3 * speed);
        if (2 * accelerationMeters > meters) {
            accelerationMeters = meters / 2;
        }

        offset = Units.inchesToMeters(6.25 * speed);

        double maxAcceleration = Math.min((speed - minSpeed) / accelerationMeters, 0.8 / Units.feetToMeters(3));
        maxSpeed = minSpeed + accelerationMeters * maxAcceleration;

        // speedRange = maxSpeed - minSpeed;

        initTickIsDone = false;

        timer = new PeriodicTimer();

        try {
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }
    }

    @Override
    public void init() {
        Subsystems.driveBase.setBrake();
        leftEncoderInitialPosition = Subsystems.driveBase.getLeftEncoderPosition();
        rightEncoderInitialPosition = Subsystems.driveBase.getRightEncoderPosition();
    }

    @Override
    public void onEnable() {
        // TODO Auto-generated method stub
        startAngle = ahrs.getYaw()%360;
        timer.reset();
    }

    // new code starts here:
    public void periodic() {
        if (!initTickIsDone) {
            startAngle = ahrs.getYaw()%360;
            initTickIsDone = true;
            timer.reset();
        }

        if (timeoutSeconds >= 0 && timer.hasElapsed(timeoutSeconds)) {
            state = AutoState.DONE;
            Subsystems.driveBase.stop();
        }

        Subsystems.driveBase.tankDrive(-speed, -speed);
    }

    @Override
    public boolean isDone() {
        double leftEncoderPosition = Subsystems.driveBase.getLeftEncoderPosition();
        double rightEncoderPosition = Subsystems.driveBase.getRightEncoderPosition();
        if (speed > 0) {
            return leftEncoderPosition - leftEncoderInitialPosition >= meters
                    || rightEncoderPosition - rightEncoderInitialPosition >= meters;
        } else if (speed < 0) {
            return leftEncoderPosition - leftEncoderInitialPosition <= -meters
                    || rightEncoderPosition - rightEncoderInitialPosition <= -meters;
        } else {
            return true;
        }
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
