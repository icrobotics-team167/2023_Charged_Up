package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import java.time.Duration;

public class DriveForwardsUntil extends Action {

    private PeriodicTimer timer;
    private AHRS navx = Subsystems.navx;

    private DriveForwardsCondition condition;
    private double speed;
    private boolean isDone = false;
    private Duration timeout;
    private double maxPitchValue;
    private boolean conditionMet;
    private boolean timeoutReached;

    public DriveForwardsUntil(DriveForwardsCondition condition, double speed, Duration timeout) {
        super();
        this.condition = condition;
        this.timer = new PeriodicTimer();
        this.timeout = timeout;
        this.speed = speed;
        this.conditionMet = false;
        this.timeoutReached = false;
        this.isDone = false;
    }

    @Override
    public void init() {
        conditionMet = false;
        timeoutReached = false;
        isDone = false;
        timer.reset();
    }

    // new code starts here:
    public void periodic() {
        if (navx.isCalibrating()) {
            return;
        }

        maxPitchValue = Math.max(maxPitchValue, Math.abs(navx.getPitch()));

        conditionMet = this.condition.call(this.navx);
        timeoutReached = timer.hasElapsed(this.timeout.toMillis() / 1_000);

        if (conditionMet || timeoutReached) {
            this.isDone = true;
            Subsystems.driveBase.tankDrive(0, 0);
        } else {
            Subsystems.driveBase.tankDrive(this.speed, this.speed);
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