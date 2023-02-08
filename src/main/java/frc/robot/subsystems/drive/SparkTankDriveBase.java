package frc.robot.subsystems.drive;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
// import com.revrobotics.SparkMaxAlternateEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.ControlType;
// import com.revrobotics.SparkMaxRelativeEncoder;
// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.SparkMaxPIDController;
// import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.MathUtil;
import frc.robot.Config;

public class SparkTankDriveBase implements TankDriveBase {

    private AHRS navx;
    private CANSparkMax leftMaster;
    private CANSparkMax leftSlave1;
    private CANSparkMax leftSlave2;
    private CANSparkMax rightMaster;
    private CANSparkMax rightSlave1;
    private CANSparkMax rightSlave2;
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;
    private boolean straightDriving;
    private double straightDriveAngleSetpoint;
    private PIDController straightDrivePID;
    private final double STRAIGHT_DRIVE_KP = 0.015; // 0.02 works
    private final double STRAIGHT_DRIVE_KI = 0;
    private final double STRAIGHT_DRIVE_KD = 0;
    private SparkMaxPIDController leftPID;
    private final double LEFT_KP = 0.002; // last tried: 0.0001
    private final double LEFT_KI = 0;
    private final double LEFT_KD = 0;
    private final double LEFT_KF = 0.000268; // with slower paths?: 0.00075
    private SparkMaxPIDController rightPID;
    private final double RIGHT_KP = 0.0015; // last tried: 0.0001
    private final double RIGHT_KI = 0;
    private final double RIGHT_KD = 0;
    private final double RIGHT_KF = 0.000232; // with slower paths?: 0.00075
    private Solenoid Solenoid;
    private boolean highGear;

    // Singleton
    private static SparkTankDriveBase instance;

    public static SparkTankDriveBase getInstance() {
        if (instance == null) {
            instance = new SparkTankDriveBase();
        }
        return instance;
    }

