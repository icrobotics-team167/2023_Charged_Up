package frc.robot.controls.controllers;

// import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;
import frc.robot.util.MathUtils;

public class XBController implements Controller {

    private XboxController controller;
    private int port;

    /**
     * Constructs a new Xbox controller.
     * 
     * @param port The port that the controller is connected to. Configure in the
     *             Driver Station.
     */
    public XBController(int port) {
        controller = new XboxController(port);
        this.port = port;
        System.out.println("XB POV Count: " + controller.getPOVCount());
    }

    /**
     * Gets the port of the controller.
     * 
     * @return The port.
     */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * Gets the type of the controller.
     * 
     * @return The controller type.
     */
    @Override
    public ControllerType getControllerType() {
        return ControllerType.XB;
    }

    /**
     * Gets the analog value of the Left Trigger. (LT)
     * 
     * @return The value. From 0 (Not pressed) to 1. (All the way pressed)
     */
    @Override
    public double getLeftTriggerValue() {
        return controller.getLeftTriggerAxis();
    }

    /**
     * Gets if the Left Trigger (LT) is pressed.
     * 
     * @return If the trigger is pressed.
     */
    @Override
    public boolean getLeftTrigger() {
        return controller.getLeftTriggerAxis() >= Config.Tolerances.XB_TRIGGER_PRESSED_THRESHOLD;
    }

    private boolean leftTriggerPrevious = false;

    /**
     * Gets if the Left Trigger (LT) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the trigger was pressed down.
     */
    @Override
    public boolean getLeftTriggerPressed() {
        boolean currentState = getLeftTrigger();
        boolean output = currentState && !leftTriggerPrevious;
        leftTriggerPrevious = currentState;
        return output;
    }

    /**
     * Gets if the Left Bumper (LB) is pressed.
     * 
     * @return If the bumper is pressed.
     */
    @Override
    public boolean getLeftBumper() {
        return controller.getLeftBumper();
    }

    /**
     * Gets if the Left Bumper (LB) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the bumper was pressed down.
     */
    @Override
    public boolean getLeftBumperPressed() {
        return controller.getLeftBumperPressed();
    }

    /**
     * Gets the analog value of the Right Trigger. (RT)
     * 
     * @return The value. From 0 (Not pressed) to 1. (All the way pressed)
     */
    @Override
    public double getRightTriggerValue() {
        return controller.getRightTriggerAxis();
    }

    /**
     * Gets if the Right Trigger (RT) is pressed.
     * 
     * @return If the trigger is pressed.
     */
    @Override
    public boolean getRightTrigger() {
        return controller.getRightTriggerAxis() >= Config.Tolerances.XB_TRIGGER_PRESSED_THRESHOLD;
    }

    private boolean rightTriggerPrevious = false;

    /**
     * Gets if the Right Trigger (RT) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the trigger was pressed down.
     */
    @Override
    public boolean getRightTriggerPressed() {
        boolean currentState = getRightTrigger();
        boolean output = currentState && !rightTriggerPrevious;
        rightTriggerPrevious = currentState;
        return output;
    }

    /**
     * Gets if the Right Bumper (RB) is pressed.
     * 
     * @return If the bumper is pressed.
     */
    @Override
    public boolean getRightBumper() {
        return controller.getRightBumper();
    }

    /**
     * Gets if the Right Bumper (RB) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the bumper was pressed down.
     */
    @Override
    public boolean getRightBumperToggled() {
        return controller.getRightBumperPressed();
    }

