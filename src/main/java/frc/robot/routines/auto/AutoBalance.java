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
    private boolean teleop;
    private ControlScheme controls;

    // Whether or not to actually drive when running code (DEBUG ONLY)
    private final boolean MOTORS_ENABLED = true;
    // The minimum angle value where if the angle's absolute value is below this, 0
    // is passed into the PID controller
    private final double SENSITIVITY_THRESHOLD = 3;
    // The max output that can be sent to the motors. The proportional value also
    // scales off this.
    private final double MAX_OUTPUT = 0.4;

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
        // Max pitch value we saw was +-12, scaling proportional coefficient to that
        // * -1 is there to flip the output values around (positive pitch means we want
        // to drive backwards)
        double proportionalCoefficient = MAX_OUTPUT / 12.0 * -1;
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
        if (Math.abs(pitch) < SENSITIVITY_THRESHOLD) {
            pitch = 0.0;
        }
        double pidOutput = pidController.compute(pitch, timer.get());

        // Clamp pidOutput to be between -maxOutput and maxOutput
        pidOutput = Math.max(pidOutput, -MAX_OUTPUT);
        pidOutput = Math.min(pidOutput, MAX_OUTPUT);

        // Ignore dead code warnings here
        if (MOTORS_ENABLED && pidOutput != 0) {
            Subsystems.driveBase.arcadeDrive(pidOutput, 0);
        } else {
            Subsystems.driveBase.stop();
        }

        SmartDashboard.putNumber("AutoBalance.pidOutput", pidOutput);
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
        // There's no code for handling how long it should be autobalancing for during
        // auto as we don't expect any other auto routines to come after autoBalance
        return false;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

    @Override
    public void onEnable() {
        // TODO Auto-generated method stub
        
    }

}