    private SparkTankDriveBase() {
        try {
            navx = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException e) {
            DriverStation.reportError("Error initializing the navX over SPI: " + e.toString(), e.getStackTrace());
        }

        leftMaster = new CANSparkMax(Config.Ports.SparkTank.LEFT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave1 = new CANSparkMax(Config.Ports.SparkTank.LEFT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave2 = new CANSparkMax(Config.Ports.SparkTank.LEFT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMaster = new CANSparkMax(Config.Ports.SparkTank.RIGHT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave1 = new CANSparkMax(Config.Ports.SparkTank.RIGHT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave2 = new CANSparkMax(Config.Ports.SparkTank.RIGHT_3, CANSparkMaxLowLevel.MotorType.kBrushless);

        leftMaster.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();
        leftMaster.setInverted(true);
        leftSlave1.setInverted(true);
        leftSlave2.setInverted(true);
        rightMaster.setInverted(false);
        rightSlave1.setInverted(false);
        rightSlave2.setInverted(false);
        leftMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftSlave1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftSlave2.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSlave1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSlave2.setIdleMode(CANSparkMax.IdleMode.kBrake);

        leftEncoder = leftMaster.getAlternateEncoder(4096);
        rightEncoder = rightMaster.getAlternateEncoder(4096);
        leftMaster.setSmartCurrentLimit(80);
        leftMaster.setSecondaryCurrentLimit(60);
        rightMaster.setSmartCurrentLimit(80);
        rightMaster.setSecondaryCurrentLimit(60);
        leftMaster.setOpenLoopRampRate(0);
        leftMaster.setClosedLoopRampRate(0);
        rightMaster.setOpenLoopRampRate(0);
        rightMaster.setClosedLoopRampRate(0);

        straightDrivePID = new PIDController(STRAIGHT_DRIVE_KP, STRAIGHT_DRIVE_KI, STRAIGHT_DRIVE_KD);
        straightDrivePID.setTolerance(0.4);

        leftPID = leftMaster.getPIDController();
        leftPID.setP(LEFT_KP);
        leftPID.setI(LEFT_KI);
        leftPID.setD(LEFT_KD);
        leftPID.setFF(LEFT_KF);
        rightPID = rightMaster.getPIDController();
        rightPID.setP(RIGHT_KP);
        rightPID.setI(RIGHT_KI);
        rightPID.setD(RIGHT_KD);
        rightPID.setFF(RIGHT_KF);

        leftSlave1.follow(leftMaster);
        leftSlave2.follow(leftMaster);
        rightSlave1.follow(rightMaster);
        rightSlave2.follow(rightMaster);

        Solenoid = new Solenoid(
                Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PH : 2,
                PneumaticsModuleType.REVPH,
                Config.Ports.SparkTank.LOW_GEAR

        );

        highGear = true;
    }

    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        rightMaster.set(-leftSpeed);
        leftMaster.set(-rightSpeed);
        straightDriving = false;
    }

    @Override
    public void arcadeDrive(double throttle, double wheel) {
        double Lval = throttle - wheel;
        double Rval = throttle + wheel;

        double leftSpeed = Lval;
        double rightSpeed = Rval;

        if (Math.abs(Lval) > 1) {
            leftSpeed = Lval / Math.abs(Lval);
            rightSpeed = Rval / Math.abs(Lval);
        } else if (Math.abs(Rval) > 1) {
            leftSpeed = Lval / Math.abs(Rval);
            rightSpeed = Rval / Math.abs(Rval);
        }
        rightMaster.set(leftSpeed);
        leftMaster.set(rightSpeed);
    }

    @Override
    public void toggleGearing() {
        if (highGear) {
            setLowGear();
        } else {
            setHighGear();
        }
    }

    @Override
    public void setHighGear() {
        Solenoid.set(false);
        highGear = true;
    }

    @Override
    public void setLowGear() {
        Solenoid.set(true);
        highGear = false;
    }

    @Override
    public boolean isHighGear() {
        return highGear;
    }

    @Override
    public boolean isLowGear() {
        return !highGear;
    }

    @Override
    public void straightDrive(double speed) {
        straightDrive(speed, true);
    }

    @Override
    public void straightDrive(double speed, boolean newAngle) {
        if (!straightDriving) {
            straightDriving = true;
            straightDriveAngleSetpoint = newAngle ? navx.getAngle() : straightDriveAngleSetpoint;
            straightDrivePID.setSetpoint(straightDriveAngleSetpoint);
        }

        double error = straightDrivePID.calculate(navx.getAngle());
        leftMaster.set(MathUtil.clamp(speed - error, -1, 1));
        rightMaster.set(MathUtil.clamp(speed + error, -1, 1));
    }

    @Override
    public void stop() {
        tankDrive(0, 0);
    }

    @Override
    public void pointTurn(double speed) {
        leftMaster.set(speed);
        rightMaster.set(-speed);
    }

    @Override
    public double getAngle() {
        return navx.getAngle();
    }

    @Override
    public double getLeftEncoderPosition() {
        double encoderValue = leftEncoder.getPosition();

        if (highGear) {
            return Units.inchesToMeters(encoderValue * 4 * Math.PI / 4.17);
        } else {
            return Units.inchesToMeters(encoderValue * 4 * Math.PI / 11.03);
        }
    }

    @Override
    public double getRightEncoderPosition() {
        double encoderValue = rightEncoder.getPosition();

        if (highGear) {
            return Units.inchesToMeters(encoderValue * 4 * Math.PI / 4.17);
        } else {
            return Units.inchesToMeters(encoderValue * 4 * Math.PI / 11.03);
        }
    }

    @Override
    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void setReferences(double leftMetersPerSecond, double rightMetersPerSecond) {
        double leftSpeed = metersPerSecondToRPM(leftMetersPerSecond);
        leftPID.setReference(leftSpeed, ControlType.kVelocity);
        System.out.println("Left RPM: " + leftSpeed);

        double rightSpeed = metersPerSecondToRPM(rightMetersPerSecond);
        rightPID.setReference(rightSpeed, ControlType.kVelocity);
        System.out.println("Right RPM: " + rightSpeed);

        straightDriving = false;
    }

    @Override
    public Rotation2d getGyroHeading() {
        return Rotation2d.fromDegrees(-navx.getRate());
    }

    @Override
    public double metersPerSecondToRPM(double metersPerSecond) {
        if (highGear) {
            return 4.17 * 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI);
        } else {
            return 11.03 * 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI);
        }
    }

    @Override
    public void setCoast() {
        leftMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightMaster.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftSlave1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        leftSlave2.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightSlave1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        rightSlave2.setIdleMode(CANSparkMax.IdleMode.kCoast);
    }

    @Override
    public void setBrake() {
        leftMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftSlave1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftSlave2.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSlave1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSlave2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    }

}
