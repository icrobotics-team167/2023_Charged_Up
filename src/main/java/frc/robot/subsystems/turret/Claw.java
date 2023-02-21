package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Config;

/**
 * Opens and closes the claw.
 */
public class Claw {

    private Solenoid openClaw;
    private Solenoid closeClaw;

    public static Claw instance;

    /**
     * Allows only one instance of Claw to exist at once.
     * 
     * @return An instance of Claw. Creates a new one if it doesn't exist already.
     */
    public static Claw getInstance() {
        if (instance == null) {
            instance = new Claw();
        }
        return instance;
    }

    /**
     * Sets up the pneumatic channels for the claw. 
     * Since two pneumatic channels are used, one is for opening the claw and one is for closing the claw.
     */
    private Claw() {
        openClaw = new Solenoid(Config.Ports.SparkTank.PH, PneumaticsModuleType.REVPH, Config.Ports.Arm.OPEN_CLAW);
        closeClaw = new Solenoid(Config.Ports.SparkTank.PH, PneumaticsModuleType.REVPH, Config.Ports.Arm.CLOSE_CLAW);
    }

    /**
     * Opens the claw
     */
    public void openClaw() {
        openClaw.set(true);
        closeClaw.set(false);
    }

    /**
     * Closes the claw
     */
    public void closeClaw() {
        openClaw.set(false);
        closeClaw.set(true);
    }
}
