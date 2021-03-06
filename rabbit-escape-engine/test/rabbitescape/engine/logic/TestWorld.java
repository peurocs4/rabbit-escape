package rabbitescape.engine.logic;

import org.junit.Test;
import rabbitescape.engine.World;
import rabbitescape.engine.World.DontStepAfterFinish;
import rabbitescape.engine.WorldStatsListener;
import rabbitescape.engine.items.BridgeItem;
import rabbitescape.engine.items.Item;
import rabbitescape.engine.items.ItemType;
import rabbitescape.engine.textworld.TextWorldManip;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.util.Position;
import rabbitescape.engine.util.WaterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_BRIDGING_RIGHT_1;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_WALKING_RIGHT;
import static rabbitescape.engine.Tools.equalTo;
import static rabbitescape.engine.World.CompletionState.*;
import static rabbitescape.engine.textworld.TextWorldManip.createWorld;
import static rabbitescape.engine.util.Util.range;

public class TestWorld
{
    @Test
    public void World_will_not_step_with_no_live_rabbits()
    {
        World world = createWorld(
            ":num_rabbits=1",
            " Q   ",
            "   O ",
            "#####"
        );

        world.step();
        world.step();
        world.step();
        world.step();  // All gone now, so

        // This step should throw
        DontStepAfterFinish exception = null;
        try
        {
            world.step();
        }
        catch( DontStepAfterFinish e )
        {
            exception = e;
        }

        assertThat( exception, notNullValue() );
    }

    @Test
    public void World_reports_when_finished_no_live_rabbits()
    {
        World world = createWorld(
            ":num_rabbits=5",
            ":rabbit_delay=5",
            " Q ",
            " O ",  // Exit right below entrance
            "###"
        );

        world.step(); // First one over the exit

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        // Fifth one over the exit: send it in
        world.step();

        // We should now be finished
        assertThat( world.completionState(), equalTo( WON ) );
    }

    @Test
    public void World_reports_when_finished_no_live_rabbits_even_if_rabbots()
    {
        World world = createWorld(
            ":num_rabbits=5",
            ":rabbit_delay=5",
            " Q    ",
            " O #y#",  // Exit right below entrance, trapped rabbot
            "######"
        );

        world.step(); // First one over the exit

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        fiveSteps( world );
        assertThat( world.completionState(), equalTo( RUNNING ) );

        // Fifth one over the exit: send it in
        world.step();

        // We should now be finished
        assertThat( world.completionState(), equalTo( WON ) );
    }

    @Test
    public void World_reports_existing_rabbits_as_out_number_out_even_if_rabbots_and_existing_rabbits()
    {
        World world = createWorld(
            "#r#y#",  // A rabbit and a rabbot
            "#####"
        );

        assertThat( world.numRabbitsOut(), equalTo( 1 ) );
    }

    @Test
    public void World_reports_existing_and_new_rabbots_as_out()
    {
        World world = createWorld(
            ":num_rabbits=2",
            ":rabbit_delay=1",
            " Q #r#",
            "   ###",
            "# ##y#",  // New rabbits from entrance, and existing ones
            "######"
        );

        assertThat( world.numRabbitsOut(), equalTo( 1 ) );
        world.step();
        assertThat( world.numRabbitsOut(), equalTo( 2 ) );
        world.step();
        assertThat( world.numRabbitsOut(), equalTo( 3 ) );
    }

    @Test
    public void World_reports_won_when_enough_rabbits_saved()
    {
        World world = createWorld(
            ":num_rabbits=0",
            ":num_saved=2",
            ":num_to_save=2",
            "   ",
            "###"
        );

        // We should now be finished
        assertThat( world.completionState(), equalTo( WON ) );
    }

    @Test
    public void World_reports_lost_when_not_enough_rabbits_saved()
    {
        World world = createWorld(
            ":num_rabbits=0",
            ":num_saved=2",
            ":num_to_save=3",
            "   ",
            "###"
        );

        // We should now be finished
        assertThat( world.completionState(), equalTo( LOST ) );
    }

