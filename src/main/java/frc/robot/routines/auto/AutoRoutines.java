package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.auto.*;
import frc.robot.routines.Routine;
import frc.robot.subsystems.turret.Turret;
import frc.robot.subsystems.turret.TurretPosition;
import frc.robot.util.MathUtils;

import java.time.Duration;

/**
 * An enum for selecting auto routines.
 */
public enum AutoRoutines {
        // ROUTINE TEMPLATE
        //
        // ROUTINE_ID("Routine Name", new Routine(new Action[] {
        //                 new NullAction(),
        // })),

        BALANCE_CAUTIOUS("Balance Cautious", new Routine(new Action[] {
                        MoveArm.to(TurretPosition.HIGH_MID),
                        new Outtake(),
                        new DriveStraight(180, -0.5).withTurret(TurretPosition.INITIAL),
                        new Wait(0.25),
                        new DriveStraightUntil(
                                        navx -> Math.abs(navx.getPitch()) >= 8,
                                        0.4,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(35, 0.4),
                        new AutoBalance()
        })),
        GO_STRAIGHT_BLUE("Score cone then cube (Blue Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CONE_BLUE),
                        new Outtake(),
                        new MoveArm(TurretPosition.INITIAL.withSwivel(-90)),
                        new DriveStraight(160, -0.7).withTurret(TurretPosition.INTAKE.withSwivel(-180)),
                        new DriveStraight(40, -0.4).withIntake(),
                        new Wait(0.25),
                        new DriveStraight(190, 0.8).withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_BLUE),
                        new Outtake(),
                        new MoveArm(TurretPosition.INITIAL)
        })),
        GO_STRAIGHT_RED("Score cone then cube (Red Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CONE_RED),
                        new Outtake(),
                        new MoveArm(TurretPosition.INITIAL.withSwivel(90)),
                        new DriveStraight(160, -0.7).withTurret(TurretPosition.INTAKE.withSwivel(180)),
                        new DriveStraight(40, -0.4).withIntake(),
                        new Wait(0.25),
                        new DriveStraight(190, 0.8).withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_RED),
                        new Outtake(),
                        new MoveArm(TurretPosition.INITIAL)
        })),
        TEST_BALANCE("Test:Balance Test", new Routine(new Action[] {
                        new DriveStraightUntil(
                                        navx -> Math.abs(navx.getPitch()) >= 8,
                                        -0.4,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(35, -0.5),
                        new AutoBalance()

        })),
        TEST_DRIVE_INTAKE("Test:Intake while driving", new Routine(new Action[] {
                        new DriveStraight(60, 0.25).withIntake(),
                        new Intake()
        })),
        NOTHING("Nothing", new Routine(new Action[] {
                        new NullAction(),
        }));

        public String name;
        public Routine actions;

        AutoRoutines(String name, Routine actions) {
                this.name = name;
                this.actions = actions;
        }
}