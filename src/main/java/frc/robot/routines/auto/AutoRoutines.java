package frc.robot.routines.auto;

import frc.robot.routines.Action;
import frc.robot.routines.Routine;
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

    SCORE_CONE("Score Cone", new Routine(new Action[] {
    // TODO: Make an auto routine for scoring a cone
    })),
    SCORE_CUBE("Score cube", new Routine(new Action[] {
    // TODO: Make an auto routine for scoring a cube
    })),
    BALANCE("Balance", new Routine(new Action[] {
            new DriveForwardsUntil(
                    navx -> navx.getPitch() >= 5,
                    0.3,
                    Duration.ofMillis(3500)),
            new AutoBalance(),
    })),
    GO_STRAIGHT("Go straight", new Routine(new Action[] {
            new DriveStraight(35, 0.5),
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