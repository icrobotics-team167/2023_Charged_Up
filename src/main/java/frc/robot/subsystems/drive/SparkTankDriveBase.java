package frc.robot.subsystems.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Config;
import frc.robot.util.MovingAverage;

public class SparkTankDriveBase implements TankDriveBase {
    private CANSparkMax leftMaster;
    private CANSparkMax leftSlave1;
    private CANSparkMax leftSlave2;
    private CANSparkMax rightMaster;
    private CANSparkMax rightSlave1;
    private CANSparkMax rightSlave2;
    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;
    private Solenoid Solenoid;
    private boolean highGear;
    private MovingAverage voltageFilter;

    private double normalSpeed = 1;
    private double slowSpeed = normalSpeed * 0.5;
    private double speedMultiplier = normalSpeed;

    private final int SMART_CURRENT_LIMIT = 60;
    private final int SECONDARY_CURRENT_LIMIT = 80;

    private final double WHEEL_DIAMETER = 6;

    private boolean nonSlowHighGear = false;

    // Singleton
    private static SparkTankDriveBase instance;

    /**
     * Allows only one instance of SparkTankDriveBase to exist at once.
     * 
     * @return An instance of SparkTankDriveBase. Creates a new one if it doesn't
     *         exist already.
     */
    public static SparkTankDriveBase getInstance() {
        if (instance == null) {
            instance = new SparkTankDriveBase();
        }
        return instance;
    }

