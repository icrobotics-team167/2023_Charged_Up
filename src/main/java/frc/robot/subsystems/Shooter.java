package frc.robot.subsystems;

// import com.revrobotics.CANEncoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Config;

public class Shooter {

    private static Shooter instance;
    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
        }
        return instance;
    }

    private boolean on;
    private int ShooterRPM, BackSpinRPM;
    private CANSparkMax shooterMotorController;
    private CANSparkMax shooterMotorController2;
    private RelativeEncoder shooterEncoder;
    private RelativeEncoder shooterEncoder2;

    private Shooter() {
        shooterMotorController = new CANSparkMax(Config.Ports.Shooter.SHOOTER, MotorType.kBrushless);
        shooterMotorController.restoreFactoryDefaults();
        shooterMotorController.setIdleMode(IdleMode.kCoast);
        shooterMotorController.setInverted(false);
        shooterMotorController.setOpenLoopRampRate(0);
        shooterMotorController.setClosedLoopRampRate(0);
        shooterMotorController.setSmartCurrentLimit(80);
        shooterMotorController.setSecondaryCurrentLimit(40);
        
        shooterMotorController2 = new CANSparkMax(Config.Ports.Shooter.SHOOTER2, MotorType.kBrushless);
        shooterMotorController.restoreFactoryDefaults();
        shooterMotorController.setIdleMode(IdleMode.kCoast);
        shooterMotorController.setInverted(true);
        shooterMotorController.setOpenLoopRampRate(0);
        shooterMotorController.setClosedLoopRampRate(0);
        shooterMotorController.setSmartCurrentLimit(80);
        shooterMotorController.setSecondaryCurrentLimit(40);

        shooterEncoder = shooterMotorController.getEncoder();
        shooterEncoder.setPosition(0);

        shooterEncoder2 = shooterMotorController2.getEncoder();
        shooterEncoder2.setPosition(0);

        on = false;
        ShooterRPM = Config.Settings.SHOOTING_RPM;
        BackSpinRPM = Config.Settings.SHOOTING_RPM*3;
    }

    public void run() {
        if (on) {
            int actualShooterRPM = (int) shooterEncoder.getVelocity();
            int actualBackSpinRPM = (int) shooterEncoder2.getVelocity();

            if (actualShooterRPM <= ShooterRPM) {
                shooterMotorController.set(1);
            } else {
                shooterMotorController.set(0);
            }

            if (actualBackSpinRPM <= BackSpinRPM) {
                shooterMotorController2.set(1);
            } else {
                shooterMotorController2.set(0);
            }


        } else {
            shooterMotorController.set(0);
            shooterMotorController2.set(0);
        }
    }

    public void setTargetRPM(int ShooterRPM) {
        this.ShooterRPM = ShooterRPM;
    }

    public void setBackSpinRPM(int BackSpinRPM) {
        this.BackSpinRPM = BackSpinRPM;
    }

    public void toggle() {
        on = !on;
    }

    public void start() {
        on = true;
    }

    public void stop() {
        on = false;
    }

    public void testShooter() {
        shooterMotorController.set(0.2);
        shooterMotorController2.set(0.2);
    }

    public int getTargetRPM() {
        return ShooterRPM;
    }

    public int getTargetBackSpinRPM() {
        return BackSpinRPM;
    }

    public int getShooterRPM() {
        int shootRPM = (int) shooterEncoder.getVelocity();
        
        return shootRPM;
    }
    
    public int getBackSpinRPM() {
        int backSpinRPM = (int) shooterEncoder2.getVelocity();
        
        return backSpinRPM;
    }

    public boolean isInStoppedMode() {
        return !on;
    }

    public boolean isInShootingMode() {
        return on;
    }

    public boolean isStopped() {
        return getShooterRPM() <= 0;
    }

    public boolean isUpToSpeed() {
        return getShooterRPM() >= ShooterRPM - 400;
    }

    public double getShootVoltage() {
        return shooterMotorController.getBusVoltage();
    }

}
