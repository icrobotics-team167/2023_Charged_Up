package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class NaiveAutoBalance extends Action {

    // private double speedRange;
    private PeriodicTimer timer;
    private AHRS navx;
    private boolean teleop;
    private ControlScheme controls;

    // The minimum angle value where if the angle's absolute value is below this, 0
    // is passed into the PID controller
    private final double SENSITIVITY_THRESHOLD = 3;
    // The max output that can be sent to the motors. The proportional value also
    // scales off this.
    // private final double MAX_OUTPUT = 0.4;
    // Time to wait between every time the robot moves in seconds
    private final double WAIT_TIME = 0.5;
    private boolean stop = false;

    /**
     * Constructs a new AutoBalance auto routine.
     */
    public NaiveAutoBalance() {
        this(false, null);
    }

    /**
     * Constructs a new AutoBalance routine.
     * 
     * @param teleop   Whether or not it's being called in teleop mode (true) or
     *                 autonomous mode (false)
     * @param controls If in teleop, takes the ControlScheme of the primary
     *                 controller for checking whether to stop or not.
     */
    public NaiveAutoBalance(boolean teleop, ControlScheme controls) {
        super();

        // Initialize the navX
        try {
            navx = new AHRS(SPI.Port.kMXP);
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
    }

    /**
     * Runs every robot tick.
     * Calculates a PID from the navX pitch values, then uses that pid to try and
     * balance the robot.
     */
    public void periodic() {
        if (stop) {
            if(timer.hasElapsed(1))
            {
                stop=false;
                timer.reset();
            } else {
                Subsystems.driveBase.stop();
            }
            
        } else {
            if(timer.hasElapsed(.25))
            {
                stop=true;
                timer.reset();
            } else {
                // double pitch = navx.getPitch();
                // if (Math.abs(pitch) < SENSITIVITY_THRESHOLD) {
                //     pitch = 0.0;
                // }
                Subsystems.driveBase.arcadeDrive(0.1, 0);
            }
        }
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

}
