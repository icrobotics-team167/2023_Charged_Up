package frc.robot.routines.auto;

import java.time.Duration;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class DriveForwardsUntil extends Action {

    private PeriodicTimer timer;
    private AHRS ahrs;

    private DriveForwardsCondition condition;
    private double speed;
    private boolean isDone = false;
    private Duration timeout;

    public DriveForwardsUntil(DriveForwardsCondition condition, double speed, Duration timeout) {
        super();
        this.condition = condition;
        this.timer = new PeriodicTimer();
        this.timeout = timeout;

        try {
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }

    @Override
    public void init() {
        Subsystems.driveBase.setBrake();
        timer.reset();
    }

    // new code starts here:
    public void periodic() {
        if (this.isDone) {
            return;
        }

        var conditionMet = this.condition.call(this.ahrs);
        var timeoutReached = timer.hasElapsed(this.timeout.toMillis() / 1_000);

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
