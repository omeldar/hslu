// We need to derive the implementation of Debug here to be able to print the binary tree into console
#[derive(Debug)]
pub struct TreeNode<'a, T: PartialEq> {     
    pub val: &'a T,
    // Option<Box<TreeNode<T>>> allows for the child nodes to be optioonal and
    // the Box<T> allows for the nodes to be allocated on the heap, rather the stack, which
    // is necessary for recursive data structures
    pub left: Option<Box<TreeNode<'a, T>>>,
    pub right: Option<Box<TreeNode<'a, T>>>
}

impl<'a, T: PartialOrd + PartialEq> TreeNode<'a, T> {
    pub fn insert(&mut self, new_val: &'a T) {
        if *self.val == *new_val {
            return
        }
        let target_tree_node: &mut Option<Box<TreeNode<T>>> = if new_val < self.val { &mut self.left } else {&mut self.right };
        match target_tree_node{
            &mut Some(ref mut sub_tree_node) => sub_tree_node.insert(new_val),
            &mut None => {
                let new_tree_node = TreeNode { val: new_val, left: None, right: None};
                let boxed_tree_node: Option<Box<TreeNode<T>>> = Some(Box::new(new_tree_node));
                *target_tree_node = boxed_tree_node;
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::TreeNode;

    #[test]
    fn test_binary_tree_with_i32() {
        let mut root: TreeNode<i32> = TreeNode { val: &5, left: None, right: None };
        root.insert(&3);
        root.insert(&7);
        root.insert(&2);
        root.insert(&8);

        assert_eq!(
            format!("{:?}", root),
            "TreeNode { val: 5, left: Some(TreeNode { val: 3, left: Some(TreeNode { val: 2, left: None, right: None }), right: None }), right: Some(TreeNode { val: 7, left: None, right: Some(TreeNode { val: 8, left: None, right: None }) }) }"
        );
    }
    
    #[test]
    fn test_binary_tree_with_strings() {
        let mut root = TreeNode {val: &"e", left: None, right: None};
        root.insert(&"f");
        root.insert(&"a");
        root.insert(&"b");
        root.insert(&"c");
        root.insert(&"d");

        let expected = "TreeNode { val: \"e\", left: Some(TreeNode { val: \"a\", left: None, right: Some(TreeNode { val: \"b\", left: None, right: Some(TreeNode { val: \"c\", left: None, right: Some(TreeNode { val: \"d\", left: None, right: None }) }) }) }), right: Some(TreeNode { val: \"f\", left: None, right: None }) }";
        assert_eq!(format!("{:?}", root), expected);
    }
}