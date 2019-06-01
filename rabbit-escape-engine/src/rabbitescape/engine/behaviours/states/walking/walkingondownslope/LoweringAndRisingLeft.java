package rabbitescape.engine.behaviours.states.walking.walkingondownslope;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_LOWERING_AND_RISING_LEFT;

public class LoweringAndRisingLeft implements IWalkingState
{
    @Override
    public State newState()
    {
        return RABBIT_LOWERING_AND_RISING_LEFT;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        --character.x;
        character.onSlope = true;
        return true;
    }
}
