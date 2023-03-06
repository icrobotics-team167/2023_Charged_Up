package frc.robot.routines.auto;

import com.kauailabs.navx.frc.AHRS;

import frc.robot.routines.Action;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.MathUtils;
import frc.robot.util.MovingAverage;
import frc.robot.util.PeriodicTimer;

public class DumbAutoBalance extends Action {
    private AHRS navx = Subsystems.navx;
    private PeriodicTimer timer;
    private MovingAverage pitchFilter;

    private static final double SPEED = 0.5;
    private static final double SENSITIVITY_THRESHOLD = 3;

    /**
     * Constructs a new DumbAutobalance auto routine.
     */
    public DumbAutoBalance() {
        super();
        timer = new PeriodicTimer();
        pitchFilter = new MovingAverage(25, true);
    }

    /**
     * Initializes the routine.
     */
    @Override
    public void init() {
        timer.reset();
        pitchFilter.clear();
    }

    /**
     * Runs every robot tick.
     * Smooths out the pitch value and if that smoothed pitch value is less than the
     * sensitivity threshold, stops the robot as it is balanced. Otherwise, uses the
     * sign of the pitch value to move the robot to try and balance.
     */
    @Override
    public void periodic() {
        pitchFilter.add(navx.getPitch());
        double pitch = pitchFilter.get();
        if (Math.abs(pitch) <= SENSITIVITY_THRESHOLD) {
            pitch = 0;
        }
        Subsystems.driveBase.arcadeDrive(MathUtils.getSign(pitch) * SPEED, 0);
    }

    /**
     * Returns if the routine is done or not. Always returns false as it is expected that
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
