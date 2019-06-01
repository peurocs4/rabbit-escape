package rabbitescape.engine.behaviours.states.walking.walkingondownslope;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_LOWERING_RIGHT_CONTINUE;

public class LoweringRightContinue implements IWalkingState
{
    @Override
    public State newState()
    {
        return RABBIT_LOWERING_RIGHT_CONTINUE;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        ++character.y;
        ++character.x;
        character.onSlope = true;
        return true;
    }
}
