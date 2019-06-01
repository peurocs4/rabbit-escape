package rabbitescape.engine.behaviours.states.drowning;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public interface IDrowningState
{
    public State newState();

    public boolean behave( World world, Character character );
}
