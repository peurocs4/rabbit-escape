package rabbitescape.engine.things;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.behaviours.*;
import rabbitescape.engine.behaviours.actions.Bashing;
import rabbitescape.engine.behaviours.actions.Climbing;
import rabbitescape.engine.behaviours.actions.Digging;
import rabbitescape.engine.behaviours.states.*;

public abstract class Character extends Thing implements Comparable<Character>
{

    public final static int NOT_INDEXED = 0;
    protected final List<Behaviour> behaviours;
    protected final List<Behaviour> behavioursTriggerOrder;

    public int index = NOT_INDEXED;
    public boolean onSlope = false;
    public boolean slopeBashHop = false;

    public Direction dir;
    protected Falling falling;

    public Character( int x, int y, State state )
    {
        super( x, y, state );
        behaviours = new ArrayList<Behaviour>();
        behavioursTriggerOrder = new ArrayList<Behaviour>();
        createBehaviours();
    }

    private void createBehaviours()
    {
        Climbing climbing = new Climbing();
        Digging digging = new Digging();
        Exploding exploding = new Exploding();
        Burning burning = new Burning();
        OutOfBounds outOfBounds = new OutOfBounds();
        Drowning drowning = new Drowning();
        Exiting exiting = new Exiting();
        Brollychuting brollychuting = new Brollychuting( climbing, digging );
        falling = new Falling( climbing, brollychuting, getFatalHeight() );
        Bashing bashing = new Bashing();
        Bridging bridging = new Bridging();
        Blocking blocking = new Blocking();
        Walking walking = new Walking();
        RabbotCrash rabbotCrash = new RabbotCrash();
        RabbotWait rabbotWait = new RabbotWait();

        List<Behaviour> behavioursList = new ArrayList<Behaviour>();

        behavioursList.add( exploding );
        behavioursList.add( outOfBounds );
        behavioursList.add( burning );
        behavioursList.add( drowning );
        behavioursList.add( rabbotCrash );
        behavioursList.add( falling );
        behavioursList.add( exiting );
        behavioursList.add( brollychuting );
        behavioursList.add( climbing );
        behavioursList.add( bashing );
        behavioursList.add( digging );
        behavioursList.add( bridging );
        behavioursList.add( blocking );
        behavioursList.add( rabbotWait );
        behavioursList.add( walking );

        behavioursTriggerOrder.addAll( behavioursList );
        behaviours.addAll( behavioursList );

        assert behavioursTriggerOrder.size() == behaviours.size();
    }

    public boolean isFallingToDeath()
    {
        return falling.isFallingToDeath();
    }
    
    public abstract int getFatalHeight();

    private void cancelAllBehavioursExcept( Behaviour exception )
    {
        for ( Behaviour behaviour : behaviours )
        {
            if ( behaviour != exception )
            {
                behaviour.cancel();
            }
        }
    }

    public void possiblyUndoSlopeBashHop( World world )
    {
        if ( !slopeBashHop )
        {
            return;
        }
        BehaviourTools t = new BehaviourTools( this, world );

        final boolean isBlockAtCharacterPosition = t.blockHere() != null;
        final boolean isBlockUnderCharacterPosition = !BehaviourTools.isSlope( t.blockBelow() );

        if ( isBlockAtCharacterPosition || isBlockUnderCharacterPosition )
        {
            return;
        }
        ++y;
        slopeBashHop = false;
    }



    @Override
    public void calcNewState( World world )
    {
        for ( Behaviour behaviour : behavioursTriggerOrder )
        {
            behaviour.triggered = false;
        }

        for ( Behaviour behaviour : behavioursTriggerOrder )
        {
            behaviour.triggered = behaviour.checkTriggered( this, world );
            if ( behaviour.triggered )
            {
                cancelAllBehavioursExcept( behaviour );
            }
        }

        boolean done = false;
        for ( Behaviour behaviour : behaviours )
        {

            State thisState = behaviour.newState(
                new BehaviourTools( this, world ), behaviour.triggered );

            if ( thisState != null && !done )
            {
                state = thisState;
                done = true;
            }
        }

    }

    @Override
    public void step( World world )
    {
        for ( Behaviour behaviour : behaviours )
        {
            boolean handled = behaviour.behave( world, this, state );
            if ( handled )
            {
                break;
            }
        }
    }

    @Override
    public Map<String, String> saveState( boolean runtimeMeta )
    {
        Map<String, String> ret = new HashMap<String, String>();
        if ( !runtimeMeta )
        {
            return ret;
        }

        BehaviourState.addToStateIfGtZero( ret, "index", index );
        BehaviourState.addToStateIfTrue( ret, "onSlope", onSlope );

        for ( Behaviour behaviour : behaviours )
        {
            behaviour.saveState( ret );
        }

        return ret;
    }

    @Override
    public void restoreFromState( Map<String, String> state )
    {
        index = BehaviourState.restoreFromState( state, "index", -1 );

        onSlope = BehaviourState.restoreFromState(
            state, "onSlope", false
        );

        for ( Behaviour behaviour : behaviours )
        {
            behaviour.restoreFromState( state );
        }
    }

    @Override
    public String overlayText()
    {
        String fmt;
        switch ( dir )
        {
            case LEFT:
                fmt = "<[%d] ";
                break;
            case RIGHT:
                fmt = " [%d]>";
                break;
            default:
                throw new RuntimeException( "Rabbit should only be left or right");
        }
        return String.format( fmt, index ) ;
    }

    @Override
    public int compareTo( Character r )
    {
        return this.index - r.index;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( null == o || !( o instanceof Character ) )
        {
            return false;
        }
        return ( (Character) o ).index == this.index;
    }

    @Override
    public int hashCode()
    {
        return index;
    }

}