    @Test
    public void Empty_world_reports_lost_immediately()
    {
        World world = createWorld(
            ":num_rabbits=0",
            ":num_saved=0",
            ":num_to_save=1",
            "   ",
            "###"
        );

        // We should now be finished
        assertThat( world.completionState(), equalTo( LOST ) );
    }

    @Test
    public void World_with_only_rabbots_reports_lost_immediately()
    {
        World world = createWorld(
            ":num_rabbits=0",
            ":num_saved=0",
            ":num_to_save=1",
            " y ",
            "###"
        );

        // We should now be finished
        assertThat( world.completionState(), equalTo( LOST ) );
    }

    @Test
    public void World_with_only_rabbots_reports_zero_rabbits_out()
    {
        World world = createWorld(
            ":num_rabbits=0",
            ":num_saved=0",
            ":num_to_save=1",
            " y ",
            "###"
        );

        // We should now be finished
        assertThat( world.numRabbitsOut(), equalTo( 0 ) );
    }

    @Test
    public void World_is_in_running_status_at_the_start()
    {
        World world = createWorld(
            ":num_rabbits=3",
            ":num_saved=2",
            ":num_to_save=3",
            "   ",
            "###"
        );

        assertThat( world.completionState(), equalTo( RUNNING ) );
    }

    @Test
    public void World_stores_number_of_abilities()
    {
        World world = createWorld(
                ":bash=5",
                "   ",
                "###"
        );

        assertThat( world.abilities.get( ItemType.bash ), equalTo( 5 ) );
    }

    @Test
    public void World_reduces_abilities_when_you_use_a_token()
    {
        World world = createWorld(
                ":bash=5",
                ":dig=3",
                ":bridge=2",
                "   ",
                "###"
        );

        // This is what we are testing
        world.changes.addToken( 0, 0, ItemType.bash );
        world.step();

        // There should be one less bash
        assertThat( world.abilities.get( ItemType.bash ), equalTo( 4 ) );

        // The dig ability was unaffected
        assertThat( world.abilities.get( ItemType.dig ), equalTo( 3 ) );

        // The bridge ability was unaffected
        assertThat( world.abilities.get( ItemType.bridge ), equalTo( 2 ) );
    }

    @Test
    public void World_refuses_to_add_a_token_if_none_left()
    {
        World world = createWorld(
                ":bash=1",
                "   ",
                "###"
        );

        // Use up the last bash
        world.changes.addToken( 0, 0, ItemType.bash );
        world.step();

        // Sanity
        assertThat( world.abilities.get( ItemType.bash ), equalTo( 0 ) );

        // This is what we are testing: can't add another
        World.UnableToAddToken caughtException = null;
        try
        {
            world.changes.addToken( 1, 0, ItemType.bash );
        }
        catch ( World.UnableToAddToken e )
        {
            caughtException = e;
        }

        assertThat( caughtException, notNullValue() );
    }

    @Test
    public void Cant_find_token_if_already_removed()
    {
        World world = createWorld(
            " i ",
            "###"
        );

        Item item = world.getItemAt( 1, 0 );

        // Sanity
        assertThat( item, is( notNullValue() ) );

        // Remove it
        world.changes.removeToken( item );

        // This is what we are testing: it's gone
        assertThat( world.getItemAt( 1, 0 ), is( nullValue() ) );

        // Sanity: after a step it's still gone
        world.step();
        assertThat( world.getItemAt( 1, 0 ), is( nullValue() ) );
    }

    @Test
    public void Can_find_token_if_there_were_2_and_only_1_is_removed()
    {
        World world = createWorld(
            " i ",
            "###"
        );

        world.things.add( new BridgeItem( 1, 0 ) );

        Item item = world.getItemAt( 1, 0 );

        // Sanity
        assertThat( item, is( notNullValue() ) );

        // Remove one item
        world.changes.removeToken( item );

        // This is what we are testing: there's another
        Item item2 = world.getItemAt( 1, 0 );
        assertThat( item2, is( notNullValue() ) );

        // Remove that one too
        world.changes.removeToken( item2 );

        // Now there's nothing left
        assertThat( world.getItemAt( 1, 0 ), is( nullValue() ) );
    }

