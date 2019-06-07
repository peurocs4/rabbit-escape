package rabbitescape.engine.newstates.characterstates.actions.climbing;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_CLIMBING_LEFT_CONTINUE_1;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_CLIMBING_LEFT_CONTINUE_2;

public class ClimbingLeftContinue1 implements IClimbingState {

    @Override
    public State getState() {
        return RABBIT_CLIMBING_LEFT_CONTINUE_1;
    }

    @Override
    public IClimbingState newState(BehaviourTools t, boolean abilityActive) {
        return new ClimbingLeftContinue2();
    }

    @Override
    public boolean behave(
        World world, Character character, boolean abilityActive
    ) {
        abilityActive = true;
        return true;
    }
}
