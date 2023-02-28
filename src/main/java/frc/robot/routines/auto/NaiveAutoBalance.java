package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
// import frc.robot.subsystems.drive.TankDriveBase;

public class NaiveAutoBalance extends Action {

    // private double speedRange;
    private PeriodicTimer timer;
    private AHRS navx;

    // The minimum angle value where if the angle's absolute value is below this, 0
    // is passed into the PID controller
    private final double SENSITIVITY_THRESHOLD = 3;
    // The max output that can be sent to the motors. The proportional value also
    // scales off this.

    // Potential change: Add these to the constructor?
    // Speed at which the robot moves while driving
    private double speed = 0.5;

    // How long the robot moves each time it moves
    private double driveTime = 0.2;
    // Time to wait between every time the robot moves in seconds
    private double waitTime = 0.75;
    private boolean stop = false;
    boolean done = false;

    /**
     * Constructs a new AutoBalance routine.
     */
    public NaiveAutoBalance() {
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
        if (done) {
            Subsystems.driveBase.stop();
            return;
        }
        double pitch = navx.getPitch();

        if (Math.abs(pitch) < SENSITIVITY_THRESHOLD) {
            Subsystems.driveBase.stop();
            done = true;
            return;
        }

        if (stop) {
            if (timer.hasElapsed(waitTime)) {
                stop = false;
                timer.reset();
            } else {
                Subsystems.driveBase.arcadeDrive(0.05, 0);
            }

        } else {
            if (timer.hasElapsed(driveTime)) {
                stop = true;
                timer.reset();
            } else {
                if (Math.abs(pitch) > SENSITIVITY_THRESHOLD) {
                    Subsystems.driveBase.arcadeDrive(pitch / Math.abs(pitch) * speed, 0);
                }
            }
        }
    }

    /**
     * @return If the routine is done or not.
     */
    @Override
    public boolean isDone() {
        // There's no code for handling how long it should be autobalancing for during
        // auto as we don't expect any other auto routines to come after autoBalance
        return done;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
