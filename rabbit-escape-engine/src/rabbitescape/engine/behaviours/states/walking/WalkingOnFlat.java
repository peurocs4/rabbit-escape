package rabbitescape.engine.behaviours.states.walking;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.behaviours.states.Blocking;
import rabbitescape.engine.behaviours.states.walking.walkingonflat.*;

import static rabbitescape.engine.Direction.RIGHT;

public class WalkingOnFlat
{
    private IWalkingState walkingState, rightState, leftState;

    public void setWalkingState( IWalkingState walkingState )
    {
        this.walkingState = walkingState;
    }

    public void setRightState( IWalkingState rightState )
    {
        this.rightState = rightState;
    }

    public void setLeftState( IWalkingState leftState )
    {
        this.leftState = leftState;
    }

    public void setBothStates( IWalkingState rightState, IWalkingState leftState )
    {
        setRightState( rightState );
        setLeftState( leftState );
    }

    public IWalkingState newState( BehaviourTools t, boolean triggered )
    {
        int nextX = t.nextX();
        int nextY = t.character.y;
        Block next = t.blockNext();

        if
        (
            t.isWall( next )
                || Blocking.blockerAt( t.world, nextX, nextY )
        )
        {
            setBothStates( new TurningRightToLeft(), new TurningLeftToRight() );
        }
        else if ( t.isUpSlope( next ) )
        {
            setBothStates( new RisingRightStart(), new RisingLeftStart() );
        }
        else if ( t.isDownSlope( t.blockBelowNext() ) )
        {
            if ( Blocking.blockerAt( t.world, nextX, t.character.y + 1 ) )
            {
                setBothStates( new TurningRightToLeft(), new TurningLeftToRight() );
            }
            else
            {
                setBothStates( new LoweringRightStart(), new LoweringLeftStart() );
            }
        }
        else
        {
            setBothStates( new WalkingRight(), new WalkingLeft() );
        }

        setWalkingState( t.character.dir == RIGHT ? rightState : leftState );

        return walkingState;
    }
}
