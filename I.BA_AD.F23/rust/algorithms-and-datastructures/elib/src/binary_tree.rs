use std::cmp::Ordering;

// Automatically implements the Debug trait for TreeNode, to allow readable printing
#[derive(Debug)]
pub struct TreeNode<'a, T: PartialEq + PartialOrd> {  
    pub val: &'a T,
    // 
    pub left: Option<Box<TreeNode<'a, T>>>,
    pub right: Option<Box<TreeNode<'a, T>>>
}

impl<'a, T: PartialOrd + PartialEq> TreeNode<'a, T> {
    // inserts a value into the tree by creating a new node
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

    pub fn contains(self, value_to_find: &'a T) -> bool{
        //partial_cmp returns Ordering:Less, if value_to_find is greater than self.val
        match self.val.partial_cmp(value_to_find) {
            Some(Ordering::Equal) => true,
            Some(Ordering::Less) => {
                match &self.right {
                    // Borrowing issue, still needs to be fixed
                    Some(ref right_node) => right_node.contains(value_to_find),
                    None => false,
                }
            }
            Some(Ordering::Greater) => {
                match &self.left {
                    // Borrowing issue, still needs to be fixed
                    Some(ref left_node) => left_node.contains(value_to_find),
                    None => false,
                }
            }
            None => false,
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