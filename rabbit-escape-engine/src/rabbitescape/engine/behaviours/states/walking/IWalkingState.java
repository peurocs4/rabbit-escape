package rabbitescape.engine.behaviours.states.walking;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

public interface IWalkingState
{
    public State newState();

    public boolean behave( World world, Character character );
}
