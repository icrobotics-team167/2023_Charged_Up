package frc.robot.routines.auto;

import frc.robot.subsystems.Subsystems;

//timeout seconds
//speed
//drives timeout second amount of time at given speed
//NO LOOIKE AT DRIVESTRAIT
import frc.robot.util.PeriodicTimer;
import frc.robot.routines.Action;

public class CalderDrive extends Action{
    private double timeOutSeconds = 0;
    

    private PeriodicTimer timer;


    private double speed;
    public CalderDrive(double speed, double timeOutSeconds) {
        this.speed = speed;
        this.timeOutSeconds = timeOutSeconds;
        timer = new PeriodicTimer();
    }
    @Override
    public void init() {
        timer.reset();
        System.out.println("IT WORKEDED");
    }

    @Override
    public void periodic() {
        Subsystems.driveBase.tankDrive(speed, speed);
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
