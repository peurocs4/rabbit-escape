package rabbitescape.engine.behaviours.actions;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.bashing.IBashingState;
import rabbitescape.engine.behaviours.actions.bashing.NotBashing;
import rabbitescape.engine.items.BashItem;
import rabbitescape.engine.things.Character;

import java.util.Map;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.RIGHT;

public class Bashing extends Behaviour
{
    private IBashingState bashingState, rightState, leftState;
    private int stepsOfBashing;

    public Bashing()
    {
        this.bashingState = new NotBashing();
    }

    public void setBashingState( IBashingState bashingState )
    {
        this.bashingState = bashingState;
    }

    public void setRightState( IBashingState rightState )
    {
        this.rightState = rightState;
    }

    public void setLeftState( IBashingState leftState )
    {
        this.leftState = leftState;
    }

    public void setBothStates( IBashingState rightState, IBashingState leftState )
    {
        setRightState( rightState );
        setLeftState( leftState );
    }

    @Override
    public void cancel()
    {
        stepsOfBashing = 0;
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        BehaviourTools t = new BehaviourTools( character, world );

        return t.pickUpItem( BashItem.TYPE );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered || stepsOfBashing > 0 )
        {
            if (
                t.isOnUpSlope()
                    && t.blockAboveNext() != null
            )
            {
                if (t.blockAboveNext().material == Block.Material.METAL)
                {
                    stepsOfBashing = 0;
                    return t.rl(
                        RABBIT_BASHING_USELESSLY_RIGHT_UP,
                        RABBIT_BASHING_USELESSLY_LEFT_UP
                    );
                }
                else
                {
                    stepsOfBashing = 2;
                    return t.rl(
                        RABBIT_BASHING_UP_RIGHT,
                        RABBIT_BASHING_UP_LEFT
                    );
                }
            }
            else if (
                t.isOnUpSlope()
                    && t.blockAboveNext() == null
                    && triggered
            )
            {
                return t.rl(
                    RABBIT_BASHING_USELESSLY_RIGHT_UP,
                    RABBIT_BASHING_USELESSLY_LEFT_UP
                );
            }
            else if ( t.blockNext() != null )
            {
                if ( t.blockNext().material == Block.Material.METAL )
                {
                    stepsOfBashing = 0;
                    return t.rl(
                        RABBIT_BASHING_USELESSLY_RIGHT,
                        RABBIT_BASHING_USELESSLY_LEFT
                    );
                }
                else
                {
                    stepsOfBashing = 2;
                    return t.rl(
                        RABBIT_BASHING_RIGHT,
                        RABBIT_BASHING_LEFT
                    );
                }
            }
            else if ( triggered )
            {
                return t.rl(
                    RABBIT_BASHING_USELESSLY_RIGHT,
                    RABBIT_BASHING_USELESSLY_LEFT
                );
            }
        }
        --stepsOfBashing;
        return null;
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        switch ( state )
        {
            case RABBIT_BASHING_RIGHT:
            case RABBIT_BASHING_LEFT:
            {
                character.slopeBashHop = false;
                world.changes.removeBlockAt( destX( character ), character.y );
                return true;
            }
            case RABBIT_BASHING_UP_RIGHT:
            case RABBIT_BASHING_UP_LEFT:
            {
                world.changes.removeBlockAt( destX( character ), character.y - 1 );
                character.slopeBashHop = true;
                character.y -= 1;
                return true;
            }
            case RABBIT_BASHING_USELESSLY_RIGHT:
            case RABBIT_BASHING_USELESSLY_LEFT:
            {
                character.slopeBashHop = false;
                return true;
            }
            case RABBIT_BASHING_USELESSLY_RIGHT_UP:
            case RABBIT_BASHING_USELESSLY_LEFT_UP:
            {
                character.slopeBashHop = true;
                character.y -= 1;
                return true;
            }
            default:
            {
                character.slopeBashHop = false;
                return false;
            }
        }
    }

    private int destX( Character character )
    {
        return ( character.dir == RIGHT ) ? character.x + 1 : character.x - 1;
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfGtZero(
            saveState, "Bashing.stepsOfBashing", stepsOfBashing
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        stepsOfBashing = BehaviourState.restoreFromState(
            saveState, "Bashing.stepsOfBashing", stepsOfBashing
        );

        if ( stepsOfBashing > 0 )
        {
            ++stepsOfBashing;
        }
    }

    @Override
    public String toString()
    {
        return "bashing";
    }
}
