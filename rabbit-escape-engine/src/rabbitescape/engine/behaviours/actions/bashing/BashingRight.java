package rabbitescape.engine.behaviours.actions.bashing;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BASHING_RIGHT;
import static rabbitescape.engine.Direction.RIGHT;

public class BashingRight implements IBashingState
{
    @Override
    public State newState()
    {
        return RABBIT_BASHING_RIGHT;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        character.slopeBashHop = false;
        world.changes.removeBlockAt( destX( character ), character.y );
        return true;
    }

    private int destX( Character character )
    {
        return ( character.dir == RIGHT ) ? character.x + 1 : character.x - 1;
    }
}
