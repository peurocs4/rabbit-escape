package rabbitescape.engine.behaviours.states.walking;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.behaviours.states.Blocking;
import rabbitescape.engine.behaviours.states.walking.walkingondownslope.*;

import static rabbitescape.engine.Direction.RIGHT;

public class WalkingOnDownSlope
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
        int nextY = t.character.y + 1;
        Block next = t.blockNext();
        Block belowNext = t.blockBelowNext();

        if (
            t.isWall( next )
                || Blocking.blockerAt( t.world, nextX, nextY )
                || ( t.isValleying() &&
                         Blocking.blockerAt( t.world, nextX, t.character.y ) )
        )
        {
            setBothStates( new TurningRightToLeftLowering(), new TurningLeftToRightLowering() );
        }
        else if ( t.isUpSlope( next ) )
        {
            setBothStates( new LoweringAndRisingRight(), new LoweringAndRisingLeft() );
        }
        else if ( t.isDownSlope( belowNext ) )
        {
            setBothStates( new LoweringRightContinue(), new LoweringLeftContinue() );
        }
        else
        {
            if ( Blocking.blockerAt( t.world, nextX, t.character.y ) )
            {
                setBothStates( new TurningRightToLeftLowering(), new TurningLeftToRightLowering() );
            }
            else
            {
                setBothStates( new LoweringRightEnd(), new LoweringLeftEnd() );
            }
        }

        setWalkingState( t.character.dir == RIGHT ? rightState : leftState );

        return walkingState;
    }
}
