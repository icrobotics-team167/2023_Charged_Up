package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.controls.controllers.Controller;
import frc.robot.controls.controllers.PSController;
import frc.robot.controls.controllers.XBController;
import frc.robot.controls.controlschemes.ControlScheme;
import frc.robot.controls.controlschemes.DoubleController;
import frc.robot.controls.controlschemes.NullController;
import frc.robot.controls.controlschemes.SingleController;
import frc.robot.routines.Action;
import frc.robot.routines.auto.*;
import frc.robot.routines.Teleop;
import frc.robot.subsystems.Subsystems;

public class Robot extends TimedRobot {

    private SendableChooser<AutoRoutines> autoChooser = new SendableChooser<>();
    private DriverStation driverStation;
    private ControlScheme controls;
    private Action auto;
    private Teleop teleop;

    public Robot() {
        super(Config.Settings.CPU_PERIOD);
    }

    @Override
    public void robotInit() {
        autoChooser.setDefaultOption(AutoRoutines.SCORE_CONE.name, AutoRoutines.SCORE_CONE);
        autoChooser.addOption(AutoRoutines.SCORE_CUBE.name, AutoRoutines.SCORE_CUBE);
        autoChooser.addOption(AutoRoutines.BALANCE.name, AutoRoutines.BALANCE);
        autoChooser.addOption(AutoRoutines.NOTHING.name, AutoRoutines.NOTHING);

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

        Subsystems.setInitialStates();
        // ******************AUTO********************* */
        auto = autoChooser.getSelected().actions;
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
