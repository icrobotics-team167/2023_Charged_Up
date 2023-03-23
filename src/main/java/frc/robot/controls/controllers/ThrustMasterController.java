package frc.robot.controls.controllers;

import edu.wpi.first.wpilibj.Joystick;

//TODO: Implement deadzones for these controllers
public class ThrustMasterController implements Controller {
    Joystick joystick;
    private int port;

    public ThrustMasterController(int port) {
        joystick = new Joystick(port);
        this.port = port;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public ControllerType getControllerType() {
        return ControllerType.JOYSTICK;
    }

    @Override
    public double getLeftTriggerValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getLeftTrigger() {
        return joystick.getTrigger();
    }

    @Override
    public boolean getLeftTriggerPressed() {
        return joystick.getTriggerPressed();
    }

    @Override
    public boolean getLeftBumper() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getLeftBumperPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getRightTriggerValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getRightTrigger() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getRightTriggerPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getRightBumper() {
        return false;
    }

    @Override
    public boolean getRightBumperToggled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getLeftStickX() {
        return -joystick.getX();
    }

    @Override
    public double getLeftStickY() {
        return -joystick.getY();
    }

    @Override
    public double getLeftStickZ() {
        return joystick.getZ();
    }

    @Override
    public boolean getLeftStickButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getLeftStickButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double getRightStickX() {
        throw new UnsupportedOperationException("no right stick on a joystick :-)");
    }

    @Override
    public double getRightStickY() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean getRightStickButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getRightStickButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getAButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getBButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getBButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getXButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getXButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getYButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getYButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getBackButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getBackButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getStartButton() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getStartButtonPressed() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getDPadUp() {
        return joystick.getPOV() == 0;
    }

    @Override
    public boolean getDPadRight() {
        return joystick.getPOV() == 90;
    }

    @Override
    public boolean getDPadDown() {
        return joystick.getPOV() == 180;
    }

    @Override
    public boolean getDPadLeft() {
        return joystick.getPOV() == 270;
    }

    @Override
    public boolean getButtonById(int buttonId) {
        return joystick.getRawButton(buttonId);
    }

    @Override
    public boolean getButtonPressedById(int buttonId) {
        return joystick.getRawButtonPressed(buttonId);
    }
    
}
