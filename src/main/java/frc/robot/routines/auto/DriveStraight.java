package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.subsystems.turret.TurretPosition;
import frc.robot.util.PeriodicTimer;
import frc.robot.util.MovingAverage;
import frc.robot.util.PID;

public class DriveStraight extends Action {

    private double meters;
    private double speed;
    private double timeoutSeconds;
    private double leftEncoderInitialPosition;
    private double rightEncoderInitialPosition;

    private boolean turretDone = true;
    private boolean driveDone;

    private PeriodicTimer timer;
    private double startAngle;

    private PID pidController;
    private double P = 0.01;
    private double I = 0.0;
    private double D = 0.008;

    private TurretPosition targetState = null;

    private MovingAverage angleFilter;

    private AHRS navx = Subsystems.navx;
    /**
     * Constructs a new DriveStraight auto routine.
     * 
     * @param inches Distance to drive in inches
     * @param speed  How fast to drive
     */
    public DriveStraight(double inches, double speed) {
        this(inches, speed, -1);
    }

    /**
     * Constructs a new DriveStraight auto routine.
     * 
     * @param inches         Distance to drive in inches
     * @param speed          How fast to drive
     * @param timeoutSeconds How many seconds before it times out and gives up on
     *                       trying to reach the target distance. -1 for no timeout.
     */
    public DriveStraight(double inches, double speed, double timeoutSeconds) {
        super();
        meters = Units.inchesToMeters(inches);
        this.speed = speed;
        this.timeoutSeconds = timeoutSeconds;
        leftEncoderInitialPosition = 0;
        rightEncoderInitialPosition = 0;

        timer = new PeriodicTimer();

        angleFilter = new MovingAverage(25, false);
    }

    /**
     * Modifies DriveStraight to move the arm as well
     * 
     * @param target A turret position that the arm wants to move to
     * @return Returns a new version of DriveStraight that wants to move the arm to the target.
     */
    public DriveStraight withTurret(TurretPosition target) {
        targetState = target;
        turretDone = false;
        return this;
    }



    @Override
    public void init() {
        Subsystems.driveBase.setBrake();
        leftEncoderInitialPosition = Subsystems.driveBase.getLeftEncoderPosition();
        rightEncoderInitialPosition = Subsystems.driveBase.getRightEncoderPosition();
        startAngle = navx.getAngle();
        timer.reset();
        pidController = new PID(P, I, D, timer.get(), startAngle);
    }

    // new code starts here:
    public void periodic() {
        if (timeoutSeconds >= 0 && timer.hasElapsed(timeoutSeconds)) { // If the timeout isn't a sentinel value and the
                                                                       // timeout has happened
            // Stop the routine and stop the robot
            state = AutoState.DONE;
            Subsystems.driveBase.stop();
        }

        // Compute the PID for keeping the robot straight
        angleFilter.add(navx.getAngle());
        double angle = angleFilter.get();
        double pidOutput = pidController.compute(angle, timer.get());
        // Clamp the PID output
        pidOutput = MathUtil.clamp(pidOutput, -1, 1);

        // Move the robot
        if(driveDone) {
            Subsystems.driveBase.stop();
        } else {
            driveDone = isDistanceReached();
            Subsystems.driveBase.tankDrive(speed - pidOutput, speed + pidOutput);
        }

        // Move the arm if specified to
        if(targetState != null && !turretDone) {
            turretDone = Subsystems.turret.moveTo(targetState);
        } else {
            Subsystems.turret.stop();
        }
    }

    @Override
    public boolean isDone() {
        return driveDone && turretDone;
    }

    /**
     * Calculates whether the drive base has stopped moving by checking how far it has driven compared 
     * to how far it wants to drive
     * @return The above
     */
    private boolean isDistanceReached() {
        // Calculates whether the drive base has stopped moving by checking how far it has driven compared 
        // to how far it wants to drive
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
        Subsystems.turret.stop();
    }

}
