package rabbitescape.engine.behaviours;

import rabbitescape.engine.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.items.ExplodeItem;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_EXPLODING;

public class Exploding extends Behaviour
{
    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        BehaviourTools t = new BehaviourTools( character, world );
        return t.pickUpItem( ExplodeItem.TYPE, true );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            return RABBIT_EXPLODING;
        }
        return null;
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        if ( state == RABBIT_EXPLODING )
        {
            world.changes.killRabbit( character );
            return true;
        }

        return false;
    }

    @Override
    public String toString()
    {
        return "Exploding";
    }
}
