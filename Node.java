public class Node {
    private int degree;
    private Node parent;
    private Node child;
    private Node left;
    private Node right;
    private int data;
    private String hashtag;
    private boolean childCut;


    public Node(int degree, Node parent, Node child, Node left, Node right, int data, String hashtag, boolean childCut){
        this.degree = degree;
        this.parent = parent;
        this.child = child;
        this.left = left;
        this.right = right;
        this.data = data;
        this.hashtag = hashtag;
        this.childCut = childCut;
    }


    public int getDegree(){
        return degree;
    }


    public void setDegree(int degree){
        this.degree = degree;
    }


    public Node getParent(){
        return parent;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }


    public Node getChild(){
        return child;
    }


    public void setChild(Node child) {
        this.child = child;
    }



    public Node getLeft(){
        return left;
    }


    public void setLeft(Node left) {
        this.left = left;
    }


    public Node getRight(){
        return right;
    }


    public void setRight(Node right) {
        this.right = right;
    }


    public int getData(){
        return data;
    }


    public void setData(int data) {
        this.data = data;
    }


    public String getHashtag(){
        return hashtag;
    }


    public boolean getChildCut(){
        return childCut;
    }

    public void setChildCut(boolean childCut) {
        this.childCut = childCut;
    }



}
