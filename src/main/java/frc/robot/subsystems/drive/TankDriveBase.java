package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;

public interface TankDriveBase {

    // Teleop drive
    void tankDrive(double leftSpeed, double rightSpeed);

    void arcadeDrive(double throttle, double wheel);

    // Gearing
    void toggleGearing();

    void setHighGear();

    void setLowGear();

    void setLowerGear(boolean lowerGear);

    boolean isHighGear();

    boolean isLowGear();

    boolean isLowerGear();

    // Auto
    void straightDrive(double speed);

    void straightDrive(double speed, boolean newAngle);

    void straightDriveAtAngle(double speed, double angle);

    void stop();

    void pointTurn(double speed);

    double getAngle();

    double getLeftEncoderPosition();

    double getRightEncoderPosition();

    void resetEncoders();

    void setReferences(double leftSpeed, double rightSpeed);

    Rotation2d getGyroHeading();

    void setCoast();

    void setBrake();

    // Utility
    double metersPerSecondToRPM(double metersPerSecond);

}
