package rabbitescape.engine.behaviours.actions.climbing;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

public interface IClimbingState
{
    public State newState();

    public boolean behave( World world, Character character, boolean abilityActive );
}
