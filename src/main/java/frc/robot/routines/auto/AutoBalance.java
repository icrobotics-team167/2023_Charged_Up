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

    /**
     * Constructs a new AutoBalance auto routine.
     */
    public AutoBalance() {
        this(false, null);
    }

    /**
     * Constructs a new AutoBalance auto routine.
     * 
     * @param teleop   Whether or not it's being called in teleop mode (true) or
     *                 autonomous mode (false)
     * @param controls If in teleop, takes the ControlScheme of the primary
     *                 controller for checking whether to stop or not.
     */
    public AutoBalance(boolean teleop, ControlScheme controls) {
        super();

        // Initialize the navX
        try {
            ahrs = new AHRS(SPI.Port.kMXP);
            // DriverStation.reportError("Not really an error, successfully loaded navX",
            // true);
        } catch (RuntimeException ex) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }

        // Set variables
        this.teleop = teleop;
        this.controls = controls;

        // Initialize timer
        timer = new PeriodicTimer();
    }

    /**
     * Initializes the instance.
     */
    @Override
    public void init() {
        Subsystems.driveBase.setBrake();
        timer.reset();
        motorsEnabled = true;
        // Minimum value before pidOutput takes effect, if it is below
        // sensitivityThreshold pidOutput is set to 0
        sensitivityThreshold = 3;
        // Max pitch value we saw was +-12, scaling proportional coefficient to that
        maxOutput = 0.4;
        // * -1 is there to flip the output values around (positive pitch means we want
        // to drive backwards)
        double proportionalCoefficient = maxOutput / 12.0 * -1;
        // Target pitch value.
        double target = 0.0;
        pidController = new PID(proportionalCoefficient, 0, 0.01, timer.get(), target, 0);
        // Known working values:
        // Max Output: 0.4
        // Sensitivity threshold: 3
        // P: 0.4 / 12 * -1
        // I: 0
        // D: 0.01
    }

    /**
     * Runs every robot tick.
     * Calculates a PID from the navX pitch values, then uses that pid to try and
     * balance the robot.
     */
    public void periodic() {
        double pitch = ahrs.getPitch();
        if (Math.abs(pitch) < sensitivityThreshold) {
            pitch = 0.0;
        }
        // SmartDashboard.putNumber("IMU_Pitch", ahrs.getPitch());
        double pidOutput = pidController.compute(pitch, timer.get());
        // SmartDashboard.putNumber("Raw PID Output", pidOutput);

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

        // SmartDashboard.putNumber("Motor Signal", pidOutput);
    }

    /**
     * @return If the routine is done or not.
     */
    @Override
    public boolean isDone() {
        if (teleop && // If it's in teleop mode and
                controls != null && // There is a primary controller attached and
                !controls.doAutoBalance() // The button for running the autoBalance code has been released
        ) {
            return true; // Return true
        }
        // There's no code for handling how long it should be autobalancing forduring
        // auto as we don't expect any other auto routines to come after autoBalance
        return false;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
