package rabbitescape.engine;

import java.util.Map;

public abstract class Environment extends Thing
{
    public Environment( int x, int y, ChangeDescription.State state )
    {
        super( x, y, state );
    }

    @Override
    public void calcNewState( World world )
    {

    }

    @Override
    public void step( World world )
    {

    }

    @Override
    public Map<String, String> saveState( boolean runtimeMeta )
    {
        return null;
    }

    @Override
    public void restoreFromState( Map<String, String> state )
    {

    }

    @Override
    public String stateName()
    {
        return super.stateName();
    }

    @Override
    public String overlayText()
    {
        return null;
    }
}
