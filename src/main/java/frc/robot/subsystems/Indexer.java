package frc.robot.subsystems;

// import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import frc.robot.Config;

public class Indexer {

    private static Indexer instance;
    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }
        return instance;
    }

    public static enum Mode {
        OFF("OFF"),
        INDEX("INDEX"),
        SHOOT("SHOOT");


        public final String name;

        Mode(String name) {
            this.name = name;
        }
    }

    private CANSparkMax liftMotorController;
    private CANSparkMax preShootMotorController;

    private Mode mode;

    private Indexer() {


        liftMotorController = new CANSparkMax(Config.Ports.Indexer.INDEX_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        liftMotorController.restoreFactoryDefaults();
        liftMotorController.setIdleMode(CANSparkMax.IdleMode.kCoast);
        liftMotorController.setInverted(true);
        liftMotorController.setOpenLoopRampRate(0);
        liftMotorController.setClosedLoopRampRate(0);
        liftMotorController.setSmartCurrentLimit(80);
        liftMotorController.setSecondaryCurrentLimit(40);

        preShootMotorController = new CANSparkMax(Config.Ports.Indexer.PRE_SHOOTER, CANSparkMaxLowLevel.MotorType.kBrushless);
        preShootMotorController.restoreFactoryDefaults();
        preShootMotorController.setIdleMode(CANSparkMax.IdleMode.kBrake);
        preShootMotorController.setInverted(true);
        preShootMotorController.setOpenLoopRampRate(0);
        preShootMotorController.setClosedLoopRampRate(0);
        preShootMotorController.setSmartCurrentLimit(80);
        preShootMotorController.setSecondaryCurrentLimit(40);


        mode = Mode.OFF;


    }

    public void run() {
        switch (mode) {
            case OFF:
                preShootMotorController.set(0);
                liftMotorController.set(0);

                break;
            case INDEX:
                preShootMotorController.set(0);
                liftMotorController.set(.8);

                break;
            case SHOOT:
                preShootMotorController.set(1);
                liftMotorController.set(1);

                break;            
            default:
                preShootMotorController.set(0);
                liftMotorController.set(0);
                break;
        }
    }

    public void setMode(Mode mode) {
        if (mode != this.mode) {
            this.mode = mode;
        }
    }

    public Mode getMode() {
        return mode;
    }



}
