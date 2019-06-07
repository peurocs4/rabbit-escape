package rabbitescape.engine.newstates.characterstates.actions.blocking;

import rabbitescape.engine.ChangeDescription.State;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BLOCKING;

public class BlockingNormal implements IBlockingState {

    @Override
    public State getState() {
        return RABBIT_BLOCKING;
    }

    @Override
    public boolean behave() {
        return true;
    }
}
