package rabbitescape.engine.behaviours.actions.climbing;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_CLIMBING_LEFT_BANG_HEAD;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_CLIMBING_RIGHT_BANG_HEAD;
import static rabbitescape.engine.Direction.opposite;

public class ClimbingLeftBangHead implements IClimbingState
{
    @Override
    public State newState()
    {
        return RABBIT_CLIMBING_LEFT_BANG_HEAD;
    }

    @Override
    public boolean behave(
        World world, Character character, boolean abilityActive
    )
    {
        character.dir = opposite( character.dir );
        return true;
    }
}
