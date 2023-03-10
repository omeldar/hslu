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

        let expected = "TreeNode { val: 5, left: Some(TreeNode { val: 3, left: Some(TreeNode { val: 2, left: None, right: None }), right: None }), right: Some(TreeNode { val: 7, left: None, right: Some(TreeNode { val: 8, left: None, right: None }) }) }";

        assert_eq!(format!("{:?}", root), expected);
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

    #[test]
    fn test_binary_search_tree_with_i32_exists(){
        let mut root: TreeNode<i32> = TreeNode { val: &5, left: None, right: None };
        root.insert(&3);
        root.insert(&7);
        root.insert(&2);
        root.insert(&8);

        let expected: bool = true;
        assert_eq!(root.contains(&5), expected);
    }

    #[test]
    fn test_binary_search_tree_with_i32_not_exists(){
        let mut root: TreeNode<i32> = TreeNode { val: &5, left: None, right: None };
        root.insert(&3);
        root.insert(&7);
        root.insert(&2);
        root.insert(&8);

        let expected: bool = false;
        assert_eq!(root.contains(&10), expected);
    }

    #[test]
    fn test_binary_search_tree_with_string_exists(){
        let mut root = TreeNode {val: &"e", left: None, right: None};
        root.insert(&"f");
        root.insert(&"a");
        root.insert(&"b");
        root.insert(&"c");
        root.insert(&"d");

        let expected: bool = true;
        assert_eq!(root.contains(&"e"), expected);
    }

    #[test]
    fn test_binary_search_tree_with_string_not_exists(){
        let mut root = TreeNode {val: &"e", left: None, right: None};
        root.insert(&"f");
        root.insert(&"a");
        root.insert(&"b");
        root.insert(&"c");
        root.insert(&"d");

        let expected: bool = false;
        assert_eq!(root.contains(&"z"), expected);
    }

    #[test]
    fn test_binary_search_tree_get_by_key_i32_complex_value_exists() {
        // arrange
        #[derive(Debug)]
        #[derive(PartialEq)]
        #[derive(PartialOrd)]
        struct Person{
            id: u32,
            name: String,
            age: u16,
        }

        let person1: Person = Person { id: 6, name: String::from("Max Muster"), age: 17 };
        let mut root = TreeNode { val: &person1, left: None, right: None };

        let person2: Person = Person { id: 7, name: String::from("John Doe"), age: 18 };
        root.insert( &person2);

        let person3: Person = Person { id: 3, name: String::from("Jane Doe"), age: 18 };
        root.insert( &person3);

        let person4: Person = Person { id: 4, name: String::from("Maxine Muster"), age: 18 };
        root.insert( &person4);

        //act
        let person_to_find = root.search(&person4);

        //assert
        assert_eq!(person_to_find, Some(&person4));

    }
}