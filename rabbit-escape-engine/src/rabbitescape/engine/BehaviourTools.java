package rabbitescape.engine;

import rabbitescape.engine.items.Item;
import rabbitescape.engine.items.ItemType;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.util.Position;

import static rabbitescape.engine.Block.Shape.*;
import static rabbitescape.engine.Direction.RIGHT;
import static rabbitescape.engine.Direction.opposite;

public class BehaviourTools
{
    public final Character character;
    public final World world;

    public BehaviourTools( Character rabbit, World world )
    {
        this.character = rabbit;
        this.world = world;
    }

    public ChangeDescription.State rl(
        ChangeDescription.State rightState,
        ChangeDescription.State leftState
    )
    {
        return character.dir == RIGHT ? rightState : leftState;
    }

    public boolean pickUpItem( ItemType type )
    {
        return pickUpItem( type, false );
    }

    public boolean rabbitIsFalling()
    {
        switch ( character.state)
        {
        case RABBIT_FALLING:
        case RABBIT_FALLING_1:
        case RABBIT_FALLING_1_TO_DEATH:
        case RABBIT_DYING_OF_FALLING_2:
        case RABBIT_DYING_OF_FALLING:
        case RABBIT_FALLING_ONTO_LOWER_RIGHT:
        case RABBIT_FALLING_ONTO_RISE_RIGHT:
        case RABBIT_FALLING_ONTO_LOWER_LEFT:
        case RABBIT_FALLING_ONTO_RISE_LEFT:
        case RABBIT_FALLING_1_ONTO_LOWER_RIGHT:
        case RABBIT_FALLING_1_ONTO_RISE_RIGHT:
        case RABBIT_FALLING_1_ONTO_LOWER_LEFT:
        case RABBIT_FALLING_1_ONTO_RISE_LEFT:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_RIGHT_2:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_RIGHT_2:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT:
        case RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT_2:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT:
        case RABBIT_DYING_OF_FALLING_2_SLOPE_RISE_LEFT_2:
            return true;
        default:
            return false;
        }
    }

    public boolean rabbitIsClimbing()
    {
        switch( character.state)
        {
        case RABBIT_ENTERING_EXIT_CLIMBING_RIGHT:
        case RABBIT_ENTERING_EXIT_CLIMBING_LEFT:
        case RABBIT_CLIMBING_LEFT_START:
        case RABBIT_CLIMBING_LEFT_CONTINUE_1:
        case RABBIT_CLIMBING_LEFT_CONTINUE_2:
        case RABBIT_CLIMBING_LEFT_END:
        case RABBIT_CLIMBING_LEFT_BANG_HEAD:
        case RABBIT_CLIMBING_RIGHT_START:
        case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
        case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
        case RABBIT_CLIMBING_RIGHT_END:
        case RABBIT_CLIMBING_RIGHT_BANG_HEAD:
            return true;
        default:
            return false;
        }
    }

