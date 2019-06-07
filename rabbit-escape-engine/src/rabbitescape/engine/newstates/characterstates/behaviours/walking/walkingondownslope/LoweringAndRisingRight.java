package rabbitescape.engine.newstates.characterstates.behaviours.walking.walkingondownslope;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.newstates.characterstates.behaviours.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_LOWERING_AND_RISING_RIGHT;

public class LoweringAndRisingRight implements IWalkingState {

    @Override
    public State getState() {
        return RABBIT_LOWERING_AND_RISING_RIGHT;
    }

    @Override
    public boolean behave(
        World world, Character character
    ) {
        ++character.x;
        character.onSlope = true;
        return true;
    }
}
