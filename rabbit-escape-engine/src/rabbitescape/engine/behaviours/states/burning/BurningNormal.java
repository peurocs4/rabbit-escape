package rabbitescape.engine.behaviours.states.burning;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;
import rabbitescape.engine.textworld.Chars;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BURNING;

public class BurningNormal implements IBurningState
{
    @Override
    public State newState()
    {
        return RABBIT_BURNING;
    }

    @Override
    public boolean behave( World world, Character character )
    {
        world.changes.killRabbit( character );
        return true;
    }

    public static void setChars( Change change, Chars chars )
    {
        chars.set( change.x, change.y, 'X' );
    }
}
