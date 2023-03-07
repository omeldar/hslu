package ch.hslu.sw03;

public interface IBinaryTreeNode<TKey, TValue> {
    IBinaryTreeNode getLeftNode();
    IBinaryTreeNode getRightNode();
    TKey getKey();
    TValue getValue();
    boolean equals(Object object);
}
