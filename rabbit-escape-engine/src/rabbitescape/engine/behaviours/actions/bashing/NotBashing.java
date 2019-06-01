package rabbitescape.engine.behaviours.actions.bashing;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

public class NotBashing implements IBashingState
{
    @Override
    public State newState()
    {
        return null;
    }

    @Override
    public boolean behave(
        World world, Character character
    )
    {
        character.slopeBashHop = false;
        return false;
    }
}
