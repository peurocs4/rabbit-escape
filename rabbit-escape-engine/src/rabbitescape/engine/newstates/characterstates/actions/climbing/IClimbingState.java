package rabbitescape.engine.newstates.characterstates.actions.climbing;

import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Character;

public interface IClimbingState
{
    public State newState(
        BehaviourTools t,
        boolean abilityActive,
        IClimbingState climbingState
    );

    public boolean behave( World world, Character character, boolean abilityActive );
}
