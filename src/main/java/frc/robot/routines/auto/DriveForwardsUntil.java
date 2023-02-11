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
    private double maxRollValue;
    private boolean timerReset;

    public DriveForwardsUntil(DriveForwardsCondition condition, double speed, Duration timeout) {
        super();
        this.condition = condition;
        this.timer = new PeriodicTimer();
        this.timeout = timeout;
        this.speed = speed;
        timerReset=false;
        try {
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }

    @Override
    public void init() {
        // Subsystems.driveBase.setBrake();
        timer.reset();
    }

    // new code starts here:
    public void periodic() {
        SmartDashboard.putBoolean("isCalibrating", ahrs.isCalibrating());
        if (ahrs.isCalibrating()) {
            return;
        }

        if(!timerReset) {
            timer.reset();
            timerReset=true;
        }

        SmartDashboard.putNumber("pitch", ahrs.getPitch());
        maxRollValue=Math.max(maxRollValue, Math.abs(ahrs.getPitch()));
        SmartDashboard.putNumber("maxRollValue", maxRollValue);

        var conditionMet = this.condition.call(this.ahrs);
        var timeoutReached = timer.hasElapsed(this.timeout.toMillis() / 1_000);

        SmartDashboard.putBoolean("conditionMet", conditionMet);
        SmartDashboard.putBoolean("timeoutReached", timeoutReached);


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