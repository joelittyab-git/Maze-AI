package Engine;

import Engine.Data.Node;
import Engine.Data.Structure.Action;
import Engine.Data.Structure.Container;
import Engine.Data.Queue;
import Engine.Data.Structure.Storage;

import java.util.ArrayList;

public class SearchEngine {
    private final Container[][] board;
    private final Storage<Node> frontier;
    private final ArrayList<Node> completed;
    private int explored;

    public SearchEngine(Container[][]board){
        this.board = board;
        this.frontier = new Queue<>();
        this.completed = new ArrayList<>();
        explored = 0;
    }

    public Container[][] start(){

        //Parent node
        Node parent = new Node(board, null, null);
        frontier.add(parent);

        while (true){
            explored++;
            if(this.frontier.isEmpty()){
                System.out.println("Sorry no solutions found...");
                return null;
            }

            //Extracting the next node in the queue
            Node nodePointer = this.frontier.pop();

            //adding the node to the completed nodes
            this.completed.add(nodePointer);

            //extracting possible nodes and appending to the queue
            Node[] possibleNodes = this.getPossibleNodes(nodePointer);

            Node trueSolution = this.getTrueSolution(possibleNodes);

            if(trueSolution!=null){
                System.out.println("The number of nodes explored "+this.explored);
                return trueSolution.getState();
            }
            if(possibleNodes!=null){
                this.frontier.add(possibleNodes);
            }
        }
    }

    private Node getTrueSolution(Node[] n){
        if(n==null){
            return null;
        }
        for(Node node : n){
            if(node !=null && this.solutionFound(node)){
                return node;
            }
        }
        return null;
    }


    private boolean solutionFound(Action action, Node node){
        int[] pointerCord = this.findPointer(node);

        assert pointerCord != null;
        int pointerX = pointerCord[1];
        int pointerY = pointerCord[0];

        int y = pointerY;
        int x = pointerX;

        switch (action){
            case UP -> {
                y = y-1;
            }
            case DOWN -> {
                y = y+1;
            }
            case LEFT -> {
                x = x-1;
            }
            case RIGHT -> {
                x = x+1;
            }

        }

        int[] goalIndexes = this.findGoal();

        assert goalIndexes != null;

        return goalIndexes[0] == y &&
                goalIndexes[1] == x;

    }

