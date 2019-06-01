package rabbitescape.engine.behaviours.actions.climbing;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_CLIMBING_RIGHT_CONTINUE_1;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_CLIMBING_RIGHT_END;

public class ClimbingRightContinue1 implements IClimbingState
{
    @Override
    public State newState()
    {
        return RABBIT_CLIMBING_RIGHT_CONTINUE_1;
    }

    @Override
    public boolean behave(
        World world, Character character, boolean abilityActive
    )
    {
        abilityActive = true;
        return true;
    }
}
