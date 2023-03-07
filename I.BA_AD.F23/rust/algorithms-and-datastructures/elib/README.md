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

The `<'a>` [specific lifetime specifier](https://doc.rust-lang.org/rust-by-example/scope/lifetime/explicit.html) indicates that the references to values of type T stored in the tree have a lifetime that is at least as long as that of the tree itself.

The `#[derive(Debug)]` attribute is a macro that automatically implements the Debug trait for
the `TreeNode` struct, allowing it to be printed in a readable format for debugging purposes.

For the sub-nodes in the TreeNode struct we use `Option<Box<TreeNode<T>>>` which allows for the child nodes to be optional. 
Meaning, that we can set the subnodes to `None`, if there is no sub-node. The `Box<T>` allows for the nodes to be allocated
on the heap, rather the stack, which is necessary for recursive data structures. 
In Rust, we can only allocate fix sized values onto the stack. This is not possible here, because its generic and rust therefore
does not know how much memory to allocate on the stack.

```rust
pub left: Option<Box<TreeNode<'a, T>>>,
pub right: Option<Box<TreeNode<'a, T>>>
```

With the implementation of the insert function, we do not allow duplicate values in our tree.
```rust
pub fn insert(&mut self, new_val: &'a T) {
    if *self.val == *new_val {
        return
    }
    let target_tree_node: &mut Option<Box<TreeNode<T>>> = if new_val < self.val { &mut self.left } else {&mut self.right };

    // traverse trough the tree to determine where to insert the value
    match target_tree_node{
        &mut Some(ref mut sub_tree_node) => sub_tree_node.insert(new_val),
        &mut None => {
            let new_tree_node = TreeNode { val: new_val, left: None, right: None};
            let boxed_tree_node: Option<Box<TreeNode<T>>> = Some(Box::new(new_tree_node));
            *target_tree_node = boxed_tree_node; // assign the value of boxed node to the memory location pointed to by the mut ref
        }
    }
}
```