package rabbitescape.engine.behaviours.states.exiting;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public class NotExiting implements IExitingState
{
    @Override
    public State newState()
    {
        return null;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        return false;
    }
}
