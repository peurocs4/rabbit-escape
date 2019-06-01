package rabbitescape.engine.behaviours.states.burning;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public class NotBurning implements IBurningState
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