    private boolean solutionFound(Node node){

        Container[][] container = node.getState();

        for(Container[] array : container){
            for(Container item:array){
                if(item==Container.GOAL){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param node
     * @param action
     * @return next node
     * @implNote A method that returns the next node of the passed action
     *          If the return is null, then that action is not possible
     */
    public Node result(Node node, Action action){

        //Extracting values
        Container[][] next;
        Container[][] state = node.getState();
        int[] pointerCord = this.findPointer(node);
        assert pointerCord != null;
        int y = pointerCord[0];
        int x = pointerCord[1];

        switch (action){
            case UP:
                if(state[y-1][x]!=Container.WALL){
                    next = state.clone();
                    next[y][x] = Container.PATH;
                    next[y-1][x] = Container.POINTER;
                    return new Node(next, Action.UP, node);
                }
                return null;    //not possible
            case DOWN:
                if(state[y+1][x]!=Container.WALL){
                    next = state.clone();
                    next[y][x] = Container.PATH;
                    next[y+1][x] = Container.POINTER;
                    return new Node(next, Action.DOWN, node);
                }
                return null;

            case LEFT:
                if(state[y][x-1]!=Container.WALL){
                    next = state.clone();
                    next[y][x] = Container.PATH;
                    next[y][x-1] = Container.POINTER;
                    return new Node(next, Action.LEFT, node);
                }
                return null;
            case RIGHT:
                if(state[y][x+1]!=Container.WALL){
                    next = state.clone();
                    next[y][x] = Container.PATH;
                    next[y][x+1] = Container.POINTER;
                    return new Node(next, Action.RIGHT, node);
                }
                return null;
        }

        return null;
    }

    private int[] findPointer(Node node){
        Container[][] state = node.getState();

        for(int i = 0; i<state.length; i++){
            for(int j = 0; j<state[i].length; j++){
                if(state[i][j]==Container.POINTER){
                    return new int[]{
                            i,j
                    };
                }
            }
        }

        return null;
    }

    private int[] findGoal(){
        for(int i = 0; i<this.board.length; i++){
            for(int j = 0; j<this.board[i].length; j++){
                if(this.board[i][j]==Container.GOAL){
                    return new int[]{
                            i,j
                    };
                }
            }
        }

        return null;
    }

    private Node[] getPossibleNodes(Node a){
        ArrayList<Node> nodes = new ArrayList<>();

        Container[][] state =  a.getState();
        int pointerX = this.findPointer(a)[1];
        int pointerY = this.findPointer(a)[0];


        if(pointerY-1 >= 0 &&
                (state[pointerY-1][pointerX]==Container.VACANT || state[pointerY-1][pointerX]==Container.GOAL))
        {       //Case up
            Container[][] newState1 = new Container[state.length][state[0].length];
            copyArray(state, newState1);
            newState1[pointerY-1][pointerX] = Container.POINTER;
            newState1[pointerY][pointerX] = Container.PATH;
            nodes.add(new Node(newState1,Action.UP,a));
        }if(pointerY+1 < this.board.length &&
                (state[pointerY+1][pointerX]==Container.VACANT || state[pointerY+1][pointerX]==Container.GOAL)
        ){       //Case down
            Container[][] newState2 = new Container[state.length][state[0].length];
            copyArray(state, newState2);
            newState2[pointerY+1][pointerX] = Container.POINTER;
            newState2[pointerY][pointerX] = Container.PATH;
            nodes.add(new Node(newState2,Action.DOWN,a));
        }if(pointerX-1 >= 0 &&
                (state[pointerY][pointerX-1]==Container.VACANT || state[pointerY][pointerX-1]==Container.GOAL)
        ){       //Case left
            Container[][] newState3 = new Container[state.length][state[0].length];
            copyArray(state, newState3);
            newState3[pointerY][pointerX-1] = Container.POINTER;
            newState3[pointerY][pointerX] = Container.PATH;
            nodes.add(new Node(newState3,Action.LEFT,a));
        }if(pointerX+1 < this.board[pointerY].length &&
                (state[pointerY][pointerX+1]==Container.VACANT || state[pointerY][pointerX+1]==Container.GOAL)
        ){       //Case right
            Container[][] newState4 = new Container[state.length][state[0].length];
            copyArray(state, newState4);
            newState4[pointerY][pointerX+1] = Container.POINTER;
            newState4[pointerY][pointerX] = Container.PATH;
            nodes.add(new Node(newState4,Action.RIGHT,a));
        }

        if(nodes.isEmpty()){
            return null;
        }

        int initialSize = nodes.size();
        Node[] futureNodes = new Node[nodes.size()];

        for(int i = 0; i< initialSize; i++){
            futureNodes[i] = nodes.removeFirst();
        }

        return futureNodes;
    }

    public static void copyArray(Container[][]arr1, Container[][]arr2){

        for(int i = 0; i< arr1.length; i++){
            for(int j = 0; j<arr1[i].length; j++){
                switch (arr1[i][j]){
                    case POINTER -> {
                        arr2[i][j] = Container.POINTER;
                    }
                    case VACANT -> {
                        arr2[i][j] = Container.VACANT;
                    }
                    case PATH -> {
                        arr2[i][j] = Container.PATH;
                    }
                    case WALL -> {
                        arr2[i][j] = Container.WALL;
                    }
                    case GOAL -> {
                        arr2[i][j] = Container.GOAL;
                    }
                }
            }
        }
    }
}
