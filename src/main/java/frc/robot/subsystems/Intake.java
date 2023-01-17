package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.Config;

public class Intake {

    private static Intake instance;
    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }
        return instance;
    }

    public static enum Mode {
        OFF,
        FORWARD,
        REVERSE
    }

    private Solenoid in_out;
    private CANSparkMax intake_motor;
    private CANSparkMax omni_motor;
    private Mode mode;
    private boolean extended;

    private Intake() {
        in_out = new Solenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : 2,
            PneumaticsModuleType.CTREPCM,
            Config.Ports.Intake.IN_OUT
        );
        intake_motor = new CANSparkMax(Config.Ports.Intake.INTAKE_MOTOR, MotorType.kBrushless);
        intake_motor.restoreFactoryDefaults();
        intake_motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        intake_motor.setInverted(true);
        intake_motor.setOpenLoopRampRate(0);
        intake_motor.setClosedLoopRampRate(0);
        intake_motor.setSmartCurrentLimit(80);
        intake_motor.setSecondaryCurrentLimit(40);
        mode = Mode.OFF;
        extended = false;

        omni_motor = new CANSparkMax(Config.Ports.Intake.OMNI_MOTOR, MotorType.kBrushless);
        omni_motor.restoreFactoryDefaults();
        omni_motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
        omni_motor.setInverted(false);
        omni_motor.setOpenLoopRampRate(0);
        omni_motor.setClosedLoopRampRate(0);
        omni_motor.setSmartCurrentLimit(80);
        omni_motor.setSecondaryCurrentLimit(40);

    }

    public void run() {
        switch (mode) {
            case OFF:
                intake_motor.set(0);
                omni_motor.set(0);
                break;
            case FORWARD:
                if (extended) {
                    intake_motor.set(.8);
                    omni_motor.set(.6);
                } else {
                    intake_motor.set(0.4);
                }
                break;
            case REVERSE:
                if (extended) {
                    intake_motor.set(-0.6);
                    omni_motor.set(-0.6);
                } else {
                    intake_motor.set(-0.3);
                }
                break;
            default:
                intake_motor.set(0);
                omni_motor.set(0);
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

    public void extend() {
        in_out.set(true);
        extended = true;
    }

    public void retract() {
        in_out.set(false);
        extended = false;
    }

    public void toggleExtension() {
        if (extended) {
            retract();
        } else {
            extend();
        }
    }


}
