package rabbitescape.engine.behaviours.states.walking.walkingonflat;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_WALKING_RIGHT;

public class WalkingRight implements IWalkingState
{
    @Override
    public State newState()
    {
        return RABBIT_WALKING_RIGHT;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        ++character.x;
        character.onSlope = false;
        return true;
    }
}