    @Test
    public void No_rabbits_at_a_location_gives_empty_array()
    {
        World world = createWorld(
            "*i ",
            "###",
            ":*=rr"  // 2 rabbits in the same place
        );

        world.step();  // Now 1 is a bridger

        // This is what we are testing: there are no rabbits in the empty square
        Character[] rabbits = world.getCharactersAt( 2, 0 );

        assertThat( rabbits.length, equalTo( 0 ) );
    }

    @Test
    public void Can_find_all_rabbits_at_a_location()
    {
        World world = createWorld(
            "*i ",
            "###",
            ":*=rr"  // 2 rabbits in the same place
        );

        world.step();  // Now 1 is a bridger

        // This is what we are testing: ask what's in the rabbitty square
        Character[] rabbits = world.getCharactersAt( 1, 0 );

        assertThat( rabbits[0].state, equalTo( RABBIT_BRIDGING_RIGHT_1 ) );
        assertThat( rabbits[1].state, equalTo( RABBIT_WALKING_RIGHT ) );

        assertThat( rabbits.length, equalTo( 2 ) );
    }

    @Test
    public void Explode_all_rabbits_explodes_all_rabbits()
    {
        World world = createWorld(
            "#r#r#r#r#r#",
            "###########"
        );

        world.step();

        // Sanity: 5 rabbits alive
        assertThat( world.rabbits.size(), equalTo( 5 ) );

        // This is what we are testing: explode them all
        world.changes.explodeAllRabbits();
        world.step();

        // They are exploding
        assertThat(
            TextWorldManip.renderWorld( world, true, false ),
            equalTo(
                "#P#P#P#P#P#",
                "###########"
            )
        );

        // And after another time step...
        world.step();

        // ... they are dead
        assertThat( world.rabbits.size(), equalTo( 0 ) );
    }

    @Test
    public void We_are_notified_when_rabbits_are_saved()
    {
        class TrackingWorldStatsListener implements WorldStatsListener
        {
            class Call
            {
                public int num_saved;
                public int num_to_save;
            }

            public List<Call> calls = new ArrayList<Call>();

            @Override
            public void worldStats( int num_saved, int num_to_save )
            {
                Call call = new Call();
                call.num_saved = num_saved;
                call.num_to_save = num_to_save;
                calls.add( call );
            }
        }

        TrackingWorldStatsListener statsListener =
            new TrackingWorldStatsListener();

        World world = createWorld(
            statsListener,
            ":num_to_save=7",
            "r O",
            "###"
        );

        // Sanity - we provide stats when we create the world
        assertThat( statsListener.calls.size(), equalTo( 1 ) );
        assertThat( statsListener.calls.get( 0 ).num_saved,   equalTo( 0 ) );
        assertThat( statsListener.calls.get( 0 ).num_to_save, equalTo( 7 ) );

        world.step(); // Rabbit moved
        assertThat( statsListener.calls.size(), equalTo( 1 ) );

        world.step(); // Rabbit entering exit
        assertThat( statsListener.calls.size(), equalTo( 1 ) );

        world.step(); // Rabbit has entered exit
        assertThat( statsListener.calls.size(), equalTo( 2 ) );
        assertThat( statsListener.calls.get( 1 ).num_saved,   equalTo( 1 ) );
        assertThat( statsListener.calls.get( 1 ).num_to_save, equalTo( 7 ) );
    }

    @Test
    public void Water_contents_can_be_retrieved()
    {
        World world = createWorld(
            "#N#n# #",
            "#######"
        );

        Map<Position, Integer> waterContents = world.getWaterContents();
        assertThat( waterContents.get( new Position( 1, 0 ) ), equalTo( WaterUtil.MAX_CAPACITY ) );
        assertThat( waterContents.get( new Position( 3, 0 ) ), equalTo( WaterUtil.HALF_CAPACITY ) );
        // There should be no reference to the empty region.
        assertThat( waterContents.containsKey( new Position( 5, 0 ) ), equalTo( false ) );
    }

    // ---

    private void fiveSteps( World world )
    {
        for( @SuppressWarnings( "unused" ) int t : range( 5 ) )
        {
            world.step();
        }
    }
}
