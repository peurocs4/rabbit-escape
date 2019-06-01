package rabbitescape.engine.behaviours.states.outofbounds;

import rabbitescape.engine.ChangeDescription;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public class NotOutOfBounds implements IOutOfBoundsState
{
    @Override
    public ChangeDescription.State newState()
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
