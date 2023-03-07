# ELIB

To analyze and describe the algorithms made for the custom data structures, I use the
*Big O-Notation*, which is a tool used to describe the time complexity of algorithms.

## BinaryTree

A binary tree allows us to do an easy binary search of items in a large dataset.
Using a binary tree, we can reduce the time complexity from O(N) to O(log N)

### Implementation

This struct defines a binary tree data structure. The tree is generic over a type T that must
 implement the [PartialEq](https://doc.rust-lang.org/std/cmp/trait.PartialEq.html) and 
 [PartialOrd](https://doc.rust-lang.org/std/cmp/trait.PartialOrd.html) traits.
 The PartialEq trait allows quality comparison and the PartialOrd trait is used for types that form a partial order. The operators `gt`, `gte`, `lt`, `lte` can be called using the `>`, `>=`, `<`, `<=` operators.

```rust
pub struct TreeNode<'a, T: PartialEq + PartialOrd> {     
    pub val: &'a T,
    pub left: Option<Box<TreeNode<'a, T>>>,
    pub right: Option<Box<TreeNode<'a, T>>>
}
```

The `'a` lifetime specifier indicates that the references to values of type T stored in the tree have
 a lifetime that is at least as long as that of the tree itself.

The `#[derive(Debug)]` attribute is a macro that automatically implements the Debug trait for
 the `TreeNode` struct, allowing it to be printed in a readable format for debugging purposes.

For the sub-nodes in the TreeNode struct we use `Option<Box<TreeNode<T>>>` which
 allows for the child nodes to be optional. Meaning, that we can set the subnodes to `None`, if there is no sub-node.
 The `Box<T>` allows for the nodes to be allocated on the heap, rather the stack, which is necessary for recursive data structures. 
 If we would allocate it on the stack, we would at some point run into a stack overflow.

```rust
pub left: Option<Box<TreeNode<'a, T>>>,
pub right: Option<Box<TreeNode<'a, T>>>
```