package rabbitescape.engine.behaviours.actions.bashing;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BASHING_USELESSLY_LEFT;

public class BashingUselesslyLeft implements IBashingState
{
    @Override
    public State newState()
    {
        return RABBIT_BASHING_USELESSLY_LEFT;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        character.slopeBashHop = false;
        return true;
    }
}