    public boolean rabbitIsBashing()
    {
        switch( character.state)
        {
        case RABBIT_BASHING_RIGHT:
        case RABBIT_BASHING_LEFT:
        case RABBIT_BASHING_UP_RIGHT:
        case RABBIT_BASHING_UP_LEFT:
        case RABBIT_BASHING_USELESSLY_RIGHT:
        case RABBIT_BASHING_USELESSLY_LEFT:
        case RABBIT_BASHING_USELESSLY_RIGHT_UP:
        case RABBIT_BASHING_USELESSLY_LEFT_UP:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks for the presence of a token. Removes token from the world
     * and returns true if a token is being picked up.
     */
    public boolean pickUpItem( ItemType type, boolean evenIfNotOnGround )
    {
        if ( rabbitIsFalling() && character.isFallingToDeath() )
        {
            return false; // Dying rabbits not allowed to consume tokens
        }

        if ( evenIfNotOnGround || onGround() )
        {
            Item item = world.getItemAt( character.x, character.y );
            if ( item != null && item.getType() == type )
            {
                world.changes.removeToken( item );
                return true;
            }
        }
        return false;
    }

    public Block blockHere()
    {
        return world.getBlockAt( character.x, character.y );
    }

    public Block blockNext()
    {
        return world.getBlockAt( nextX(), character.y );
    }

    public Block blockBelow()
    {
        return world.getBlockAt( character.x, character.y + 1 );
    }

    public Block block2Below()
    {
        return world.getBlockAt( character.x, character.y + 2 );
    }

    public Block blockBelowNext()
    {
        return world.getBlockAt( nextX(), character.y + 1 );
    }

    public Block blockAbove()
    {
        return world.getBlockAt( character.x, character.y - 1 );
    }

    public Block blockAboveNext()
    {
        return world.getBlockAt( nextX(), character.y - 1 );
    }

    private boolean onGround()
    {
        return ( character.onSlope || blockBelow() != null );
    }

    public boolean isWall( Block block )
    {
        return (
               block != null
            && (
                   block.shape == FLAT
                || (
                    block.riseDir() == opposite( character.dir )
                    && isSolid( block )
                )
            )
        );
    }


    public static boolean shapeEquals( Block b, Block.Shape s )
    {
        if ( null == b )
        {
            return false;
        }
        return s == b.shape;
    }

    public static boolean isRightRiseSlope( Block b )
    {
        if( b == null )
        {
            return false;
        }
        return b.shape == UP_RIGHT
            || b.shape == BRIDGE_UP_RIGHT;
    }

    public static boolean isLeftRiseSlope( Block b )
    {
        if( b == null )
        {
            return false;
        }
        return b.shape == UP_LEFT
            || b.shape == BRIDGE_UP_LEFT;
    }

    public static boolean isSlope( Block b )
    {
        return isRightRiseSlope( b ) || isLeftRiseSlope( b );
    }

    public static boolean isBridge( Block b )
    {
        if( b == null )
        {
            return false;
        }
        switch ( b.shape )
        {
        case BRIDGE_UP_LEFT:
        case BRIDGE_UP_RIGHT:
            return true;
        default:
            return false;
        }
    }

    public static boolean isSolid( Block block )
    {
        return (
               block.shape == FLAT
            || block.shape == UP_LEFT
            || block.shape == UP_RIGHT
        );
    }

    public boolean isRoof( Block block )
    {
        return
            (
                block != null
                && (
                       block.shape == FLAT
                    || block.shape == UP_LEFT
                    || block.shape == UP_RIGHT
                )
            );
    }

    public boolean isOnSlopeStateUnreliable()
    {
        Block block = blockHere();
        return
            null != block &&
            (
                   block.shape == UP_LEFT
                || block.shape == UP_RIGHT
                || block.shape == BRIDGE_UP_LEFT
                || block.shape == BRIDGE_UP_RIGHT
            );
    }

    public boolean isFlat( Block block )
    {
        return s_isFlat( block );
    }

    public static boolean s_isFlat( Block block )
    {
        return ( block != null && block.shape == FLAT );
    }

    private boolean goingUpSlope()
    {
        if ( character.onSlope )
        {
            if( isOnUpSlope() )
            {
                return true;
            }
        }
        return false;
    }

    public boolean isOnUpSlope()
    {
        return character.onSlope && hereIsUpSlope();
    }

    /**
     * Check if character is changing from an up slope directly to a down slope.
     */
    public boolean isCresting()
    {
        // block where slope would be if it continues
        Block contBlock = world.getBlockAt( nextX(), nextY() );
        Block belowContBlock = world.getBlockAt( nextX(), nextY() + 1 );

        return isOnUpSlope() &&
               null == contBlock &&
               isDownSlope( belowContBlock );
    }

    /**
     * Check if character is changing from a down slope directly to an up slope.
     */
    public boolean isValleying()
    {
        // block where slope would be if it continues
        Block alongBlock = world.getBlockAt( nextX(), character.y );

        return isOnDownSlope() &&
               isUpSlope( alongBlock );
    }

    public boolean hereIsUpSlope()
    {
        return isUpSlope( blockHere() );
    }

    public boolean isUpSlope( Block block )
    {
        return ( block != null && block.riseDir() == character.dir );
    }

    public boolean isOnDownSlope()
    {
        return character.onSlope && hereIsDownSlope();
    }

    private boolean hereIsDownSlope()
    {
        return isDownSlope( blockHere() );
    }

    public boolean isDownSlope( Block block )
    {
        return ( block != null && block.riseDir() == opposite( character.dir ) );
    }

    public static boolean isSlopeNotBridge( Block b )
    {
        if ( null == b )
        {
            return false;
        }
        switch( b.shape )
        {
        case UP_LEFT:
        case UP_RIGHT:
            return true;
        default:
            return false;
        }
    }

    public int nextX()
    {
        return
            character.x + (
                character.dir == RIGHT ? 1 : -1
            );
    }

    public int nextY()
    {
        if ( goingUpSlope() )
        {
            return character.y - 1;
        }
        else
        {
            return character.y;
        }
    }

    /**
     * @brief A character may be on a slope block as a digger
     *        or basher removes it. This is here to make sure
     *        they fall.
     */
    public boolean blockHereJustRemoved()
    {
        for ( Position p : world.changes.blocksJustRemoved )
        {
            if ( character.x == p.x && character.y == p.y )
            {
                return true;
            }
        }
        return false;
    }
}
