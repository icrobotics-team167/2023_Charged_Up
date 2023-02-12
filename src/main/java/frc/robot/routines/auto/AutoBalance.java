package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.PID;
import frc.robot.controls.controlschemes.ControlScheme;

public class AutoBalance extends Action {

    // private double speedRange;
    private PeriodicTimer timer;
    private AHRS ahrs;
    private PID pidController;
    private double maxOutput;
    private boolean motorsEnabled;
    private double sensitivityThreshold;
    private boolean teleop;
    private ControlScheme controls;

    public AutoBalance() {
        this(false,null);
    }

    public AutoBalance(boolean teleop, ControlScheme controls) {
        super();
        try {
            ahrs = new AHRS(SPI.Port.kMXP);
            // DriverStation.reportError("Not really an error, successfully loaded navX", true);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        this.teleop = teleop;
        this.controls = controls;
        timer = new PeriodicTimer();
    }

    @Override
    public void init() {
        Subsystems.driveBase.setBrake();
        timer.reset();
        motorsEnabled = true;
        // Minimum value before pidOutput takes effect, if it is below sensitivityThreshold pidOutput is set to 0
        sensitivityThreshold = 0.02;
        // Max roll value we saw was +-12, scaling proportional coefficient to that
        maxOutput = 0.1;
        // * -1 is there to flip the output values around (positive roll means we want
        // to drive backwards)
        double proportionalCoefficient = maxOutput / 12.0 * -1;
        // Target roll value. Not 0 because the robot isn't perfectly flat on the cart
        double target = 0.0;
        pidController = new PID(proportionalCoefficient, 0, 0.0, timer.get(), target, 0);
    }

    // new code starts here:
    public void periodic() {
        double roll = ahrs.getRoll();
        SmartDashboard.putNumber("IMU_Roll", ahrs.getRoll());
        double pidOutput = pidController.compute(roll, timer.get());
        SmartDashboard.putNumber("Raw PID Output", pidOutput);
        
        if (Math.abs(pidOutput) < sensitivityThreshold) {
            pidOutput = 0.0;
        }
        // Clamp pidOutput to be between -maxOutput and maxOutput
        pidOutput = Math.max(pidOutput, -maxOutput);
        pidOutput = Math.min(pidOutput, maxOutput);

        if (motorsEnabled) {
            if (pidOutput == 0.0) {
                Subsystems.driveBase.stop();
            } else {
                Subsystems.driveBase.arcadeDrive(pidOutput, 0.0);
            }
        }

        SmartDashboard.putNumber("Motor Signal", pidOutput);
    }

    @Override
    public boolean isDone() {
        if (teleop && controls != null && !controls.doAutoBalance()) {
            return true;
        }
        return false;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
