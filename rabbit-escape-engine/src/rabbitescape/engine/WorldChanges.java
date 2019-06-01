package rabbitescape.engine;

import java.util.ArrayList;
import java.util.List;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World.CantAddTokenOutsideWorld;
import rabbitescape.engine.World.NoBlockFound;
import rabbitescape.engine.World.NoSuchAbilityInThisWorld;
import rabbitescape.engine.World.NoneOfThisAbilityLeft;
import rabbitescape.engine.World.UnableToAddToken;
import rabbitescape.engine.items.Item;
import rabbitescape.engine.items.ItemFactory;
import rabbitescape.engine.items.ItemType;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.things.characters.Rabbit;
import rabbitescape.engine.util.Position;

public class WorldChanges
{

    private final World world;
    public final WorldStatsListener statsListener;

    private final List<Character> charactersToEnter = new ArrayList<Character>();
    private final List<Character> charactersToKill = new ArrayList<Character>();
    private final List<Character> charactersToSave = new ArrayList<Character>();
    private final List<Item>  tokensToAdd    = new ArrayList<Item>();
    public  final List<Item>  tokensToRemove = new ArrayList<Item>();
    public  final List<Fire>   fireToRemove   = new ArrayList<Fire>();
    private final List<Block>  blocksToAdd    = new ArrayList<Block>();
    private final List<Block>  blocksToRemove = new ArrayList<Block>();
    public final List<Position>   blocksJustRemoved = new ArrayList<Position>();
    private final List<Position>  waterPointsToRecalculate = new ArrayList<>();

    private boolean explodeAll = false;

    private List<Character> rabbitsJustEntered = new ArrayList<Character>();

    public WorldChanges( World world, WorldStatsListener statsListener )
    {
        this.world = world;
        this.statsListener = statsListener;
        updateStats();
    }

    public synchronized void apply()
    {
        // Add any new things
        for ( Character rabbit : charactersToEnter )
        {
            rabbit.calcNewState( world );
        }
        world.rabbits.addAll( charactersToEnter );
        world.things.addAll( tokensToAdd );
        world.blockTable.addAll( blocksToAdd );

        // Remove dead/saved rabbits, used tokens, dug out blocks
        world.rabbits.removeAll( charactersToKill );
        world.rabbits.removeAll( charactersToSave );
        world.things.removeAll(  tokensToRemove );
        world.things.removeAll( fireToRemove );
        world.blockTable.removeAll(  blocksToRemove );

        for ( Position point : waterPointsToRecalculate )
        {
            world.recalculateWaterRegions( point );
        }

        if ( charactersToSave.size() > 0 )
        {
            updateStats();
        }

        charactersToEnter.clear();
        charactersToKill.clear();
        charactersToSave.clear();
        tokensToAdd.clear();
        tokensToRemove.clear();
        fireToRemove.clear();
        blocksToAdd.clear();
        blocksToRemove.clear();
        waterPointsToRecalculate.clear();

        if ( explodeAll )
        {
            doExplodeAll();
        }
    }

    private void updateStats()
    {
        statsListener.worldStats( world.num_saved, world.num_to_save );
    }

    private void doExplodeAll()
    {
        world.num_waiting = 0;
        for ( Character rabbit : world.rabbits )
        {
            rabbit.state = State.RABBIT_EXPLODING;
        }
    }

    public synchronized void revert()
    {
        revertEnterRabbits();
        revertKillRabbits();
        revertSaveRabbits();
        revertAddTokens();
        tokensToRemove.clear();
        blocksToAdd.clear();
        blocksToRemove.clear();
        waterPointsToRecalculate.clear();
    }

    private synchronized void revertEnterRabbits()
    {
        world.num_waiting += charactersToEnter.size();
        charactersToEnter.clear();
    }

    public synchronized void enterRabbit( Character character )
    {
        --world.num_waiting;
        charactersToEnter.add( character );
    }

    private synchronized void revertKillRabbits()
    {
        for ( Character character : charactersToKill )
        {
            if ( character instanceof Rabbit )
            {
                --world.num_killed;
            }
        }
        charactersToKill.clear();
    }

    public synchronized void killRabbit( Character character )
    {
        if ( character instanceof Rabbit )
        {
            ++world.num_killed;
        }
        charactersToKill.add( character );
    }

    private void revertSaveRabbits()
    {
        world.num_saved -= charactersToSave.size();
        charactersToSave.clear();
    }

    public synchronized void saveRabbit( Character character )
    {
        ++world.num_saved;
        charactersToSave.add( character );
    }

    private synchronized void revertAddTokens()
    {
        for ( Item t : tokensToAdd )
        {
            world.abilities.put( t.getType(), world.abilities.get( t.getType() ) + 1 );
        }
        tokensToAdd.clear();
    }

    public synchronized void addToken( int x, int y, ItemType type )
    throws UnableToAddToken
    {
        Integer numLeft = world.abilities.get( type );

        if ( numLeft == null )
        {
            throw new NoSuchAbilityInThisWorld( type );
        }

        if ( numLeft == 0 )
        {
            throw new NoneOfThisAbilityLeft( type );
        }

        if ( x < 0 || y < 0 || x >= world.size.width || y >= world.size.height )
        {
            throw new CantAddTokenOutsideWorld( type, x, y, world.size );
        }

        Block block = world.getBlockAt( x, y );
        if ( BehaviourTools.s_isFlat( block ) )
        {
            return;
        }

        tokensToAdd.add( ItemFactory.createItem( x, y, type, world ) );
        world.abilities.put( type, numLeft - 1 );
    }

    public synchronized void removeToken( Item thing )
    {
        tokensToRemove.add( thing );
    }

    public synchronized void removeFire( Fire thing )
    {
        fireToRemove.add( thing );
    }

    public synchronized void addBlock( Block block )
    {
        blocksToAdd.add( block );
        waterPointsToRecalculate.add( new Position( block.x, block.y ) );
    }

    public synchronized void removeBlockAt( int x, int y )
    {
        Block block = world.getBlockAt( x, y );
        if ( block == null )
        {
            throw new NoBlockFound( x, y );
        }
        blocksJustRemoved.add( new Position( x, y ) );
        blocksToRemove.add( block );
        waterPointsToRecalculate.add( new Position( x, y ) );
    }

    public synchronized List<Thing> tokensAboutToAppear()
    {
        return new ArrayList<Thing>( tokensToAdd );
    }

    public synchronized void explodeAllRabbits()
    {
        explodeAll = true;
    }

    public List<Character> rabbitsJustEntered()
    {
        return rabbitsJustEntered;
    }

    public void rememberWhatWillHappen()
    {
        rabbitsJustEntered = new ArrayList<Character>( charactersToEnter );
    }
}
