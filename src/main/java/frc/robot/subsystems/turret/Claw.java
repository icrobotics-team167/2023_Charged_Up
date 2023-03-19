package frc.robot.subsystems.turret;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Config;

public class Claw {
    private CANSparkMax intakeMotor;

    private static final double INTAKE_SPEED = 0.4;
    private static final double OUTTAKE_SPEED = 0.2;

    private boolean overCurrent;
    private static final double CURRENT_LIMIT = 9;

    public static Claw instance;
    public static Claw getInstance() {
        if (instance == null) {
            instance = new Claw();
        }
        return instance;
    }

    private Claw() {
        intakeMotor = new CANSparkMax(Config.Ports.Arm.CLAW, CANSparkMaxLowLevel.MotorType.kBrushless);

        intakeMotor.restoreFactoryDefaults();
        intakeMotor.setInverted(false);
        intakeMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        intakeMotor.setSmartCurrentLimit(40);
        intakeMotor.setSecondaryCurrentLimit(60);

        overCurrent = false;
    }

    public void intake() {
        if (overCurrent) {
            intakeMotor.stopMotor();
            return;
        }
        // SmartDashboard.putNumber("Claw.Voltage", intakeMotor.getBusVoltage());
        overCurrent = intakeMotor.getBusVoltage() < CURRENT_LIMIT;
        intakeMotor.set(INTAKE_SPEED);
    }

    public void outtake() {
        overCurrent = false;
        // SmartDashboard.putNumber("Claw.Voltage", intakeMotor.getBusVoltage());
        intakeMotor.set(-OUTTAKE_SPEED);
    }

    public void stop() {
        overCurrent = false;
        // SmartDashboard.putNumber("Claw.Voltage", intakeMotor.getBusVoltage());
        intakeMotor.stopMotor();
    }
}