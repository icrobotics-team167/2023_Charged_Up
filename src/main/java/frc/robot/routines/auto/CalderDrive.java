package frc.robot.routines.auto;
//takes in 3 parameters
//time out seconds
//speed
//turn= num of degrees robot turn - = left +=right
//use my funny actions to do funny lap around tablepackage frc.robot.routines.auto;

import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;
import frc.robot.routines.Action;

public class CalderDrive extends Action{
    private double timeOutSeconds = 0;
    

    private PeriodicTimer timer;
    private double drect;
    private double throttle;

    

    public CalderDrive(double timeOutSeconds, double drect, double throttle) {
        this.timeOutSeconds = timeOutSeconds;
        this.drect = drect;
        this.throttle = throttle;

        timer = new PeriodicTimer();
    }
    @Override
    public void init() {
        timer.reset();
    }

    @Override
    public void periodic() {
        Subsystems.driveBase.arcadeDrive(throttle, drect);
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(timeOutSeconds);
    }

    @Override
    public void done() {
        Subsystems.driveBase.stop();
    }

}
