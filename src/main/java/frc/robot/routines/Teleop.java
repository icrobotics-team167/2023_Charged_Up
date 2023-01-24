package frc.robot.routines;

import com.kauailabs.navx.frc.AHRS;

// import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {

    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Intake intake;
    private Indexer indexer;
    private Shooter shooter;
    private Climber climber;
    // private boolean climbMode;

    AHRS ahrs;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        intake = Subsystems.intake;
        indexer = Subsystems.indexer;
        shooter = Subsystems.shooter;
        climber = Subsystems.climber;
    }

    public void init() {
        driveBase.setHighGear();
        // driveBase.setCoast();
        driveBase.resetEncoders();
        // intake.setMode(Intake.Mode.OFF);
        // intake.retract();
        // indexer.setMode(Indexer.Mode.OFF);
        // shooter.start();
        // climber.retract();
        // climbMode = false;
    }

    public void periodic() {

        // driveBase.setCoast();
        // //Classic Tank Drive Controls
        // if(controls.doFlipityFlop()) {
        // driveBase.tankDrive(controls.getTankLeftSpeed(),
        // controls.getTankRightSpeed());
        // }else{
        // driveBase.tankDrive(controls.getTankRightSpeed()*-1,
        // controls.getTankLeftSpeed()*-1);
        // }

        // Arcade Tank Drive Controls
        if (controls.doFlipityFlop()) {
            driveBase.arcadeDrive(controls.getTankThrottle(), controls.getTankWheel());
        } else {
            driveBase.arcadeDrive(controls.getTankThrottle() * -1, controls.getTankWheel() * -1);
        }

        if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        } else {
            driveBase.setHighGear();
        }

        /* Display 6-axis Processed Angle Data */
        SmartDashboard.putBoolean("IMU_Connected", ahrs.isConnected());
        SmartDashboard.putBoolean("IMU_IsCalibrating", ahrs.isCalibrating());
        SmartDashboard.putNumber("IMU_Yaw", ahrs.getYaw());
        SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
        SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());

        /* Display tilt-corrected, Magnetometer-based heading (requires */
        /* magnetometer calibration to be useful) */

        SmartDashboard.putNumber("IMU_CompassHeading", ahrs.getCompassHeading());

        /* Display 9-axis Heading (requires magnetometer calibration to be useful) */
        SmartDashboard.putNumber("IMU_FusedHeading", ahrs.getFusedHeading());

        /* These functions are compatible w/the WPI Gyro Class, providing a simple */
        /* path for upgrading from the Kit-of-Parts gyro to the navx-MXP */

        SmartDashboard.putNumber("IMU_TotalYaw", ahrs.getAngle());
        SmartDashboard.putNumber("IMU_YawRateDPS", ahrs.getRate());

        /* Display Processed Acceleration Data (Linear Acceleration, Motion Detect) */

        SmartDashboard.putNumber("IMU_Accel_X", ahrs.getWorldLinearAccelX());
        SmartDashboard.putNumber("IMU_Accel_Y", ahrs.getWorldLinearAccelY());
        SmartDashboard.putBoolean("IMU_IsMoving", ahrs.isMoving());
        SmartDashboard.putBoolean("IMU_IsRotating", ahrs.isRotating());

        /* Display estimates of velocity/displacement. Note that these values are */
        /* not expected to be accurate enough for estimating robot position on a */
        /* FIRST FRC Robotics Field, due to accelerometer noise and the compounding */
        /* of these errors due to single (velocity) integration and especially */
        /* double (displacement) integration. */

        SmartDashboard.putNumber("Velocity_X", ahrs.getVelocityX());
        SmartDashboard.putNumber("Velocity_Y", ahrs.getVelocityY());
        SmartDashboard.putNumber("Displacement_X", ahrs.getDisplacementX());
        SmartDashboard.putNumber("Displacement_Y", ahrs.getDisplacementY());

        /* Display Raw Gyro/Accelerometer/Magnetometer Values */
        /* NOTE: These values are not normally necessary, but are made available */
        /* for advanced users. Before using this data, please consider whether */
        /* the processed data (see above) will suit your needs. */

        SmartDashboard.putNumber("RawGyro_X", ahrs.getRawGyroX());
        SmartDashboard.putNumber("RawGyro_Y", ahrs.getRawGyroY());
        SmartDashboard.putNumber("RawGyro_Z", ahrs.getRawGyroZ());
        SmartDashboard.putNumber("RawAccel_X", ahrs.getRawAccelX());
        SmartDashboard.putNumber("RawAccel_Y", ahrs.getRawAccelY());
        SmartDashboard.putNumber("RawAccel_Z", ahrs.getRawAccelZ());
        SmartDashboard.putNumber("RawMag_X", ahrs.getRawMagX());
        SmartDashboard.putNumber("RawMag_Y", ahrs.getRawMagY());
        SmartDashboard.putNumber("RawMag_Z", ahrs.getRawMagZ());
        SmartDashboard.putNumber("IMU_Temp_C", ahrs.getTempC());

        /* Omnimount Yaw Axis Information */
        /* For more info, see http://navx-mxp.kauailabs.com/installation/omnimount */
        AHRS.BoardYawAxis yaw_axis = ahrs.getBoardYawAxis();
        SmartDashboard.putString("YawAxisDirection", yaw_axis.up ? "Up" : "Down");
        SmartDashboard.putNumber("YawAxis", yaw_axis.board_axis.getValue());

        /* Sensor Board Information */
        SmartDashboard.putString("FirmwareVersion", ahrs.getFirmwareVersion());

        /* Quaternion Data */
        /* Quaternions are fascinating, and are the most compact representation of */
        /* orientation data. All of the Yaw, Pitch and Roll Values can be derived */
        /* from the Quaternions. If interested in motion processing, knowledge of */
        /* Quaternions is highly recommended. */
        SmartDashboard.putNumber("QuaternionW", ahrs.getQuaternionW());
        SmartDashboard.putNumber("QuaternionX", ahrs.getQuaternionX());
        SmartDashboard.putNumber("QuaternionY", ahrs.getQuaternionY());
        SmartDashboard.putNumber("QuaternionZ", ahrs.getQuaternionZ());

        /* Connectivity Debugging Support */
        SmartDashboard.putNumber("IMU_Byte_Count", ahrs.getByteCount());
        SmartDashboard.putNumber("IMU_Update_Count", ahrs.getUpdateCount());

        // // Shooter
        // if (controls.doToggleShooter()) {
        // shooter.toggle();
        // }

        // if (controls.doRetractIntake()){
        // intake.retract();
        // }

        // if (controls.doExtendIntake()){
        // intake.extend();
        // }

        // if (controls.doRunIntake()){
        // intake.extend();
        // intake.setMode(Intake.Mode.FORWARD);
        // indexer.setMode(Indexer.Mode.INDEX);
        // } else if (controls.doRunIntakeRev()){
        // intake.extend();
        // intake.setMode(Intake.Mode.REVERSE);
        // } else{
        // intake.setMode(Intake.Mode.OFF);
        // indexer.setMode(Indexer.Mode.OFF);
        // }

        // if(controls.doRunPreShooter()){
        // intake.retract();
        // intake.setMode(Intake.Mode.FORWARD);
        // indexer.setMode(Indexer.Mode.SHOOT);
        // }

        // climber.climb(controls.getElevatorSpeed());

        // if(controls.doArmExtend()){
        // climber.extend();
        // } else if(controls.doArmRetract()){
        // climber.retract();
        // }

        // // Run
        // intake.run();
        // indexer.run();
        // shooter.run();

    }

}