    private SparkTankDriveBase() {
        // Initialize drive motors
        leftMaster = new CANSparkMax(Config.Ports.SparkTank.LEFT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave1 = new CANSparkMax(Config.Ports.SparkTank.LEFT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftSlave2 = new CANSparkMax(Config.Ports.SparkTank.LEFT_3, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMaster = new CANSparkMax(Config.Ports.SparkTank.RIGHT_1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave1 = new CANSparkMax(Config.Ports.SparkTank.RIGHT_2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightSlave2 = new CANSparkMax(Config.Ports.SparkTank.RIGHT_3, CANSparkMaxLowLevel.MotorType.kBrushless);

        // Set drive motor settings
        leftMaster.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();

        leftMaster.setInverted(true);
        rightMaster.setInverted(false);

        leftMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftSlave1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        leftSlave2.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightMaster.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSlave1.setIdleMode(CANSparkMax.IdleMode.kBrake);
        rightSlave2.setIdleMode(CANSparkMax.IdleMode.kBrake);

        leftMaster.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        leftMaster.setSecondaryCurrentLimit(SECONDARY_CURRENT_LIMIT);
        leftSlave1.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        leftSlave1.setSecondaryCurrentLimit(SECONDARY_CURRENT_LIMIT);
        leftSlave2.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        leftSlave2.setSecondaryCurrentLimit(SECONDARY_CURRENT_LIMIT);
        rightMaster.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        rightMaster.setSecondaryCurrentLimit(SECONDARY_CURRENT_LIMIT);
        rightSlave1.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        rightSlave1.setSecondaryCurrentLimit(SECONDARY_CURRENT_LIMIT);
        rightSlave2.setSmartCurrentLimit(SMART_CURRENT_LIMIT);
        rightSlave2.setSecondaryCurrentLimit(SECONDARY_CURRENT_LIMIT);

        leftMaster.setOpenLoopRampRate(0);
        leftMaster.setClosedLoopRampRate(0);
        leftSlave1.setOpenLoopRampRate(0);
        leftSlave1.setClosedLoopRampRate(0);
        leftSlave2.setOpenLoopRampRate(0);
        leftSlave2.setClosedLoopRampRate(0);
        rightMaster.setOpenLoopRampRate(0);
        rightMaster.setClosedLoopRampRate(0);
        rightSlave1.setOpenLoopRampRate(0);
        rightSlave1.setClosedLoopRampRate(0);
        rightSlave2.setOpenLoopRampRate(0);
        rightSlave2.setClosedLoopRampRate(0);

        leftSlave1.follow(leftMaster, false);
        leftSlave2.follow(leftMaster, false);
        rightSlave1.follow(rightMaster, false);
        rightSlave2.follow(rightMaster, false);

        // Initialize drive encoders
        leftEncoder = leftMaster.getEncoder();
        rightEncoder = rightMaster.getEncoder();

        // Initialize gear switching solenoids
        Solenoid = new Solenoid(
                Config.Ports.SparkTank.PH,
                PneumaticsModuleType.REVPH,
                Config.Ports.SparkTank.LOW_GEAR

        );

        setLowGear();

        voltageFilter = new MovingAverage(25, true);
    }

    /**
     * Drives using tank drive controls.
     * 
     * @param leftSpeed  The motor speeds for the left side of the robot. Between -1
     *                   (100% speed backwards) and 1. (100% speed forwards)
     * @param rightSpeed The motor speeds for the right side of the robot. Between
     *                   -1
     *                   (100% speed backwards) and 1. (100% speed forwards)
     */
    @Override
    public void tankDrive(double leftSpeed, double rightSpeed) {
        double voltageMultiplier = adjustVoltage();

        rightMaster.set(leftSpeed * speedMultiplier * voltageMultiplier);
        leftMaster.set(rightSpeed * speedMultiplier * voltageMultiplier);
    }

    /**
     * Drives using arcade drive controls.
     * 
     * @param throttle The throttle speed for the robot's forward motion. Between -1
     *                 (100% speed backwards) and 1. (100% speed forwards)
     * @param wheel    The speed for the robot's turning motion. Between -1 (100%
     *                 speed clockwise) and 1. (100% speed counterclockwise)
     */
    @Override
    public void arcadeDrive(double throttle, double wheel) {
        double voltageMultiplier = adjustVoltage();

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

        rightMaster.set(leftSpeed * speedMultiplier * voltageMultiplier);
        leftMaster.set(rightSpeed * speedMultiplier * voltageMultiplier);
    }

    /**
     * Toggles between high gear and low gear.
     */
    @Override
    public void toggleGearing() {
        if (highGear) {
            setLowGear();
        } else {
            setHighGear();
        }
        SmartDashboard.putBoolean("SparkTankDriveBase.highGear", highGear);
    }

    /**
     * Sets the drivetrain to high gear.
     */
    @Override
    public void setHighGear() {
        Solenoid.set(true);
        highGear = true;
    }

    /**
     * Sets the drivetrain to low gear.
     */
    @Override
    public void setLowGear() {
        Solenoid.set(false);
        highGear = false;
    }

    /**
     * Sets whether or not to move at a slower speed or not.
     * 
     * @param lowerGear Whether or not to move at slow mode. true for slower mode,
     *                  false for normal mode.
     */
    @Override
    public void setLowerGear(boolean lowerGear) {
        if (lowerGear) {
            speedMultiplier = slowSpeed;
            setLowGear();
        } else {
            speedMultiplier = normalSpeed;
            if (nonSlowHighGear) {
                setHighGear();
            } else {
                setLowGear();
            }
        }
        SmartDashboard.putBoolean("SparkTankDriveBase.lowerGear", lowerGear);
    }

    /**
     * @return Whether or not the drivetrain is on high gear or not.
     */
    @Override
    public boolean isHighGear() {
        return highGear;
    }

    /**
     * @return Whether or not the drivetrain is on low gear or not.
     */
    @Override
    public boolean isLowGear() {
        return !highGear;
    }

    /**
     * @return Whether or not the drivetrain is on slow mode or not.
     */
    @Override
    public boolean isLowerGear() {
        return speedMultiplier != 1;
    }

    /**
     * Stops the drivetrain.
     */
    @Override
    public void stop() {
        tankDrive(0, 0);
    }

    /**
     * Gets the encoder positon of the left side of the robots, in meters traveled.
     */
    @Override
    public double getLeftEncoderPosition() {
        return encoderDistanceToMeters(leftEncoder.getPosition());
    }

    /**
     * Gets the encoder position of the right side of the robot, in meters traveled.
     */
    @Override
    public double getRightEncoderPosition() {
        return encoderDistanceToMeters(rightEncoder.getPosition());
    }

    /**
     * Converts encoder ticks to meters traveled.
     * 
     * @param encoderValue The encoder position
     * @return Meters traveled.
     */
    public double encoderDistanceToMeters(double encoderValue) {
        double gearRatio = highGear ? 5.1 : 13.5;
        double wheelCircumferenceInches = WHEEL_DIAMETER * Math.PI;
        double scalar = 0.9;
        return Units.inchesToMeters(encoderValue * (wheelCircumferenceInches / gearRatio) / scalar);
    }

    /**
     * Resets the encoders to 0.
     */
    @Override
    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public double metersPerSecondToRPM(double metersPerSecond) {
        if (highGear) {
            return 5.1 * 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI); // Ask later what the hell is the
                                                                                     // 60 and the 5 doing here
        } else {
            return 13.5 * 60 * Units.metersToInches(metersPerSecond) / (5 * Math.PI);
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

    /**
     * Returns a multiplier for drive motor speeds depending on the battery voltage
     * to prevent brownouts.
     * 
     * @return A speed multiplier, from 0 to 1 (0% to 100% speed)
     */
    private double adjustVoltage() {
        final double MIN_VOLTAGE = 10; // Minimum voltage where if it falls below this, motor speeds are multiplied by
                                       // MIN_MULTIPLIER
        final double NOMINAL_VOLTAGE = 12; // Nominal voltage where the motors can run full speed
        final double MIN_MULTIPLIER = 0.25; // Minimum multiplier for when the voltage falls below MIN_VOLTAGE

        double output;

        double voltage = RobotController.getBatteryVoltage();

        // Moving average filter
        voltageFilter.add(voltage);
        double filteredVoltage = voltageFilter.get();

        if (filteredVoltage < MIN_VOLTAGE) {
            output = MIN_MULTIPLIER;
        } else if (filteredVoltage >= NOMINAL_VOLTAGE) {
            output = 1;
        } else {
            var a = MIN_VOLTAGE;
            var b = NOMINAL_VOLTAGE;
            var c = MIN_MULTIPLIER;
            // y = output
            var x = filteredVoltage;
            // y = (x(1-c))/(b-a) - (a(1-c)/(b-a)) + c
            output = (x * (1 - c)) / (b - a) - (a * (1 - c) / (b - a)) + c;
        }
        SmartDashboard.putNumber("SparkTankDriveBase.voltageMultiplier", output);
        return output;
    }

    @Override
    public void setNonSlowHighGear(boolean highGear) {
        nonSlowHighGear = highGear;
    }
}
