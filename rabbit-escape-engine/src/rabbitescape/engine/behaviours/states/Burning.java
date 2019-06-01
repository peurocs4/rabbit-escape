package rabbitescape.engine.behaviours.states;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.behaviours.states.burning.BurningNormal;
import rabbitescape.engine.behaviours.states.burning.BurningOnSlope;
import rabbitescape.engine.behaviours.states.burning.IBurningState;
import rabbitescape.engine.behaviours.states.burning.NotBurning;

public class Burning extends Behaviour
{

    private IBurningState burningState;

    public Burning()
    {
        this.burningState = new NotBurning();
    }

    public void setBurningState( IBurningState burningState) {
        this.burningState = burningState;
    }

    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        return world.fireAt( character.x, character.y );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered ) {
            setBurningState( new BurningNormal() );

            if (t.character.onSlope)
            {
                setBurningState( new BurningOnSlope() );
            }
        }

        return burningState.newState();
    }

    @Override
    public boolean behave(
        World world, Character character, State state
    )
    {
        return burningState.behave( world, character );
    }

    @Override
    public String toString()
    {
        return "Burning";
    }
}
