package rabbitescape.engine;

import java.util.Locale;
import java.util.Map;

import rabbitescape.engine.ChangeDescription.State;

public abstract class Thing implements ShownOnOverlay
{
    public int x;
    public int y;
    public State state;

    public Thing( int x, int y, State state )
    {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public abstract void calcNewState( World world );

    public abstract void step( World world );

    public abstract Map<String, String> saveState( boolean runtimeMeta );

    public abstract void restoreFromState( Map<String, String> state );

    public String stateName()
    {
        return state.name().toLowerCase( Locale.ENGLISH );
    }

    public abstract String overlayText();
}
