package rabbitescape.engine.things.characters;

import rabbitescape.engine.Direction;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.*;

public class Rabbot extends Character
{
    String name = "rabbot";

    public Rabbot(int x,int y, Direction dir)
    {
        super( x, y, RABBIT_WALKING_LEFT );
        this.dir = dir;
    }

    @Override
    public int getFatalHeight() { return 5; }

    @Override
    public String stateName()
    {
        String normalName = super.stateName();
        return normalName.replaceFirst("^rabbit", name.toLowerCase() );
    }
}
