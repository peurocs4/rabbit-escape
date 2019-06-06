package rabbitescape.engine.newstates.characterstates.actions.climbing;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.*;

public class ClimbingLeftContinue2 implements IClimbingState
{
    @Override
    public State newState( BehaviourTools t, boolean abilityActive, IClimbingState climbingState )
    {
        Block aboveBlock = t.blockAbove();

        if ( t.isRoof( aboveBlock ) )
        {
            abilityActive = false;
            climbingState = new ClimbingLeftBangHead();
            return RABBIT_CLIMBING_LEFT_BANG_HEAD;
        }

        Block endBlock = t.blockAboveNext();

        if ( t.isWall( endBlock ) )
        {
            climbingState = new ClimbingLeftContinue1();
            return RABBIT_CLIMBING_LEFT_CONTINUE_1;
        }
        else
        {
            climbingState = new ClimbingLeftEnd();
            return RABBIT_CLIMBING_LEFT_END;
        }
    }

    @Override
    public boolean behave(
        World world, Character character, boolean abilityActive
    )
    {
        abilityActive = true;
        --character.y;
        return true;
    }
}
