package rabbitescape.engine.behaviours.states.exiting;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public interface IExitingState
{
    public State newState();

    public boolean behave( World world, Character character );
}
