package frc.robot;

import edu.wpi.first.cscore.UsbCamera;

import java.time.Duration;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;
import frc.robot.controls.controllers.XBController;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.controls.controlschemes.DoubleController;
import frc.robot.controls.controlschemes.NullController;
import frc.robot.controls.controlschemes.SingleController;
import frc.robot.routines.Action;
import frc.robot.routines.Routine;
import frc.robot.routines.Teleop;
import frc.robot.routines.auto.AutoBalance;
import frc.robot.routines.auto.AutoRoutine;
import frc.robot.routines.auto.DriveForwardsUntil;
import frc.robot.routines.auto.SmartDriveStraight;
import frc.robot.routines.auto.DriveStraight;
import frc.robot.routines.auto.Wait;
import frc.robot.subsystems.Subsystems;

public class Robot extends TimedRobot {

    private SendableChooser<AutoRoutine> autoChooser = new SendableChooser<>();
    private DriverStation driverStation;
    private ControlScheme controls;
    private Action auto;
    private Teleop teleop;

    public Robot() {
        super(Config.Settings.CPU_PERIOD);
    }

    @Override
    public void robotInit() {
        autoChooser.setDefaultOption(AutoRoutine.NULL.name, AutoRoutine.NULL);
        autoChooser.addOption(AutoRoutine.ENEMY_TENCH_RUN.name, AutoRoutine.ENEMY_TENCH_RUN);
        autoChooser.addOption(AutoRoutine.SHOOT_3.name, AutoRoutine.SHOOT_3);
        SmartDashboard.putData("Autonomous Routines", autoChooser);

        // driverStation = DriverStation.getInstance();

        Controller primaryController = null;
        switch (Config.Settings.PRIMARY_CONTROLLER_TYPE) {
            case XB:
                primaryController = new XBController(Config.Ports.PRIMARY_CONTROLLER);
                break;
            case PS:
                primaryController = new PSController(Config.Ports.PRIMARY_CONTROLLER);
                break;
            case NONE:
                primaryController = null;
                break;
        }
        Controller secondaryController = null;
        switch (Config.Settings.SECONDARY_CONTROLLER_TYPE) {
            case XB:
                secondaryController = new XBController(Config.Ports.SECONDARY_CONTROLLER);
                break;
            case PS:
                secondaryController = new PSController(Config.Ports.SECONDARY_CONTROLLER);
                break;
            case NONE:
                secondaryController = null;
                break;
        }
        if (primaryController == null && secondaryController == null) {
            controls = new NullController();
        } else if (primaryController != null && secondaryController == null) {
            controls = new SingleController(primaryController);
        } else if (primaryController != null && secondaryController != null) {
            controls = new DoubleController(primaryController, secondaryController);
        } else {
            // Fallback
            // This should be unreachable in normal conditions
            // This could only occur if the secondary controller is configured but the
            // primary controller isn't
            controls = new NullController();
        }

        new Thread(() -> {
            UsbCamera camera = CameraServer.startAutomaticCapture();
            // camera.setFPS(30);
            // camera.setRessssa1olution(320, 240);
        }).start();

        Subsystems.setInitialStates();
        // ******************AUTO********************* */
        auto = new Routine(new Action[] {

                // // Drive forwards until we sense ourselves starting up the ramp
                // new DriveForwardsUntil(
                //         ahrs -> Math.abs(ahrs.getPitch()) >= 5, // condition
                //         0.2, // speed
                //         Duration.ofMillis(5_000) // duration
                // ),
                // new AutoBalance(),
                new DriveStraight(40.0, 0.4, 10),
        });
        teleop = new Teleop(controls);
    }

    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("drive/leftEncoderPosition", Subsystems.driveBase.getLeftEncoderPosition());
        SmartDashboard.putNumber("drive/rightEncoderPosition", Subsystems.driveBase.getRightEncoderPosition());
        SmartDashboard.putNumber("drive/gyroAngle", Subsystems.driveBase.getAngle());
    }

    @Override
    public void autonomousInit() {
        Subsystems.driveBase.resetEncoders();
        Subsystems.driveBase.setHighGear();
        auto.exec();
        System.out.println("Auto selected: " + autoChooser.getSelected().name);
    }

    @Override
    public void autonomousPeriodic() {
        auto.exec();
    }

    @Override
    public void teleopInit() {
        teleop.init();
    }

    @Override
    public void teleopPeriodic() {
        teleop.periodic();
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

}
