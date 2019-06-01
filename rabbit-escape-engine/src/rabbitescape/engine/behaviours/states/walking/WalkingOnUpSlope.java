package rabbitescape.engine.behaviours.states.walking;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.behaviours.states.Blocking;
import rabbitescape.engine.behaviours.states.walking.walkingonupslope.*;

import static rabbitescape.engine.Direction.RIGHT;

public class WalkingOnUpSlope
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
        int nextY = t.character.y - 1;
        Block aboveNext = t.blockAboveNext();
        Block above = t.blockAbove();

        if
        (
            t.isWall( aboveNext )
                || Blocking.blockerAt( t.world, nextX, nextY )
                || t.isRoof( above )
                || ( t.isCresting() &&
                         Blocking.blockerAt( t.world, nextX, t.character.y ) )
        )
        {
            setBothStates( new TurningRightToLeftRising(), new TurningLeftToRightRising() );
        }
        else if ( t.isUpSlope( aboveNext ) )
        {
            setBothStates( new RisingRightContinue(), new RisingLeftContinue() );
        }
        else if ( t.isDownSlope( t.blockNext() ) )
        {
            setBothStates( new RisingAndLoweringRight(), new RisingAndLoweringLeft() );
        }
        else
        {
            setBothStates( new RisingRightEnd(), new RisingLeftEnd() );
        }

        setWalkingState( t.character.dir == RIGHT ? rightState : leftState );

        return walkingState;
    }
}
