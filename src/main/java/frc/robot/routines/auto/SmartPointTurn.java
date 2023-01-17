package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class SmartPointTurn extends Action {

    private double degreesClockwise;
    private double speed;
    private double timeoutSeconds;
    private double initialAngle;
    private double minSpeed;
    private double maxSpeed;
    private double speedRange;
    private double accelerationDegrees;
    private PeriodicTimer timer;

    public SmartPointTurn(double degreesClockwise, double speed) {
        this(degreesClockwise, speed, -1);
    }

    public SmartPointTurn(double degreesClockwise, double speed, double timeoutSeconds) {
        super();
        this.degreesClockwise = degreesClockwise;
        this.speed = speed;
        this.timeoutSeconds = timeoutSeconds;
        initialAngle = 0;

        speed = Math.abs(speed);

        minSpeed = 0.2 * speed;
        if (minSpeed < 0.1) {
            minSpeed = 0.1;
        }
        if (minSpeed > speed) {
            minSpeed = speed;
        }

        accelerationDegrees = 20 * speed;
        if (2 * accelerationDegrees > degreesClockwise) {
            accelerationDegrees = degreesClockwise / 2;
        }

        double maxAcceleration = Math.min((speed - minSpeed) / accelerationDegrees, 0.8 / 20);
        maxSpeed = minSpeed + accelerationDegrees * maxAcceleration;

        speedRange = maxSpeed - minSpeed;

        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        initialAngle = Subsystems.driveBase.getAngle();
        timer.reset();
    }

    @Override
    public void periodic() {
        if (timeoutSeconds >= 0 && timer.hasElapsed(timeoutSeconds)) {
            state = AutoState.EXIT;
        }
        if (speed > 0) {
            double degreesTraveled = Subsystems.driveBase.getAngle() - initialAngle;
            if (degreesTraveled > degreesClockwise - accelerationDegrees) {
                Subsystems.driveBase.pointTurn(minSpeed + ((((degreesClockwise - degreesTraveled) / accelerationDegrees)) * speedRange));
            } else if (degreesTraveled < accelerationDegrees) {
                Subsystems.driveBase.pointTurn(minSpeed + ((degreesTraveled / accelerationDegrees) * speedRange));
            } else {
                Subsystems.driveBase.pointTurn(maxSpeed);
            }
        } else if (speed < 0) {
            double degreesTraveled = Math.abs(Subsystems.driveBase.getAngle() - initialAngle);
            if (degreesTraveled > degreesClockwise - accelerationDegrees) {
                Subsystems.driveBase.pointTurn(-(minSpeed + ((((degreesClockwise - degreesTraveled) / accelerationDegrees)) * speedRange)));
            } else if (degreesTraveled < accelerationDegrees) {
                Subsystems.driveBase.pointTurn(-(minSpeed + ((degreesTraveled / accelerationDegrees) * speedRange)));
            } else {
                Subsystems.driveBase.pointTurn(-maxSpeed);
            }
        } else {
            Subsystems.driveBase.stop();
        }
    }

    @Override
    public boolean isDone() {
        if (speed > 0) {
            return Subsystems.driveBase.getAngle() - initialAngle >= degreesClockwise;
        } else if (speed < 0) {
            return Subsystems.driveBase.getAngle() - initialAngle <= -degreesClockwise;
        } else {
            return true;
        }
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
