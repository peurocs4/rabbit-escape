package rabbitescape.engine.behaviours.actions.digging;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_DIGGING_USELESSLY;

public class DiggingUselessly implements IDiggingState
{
    @Override
    public State newState()
    {
        return RABBIT_DIGGING_USELESSLY;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        return true;
    }
}
