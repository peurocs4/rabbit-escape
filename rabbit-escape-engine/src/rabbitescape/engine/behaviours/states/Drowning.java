package rabbitescape.engine.behaviours.states;

import static rabbitescape.engine.CellularDirection.DOWN;
import static rabbitescape.engine.CellularDirection.UP;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.behaviours.states.drowning.DrowningNormal;
import rabbitescape.engine.behaviours.states.drowning.IDrowningState;
import rabbitescape.engine.behaviours.states.drowning.NotDrowning;
import rabbitescape.engine.things.characters.Rabbot;

public class Drowning extends Behaviour
{
    private IDrowningState drowningState;

    public Drowning()
    {
        this.drowningState = new NotDrowning();
    }

    public void setDrowningState( IDrowningState drowningState )
    {
        this.drowningState = drowningState;
    }

    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        if ( character instanceof Rabbot )
        {
            return false;  // Rabbots don't drown
        }

        int yCoordinate = character.y;
        CellularDirection directionToCheck = UP;
        if ( character.onSlope )
        {
            // The character's head is at the bottom of the cell above.
            yCoordinate = character.y - 1;
            directionToCheck = DOWN;
        }
        // TODO Find out why the character's y coordinate is allowed to be
        // larger than the size of the world (see solution for easy-12).
        if ( yCoordinate < 0 || yCoordinate >= world.size.height )
        {
            return false;
        }
        for ( WaterRegion waterRegion :
              world.waterTable.getItemsAt( character.x, yCoordinate ) )
        {
            if ( waterRegion.isConnected( directionToCheck ) )
            {
                return ( waterRegion.getContents() >= waterRegion.capacity );
            }
        }
        return false;
    }

    @Override
    public State newState(
        BehaviourTools t,
        boolean triggered )
    {
        if ( triggered ) {
            setDrowningState( new DrowningNormal() );
        }

        return drowningState.newState();
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        return drowningState.behave( world, character );
    }

    @Override
    public String toString()
    {
        return "Drowning";
    }
}
