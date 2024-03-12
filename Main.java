import Engine.Data.Node;
import Engine.Data.Structure.Action;
import Engine.Data.Structure.Container;
import Engine.SearchEngine;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Container arr[][] = {
                {Container.VACANT,Container.VACANT,Container.VACANT,Container.VACANT},
                {Container.POINTER,Container.WALL,Container.VACANT,Container.GOAL},
                {Container.VACANT,Container.WALL,Container.VACANT,Container.WALL},
                {Container.VACANT, Container.VACANT,Container.VACANT,Container.VACANT}
        };

        SearchEngine engine = new SearchEngine(arr);
        Container[][] c = engine.start();
        System.out.println(Arrays.deepToString(c));

    }
}