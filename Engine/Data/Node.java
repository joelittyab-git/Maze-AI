package Engine.Data;

import Engine.Data.Structure.Action;
import Engine.Data.Structure.Container;

public class Node {

    private Container[][] state;
    private Action action;
    private Node parent;

    public Node(Container[][] state){
        this.state = state;
    }
    public Node(Container[][] state, Action action, Node parent){
        this.state = state;
        this.action = action;
        this.parent = parent;
    }

    public Container[][] getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setState(Container[][] state) {
        this.state = state;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
