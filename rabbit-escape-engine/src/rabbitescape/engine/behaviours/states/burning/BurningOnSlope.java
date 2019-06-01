package rabbitescape.engine.behaviours.states.burning;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;
import rabbitescape.engine.textworld.Chars;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BURNING_ON_SLOPE;

public class BurningOnSlope implements IBurningState
{
    @Override
    public State newState()
    {
        return RABBIT_BURNING_ON_SLOPE;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        world.changes.killRabbit( character );
        return false;
    }

    public static void setChars( Change change, Chars chars )
    {
        chars.set( change.x, change.y, 'X' );
    }
}
