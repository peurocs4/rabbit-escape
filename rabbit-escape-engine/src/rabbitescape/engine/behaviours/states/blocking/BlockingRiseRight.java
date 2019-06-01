package rabbitescape.engine.behaviours.states.blocking;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.burning.IBurningState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BLOCKING_RISE_RIGHT;

public class BlockingRiseRight implements IBlockingState
{
    @Override
    public State newState()
    {
        return RABBIT_BLOCKING_RISE_RIGHT;
    }

    @Override
    public boolean behave()
    {
        return true;
    }
}
