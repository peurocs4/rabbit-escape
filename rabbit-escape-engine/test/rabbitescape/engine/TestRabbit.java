package rabbitescape.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static rabbitescape.engine.ChangeDescription.State.*;

import org.junit.Test;
import rabbitescape.engine.things.characters.Rabbit;
import rabbitescape.engine.things.characters.Rabbot;

public class TestRabbit
{
    @Test
    public void Rabbit_reports_state_in_lowercase()
    {
        Rabbit r = new Rabbit( 1, 1, Direction.LEFT);
        r.state = RABBIT_WALKING_LEFT;
        assertThat(r.stateName(), equalTo("rabbit_walking_left"));
    }

    @Test
    public void Rabbot_reports_state_except_it_says_rabbot()
    {
        Rabbot r = new Rabbot( 1, 1, Direction.LEFT);
        r.state = RABBIT_WALKING_LEFT;
        assertThat(r.stateName(), equalTo("rabbot_walking_left"));
    }
}
