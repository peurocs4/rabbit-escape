package rabbitescape.engine.newstates.characterstates.actions.bridging.bridging1;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.newstates.characterstates.actions.Bridging.*;
import rabbitescape.engine.newstates.characterstates.actions.bridging.IBridgingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BRIDGING_DOWN_UP_RIGHT_1;

public class BridgingDownUpRight1 implements IBridgingState
{
    @Override
    public State newState()
    {
        return RABBIT_BRIDGING_DOWN_UP_RIGHT_1;
    }

    @Override
    public boolean behave(
        World world, Character character, BridgeType bridgeType
    )
    {
        character.onSlope = true;
        bridgeType = BridgeType.DOWN_UP;
        return true;
    }
}