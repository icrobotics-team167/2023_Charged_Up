package frc.robot.routines.auto;

import java.time.Duration;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForwardsUntil extends Action {

    private PeriodicTimer timer;
    private AHRS ahrs;

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
        try {
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }

    @Override
    public void init() {
        // Subsystems.driveBase.setBrake();
        conditionMet = false;
        timeoutReached = false;
        isDone = false;
        timer.reset();
    }

    // new code starts here:
    public void periodic() {
        SmartDashboard.putBoolean("isCalibrating", ahrs.isCalibrating());
        if (ahrs.isCalibrating()) {
            return;
        }

        SmartDashboard.putNumber("pitch", ahrs.getPitch());
        maxPitchValue = Math.max(maxPitchValue, Math.abs(ahrs.getPitch()));
        SmartDashboard.putNumber("maxRollValue", maxPitchValue);

        conditionMet = this.condition.call(this.ahrs);
        timeoutReached = timer.hasElapsed(this.timeout.toMillis() / 1_000);

        SmartDashboard.putBoolean("conditionMet", conditionMet);
        SmartDashboard.putBoolean("timeoutReached", timeoutReached);
        SmartDashboard.putNumber("timer", timer.get());

        if (conditionMet || timeoutReached) {
            this.isDone = true;
            Subsystems.driveBase.tankDrive(0, 0);
        } else {
            Subsystems.driveBase.tankDrive(this.speed, this.speed);
        }
        SmartDashboard.putBoolean("hello", conditionMet || timeoutReached);
        SmartDashboard.putBoolean("done", this.isDone);

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