package rabbitescape.engine.logic;

import static rabbitescape.engine.util.WorldAssertions.*;

import org.junit.Test;

public class TestBridging
{
    @Test
    public void Bridge_on_the_flat()
    {
        assertWorldEvolvesLike(
            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            " ri         ij " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "  rB       Ej  " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "  r[       ]j  " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "  r{       }j  " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "    B     E    " + "\n" +
            "   r       j   " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "    [     ]    " + "\n" +
            "   r       j   " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "               " + "\n" +
            "    {     }    " + "\n" +
            "   r       j   " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "     B   E     " + "\n" +
            "    r     j    " + "\n" +
            "   (       )   " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "     [   ]     " + "\n" +
            "    r     j    " + "\n" +
            "   (       )   " + "\n" +
            "###############",

            "               " + "\n" +
            "               " + "\n" +
            "     {   }     " + "\n" +
            "    r     j    " + "\n" +
            "   (       )   " + "\n" +
            "###############",

            "               " + "\n" +
            "      ' !      " + "\n" +
            "     r   j     " + "\n" +
            "    (     )    " + "\n" +
            "   (       )   " + "\n" +
            "###############",

            "               " + "\n" +
            "      r j      " + "\n" +
            "     (f f)     " + "\n" +
            "    ( f f )    " + "\n" +
            "   (       )   " + "\n" +
            "###############"
        );
    }

    @Test
    public void Bridge_on_an_up_slope()
    {
        assertWorldEvolvesLike(
            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "  i           i  " + "\n" +
            "r /           \\ j" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            " r~           `j " + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "   B         E   " + "\n" +
            "  r           j  " + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "   [         ]   " + "\n" +
            "  r           j  " + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "   {         }   " + "\n" +
            "  r           j  " + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "    B       E    " + "\n" +
            "   r         j   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "    [       ]    " + "\n" +
            "   r         j   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "    {       }    " + "\n" +
            "   r         j   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "                 " + "\n" +
            "     B     E     " + "\n" +
            "    r       j    " + "\n" +
            "   (         )   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "                 " + "\n" +
            "     [     ]     " + "\n" +
            "    r       j    " + "\n" +
            "   (         )   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "                 " + "\n" +
            "     {     }     " + "\n" +
            "    r       j    " + "\n" +
            "   (         )   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "      '   !      " + "\n" +
            "     r     j     " + "\n" +
            "    (       )    " + "\n" +
            "   (         )   " + "\n" +
            "  /           \\  " + "\n" +
            "#################",

            "      r   j      " + "\n" +
            "     (f   f)     " + "\n" +
            "    ( f   f )    " + "\n" +
            "   (         )   " + "\n" +
            "  /           \\  " + "\n" +
            "#################"
        );
    }

    @Test
    public void Bridge_on_a_down_slope()
    {
        assertWorldEvolvesLike(
            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "ri             ij" + "\n" +
            "#\\             /#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "#rB           Ej#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "#r[           ]j#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "#r{           }j#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "   B         E   " + "\n" +
            "#\\r           j/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "   [         ]   " + "\n" +
            "#\\r           j/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "                 " + "\n" +
            "   {         }   " + "\n" +
            "#\\r           j/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "    B       E    " + "\n" +
            "   r         j   " + "\n" +
            "#\\(           )/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "    [       ]    " + "\n" +
            "   r         j   " + "\n" +
            "#\\(           )/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "                 " + "\n" +
            "    {       }    " + "\n" +
            "   r         j   " + "\n" +
            "#\\(           )/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "     '     !     " + "\n" +
            "    r       j    " + "\n" +
            "   (         )   " + "\n" +
            "#\\(           )/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################",

            "                 " + "\n" +
            "     r     j     " + "\n" +
            "    (f     f)    " + "\n" +
            "   ( f     f )   " + "\n" +
            "#\\(           )/#" + "\n" +
            "##\\           /##" + "\n" +
            "#################"
        );
    }

    @Test
    public void Stop_bridging_when_hit_a_wall()
    {
        assertWorldEvolvesLike(
            "#          #" + "\n" +
            "# ij    ri #" + "\n" +
            "############",

            "#          #" + "\n" +
            "#Ej      rB#" + "\n" +
            "############",

            "#          #" + "\n" +
            "#]j      r[#" + "\n" +
            "############",

            "#          #" + "\n" +
            "#}j      r{#" + "\n" +
            "############",

            "#          #" + "\n" +
            "#|        ?#" + "\n" +
            "############",

            "#          #" + "\n" +
            "#r_      +j#" + "\n" +
            "############",

            "#          #" + "\n" +
            "#)r>    <j(#" + "\n" +
            "############"
        );
    }

