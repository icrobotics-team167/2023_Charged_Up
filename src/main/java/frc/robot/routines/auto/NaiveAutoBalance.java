package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.MathUtils;
import frc.robot.util.PeriodicTimer;
// import frc.robot.subsystems.drive.TankDriveBase;

public class NaiveAutoBalance extends Action {

    // private double speedRange;
    private PeriodicTimer timer;
    private AHRS navx = Subsystems.navx;

    // The minimum angle value where if the angle's absolute value is below this, 0
    // is passed into the PID controller
    private final double SENSITIVITY_THRESHOLD = 4;
    // The max output that can be sent to the motors. The proportional value also
    // scales off this.

    // Potential change: Add these to the constructor?
    // Speed at which the robot moves while driving
    private double speed = 0.25;

    // How long the robot moves each time it moves
    private double driveTime = 0.2;
    // Time to wait between every time the robot moves in seconds
    private double waitTime = 1;
    private boolean stop = false;

    /**
     * Constructs a new AutoBalance routine.
     */
    public NaiveAutoBalance() {
        super();

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
        double pitch = navx.getPitch();

        if (Math.abs(pitch) < SENSITIVITY_THRESHOLD) {
            timer.reset();
            Subsystems.driveBase.stop();
            return;
        }

        if (stop) {
            if (timer.hasElapsed(waitTime)) {
                stop = false;
                timer.reset();
            } else {
                Subsystems.driveBase.arcadeDrive((pitch/8) * 0.075, 0);
            }

        } else {
            if (timer.hasElapsed(driveTime)) {
                stop = true;
                timer.reset();
            } else {
                if (Math.abs(pitch) > SENSITIVITY_THRESHOLD) {
                    // Subsystems.driveBase.arcadeDrive(MathUtils.getSign(pitch) * speed, 0);
                    Subsystems.driveBase.arcadeDrive((pitch/8) * 0.075, 0);
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
        return false;
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
