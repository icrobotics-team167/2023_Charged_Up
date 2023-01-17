package frc.robot.routines;

// import frc.robot.Config;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.subsystems.*;
import frc.robot.subsystems.drive.TankDriveBase;

public class Teleop {

    private ControlScheme controls;
    private TankDriveBase driveBase;
    private Intake intake;
    private Indexer indexer;
    private Shooter shooter;
    private Climber climber;
    // private boolean climbMode;

    public Teleop(ControlScheme controls) {
        this.controls = controls;
        driveBase = Subsystems.driveBase;
        intake = Subsystems.intake;
        indexer = Subsystems.indexer;
        shooter = Subsystems.shooter;
        climber = Subsystems.climber;
    }

    public void init() {
        driveBase.setHighGear();
        driveBase.setCoast();
        driveBase.resetEncoders();
        intake.setMode(Intake.Mode.OFF);
        intake.retract();
        // indexer.setMode(Indexer.Mode.OFF);
        shooter.start();
        climber.retract();
        // climbMode = false;
    }

    public void periodic() {

        driveBase.setCoast();
        // //Classic Tank Drive Controls
        // if(controls.doFlipityFlop()) {
        //     driveBase.tankDrive(controls.getTankLeftSpeed(), controls.getTankRightSpeed());
        // }else{
        //     driveBase.tankDrive(controls.getTankRightSpeed()*-1, controls.getTankLeftSpeed()*-1);
        // }
        
        //Arcade Tank Drive Controls
        if(controls.doFlipityFlop()) {
            driveBase.arcadeDrive(controls.getTankThrottle(), controls.getTankWheel());
        }else{
            driveBase.arcadeDrive(controls.getTankThrottle()*-1, controls.getTankWheel()*-1);
        }

        if (controls.doSwitchLowGear()) {
            driveBase.setLowGear();
        } else{
            driveBase.setHighGear();
        }

        // Shooter
        if (controls.doToggleShooter()) {
            shooter.toggle();
        }

        if (controls.doRetractIntake()){
            intake.retract();
        }

        if (controls.doExtendIntake()){
            intake.extend();
        }

        if (controls.doRunIntake()){
            intake.extend();
            intake.setMode(Intake.Mode.FORWARD);
            indexer.setMode(Indexer.Mode.INDEX);
        } else if (controls.doRunIntakeRev()){
            intake.extend();
            intake.setMode(Intake.Mode.REVERSE);
        } else{
            intake.setMode(Intake.Mode.OFF);
            indexer.setMode(Indexer.Mode.OFF);
        }

        if(controls.doRunPreShooter()){
            intake.retract();
            intake.setMode(Intake.Mode.FORWARD);
            indexer.setMode(Indexer.Mode.SHOOT);
        }

        climber.climb(controls.getElevatorSpeed());

        if(controls.doArmExtend()){
            climber.extend();
        } else if(controls.doArmRetract()){
            climber.retract();
        }


        // Run
        intake.run();
        indexer.run();
        shooter.run();

    }

}
