package rabbitescape.engine.behaviours.states.blocking;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

public interface IBlockingState
{
    public State newState();

    public boolean behave();
}
