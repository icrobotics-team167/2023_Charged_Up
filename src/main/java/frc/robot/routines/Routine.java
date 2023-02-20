package frc.robot.routines;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import frc.robot.routines.auto.AutoState;

public class Routine extends Action {

    private Queue<Action> actions;
    private Action currentAction;

    public Routine(Action... actions) {
        this.actions = new LinkedList<>(Arrays.asList(actions));
        setState(AutoState.INIT);
    }

    @Override
    public void init() {
        currentAction = actions.poll();
    }

    @Override
    public void periodic() {
        if (currentAction != null) {
            if (currentAction.getState() == AutoState.EXIT) {
                state = AutoState.EXIT;
            }
            if (currentAction.getState() == AutoState.FINISHED) {
                currentAction = actions.poll();
                if (currentAction == null) {
                    return;
                }
            }
            currentAction.exec();
        }
    }

    @Override
    public boolean isDone() {
        return currentAction == null;
    }

    @Override
    public void done() {
    }

}
