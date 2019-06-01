package rabbitescape.engine.behaviours.actions;

import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.items.ItemType.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.bashing.*;
import rabbitescape.engine.things.Character;

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

        return t.pickUpToken( bash );
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
                    setBothStates( new BashingUselesslyRightUp(), new BashingUselesslyLeftUp() );
                }
                else
                {
                    stepsOfBashing = 2;
                    setBothStates( new BashingUpRight(), new BashingUpLeft() );
                }
            }
            else if (
                t.isOnUpSlope()
             && t.blockAboveNext() == null
             && triggered
            )
            {
                setBothStates( new BashingUselesslyRightUp(), new BashingUselesslyLeftUp() );
            }
            else if ( t.blockNext() != null )
            {
                if ( t.blockNext().material == Block.Material.METAL )
                {
                    stepsOfBashing = 0;
                    setBothStates( new BashingUselesslyRight(), new BashingUselesslyLeft() );
                }
                else
                {
                    stepsOfBashing = 2;
                    setBothStates( new BashingRight(), new BashingLeft() );
                }
            }
            else if ( triggered )
            {
                setBothStates( new BashingUselesslyRight(), new BashingUselesslyLeft() );
            }

            setBashingState( t.character.dir == RIGHT ? rightState : leftState );
        } else
        {
            --stepsOfBashing;
        }

        return bashingState.newState();
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {

        return bashingState.behave( world, character );
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
