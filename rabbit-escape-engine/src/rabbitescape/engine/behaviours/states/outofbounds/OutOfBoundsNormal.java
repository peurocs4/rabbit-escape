package rabbitescape.engine.behaviours.states.outofbounds;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;
import rabbitescape.engine.config.TapTimer;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_OUT_OF_BOUNDS;

public class OutOfBoundsNormal implements IOutOfBoundsState
{
    @Override
    public State newState()
    {
        return RABBIT_OUT_OF_BOUNDS;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        checkMars( world, character );
        world.changes.killRabbit( character );
        return true;
    }

    /**
     * Test if mars mode has been triggered
     */
    private void checkMars( World world, Character character)
    {
        /* The character must leave the world at the correct coordinates,
         * the index count is likely to only be correct if this is the
         * first character out of the entrance, and it must be the correct
         * level.
         */
        if ( 12 == character.x && -1 == character.y &&
            world.getRabbitIndexCount() == 2 &&
            world.name.equals( "Ghost versus pie" ) )
        {
            TapTimer.setMars();
        }
    }
}
