package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.MathUtils;
import frc.robot.util.MovingAverage;

public class AutoBalance extends Action {
    private AHRS navx = Subsystems.navx;

    private static final double SPEED = 0.1;
    private static final double SENSITIVITY_THRESHOLD = 5;

    private int lastPitchSign;
    private double dampeningFactor;
    private double balancedTickCount;

    /**
     * Constructs a new DumbAutobalance auto routine.
     */
    public AutoBalance() {
        super();
    }

    /**
     * Initializes the routine.
     */
    @Override
    public void init() {
        dampeningFactor = 1;
        lastPitchSign = MathUtils.getSign(navx.getPitch());
        balancedTickCount = 0;
    }

    /**
     * Runs every robot tick.
     * Smooths out the pitch value and if the absolute value of that smoothed pitch
     * value is less than the sensitivity threshold, stops the robot as it is
     * balanced. Otherwise, uses the sign of the pitch value to move the robot to
     * try and balance.
     */
    @Override
    public void periodic() {
        SmartDashboard.putNumber("DumbAutoBalance.dampeningFactor", dampeningFactor);
        double pitch = navx.getPitch(); // Get the NavX pitch
        if (MathUtils.getSign(pitch) * -1 == lastPitchSign) { //If the pitch tilts past balanced
            dampeningFactor += 0.4; // Lower speed to adjust more carefully
        }
        if (Math.abs(pitch) <= SENSITIVITY_THRESHOLD) { // If we're not balanced
            // If we're balanced for more than 2 seconds, lower the dampening factor in
            // case we get knocked off balance
            if ((double) balancedTickCount / 50.0 >= 2 && dampeningFactor >= 1) {
                dampeningFactor -= 0.01;
            }
            balancedTickCount++;

            pitch = 0;
        } else {
            balancedTickCount = 0;
        }
        Subsystems.driveBase.arcadeDrive(pitch / 9 * (SPEED / (dampeningFactor)), 0); // Move according to the pitch
        lastPitchSign = MathUtils.getSign(navx.getPitch());
    }

    /**
     * Returns if the routine is done or not. Always returns false as it is expected
     * that
     * the routine is the last one in the list of actions and thus will run until
     * the end of the auto period.
     * 
     * @return If the routine is done or not. Always false.
     */
    @Override
    public boolean isDone() {
        return false;
    }

    /**
     * Runs when the routine is done. Is not implemented because isDone() always
     * returns false.
     */
    @Override
    public void done() {
    }
}
