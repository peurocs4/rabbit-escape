package rabbitescape.engine.behaviours.states.walking.walkingondownslope;

import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.Block.Shape.BRIDGE_UP_LEFT;
import static rabbitescape.engine.Block.Shape.BRIDGE_UP_RIGHT;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING;
import static rabbitescape.engine.Direction.RIGHT;

public class TurningLeftToRightLowering implements IWalkingState
{
    @Override
    public State newState()
    {
        return RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        character.dir = RIGHT;
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
