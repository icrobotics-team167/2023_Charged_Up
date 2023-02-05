package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoBalance extends Action {

    // private double speedRange;
    private PeriodicTimer timer;
    private AHRS ahrs;

    public AutoBalance() {
        super();
        try {
            ahrs = new AHRS(SPI.Port.kMXP);
            DriverStation.reportError("Not really an error, successfully loaded navX", true);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        Subsystems.driveBase.setBrake();
        timer.reset();
    }

    // new code starts here:
    public void periodic() {
        double roll = ahrs.getRoll();

        double noMoveAngle = 2.0;
        double minSpeedAngle = 3.0;
        double maxSpeedAngle = 12.0;
        double minSpeed = 0.1;
        double maxSpeed = 0.4;
        double slope = (maxSpeed - minSpeed) / (maxSpeedAngle - minSpeedAngle);

        if (Math.abs(roll) <= noMoveAngle) {
            Subsystems.driveBase.stop();
        } else if (roll > noMoveAngle) {
            if (roll <= minSpeedAngle) {
                Subsystems.driveBase.tankDrive(minSpeed * -1, minSpeed * -1);
            } else {
                double speed = slope * roll + (minSpeed - minSpeedAngle * slope);
                Subsystems.driveBase.tankDrive(speed * -1, speed * -1);
            }
        } else if (roll < noMoveAngle * -1) {
            if (roll >= minSpeedAngle * -1) {
                Subsystems.driveBase.tankDrive(minSpeed, minSpeed);
            } else {
                double speed = slope * roll * -1 + (minSpeed - minSpeedAngle * slope);
                Subsystems.driveBase.tankDrive(speed, speed);
            }
        }
        SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
