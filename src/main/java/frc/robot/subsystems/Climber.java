package frc.robot.subsystems;

import frc.robot.Config;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Climber {

    private static Climber instance;
    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
        }
        return instance;
    }

    
    private CANSparkMax winchMotor;
    private CANSparkMax winchMotor2;
    private Solenoid in_out;
    private boolean extended;

    private Climber() {
        winchMotor = new CANSparkMax(Config.Ports.Climb.WINCH, MotorType.kBrushed);
        winchMotor.restoreFactoryDefaults();
        winchMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        winchMotor.setInverted(true);
        winchMotor.setOpenLoopRampRate(0);
        winchMotor.setClosedLoopRampRate(0);
        winchMotor.setSmartCurrentLimit(80);
        winchMotor.setSecondaryCurrentLimit(40);

        winchMotor2 = new CANSparkMax(Config.Ports.Climb.WINCH2, MotorType.kBrushed);
        winchMotor2.restoreFactoryDefaults();
        winchMotor2.setIdleMode(CANSparkMax.IdleMode.kBrake);
        winchMotor2.setInverted(false);
        winchMotor2.setOpenLoopRampRate(0);
        winchMotor2.setClosedLoopRampRate(0);
        winchMotor2.setSmartCurrentLimit(80);
        winchMotor2.setSecondaryCurrentLimit(40);

        extended = false; 
        
        in_out = new Solenoid(
            Config.Settings.SPARK_TANK_ENABLED ? Config.Ports.SparkTank.PCM : 2,
            PneumaticsModuleType.CTREPCM,
            Config.Ports.Climb.CLIMBER_ARMS
        );

    }

    public void climbExtend() {
        winchMotor.set(1);
        winchMotor2.set(1);
    }

    public void climbRetract() {
        winchMotor.set(-1);
        winchMotor2.set(-1);
    }

    public void climb(double winchSpeed) {
        winchMotor.set(winchSpeed);
    }

    public void stopClimb() {
        winchMotor.set(0);
        winchMotor2.set(0);
    }

    public void extend(){
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
