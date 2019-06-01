package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.items.ItemType.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.climbing.*;
import rabbitescape.engine.things.Character;

public class Climbing extends Behaviour
{
    private IClimbingState climbingState, rightState, leftState;
    boolean hasAbility = false;
    public boolean abilityActive = false;

    public Climbing()
    {
        setClimbingState( new NotClimbing() );
    }

    public void setClimbingState( IClimbingState climbingState )
    {
        this.climbingState = climbingState;
    }

    public void setRightState( IClimbingState rightState )
    {
        this.rightState = rightState;
    }

    public void setLeftState( IClimbingState leftState )
    {
        this.leftState = leftState;
    }

    public void setBothStates( IClimbingState rightState, IClimbingState leftState )
    {
        setRightState( rightState );
        setLeftState( leftState );
    }

    @Override
    public void cancel()
    {
        abilityActive = false;
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        BehaviourTools t = new BehaviourTools( character, world );

        return !hasAbility && t.pickUpToken( climb, true );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            hasAbility = true;
        }

        if ( !hasAbility )
        {
            return null;
        }

        switch ( t.character.state )
        {
            case RABBIT_CLIMBING_RIGHT_START:
            case RABBIT_CLIMBING_LEFT_START:
                return newStateStart( t );
            case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
            case RABBIT_CLIMBING_LEFT_CONTINUE_1:
                return newStateCont1( t );
            case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
            case RABBIT_CLIMBING_LEFT_CONTINUE_2:
                return newStateCont2( t );
            default:
                return newStateNotClimbing( t );
        }
    }

    private State newStateStart( BehaviourTools t )
    {
        Block endBlock = t.blockAboveNext();

        if ( t.isWall( endBlock ) )
        {
            setBothStates( new ClimbingRightContinue2(), new ClimbingLeftContinue2() );
        }
        else
        {
            setBothStates( new ClimbingRightEnd(), new ClimbingLeftEnd() );
        }
        setClimbingState( t.character.dir == RIGHT ? rightState : leftState );

        return climbingState.newState();
    }

    private State newStateCont1( BehaviourTools t )
    {
        setBothStates( new ClimbingRightContinue2(), new ClimbingLeftContinue2() );
        setClimbingState( t.character.dir == RIGHT ? rightState : leftState );

        return climbingState.newState();
    }

    private State newStateCont2( BehaviourTools t )
    {
        Block aboveBlock = t.blockAbove();
        Block endBlock = t.blockAboveNext();

        if ( t.isRoof( aboveBlock ) )
        {
            abilityActive = false;
            setBothStates( new ClimbingRightBangHead(), new ClimbingLeftBangHead() );
        }
        else if ( t.isWall( endBlock ) )
        {
            setBothStates( new ClimbingRightContinue1(), new ClimbingLeftContinue1() );
        }
        else
        {
            setBothStates( new ClimbingRightEnd(), new ClimbingLeftEnd() );
        }
        setClimbingState( t.character.dir == RIGHT ? rightState : leftState );

        return climbingState.newState();
    }

    private State newStateNotClimbing( BehaviourTools t )
    {
        int nextX = t.nextX();
        int nextY = t.nextY();
        Block nextBlock = t.world.getBlockAt( nextX, nextY );
        Block aboveBlock = t.world.getBlockAt( t.character.x, t.character.y - 1 );

        if ( !t.isRoof( aboveBlock ) && t.isWall( nextBlock ) )
        {
            setBothStates( new ClimbingRightStart(), new ClimbingLeftStart() );
            setClimbingState( t.character.dir == RIGHT ? rightState : leftState );
        }

        return climbingState.newState();
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        BehaviourTools t = new BehaviourTools( character, world );

        if( t.rabbitIsClimbing() )
        { // Can't be both on a wall and on a slope.
            character.onSlope = false;
        }

        return climbingState.behave( world, character, abilityActive );
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfTrue(
            saveState, "Climbing.hasAbility", hasAbility
        );

        BehaviourState.addToStateIfTrue(
            saveState, "Climbing.abilityActive", abilityActive
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        hasAbility = BehaviourState.restoreFromState(
            saveState, "Climbing.hasAbility", hasAbility
        );

        abilityActive = BehaviourState.restoreFromState(
            saveState, "Climbing.abilityActive", abilityActive
        );
    }

    @Override
    public String toString()
    {
        return "Climbing";
    }
}
