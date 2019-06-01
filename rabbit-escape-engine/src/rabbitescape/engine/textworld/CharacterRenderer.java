package rabbitescape.engine.textworld;

import static rabbitescape.engine.Direction.*;

import java.util.List;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.things.characters.Rabbit;

public class CharacterRenderer
{
    public static void render( 
        Chars chars, 
        List<Character> characters,
        boolean runtimeMeta 
    )
    {
        for ( Character character : characters )
        {
            if ( State.RABBIT_OUT_OF_BOUNDS == character.state )
            {
                continue;
            }
            chars.set(
                character.x,
                character.y,
                charForRabbit( character ),
                character.saveState( runtimeMeta )
            );
        }
    }

    private static char charForRabbit( Character character )
    {
        if ( character.dir == RIGHT )
        {
            if ( character instanceof Rabbit )
            {
                return 'r';
            }
            else
            {
                return 't';
            }
        }
        else
        {
            if ( character instanceof Rabbit)
            {
                return 'j';
            }
            else
            {
                return 'y';
            }
        }
    }
}
