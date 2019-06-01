package rabbitescape.engine.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.actions.Digging;
import rabbitescape.engine.behaviours.states.Blocking;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.things.characters.Rabbit;
import rabbitescape.engine.things.characters.Rabbot;

public class RabbotWait extends Behaviour
{
    private boolean within1Vertically( Character otherRabbit, Character rabbit )
    {
        return ( Math.abs( otherRabbit.y - rabbit.y ) < 2 );
    }

    private boolean noseToNose( Character otherRabbit, Character rabbit )
    {
        if ( 
            otherRabbit.x == rabbit.x - 1 &&
            otherRabbit.dir == Direction.RIGHT &&
            rabbit.dir == Direction.LEFT 
        )
        {
            return true;
        }
        else if ( 
            otherRabbit.x == rabbit.x + 1 &&
            otherRabbit.dir == Direction.LEFT &&
            rabbit.dir == Direction.RIGHT 
        )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        if (
            character instanceof Rabbot &&
            !Blocking.isBlocking(character.state) &&
            !Digging.isDigging(character.state)
        )
        {
            for ( Character otherCharacter : world.rabbits )
            {
                if (
                    otherCharacter instanceof Rabbit &&
                    within1Vertically( otherCharacter, character ) &&
                    noseToNose( otherCharacter, character ) &&
                    !Blocking.isBlocking(otherCharacter.state)
                )
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            return t.rl( 
                State.RABBIT_WAITING_RIGHT,
                State.RABBIT_WAITING_LEFT 
            );
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        if ( 
            state == State.RABBIT_WAITING_LEFT ||
            state == State.RABBIT_WAITING_RIGHT 
        )
        {
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "RabbotWait";
    }
}
