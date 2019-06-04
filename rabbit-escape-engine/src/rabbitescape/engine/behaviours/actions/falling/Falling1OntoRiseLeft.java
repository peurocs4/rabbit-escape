package rabbitescape.engine.behaviours.actions.falling;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_FALLING_1_ONTO_RISE_LEFT;

public class Falling1OntoRiseLeft implements IFallingState
{
    @Override
    public State newState()
    {
        return RABBIT_FALLING_1_ONTO_RISE_LEFT;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        return false;
    }
}