    @Test
    public void Dont_stop_bridging_when_2_above_head_at_start()
    {
        assertWorldEvolvesLike(
            "# #      # #" + "\n" +
            "#          #" + "\n" +
            "# ij    ri #" + "\n" +
            "############",

            "# #      # #" + "\n" +
            "#          #" + "\n" +
            "#Ej      rB#" + "\n" +
            "############"
        );
    }

    @Test
    public void Stop_bridging_when_hit_head_here_at_start()
    {
        assertWorldEvolvesLike(
            "# #      # #" + "\n" +
            "# ij    ri #" + "\n" +
            "############",

            "# #      # #" + "\n" +
            "#<j      r>#" + "\n" +
            "############"
        );
    }

    @Test
    public void Stop_bridging_when_hit_head_in_front_at_start()
    {
        assertWorldEvolvesLike(
            "##        ##" + "\n" +
            "# ij    ri #" + "\n" +
            "############",

            "##        ##" + "\n" +
            "#<j      r>#" + "\n" +
            "############"
        );
    }

    @Test
    public void Stop_bridging_when_hit_head_here_later()
    {
        assertWorldEvolvesLike(
            "#  #    #  #" + "\n" +
            "#          #" + "\n" +
            "#   ijri   #" + "\n" +
            "############",

            "#  #    #  #" + "\n" +
            "#          #" + "\n" +
            "#  Ej  rB  #" + "\n" +
            "############",

            "#  #    #  #" + "\n" +
            "#          #" + "\n" +
            "#  ]j  r[  #" + "\n" +
            "############",

            "#  #    #  #" + "\n" +
            "#          #" + "\n" +
            "#  }j  r{  #" + "\n" +
            "############",

            "#  #    #  #" + "\n" +
            "# !      ' #" + "\n" +
            "#  j    r  #" + "\n" +
            "############"
        );
    }

    @Test
    public void Stop_bridging_when_hit_head_in_front_later()
    {
        assertWorldEvolvesLike(
            "# #      # #" + "\n" +
            "#          #" + "\n" +
            "#   ijri   #" + "\n" +
            "############",

            "# #      # #" + "\n" +
            "#          #" + "\n" +
            "#  Ej  rB  #" + "\n" +
            "############",

            "# #      # #" + "\n" +
            "#          #" + "\n" +
            "#  ]j  r[  #" + "\n" +
            "############",

            "# #      # #" + "\n" +
            "#          #" + "\n" +
            "#  }j  r{  #" + "\n" +
            "############",

            "# #      # #" + "\n" +
            "# !      ' #" + "\n" +
            "#  j    r  #" + "\n" +
            "############"
        );
    }

    // TODO: Stop_bridging_when_hit_a_ceiling

    @Test
    public void Stop_bridging_when_skim_a_wall_top()
    {
        assertWorldEvolvesLike(
            "            " + "\n" +
            "#          #" + "\n" +
            "#  ij  ri  #" + "\n" +
            "############",

            "            " + "\n" +
            "#          #" + "\n" +
            "# Ej    rB #" + "\n" +
            "############",

            "            " + "\n" +
            "#          #" + "\n" +
            "# ]j    r[ #" + "\n" +
            "############",

            "            " + "\n" +
            "#          #" + "\n" +
            "# }j    r{ #" + "\n" +
            "############",

            "            " + "\n" +
            "#E        B#" + "\n" +
            "# j      r #" + "\n" +
            "############",

            "            " + "\n" +
            "#]        [#" + "\n" +
            "# j      r #" + "\n" +
            "############",

            "            " + "\n" +
            "#}        {#" + "\n" +
            "# j      r #" + "\n" +
            "############",

            "!          '" + "\n" +
            "#j        r#" + "\n" +
            "# )      ( #" + "\n" +
            "############"
        );
    }

    @Test
    public void Stop_bridging_when_join_opposite_bridge_long()
    {
        assertWorldEvolvesLike(
            "#   (   )   #" + "\n" +
            "#  (     )  #" + "\n" +
            "# ( ij ri ) #" + "\n" +
            "#############",

            "#   (   )   #" + "\n" +
            "#  (     )  #" + "\n" +
            "# (Ej   rB) #" + "\n" +
            "#############",

            "#   (   )   #" + "\n" +
            "#  (     )  #" + "\n" +
            "# (]j   r[) #" + "\n" +
            "#############",

            "#   (   )   #" + "\n" +
            "#  (     )  #" + "\n" +
            "# (}j   r{) #" + "\n" +
            "#############",

            "#   (   )   #" + "\n" +
            "#  (     )  #" + "\n" +
            "# *j     r& #" + "\n" +
            "#############"
        );
    }

