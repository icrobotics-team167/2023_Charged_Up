package frc.robot.routines.auto;

import frc.robot.Config;
import frc.robot.routines.Action;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Subsystems;
import frc.robot.util.PeriodicTimer;

public class RunShooter extends Action {

    private PeriodicTimer timer;
    private double seconds;
    private double delay;

    public RunShooter(double seconds) {
        this(seconds, 1);
    }

    public RunShooter(double seconds, double delay) {
        super();
        timer = new PeriodicTimer();
        this.seconds = seconds;
        this.delay = delay;
    }

    @Override
    public void init() {
        Subsystems.shooter.setTargetRPM(Config.Settings.SHOOTING_RPM);
        // Subsystems.shooter.start();
        Subsystems.intake.retract();
        Subsystems.intake.setMode(Intake.Mode.OFF);
        Subsystems.indexer.setMode(Indexer.Mode.INDEX);
        timer.reset();
    }

    @Override
    public void periodic() {
        // if (!timer.hasElapsed(delay)) {
        //     Subsystems.indexer.setMode(Indexer.Mode.OFF);
        // } else {
        Subsystems.intake.retract();
        Subsystems.intake.setMode(Intake.Mode.FORWARD);
        Subsystems.indexer.setMode(Indexer.Mode.SHOOT);
        // }
    }

    @Override
    public boolean isDone() {
        return timer.hasElapsed(seconds + delay);
    }

    @Override
    public void done() {
        Subsystems.intake.extend();
        Subsystems.intake.setMode(Intake.Mode.FORWARD);
        Subsystems.indexer.setMode(Indexer.Mode.INDEX);
    }

}
