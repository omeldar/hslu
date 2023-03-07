package ch.hslu.sw03;

import org.jetbrains.annotations.NotNull;

public class BinaryTreeNode<TKey extends Comparable<TKey>, TValue>
        implements Comparable<IBinaryTreeNode<TKey, TValue>>{

    private TKey key;
    private TValue value;
    private BinaryTreeNode leftBinaryTreeNode;
    private BinaryTreeNode rightBinaryTreeNode;

    @Override
    public IBinaryTreeNode getLeftNode() {
        return leftBinaryTreeNode;
    }

    @Override
    public IBinaryTreeNode getRightNode() {
        return rightBinaryTreeNode;
    }

    @Override
    public TKey getKey() {
        return key;
    }   // return hashCode() ????

    @Override
    public TValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (!(o instanceof BinaryTreeNode))
            return false;
        if (this.key == ((BinaryTreeNode<?, ?>) o).getKey()){   // check if values or keys
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 0;       // implement
    }

    @Override
    public int compareTo(@NotNull IBinaryTreeNode<TKey, TValue> o) {
        return 0;   // needs to be implemented by following implementation rules of compareTo
    }
}
