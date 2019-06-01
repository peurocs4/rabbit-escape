package rabbitescape.engine.behaviours.states.walking.walkingonupslope;

import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.Block.Shape.BRIDGE_UP_LEFT;
import static rabbitescape.engine.Block.Shape.BRIDGE_UP_RIGHT;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_TURNING_RIGHT_TO_LEFT_RISING;
import static rabbitescape.engine.Direction.LEFT;

public class TurningRightToLeftRising implements IWalkingState
{
    @Override
    public State newState()
    {
        return RABBIT_TURNING_RIGHT_TO_LEFT_RISING;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        character.dir = LEFT;
        checkJumpOntoSlope( world, character );
        return true;
    }

    /**
     * If we turn around near a slope, we jump onto it
     */
    public void checkJumpOntoSlope( World world, Character character )
    {
        Block thisBlock = world.getBlockAt( character.x, character.y );
        if ( isBridge( thisBlock ) )
        {
            Block aboveBlock = world.getBlockAt( character.x, character.y - 1 );
            if ( character.onSlope && isBridge( aboveBlock ) )
            {
                character.y--;
            }
            else
            {
                character.onSlope = true;
            }
        }
    }

    private boolean isBridge( Block block )
    {
        return (
            block != null
                && (
                block.shape == BRIDGE_UP_LEFT
                    || block.shape == BRIDGE_UP_RIGHT
            )
        );
    }
}
