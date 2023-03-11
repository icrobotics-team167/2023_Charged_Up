package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.auto.*;
import frc.robot.routines.Routine;
import frc.robot.subsystems.turret.Turret;
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
                        // new MoveArm(TurretPosition.INTAKE.withExtension(3.5).withPivot(-20)),
                        new CloseClaw(),
                        new DriveForwardsUntil(
                                        navx -> navx.getPitch() >= 8,
                                        0.2,
                                        Duration.ofMillis(3500)),
                        new DriveStraight(49, 0.4),
        // new Wait(1),
        // new NaiveAutoBalance()
        })),
        GO_STRAIGHT_BLUE("Score cube then cone (Blue Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_BLUE),
                        // new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new OpenClaw(),
                        new DriveStraight(180, -0.5)
                                        .withTurret(new TurretPosition(TurretPosition.HIGH_GOAL_CUBE_BLUE.pivotAngle(),
                                                        180, 3.5)),
                        new MoveArm(new TurretPosition(-30, 180, 3.5)),
                        new CloseClaw(),
                        new DriveStraight(180, 0.5).withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.HIGH_GOAL_CONE_BLUE),
                        new OpenClaw(),
        })),
        GO_STRAIGHT_RED("Score cube then cone (Red Alliance)", new Routine(new Action[] {
                        new MoveArm(TurretPosition.HIGH_GOAL_CUBE_RED),
                        // new MoveArm(TurretPosition.HIGH_GOAL_CENTER),
                        new OpenClaw(),
                        new DriveStraight(180, -0.5)
                                        .withTurret(new TurretPosition(TurretPosition.HIGH_GOAL_CUBE_RED.pivotAngle(),
                                                        -180, 3.5)),
                        new MoveArm(new TurretPosition(-30, -180, 3.5)),
                        new CloseClaw(),
                        new DriveStraight(180, 0.5).withTurret(TurretPosition.INITIAL),
                        new MoveArm(TurretPosition.HIGH_GOAL_CONE_RED),
                        new OpenClaw(),
        })),
        TEST_SCORING_POS("Test:Scoring positions", new Routine(new Action[] {
                new MoveArm(TurretPosition.MID_MID),
                new OpenClaw(),
                // new Wait(0.5),
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