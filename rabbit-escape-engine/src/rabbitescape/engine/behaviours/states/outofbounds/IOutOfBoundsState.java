package rabbitescape.engine.behaviours.states.outofbounds;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public interface IOutOfBoundsState
{
    public State newState();

    public boolean behave( World world, Character character );
}
