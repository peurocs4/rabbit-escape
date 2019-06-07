package rabbitescape.engine.items;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

public class BrollyItem extends Item
{
    public static final ItemType TYPE = ItemType.brolly;

    public BrollyItem( int x, int y )
    {
        super( x, y, State.TOKEN_BROLLY_FALL_TO_SLOPE, TYPE );
    }

    public BrollyItem( int x, int y, World world )
    {
        super( x, y, State.TOKEN_BROLLY_FALL_TO_SLOPE, TYPE, world );
    }

    @Override
    public char getCharRepresentation()
    {
        return 'l';
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_BROLLY_STILL;
    }

    @Override
    public State getFallingState()
    {
        return State.TOKEN_BROLLY_FALLING;
    }

    @Override
    public State getFallToSlopState()
    {
        return State.TOKEN_BROLLY_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_BROLLY_ON_SLOPE;
    }

    @Override
    public Item copyWithoutState()
    {
        return new BrollyItem( this.x, this.y );
    }
}
