package rabbitescape.engine.behaviours.states.blocking;

import rabbitescape.engine.ChangeDescription.Change;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.burning.IBurningState;
import rabbitescape.engine.textworld.Chars;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BLOCKING;

public class BlockingNormal implements IBlockingState
{
    @Override
    public State newState()
    {
        return RABBIT_BLOCKING;
    }

    @Override
    public boolean behave()
    {
        return true;
    }
}
