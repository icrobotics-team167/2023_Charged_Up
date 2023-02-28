package frc.robot.controls.controllers;

// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class XBController implements Controller {

    private XboxController controller;
    private int port;

    public XBController(int port) {
        controller = new XboxController(port);
        this.port = port;
        System.out.println("XB POV Count: " + controller.getPOVCount());
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isXBController() {
        return true;
    }

    @Override
    public boolean isPSController() {
        return false;
    }

    @Override
    public double getLeftTriggerValue() {
        return controller.getLeftTriggerAxis();
    }

    @Override
    public boolean getLeftTrigger() {
        return controller.getLeftTriggerAxis() >= 0.2;
    }

    private boolean leftTriggerPrevious = false;

    private boolean getLeftTriggerPressed() {
        boolean result = !leftTriggerPrevious && getLeftTrigger();
        leftTriggerPrevious = getLeftTrigger();
        return result;
    }

    private boolean leftTriggerToggle = false;

    @Override
    public boolean getLeftTriggerToggled() {
        if (getLeftTriggerPressed()) {
            leftTriggerToggle = !leftTriggerToggle;
        }
        return leftTriggerToggle;
    }

    @Override
    public boolean getLeftBumper() {
        return controller.getLeftBumper();
    }

    private boolean leftBumperToggle = false;

    @Override
    public boolean getLeftBumperToggled() {
        if (controller.getLeftBumperPressed()) {
            leftBumperToggle = !leftBumperToggle;
        }
        return leftBumperToggle;
    }

    @Override
    public double getRightTriggerValue() {
        return controller.getRightTriggerAxis();
    }

    @Override
    public boolean getRightTrigger() {
        return controller.getRightTriggerAxis() >= 0.2;
    }

    private boolean rightTriggerPrevious = false;

    private boolean getRightTriggerPressed() {
        boolean result = !rightTriggerPrevious && getRightTrigger();
        rightTriggerPrevious = getRightTrigger();
        return result;
    }

    private boolean rightTriggerToggle = false;

    @Override
    public boolean getRightTriggerToggled() {
        if (getRightTriggerPressed()) {
            rightTriggerToggle = !rightTriggerToggle;
        }
        return rightTriggerToggle;
    }

    @Override
    public boolean getRightBumper() {
        return controller.getRightBumper();
    }

    private boolean rightBumperToggle = false;

    @Override
    public boolean getRightBumperToggled() {
        if (controller.getRightBumperPressed()) {
            rightBumperToggle = !rightBumperToggle;
        }
        return rightBumperToggle;
    }

    @Override
    public double getLeftStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(controller.getLeftX(), 2) * MathUtils.getSign(controller.getLeftX());
        }
        return controller.getLeftX();
    }

    @Override
    public double getLeftStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(controller.getLeftY(), 2) * MathUtils.getSign(controller.getLeftY());
        }
        return -controller.getLeftY();
    }

    @Override
    public boolean getLeftStickButton() {
        return controller.getLeftStickButton();
    }

    private boolean leftStickButtonToggle = false;

    @Override
    public boolean getLeftStickButtonToggled() {
        if (controller.getLeftStickButtonPressed()) {
            leftStickButtonToggle = !leftStickButtonToggle;
        }
        return leftStickButtonToggle;
    }

    @Override
    public double getRightStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(controller.getRightX(), 2) * MathUtils.getSign(controller.getRightX());
        }
        return controller.getRightX();
    }

    @Override
    public double getRightStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(controller.getRightY(), 2) * MathUtils.getSign(controller.getRightY());
        }
        return -controller.getRightY();
    }

    @Override
    public boolean getRightStickButton() {
        return controller.getRightStickButton();
    }

    private boolean rightStickButtonToggle = false;

    @Override
    public boolean getRightStickButtonToggled() {
        if (controller.getRightStickButtonPressed()) {
            rightStickButtonToggle = !rightStickButtonToggle;
        }
        return rightStickButtonToggle;
    }

    @Override
    public boolean getAButton() {
        return controller.getAButton();
    }

    @Override
    public boolean getAButtonToggled() {
        return controller.getAButtonPressed();
    }

    @Override
    public boolean getBButton() {
        return controller.getBButton();
    }

    @Override
    public boolean getBButtonToggled() {
        return controller.getBButtonPressed();
    }

    @Override
    public boolean getXButton() {
        return controller.getXButton();
    }

    @Override
    public boolean getXButtonToggled() {
        return controller.getXButtonPressed();
    }

    @Override
    public boolean getYButton() {
        return controller.getYButton();
    }

    @Override
    public boolean getYButtonToggled() {
        return controller.getYButtonPressed();
    }

    @Override
    public boolean getBackButton() {
        return controller.getBackButton();
    }

    @Override
    public boolean getBackButtonToggled() {
        return controller.getBackButtonPressed();
    }

    @Override
    public boolean getStartButton() {
        return controller.getStartButton();
    }

    @Override
    public boolean getStartButtonToggled() {
        return controller.getStartButtonPressed();
    }

    @Override
    public boolean getDPadUp() {
        return controller.getPOV() == 0;
    }

    @Override
    public boolean getDPadRight() {
        return controller.getPOV() == 90;
    }

    @Override
    public boolean getDPadDown() {
        return controller.getPOV() == 180;
    }

    @Override
    public boolean getDPadLeft() {
        return controller.getPOV() == 270;
    }

}
