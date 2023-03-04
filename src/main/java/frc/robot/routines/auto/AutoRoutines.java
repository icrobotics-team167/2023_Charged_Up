package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.auto.*;
import frc.robot.routines.Routine;
import frc.robot.subsystems.turret.TurretPosition;

import java.time.Duration;

/**
 * An enum for selecting auto routines.
 */
public enum AutoRoutines {
        // ROUTINE TEMPLATE
        //
        // ROUTINE_ID("Routine Name", new Routine(new Action[] {
        // new NullAction(),
        // })),

        BALANCE_CAUTIOUS("Balance Cautious", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new Wait(0.25),
                        new OpenClaw(),
                        new Wait(0.25),
                        new DriveStraight(180, -0.3)
                                        .withTurret(TurretPosition.INTAKE.withExtension(3.5).withPivot(-20)),
                        // new MoveArm(TurretPo6sition.INTAKE.withExtension(3.5).withPivot(-20)),
                        new CloseClaw(),
                        new DriveForwardsUntil(
                                        navx -> navx.getPitch() >= 8,
                                        0.2,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(48, 0.4),
                        // new Wait(1),
                        // new NaiveAutoBalance()
        })),
        GO_STRAIGHT_BLUE("Score cube then cone (Blue Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_BLUE),
                        // new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new OpenClaw(),
                        new DriveStraight(180, -0.5)
                                        .withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.INTAKE.withExtension(3.5).withPivot(-20).withSwivel(180)),
                        new CloseClaw(),
        })),
        GO_STRAIGHT_RED("Score cube then cone (Red Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_RED),
                        // new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new OpenClaw(),
                        new DriveStraight(180, -0.5)
                                        .withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.INTAKE.withExtension(3.5).withPivot(-20).withSwivel(-180)),
                        new CloseClaw(),
        })),
        TEST_CONE_SCORE("Test:Score cone", new Routine(new Action[] {
                new MoveArm(TurretPosition.HIGH_GOAL_CONE_BLUE),
                new OpenClaw(),
                new DriveStraight(160, -0.5).withTurret(TurretPosition.HIGH_GOAL_CONE_BLUE.withExtension(3.5)),
                new MoveArm(TurretPosition.INTAKE.withSwivel(180), 0.8)
        })),
        TEST_INTAKE_CONE("Test:Intake cone from behind", new Routine(new Action[] {
                new OpenClaw(),
                new MoveArm(TurretPosition.INTAKE.withSwivel(180)),
                new CloseClaw(),
                new ResetArm(),
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