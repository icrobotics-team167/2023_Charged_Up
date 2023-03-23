package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.MovingAverage;
import frc.robot.util.PID;
import frc.robot.util.PeriodicTimer;
import java.time.Duration;

public class DriveStraightUntil extends Action {

    private PeriodicTimer timer;
    private AHRS navx = Subsystems.navx;

    private DriveForwardsCondition condition;
    private double speed;
    private boolean isDone = false;
    private Duration timeout;
    private double maxPitchValue;
    private boolean conditionMet;
    private boolean timeoutReached;
    private double startAngle;
    private PID pidController;
    private final double P = 0.01;
    private final double I = 0;
    private final double D = 0.008;
    private MovingAverage angleFilter;

    public DriveStraightUntil(DriveForwardsCondition condition, double speed, Duration timeout) {
        super();
        this.condition = condition;
        this.timer = new PeriodicTimer();
        this.timeout = timeout;
        this.speed = speed;
        this.conditionMet = false;
        this.timeoutReached = false;
        this.isDone = false;
        angleFilter = new MovingAverage(25, false);
    }

    @Override
    public void init() {
        conditionMet = false;
        timeoutReached = false;
        isDone = false;
        timer.reset();
        startAngle = navx.getAngle();
        pidController = new PID(P, I, D, timer.get(), startAngle);
        angleFilter.clear();
    }

    // new code starts here:
    public void periodic() {
        if (navx.isCalibrating()) {
            return;
        }

        maxPitchValue = Math.max(maxPitchValue, Math.abs(navx.getPitch()));

        conditionMet = this.condition.call(this.navx);
        timeoutReached = timer.hasElapsed(this.timeout.toMillis() / 1_000);

        // Compute the PID for keeping the robot straight
        angleFilter.add(navx.getAngle());
        double angle = angleFilter.get();
        double pidOutput = pidController.compute(angle, timer.get());
        // Clamp the PID output
        pidOutput = MathUtil.clamp(pidOutput, -1, 1);

        if (conditionMet || timeoutReached) {
            this.isDone = true;
            Subsystems.driveBase.tankDrive(0, 0);
        } else {
            Subsystems.driveBase.tankDrive(speed - pidOutput, speed + pidOutput);
        }

    }

    @Override
    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }
}