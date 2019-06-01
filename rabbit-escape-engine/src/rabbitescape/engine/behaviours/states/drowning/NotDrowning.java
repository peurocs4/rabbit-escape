package rabbitescape.engine.behaviours.states.drowning;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public class NotDrowning implements IDrowningState
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
