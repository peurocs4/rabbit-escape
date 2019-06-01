package rabbitescape.engine;

import java.util.Map;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;

public abstract class Behaviour
{
    public boolean triggered;

    /**
     * Subclasses examine the character's situation using BehaviourTools and
     * return the state (see ChangeDescription) for the next time step.
     * This method may return null indicating that a different Behaviour
     * must take over.
     *
     * Note that the state determines the animation used.
     */
    public abstract State newState( BehaviourTools t, boolean triggered );

    /**
     * Move the character in the world. Kill it, or record its safe exit.
     *
     * @return true if this behaviour has done all the work needed for
     *         this time step
     */
    public abstract boolean behave( World world, Character character, State state );

    /**
     * Examine the character's situation and return true if this Behaviour must
     * take control.
     */
    public abstract boolean checkTriggered( Character character, World world );

    public abstract void cancel();

    public void saveState( Map<String, String> saveState )
    {
    }

    public void restoreFromState( Map<String, String> saveState )
    {
    }

    public abstract String toString();
}
