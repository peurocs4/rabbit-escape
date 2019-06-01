package rabbitescape.engine.behaviours.states.exiting;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_ENTERING_EXIT;

public class EnteringExit implements IExitingState
{
    @Override
    public State newState()
    {
        return RABBIT_ENTERING_EXIT;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        world.changes.saveRabbit( character );
        return true;
    }
}
