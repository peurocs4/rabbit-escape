package rabbitescape.engine;

import rabbitescape.engine.things.Character;
import rabbitescape.engine.util.Position;

public class BridgeTools
{

    public static boolean someoneIsBridgingAt( World world, int x, int y )
    {
        for ( Character character : world.rabbits )
        {
            if ( rabbitIsBridgingAt( character, x, y ) )
            {
                return true;
            }
        }
        return false;
    }

    public static boolean rabbitIsBridgingAt( Character character, int x, int y )
    {
        Position bridging = RabbitStates.whereBridging(
            new StateAndPosition( character.state, character.x, character.y ) );

        if ( bridging == null )
        {
            return false;
        }
        else
        {
            return ( bridging.x == x && bridging.y == y );
        }
    }

}
