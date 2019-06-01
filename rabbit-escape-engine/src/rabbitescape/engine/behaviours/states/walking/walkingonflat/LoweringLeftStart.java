package rabbitescape.engine.behaviours.states.walking.walkingonflat;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_LOWERING_LEFT_START;

public class LoweringLeftStart implements IWalkingState
{
    @Override
    public State newState()
    {
        return RABBIT_LOWERING_LEFT_START;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        ++character.y;
        --character.x;
        character.onSlope = true;
        return true;
    }
}
