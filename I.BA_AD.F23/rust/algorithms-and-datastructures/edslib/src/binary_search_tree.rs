use std::cmp::Ordering;

// Automatically implements the Debug trait for TreeNode, to allow readable printing
#[derive(Debug)]
pub struct TreeNode<'a, T: PartialEq + PartialOrd> {  
    pub val: &'a T,
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

    pub fn search(&self, value_to_find: &'a T) -> Option<&'a T>{
        //partial_cmp returns Ordering:Less, if value_to_find is greater than self.val
        match self.val.partial_cmp(value_to_find) {
            Some(Ordering::Equal) => return Some(self.val),
            Some(Ordering::Less) => {
                match &self.right {
                    Some(ref right_node) => right_node.search(value_to_find),
                    None => return None,
                }
            }
            Some(Ordering::Greater) => {
                match &self.left {
                    Some(ref left_node) => left_node.search(value_to_find),
                    None => return None,
                }
            }
            None => return None,
        }
    }

    pub fn contains(&self, value_to_find: &'a T) -> bool{
        match self.search(value_to_find){
            Some(_) => return true,
            None => return false,
        }
    }
}