package rabbitescape.engine.behaviours.actions.bashing;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BASHING_USELESSLY_LEFT_UP;

public class BashingUselesslyLeftUp implements IBashingState
{
    @Override
    public State newState()
    {
        return RABBIT_BASHING_USELESSLY_LEFT_UP;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        character.slopeBashHop = true;
        character.y -= 1;
        return true;
    }
}
