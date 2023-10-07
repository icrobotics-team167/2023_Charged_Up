package frc.robot.routines;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import frc.robot.routines.auto.AutoState;

public class Routine extends Action {

    private List<Action> savedActions;
    private Queue<Action> actions;
    private Action currentAction;

    public Routine(Action... actions) {
        savedActions = Arrays.asList(actions);
        setState(AutoState.INIT);
    }

    @Override
    public void init() {
        this.actions = new LinkedList<>(savedActions);
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
