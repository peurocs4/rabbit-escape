package rabbitescape.engine.behaviours.actions;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.climbing.IClimbingState;
import rabbitescape.engine.behaviours.actions.climbing.NotClimbing;
import rabbitescape.engine.things.Character;

import java.util.Map;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.opposite;
import static rabbitescape.engine.items.ItemType.climb;

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

        return !hasAbility && t.pickUpItem( climb, true );
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
            return t.rl(
                RABBIT_CLIMBING_RIGHT_CONTINUE_2,
                RABBIT_CLIMBING_LEFT_CONTINUE_2
            );
        }
        else
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_END,
                RABBIT_CLIMBING_LEFT_END
            );
        }
    }

    private State newStateCont1( BehaviourTools t )
    {
        return t.rl(
            RABBIT_CLIMBING_RIGHT_CONTINUE_2,
            RABBIT_CLIMBING_LEFT_CONTINUE_2
        );
    }

    private State newStateCont2( BehaviourTools t )
    {
        Block aboveBlock = t.blockAbove();

        if ( t.isRoof( aboveBlock ) )
        {
            abilityActive = false;
            return t.rl(
                RABBIT_CLIMBING_RIGHT_BANG_HEAD,
                RABBIT_CLIMBING_LEFT_BANG_HEAD
            );
        }

        Block endBlock = t.blockAboveNext();

        if ( t.isWall( endBlock ) )
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_CONTINUE_1,
                RABBIT_CLIMBING_LEFT_CONTINUE_1
            );
        }
        else
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_END,
                RABBIT_CLIMBING_LEFT_END
            );
        }
    }

    private State newStateNotClimbing( BehaviourTools t )
    {
        int nextX = t.nextX();
        int nextY = t.nextY();
        Block nextBlock = t.world.getBlockAt( nextX, nextY );
        Block aboveBlock = t.world.getBlockAt( t.character.x, t.character.y - 1 );

        if ( !t.isRoof( aboveBlock ) && t.isWall( nextBlock ) )
        {
            return t.rl(
                RABBIT_CLIMBING_RIGHT_START,
                RABBIT_CLIMBING_LEFT_START
            );
        }

        return null;
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        BehaviourTools t = new BehaviourTools( character, world );

        if( t.rabbitIsClimbing() )
        { // Can't be both on a wall and on a slope.
            character.onSlope = false;
        }

        switch ( state )
        {
            case RABBIT_CLIMBING_RIGHT_START:
            case RABBIT_CLIMBING_LEFT_START:
            {
                abilityActive = true;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_END:
            case RABBIT_CLIMBING_LEFT_END:
            {
                character.x = t.nextX();
                --character.y;
                if ( t.hereIsUpSlope() )
                {
                    character.onSlope = true;
                }
                abilityActive = false;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
            case RABBIT_CLIMBING_LEFT_CONTINUE_1:
            {
                abilityActive = true;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
            case RABBIT_CLIMBING_LEFT_CONTINUE_2:
            {
                abilityActive = true;
                --character.y;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_BANG_HEAD:
            case RABBIT_CLIMBING_LEFT_BANG_HEAD:
            {
                character.dir = opposite( character.dir );
                return true;
            }
            default:
            {
                return false;
            }
        }
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
