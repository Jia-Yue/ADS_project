import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class MaxFibonacciHeap {
    private Node max = null;


    public Node insert(Node node){
        if(node == null)
            return null;
        //insert first node into fibonacci heap
        node.setParent(null);
        node.setChild(null);
        node.setChildCut(false);
        if(max == null){
            max = node;
            max.setLeft(node);
            max.setRight(node);
            max.setDegree(0);
            max.setChildCut(false);
            return max;
        }
        if (node.getData() > max.getData()) {
            max = insertBig(node, max);
        } else {
            insertSmall(node,max);
        }
        return node;
    }


    public Node removeMax(int queriesNumber){
        Node maxBeforeRemove = max;
        if(max == null)
            return null;
        // create an array list to store max's children
        Node child = max.getChild();
        if(child != null){
            List<Node> childNodes = getListOfNodes(child);
            Node maxChildNode = childNodes.get(0);//the max value in subtree of current max root
            Node current = max;
            // insert all children of max and max to the root list
            insertListOfNodesAtRootLevel(childNodes,current);
        }
        Node startNode = null;
        // if there is only one node in heap
        if(max.getRight() == max)
            return null;
        else
            startNode = max.getRight();

         deleteNode(max);

        // now we pairwise
        pairWise(startNode, queriesNumber);
        maxBeforeRemove.setChild(null);
        maxBeforeRemove.setDegree(0);
        return maxBeforeRemove;
    }


    public void increaseKey (Node node, int addend) {
        int newKey = node.getData()+addend;
        node.setData(newKey);
        // the node to be increased is at root level
        if(node.getParent()==null && node.getData()>max.getData())
            max = node;
        // the node to be increased is not at root level
        if(node.getParent()!=null && node.getData()>node.getParent().getData()){
            while(node.getParent()!=null && node.getChildCut()){
                Node nodeParent = node.getParent();
                nodeParent.setDegree(nodeParent.getDegree()-1);
                //the node to be removed is the first child of parent
                if (nodeParent.getChild() == node) {
                    if (node.getRight() != node)
                        nodeParent.setChild(node.getRight());
                    else // the node's parent has only one child
                        nodeParent.setChild(null);
                }
                deleteNode(node);
                insert(node);
                node = nodeParent;
            }
            if(node.getParent()!=null){
                node.getParent().setDegree(node.getParent().getDegree()-1);
                node.getParent().setChildCut(true);
                if (node.getParent().getChild() == node) {
                    if (node.getRight() != node) {
                        node.getParent().setChild(node.getRight());
                    } else {
                        node.getParent().setChild(null);
                    }
                }
                deleteNode(node);
                insert(node);
            }
        }
    }


    private void pairWise(Node startNode, int queriesNumber){
        Hashtable<Integer, Node> degreeTable = new Hashtable<>();
        List<Node> listOfStartNode = getListOfNodes(startNode);
        List<Node> listAfter = new ArrayList<>();

        for(Node current:listOfStartNode){
            while(degreeTable.get(current.getDegree()) != null) {
                Node nodeOfDegree = degreeTable.get(current.getDegree());
                Node newParent;
                int degreeToRemove = current.getDegree();
                if (nodeOfDegree.getData() >= current.getData())
                    newParent = parentChild(nodeOfDegree, current);
                else
                    newParent = parentChild(current, nodeOfDegree);
                degreeTable.remove(degreeToRemove);
                listAfter.remove(nodeOfDegree);
                current = newParent;
            }
            listAfter.add(current);
            degreeTable.put(current.getDegree(), current);
            }


        max = insertListOfNodesAtRootLevel(listAfter,null);
    }



    private Node insertListOfNodesAtRootLevel (List<Node> nodesList, Node next) {
        Node maxNode = nodesList.get(0);
        Node current  = next;
        for (Node node : nodesList) {
            node.setParent(null);
            node.setChildCut(false);
            if (node.getData() > maxNode.getData()) {
                maxNode = node;
            }
            if (current == null) {
                node.setLeft(node);
                node.setRight(node);
                current = node;
            } else {
                Node lastNode = current.getLeft();
                node.setRight(current);
                node.setLeft(current.getLeft());
                current.setLeft(node);
                lastNode.setRight(node);
                current = node;
            }
        }
        return maxNode;
    }


    private Node deleteNode(Node node){
        node.getLeft().setRight(node.getRight());
        node.getRight().setLeft(node.getLeft());
        node.setParent(null);
        return node;
    }


    private List<Node> getListOfNodes(Node startNode) {
        List<Node> listOfNodes = new ArrayList<>();
        listOfNodes.add(startNode);
        Node nextNode = startNode.getRight();
        while (nextNode != startNode) {
            listOfNodes.add(nextNode);
            nextNode = nextNode.getRight();
        }
        return listOfNodes;
    }


    private Node parentChild(Node parent, Node child) {
        Node currentChildOfParent = parent.getChild();
        if (currentChildOfParent == null) {
            child.setLeft(child);
            child.setRight(child);
            parent.setChild(child);
        } else {
            child.setRight(currentChildOfParent);
            Node lastNodeOfParentChild = currentChildOfParent.getLeft();
            child.setLeft(currentChildOfParent.getLeft());
            lastNodeOfParentChild.setRight(child);
            currentChildOfParent.setLeft(child);
        }
        child.setParent(parent);
        child.setChildCut(false);
        parent.setDegree(parent.getDegree() + 1);
        return parent;
    }

    private Node insertBig(Node insertNode, Node startNode) {
        Node lastNode = startNode.getLeft();
        insertNode.setRight(startNode);
        insertNode.setLeft(lastNode);
        startNode.setLeft(insertNode);
        lastNode.setRight(insertNode);
        return insertNode;
    }


    private void insertSmall (Node insertNode, Node startNode) {
        insertNode.setRight(startNode);
        Node lastNode = startNode.getLeft();
        insertNode.setLeft(startNode.getLeft());
        startNode.setLeft(insertNode);
        lastNode.setRight(insertNode);
    }

}
