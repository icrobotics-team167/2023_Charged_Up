package frc.robot.subsystems.turret.drive;

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
    void stop();

    double getLeftEncoderPosition();

    double getRightEncoderPosition();

    void resetEncoders();

    void setCoast();

    void setBrake();

    // Utility
    double metersPerSecondToRPM(double metersPerSecond);
    
    void setNonSlowHighGear();

    void setNonSlowLowGear();

}
