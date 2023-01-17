package frc.robot.routines.auto;

import edu.wpi.first.math.util.Units;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

// NEW COPY OF THIS IS DRIVESTRAIGHT.JAVA

public class SmartDriveStraight extends Action {

    private double meters;
    private double speed;
    private double timeoutSeconds;
    private double leftEncoderInitialPosition;
    private double rightEncoderInitialPosition;
    private double minSpeed;
    private double maxSpeed;
    private double speedRange;
    private double accelerationMeters;
    private PeriodicTimer timer;

    public SmartDriveStraight(double inches, double speed) {
        this(inches, speed, -1);
    }

    public SmartDriveStraight(double inches, double speed, double timeoutSeconds) {
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

        speedRange = maxSpeed - minSpeed;

        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        leftEncoderInitialPosition = Subsystems.driveBase.getLeftEncoderPosition();
        rightEncoderInitialPosition = Subsystems.driveBase.getRightEncoderPosition();
        timer.reset();
    }

    @Override
    public void periodic() {
        if (timeoutSeconds >= 0 && timer.hasElapsed(timeoutSeconds)) {
            state = AutoState.EXIT;
        }
        if (speed > 0) {
            double leftEncoderPosition = Subsystems.driveBase.getLeftEncoderPosition() - leftEncoderInitialPosition;
            double rightEncoderPosition = Subsystems.driveBase.getRightEncoderPosition() - rightEncoderInitialPosition;
            double metersTraveled = Math.max(leftEncoderPosition, rightEncoderPosition);

            
            if (metersTraveled > meters - accelerationMeters) {
                Subsystems.driveBase.straightDrive(minSpeed + ((((meters - metersTraveled) / accelerationMeters)) * speedRange), false);
            } else if (metersTraveled < accelerationMeters) {
                Subsystems.driveBase.straightDrive(minSpeed + ((metersTraveled / accelerationMeters) * speedRange), false);
            } else {
                Subsystems.driveBase.straightDrive(maxSpeed, false);
            }
        } else if (speed < 0) {
            double leftEncoderPosition = Math.abs(Subsystems.driveBase.getLeftEncoderPosition() - leftEncoderInitialPosition);
            double rightEncoderPosition = Math.abs(Subsystems.driveBase.getRightEncoderPosition() - rightEncoderInitialPosition);
            double metersTraveled = Math.max(leftEncoderPosition, rightEncoderPosition);
            if (metersTraveled > meters - accelerationMeters) {
                Subsystems.driveBase.straightDrive(-(minSpeed + ((((meters - metersTraveled) / accelerationMeters)) * speedRange)), false);
            } else if (metersTraveled < accelerationMeters) {
                Subsystems.driveBase.straightDrive(-(minSpeed + ((metersTraveled / accelerationMeters) * speedRange)), false);
            } else {
                Subsystems.driveBase.straightDrive(-maxSpeed, false);
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
            return leftEncoderPosition - leftEncoderInitialPosition >= meters || rightEncoderPosition - rightEncoderInitialPosition >= meters;
        } else if (speed < 0) {
            return leftEncoderPosition - leftEncoderInitialPosition <= -meters || rightEncoderPosition - rightEncoderInitialPosition <= -meters;
        } else {
            return true;
        }
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
