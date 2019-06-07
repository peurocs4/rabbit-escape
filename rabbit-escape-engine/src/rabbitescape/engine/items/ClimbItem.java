package rabbitescape.engine.items;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;

public class ClimbItem extends Item
{
    public static final ItemType TYPE = ItemType.climb;

    public ClimbItem( int x, int y )
    {
        super( x, y, State.TOKEN_CLIMB_FALL_TO_SLOPE, TYPE );
    }

    public ClimbItem( int x, int y, World world )
    {
        super( x, y, State.TOKEN_CLIMB_FALL_TO_SLOPE, TYPE, world );
    }

    @Override
    public char getCharRepresentation()
    {
        return 'c';
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_CLIMB_STILL;
    }

    @Override
    public State getFallingState()
    {
        return State.TOKEN_CLIMB_FALLING;
    }

    @Override
    public State getFallToSlopState()
    {
        return State.TOKEN_CLIMB_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_CLIMB_ON_SLOPE;
    }

    @Override
    public Item copyWithoutState()
    {
        return new ClimbItem( this.x, this.y );
    }
}