    /**
     * Gets the horizontal axis of the Left Joystick.
     * 
     * @return The horizontal position. From -1 (All the way left) to 1. (All the
     *         way right)
     */
    @Override
    public double getLeftStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(Math.abs(controller.getLeftX()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getLeftX());
        }
        return controller.getLeftX();
    }

    /**
     * Gets the vertical axis of the Left Joystick.
     * 
     * @return The vertical position. From -1 (All the way down) to 1. (All the way
     *         up)
     */
    @Override
    public double getLeftStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(Math.abs(controller.getLeftY()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getLeftY());
        }
        return -controller.getLeftY();
    }

    @Override
    public double getLeftStickZ() {
        return 0;
    }

    /**
     * Gets if the Left Stick Button (LSB) is pressed.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getLeftStickButton() {
        return controller.getLeftStickButton();
    }

    /**
     * Gets if the Left Stick Button (LSB) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getLeftStickButtonPressed() {
        return controller.getLeftStickButtonPressed();
    }

    /**
     * Gets the horizontal axis of the Right Stick.
     * 
     * @return The horizontal position. From -1 (All the way left) to 1. (All the
     *         way right)
     */
    @Override
    public double getRightStickX() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return Math.pow(Math.abs(controller.getRightX()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getRightX());
        }
        return controller.getRightX();
    }

    /**
     * Gets the vertical axis of the Right Stick.
     * 
     * @return The vertical position. From -1 (All the way down) to 1. (All the way
     *         up)
     */
    @Override
    public double getRightStickY() {
        if (Config.Settings.EXPONENTIAL_JOYSTICKS) {
            return -Math.pow(Math.abs(controller.getRightY()), Config.Settings.JOYSTICKS_EXPONENT)
                    * MathUtils.getSign(controller.getRightY());
        }
        return -controller.getRightY();
    }

    /**
     * Gets if the Right Stick Button (RSB) is pressed.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getRightStickButton() {
        return controller.getRightStickButton();
    }

    /**
     * Gets if the Right Stick Button (RSB) was pressed down. Useful for rising-type
     * toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getRightStickButtonPressed() {
        return controller.getRightStickButtonPressed();
    }

    /**
     * Gets if the A button is pressed. Corresponds to the Cross button on
     * Playstation controllers or the B button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getAButton() {
        return controller.getAButton();
    }

    /**
     * Gets if the A button was pressed down. Corresponds to the Cross button on
     * Playstation controllers or the B button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getAButtonPressed() {
        return controller.getAButtonPressed();
    }

    /**
     * Gets if the B button is pressed. Corresponds to the Circle button on
     * Playstation controllers or the A button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getBButton() {
        return controller.getBButton();
    }

    /**
     * Gets if the B button was pressed down. Corresponds to the Circle button on
     * Playstation controllers or the A button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getBButtonPressed() {
        return controller.getBButtonPressed();
    }

    /**
     * Gets if the X button is pressed. Corresponds to the Square button on
     * Playstation controllers or the Y button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getXButton() {
        return controller.getXButton();
    }

    /**
     * Gets if the X button was pressed down. Corresponds to the Square button on
     * Playstation controllers or the Y button on Nintendo controllers.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getXButtonPressed() {
        return controller.getXButtonPressed();
    }

    /**
     * Gets if the Y button is pressed. Corresponds to the Triangle button on
     * Playstation controllers or the X button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getYButton() {
        return controller.getYButton();
    }

    /**
     * Gets if the Y button was pressed down. Corresponds to the Triangle button on
     * Playstation controllers or the X button on Nintendo controllers. Useful for
     * rising-type toggles.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getYButtonPressed() {
        return controller.getYButtonPressed();
    }

    /**
     * Gets if the Back button is pressed. Corresponds to the Share button on
     * Playstation controllers or the Minus button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getBackButton() {
        return controller.getBackButton();
    }

    /**
     * Gets if the Back button was pressed down. Corresponds to the Share button on
     * Playstation controllers or the Minus button on Nintendo controllers. Useful
     * for rising-type toggles.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getBackButtonPressed() {
        return controller.getBackButtonPressed();
    }

    /**
     * Gets if the Start button is pressed. Corresponds to the Options button on
     * Playstation controllers or the Plus button on Nintendo controllers.
     * 
     * @return If the button is pressed.
     */
    @Override
    public boolean getStartButton() {
        return controller.getStartButton();
    }

    /**
     * Gets if the Start button was pressed down. Corresponds to the Options button
     * on Playstation controllers or the Plus button on Nintendo controllers.
     * 
     * @return If the button was pressed down.
     */
    @Override
    public boolean getStartButtonPressed() {
        return controller.getStartButtonPressed();
    }

    /**
     * Gets if D-Pad Up is pressed.
     * 
     * @return If D-Pad Up is pressed.
     */
    @Override
    public boolean getDPadUp() {
        return controller.getPOV() == 0;
    }

    /**
     * Gets if D-Pad Right is pressed.
     * 
     * @return If D-Pad Right is pressed.
     */
    @Override
    public boolean getDPadRight() {
        return controller.getPOV() == 90;
    }

    /**
     * Gets if D-Pad Down is pressed.
     * 
     * @return If D-Pad Down is pressed.
     */
    @Override
    public boolean getDPadDown() {
        return controller.getPOV() == 180;
    }

    /**
     * Gets if D-Pad Left is pressed.
     * 
     * @return If D-Pad Left is pressed.
     */
    @Override
    public boolean getDPadLeft() {
        return controller.getPOV() == 270;
    }

    @Override
    public boolean getButtonPressedById(int buttonId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean getRawButtonPressedById(int buttonId) {
        // TODO Auto-generated method stub
        return false;
    }

}
