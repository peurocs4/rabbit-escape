package rabbitescape.engine.items;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

public class BlockItem extends Item
{
    public static final ItemType TYPE = ItemType.block;

    public BlockItem( int x, int y )
    {
        super( x, y, State.TOKEN_BLOCK_FALL_TO_SLOPE, TYPE );
    }

    public BlockItem( int x, int y, World world )
    {
        super( x, y, State.TOKEN_BLOCK_FALL_TO_SLOPE, TYPE, world );
    }

    @Override
    public char getCharRepresentation()
    {
        return 'k';
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_BLOCK_STILL;
    }

    @Override
    public State getFallingState()
    {
        return State.TOKEN_BLOCK_FALLING;
    }

    @Override
    public State getFallToSlopState()
    {
        return State.TOKEN_BLOCK_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_BLOCK_ON_SLOPE;
    }

    @Override
    public Item copyWithoutState()
    {
        return new BlockItem( this.x, this.y );
    }
}