    @Test
    public void Stop_bridging_and_turn_when_hit_back_slope()
    {
        assertWorldEvolvesLike(
            "#   /   \\   #" + "\n" +
            "#  /     \\  #" + "\n" +
            "# / ij ri \\ #" + "\n" +
            "#############",

            "#   /   \\   #" + "\n" +
            "#  /     \\  #" + "\n" +
            "# /<j   r>\\ #" + "\n" +
            "#############",

            "#   /   \\   #" + "\n" +
            "#  /     \\  #" + "\n" +
            "# /|     ?\\ #" + "\n" +
            "#############"
        );
    }

    @Test
    public void Stop_bridging_and_turn_when_hit_back_slope_later()
    {
        assertWorldEvolvesLike(
            "#   /       \\   #" + "\n" +
            "#  /         \\  #" + "\n" +
            "# /   ij ri   \\ #" + "\n" +
            "#################",

            "#   /       \\   #" + "\n" +
            "#  /         \\  #" + "\n" +
            "# /  Ej   rB  \\ #" + "\n" +
            "#################",

            "#   /       \\   #" + "\n" +
            "#  /         \\  #" + "\n" +
            "# /  ]j   r[  \\ #" + "\n" +
            "#################",

            "#   /       \\   #" + "\n" +
            "#  /         \\  #" + "\n" +
            "# /  }j   r{  \\ #" + "\n" +
            "#################",

            "#   /       \\   #" + "\n" +
            "#  /!       '\\  #" + "\n" +
            "# /  j     r  \\ #" + "\n" +
            "#################"
        );
    }

    @Test
    public void Stop_bridging_when_join_opposite_bridge_short()
    {
        assertWorldEvolvesLike(
            "#           #" + "\n" +
            "# ( ij ri ) #" + "\n" +
            "#############",

            "#           #" + "\n" +
            "# (Ej   rB) #" + "\n" +
            "#############",

            "#           #" + "\n" +
            "# (]j   r[) #" + "\n" +
            "#############",

            "#           #" + "\n" +
            "# (}j   r{) #" + "\n" +
            "#############",

            "#           #" + "\n" +
            "# *j     r& #" + "\n" +
            "#############"
        );
    }

    @Test
    public void Stop_bridging_when_join_opposite_bridge_long_later()
    {
        assertWorldEvolvesLike(
            "#   (       )   #" + "\n" +
            "#  (         )  #" + "\n" +
            "# (   ij ri   ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  (         )  #" + "\n" +
            "# (  Ej   rB  ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  (         )  #" + "\n" +
            "# (  ]j   r[  ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  (         )  #" + "\n" +
            "# (  }j   r{  ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  (E       B)  #" + "\n" +
            "# (  j     r  ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  (]       [)  #" + "\n" +
            "# (  j     r  ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  (}       {)  #" + "\n" +
            "# (  j     r  ) #" + "\n" +
            "#################",

            "#   (       )   #" + "\n" +
            "#  *j       r&  #" + "\n" +
            "# (  )     (  ) #" + "\n" +
            "#################"
        );
    }

    @Test
    public void Stop_bridging_when_offset_join_opposite_bridge_long_later()
    {
        assertWorldEvolvesLike(
            "#   (    )   #" + "\n" +
            "#  (      )  #" + "\n" +
            "# (  ijri  ) #" + "\n" +
            "##############",

            "#   (    )   #" + "\n" +
            "#  (      )  #" + "\n" +
            "# ( Ej  rB ) #" + "\n" +
            "##############",

            "#   (    )   #" + "\n" +
            "#  (      )  #" + "\n" +
            "# ( ]j  r[ ) #" + "\n" +
            "##############",

            "#   (    )   #" + "\n" +
            "#  (      )  #" + "\n" +
            "# ( }j  r{ ) #" + "\n" +
            "##############",

            // Build a bridge and leave an annoying gap
            "#   (    )   #" + "\n" +
            "#  !      '  #" + "\n" +
            "# ( j    r ) #" + "\n" +
            "##############",

            "#   (    )   #" + "\n" +
            "#  j      r  #" + "\n" +
            "# % )    ( @ #" + "\n" +
            "##############"
        );
    }

    @Test
    public void Stop_bridging_when_join_opposite_bridge_short_later()
    {
        assertWorldEvolvesLike(
            "#              #" + "\n" +
            "#  (        )  #" + "\n" +
            "# (   ijri   ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  (        )  #" + "\n" +
            "# (  Ej  rB  ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  (        )  #" + "\n" +
            "# (  ]j  r[  ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  (        )  #" + "\n" +
            "# (  }j  r{  ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  (E      B)  #" + "\n" +
            "# (  j    r  ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  (]      [)  #" + "\n" +
            "# (  j    r  ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  (}      {)  #" + "\n" +
            "# (  j    r  ) #" + "\n" +
            "################",

            "#              #" + "\n" +
            "#  *j      r&  #" + "\n" +
            "# (  )    (  ) #" + "\n" +
            "################"
        );
    }
}
