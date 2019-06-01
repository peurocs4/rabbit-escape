package rabbitescape.engine.behaviours.states;

import rabbitescape.engine.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;
import rabbitescape.engine.behaviours.states.outofbounds.IOutOfBoundsState;
import rabbitescape.engine.behaviours.states.outofbounds.NotOutOfBounds;
import rabbitescape.engine.behaviours.states.outofbounds.OutOfBoundsNormal;

public class OutOfBounds extends Behaviour
{
    private IOutOfBoundsState outOfBoundsState;

    public OutOfBounds()
    {
        this.outOfBoundsState = new NotOutOfBounds();
    }

    public void setOutOfBoundsState( IOutOfBoundsState outOfBoundsState )
    {
        this.outOfBoundsState = outOfBoundsState;
    }

    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        return (
               character.x < 0
            || character.x >= world.size.width
            || character.y < 0
            || character.y >= world.size.height
        );
    }

    @Override
    public State newState(
        BehaviourTools t, boolean triggered
    )
    {
        if ( triggered )
        {
            setOutOfBoundsState( new OutOfBoundsNormal() );
        }

        return outOfBoundsState.newState();
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        return outOfBoundsState.behave( world, character );
    }

    @Override
    public String toString()
    {
        return "OutOfBounds";
    }
}
