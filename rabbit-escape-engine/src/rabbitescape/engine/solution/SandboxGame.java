package rabbitescape.engine.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import rabbitescape.engine.things.Character;
import rabbitescape.engine.Entrance;
import rabbitescape.engine.Exit;
import rabbitescape.engine.Fire;
import rabbitescape.engine.IgnoreWorldStatsListener;
import rabbitescape.engine.Pipe;
import rabbitescape.engine.things.characters.Rabbit;
import rabbitescape.engine.Thing;
import rabbitescape.engine.items.Item;
import rabbitescape.engine.VoidMarkerStyle;
import rabbitescape.engine.World;
import rabbitescape.engine.items.ItemType;
import rabbitescape.engine.textworld.Comment;

/**
 * A completely sandboxed game that can be edited and have solutions run against
 * without affecting the real game.
 */
public class SandboxGame
{
    /**
     * The token type that is currently 'selected' by whatever is interacting
     * with the sandbox.
     */
    private ItemType selectedType = null;
    /** The world object that is contained in the game. */
    private final World world;

    /**
     * Create a sandbox game based on a given world. This allows playing with
     * the sandbox game without affecting the world in any way.
     *
     * @param world
     *            The world to copy.
     */
    public SandboxGame( World world )
    {
        List<Character> clonedRabbits = makeClonedRabbits( world.rabbits );
        List<Thing> clonedThings = makeClonedThings( world.things );
        this.world = new World( world.size,
            world.blockTable.getListCopy(),
            clonedRabbits,
            clonedThings,
            world.getWaterContents(),
            new HashMap<>( world.abilities ),
            world.name,
            world.description,
            world.author_name,
            world.author_url,
            Arrays.copyOf( world.hints, world.hints.length ),
            Arrays.copyOf( world.solutions, world.solutions.length ),
            world.num_rabbits,
            world.num_to_save,
            world.rabbit_delay,
            world.music,
            world.num_saved,
            world.num_killed,
            world.num_waiting,
            world.getRabbitIndexCount(),
            world.paused,
            new Comment[] {},
            new IgnoreWorldStatsListener(),
            VoidMarkerStyle.Style.HIGHLIGHTER
        );
    }

    /**
     * Create a clone of the list of a given list of things.
     *
     * @param things
     *            The things to clone.
     * @return The cloned list of things.
     */
    private List<Thing> makeClonedThings( List<Thing> things )
    {
        List<Thing> clonedThings = new ArrayList<>();
        for ( Thing thing : things )
        {
            if ( thing instanceof Entrance )
            {
                clonedThings.add( new Entrance( thing.x, thing.y ) );
            }
            else if ( thing instanceof Exit )
            {
                clonedThings.add( new Exit( thing.x, thing.y ) );
            }
            else if ( thing instanceof Rabbit )
            {
                Rabbit rabbit = (Rabbit)thing;
                clonedThings.add( cloneRabbit( rabbit ) );
            }
            else if ( thing instanceof Item )
            {
                Item item = (Item)thing;
                clonedThings.add( item.copyWithoutState() );
            }
            else if ( thing instanceof Fire )
            {
                Fire fire = (Fire)thing;
                clonedThings.add( new Fire( fire.x, fire.y, fire.variant ) );
            }
            else if ( thing instanceof Pipe )
            {
                Pipe pipe = (Pipe)thing;
                clonedThings.add( new Pipe( pipe.x, pipe.y ) );
            }
            else
            {
                // We've created a new type of Thing, but haven't updated the
                // code here to cope with it.
                throw new IllegalStateException( "Unrecognised type of Thing: "
                    + thing );
            }
        }
        return clonedThings;
    }

    /**
     * Make a clone of a list of rabbits.
     *
     * @param characters
     *            The list of rabbits to clone.
     * @return The cloned list.
     */
    private List<Character> makeClonedRabbits( List<Character> characters )
    {
        List<Character> clonedCharacters = new ArrayList<>();
        for ( Character character : characters )
        {
            clonedCharacters.add( cloneRabbit( character ) );
        }
        return clonedCharacters;
    }

    /**
     * Clone a single character.
     *
     * @param character
     *            The character to be cloned.
     * @return The cloned character.
     */
    private Rabbit cloneRabbit( Character character )
    {
        return new Rabbit( character.x, character.y, character.dir);
    }

    /**
     * Get the currently selected token type.
     *
     * @return The token type selected.
     */
    public ItemType getSelectedType()
    {
        return selectedType;
    }

    /**
     * Select a token type to place next.
     *
     * @param selectedType
     *            The type to select.
     */
    public void setSelectedType( ItemType selectedType )
    {
        this.selectedType = selectedType;
    }

    /**
     * Get the current world.
     *
     * @return The sandboxed world.
     */
    public World getWorld()
    {
        return world;
    }
}
