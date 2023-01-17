package frc.robot.subsystems;

import frc.robot.Config;
import frc.robot.subsystems.drive.SparkTankDriveBase;
import frc.robot.subsystems.drive.TankDriveBase;

public class Subsystems {

    public static final TankDriveBase driveBase;
    public static final Intake intake;
    public static final Indexer indexer;
    public static final Shooter shooter;
    public static final Climber climber;

    static {
        if (Config.Settings.SPARK_TANK_ENABLED) {
            driveBase = SparkTankDriveBase.getInstance();
        } else {
        }

        intake = Intake.getInstance();

        indexer = Indexer.getInstance();

        shooter = Shooter.getInstance();

        climber = Climber.getInstance();
    }

    public static void setInitialStates() {
        driveBase.resetEncoders();
        driveBase.setHighGear();
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        shooter.stop();
    }

    public static void setClimberStates() {
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        indexer.setMode(Indexer.Mode.OFF);
        shooter.stop();
    }

}
