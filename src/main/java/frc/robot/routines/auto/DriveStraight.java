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
    // private double speedRange;
    private double accelerationMeters;
    private PeriodicTimer timer;
    private double startAngle;
    private boolean initTickIsDone;
    private AHRS ahrs;

    public DriveStraight(double inches, double speed) {
        this(inches, speed, -1);
    }

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

        // Subsystems.driveBase.tankDrive(-speed, -speed);
        SmartDashboard.putNumber("Right encoder position", Subsystems.driveBase.getRightEncoderPosition());
        SmartDashboard.putNumber("Left encoder position", Subsystems.driveBase.getLeftEncoderPosition());
        if (speed != 0) {
            double leftEncoderPosition = Math.abs(Subsystems.driveBase.getLeftEncoderPosition() -
                    leftEncoderInitialPosition);
            double rightEncoderPosition = Math.abs(Subsystems.driveBase.getRightEncoderPosition()
                    - rightEncoderInitialPosition);
            double metersTraveled = Math.max(leftEncoderPosition, rightEncoderPosition);
            Subsystems.driveBase.straightDriveAtAngle(maxSpeed*(speed/Math.abs(speed)), startAngle);
            SmartDashboard.putNumber("distance Traveled", metersTraveled);
            if (metersTraveled > meters) {
                Subsystems.driveBase.stop();
            }
        } else {
            Subsystems.driveBase.stop();
        }
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
