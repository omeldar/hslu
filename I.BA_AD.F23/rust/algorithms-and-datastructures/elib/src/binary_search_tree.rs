use std::cmp::Ordering;

// Automatically implements the Debug trait for TreeNode, to allow readable printing
#[derive(Debug)]
pub struct TreeNode<'a, T: PartialEq + PartialOrd, V> {
    pub key: &'a T,  
    pub val: &'a V,
    pub left: Option<Box<TreeNode<'a, T, V>>>,
    pub right: Option<Box<TreeNode<'a, T, V>>>
}

impl<'a, T: PartialOrd + PartialEq, V> TreeNode<'a, T, V> {
    // inserts a value into the tree by creating a new node
    pub fn insert(&mut self, new_key: &'a T, new_val: &'a V) {
        if *self.key == *new_key {
            return
        }
        let target_tree_node: &mut Option<Box<TreeNode<T, V>>> = if new_key < self.key { &mut self.left } else {&mut self.right };

        // traverse trough the tree to determine where to insert the value
        match target_tree_node{
            &mut Some(ref mut sub_tree_node) => sub_tree_node.insert(new_key, new_val),
            &mut None => {
                let new_tree_node = TreeNode { key: new_key, val: new_val, left: None, right: None};
                let boxed_tree_node: Option<Box<TreeNode<T, V>>> = Some(Box::new(new_tree_node));
                *target_tree_node = boxed_tree_node; // assign the value of boxed node to the memory location pointed to by the mut ref
            }
        }
    }

    pub fn contains(&self, element_to_find: &'a T) -> bool{
        //partial_cmp returns Ordering:Less, if value_to_find is greater than self.val
        match self.key.partial_cmp(element_to_find) {
            Some(Ordering::Equal) => true,
            Some(Ordering::Less) => {
                match &self.right {
                    Some(ref right_node) => right_node.contains(element_to_find),
                    None => false,
                }
            }
            Some(Ordering::Greater) => {
                match &self.left {
                    Some(ref left_node) => left_node.contains(element_to_find),
                    None => false,
                }
            }
            None => false,
        }
    }

    pub fn get_by_key(&self, element_key: &'a T) -> Option<&'a V> {
        //partial_cmp returns Ordering:Less, if value_to_find is greater than self.val
        match self.key.partial_cmp(element_key) {
            Some(Ordering::Equal) => return Some(self.val),
            Some(Ordering::Less) => {
                match &self.right {
                    Some(ref right_node) => right_node.get_by_key(element_key),
                    None => return None,
                }
            }
            Some(Ordering::Greater) => {
                match &self.left {
                    Some(ref left_node) => left_node.get_by_key(element_key),
                    None => return None,
                }
            }
            None => return None,
        }
    }

}

#[cfg(test)]
mod tests {
    use super::TreeNode;

    #[test]
    fn test_binary_tree_with_i32() {
        let mut root: TreeNode<i32, &str> = TreeNode { key: &5, val: &"a", left: None, right: None };
        root.insert(&3, &"b");
        root.insert(&7, &"c");
        root.insert(&2, &"d");
        root.insert(&8, &"e");

        let expected = "TreeNode { key: 5, val: \"a\", left: Some(TreeNode { key: 3, val: \"b\", left: Some(TreeNode { key: 2, val: \"d\", left: None, right: None }), right: None }), right: Some(TreeNode { key: 7, val: \"c\", left: None, right: Some(TreeNode { key: 8, val: \"e\", left: None, right: None }) }) }";

        assert_eq!(format!("{:?}", root), expected);
    }
    
    #[test]
    fn test_binary_tree_with_strings() {
        let mut root = TreeNode {key: &"e", val: &0, left: None, right: None};
        root.insert(&"f", &1);
        root.insert(&"a", &2);
        root.insert(&"b", &3);
        root.insert(&"c", &4);
        root.insert(&"d", &5);

        let expected = "TreeNode { key: \"e\", val: 0, left: Some(TreeNode { key: \"a\", val: 2, left: None, right: Some(TreeNode { key: \"b\", val: 3, left: None, right: Some(TreeNode { key: \"c\", val: 4, left: None, right: Some(TreeNode { key: \"d\", val: 5, left: None, right: None }) }) }) }), right: Some(TreeNode { key: \"f\", val: 1, left: None, right: None }) }";
        assert_eq!(format!("{:?}", root), expected);
    }

    #[test]
    fn test_binary_search_tree_with_i32_exists(){
        let mut root: TreeNode<i32, i32> = TreeNode { key: &5, val: &5, left: None, right: None };
        root.insert(&3, &3);
        root.insert(&7, &7);
        root.insert(&2, &2);
        root.insert(&8, &8);

        let expected: bool = true;
        assert_eq!(root.contains(&5), expected);
    }

    #[test]
    fn test_binary_search_tree_with_i32_not_exists(){
        let mut root: TreeNode<i32, i32> = TreeNode { key: &5, val: &5, left: None, right: None };
        root.insert(&3, &3);
        root.insert(&7, &7);
        root.insert(&2, &2);
        root.insert(&8, &8);

        let expected: bool = false;
        assert_eq!(root.contains(&10), expected);
    }

    #[test]
    fn test_binary_search_tree_with_string_exists(){
        let mut root = TreeNode { key: &"e",val: &"e", left: None, right: None };
        root.insert(&"f", &"f");
        root.insert(&"a", &"a");
        root.insert(&"b", &"b");
        root.insert(&"c", &"c");
        root.insert(&"d", &"d");

        let expected: bool = true;
        assert_eq!(root.contains(&"e"), expected);
    }

    #[test]
    fn test_binary_search_tree_with_string_not_exists(){
        let mut root = TreeNode { key: &"e",val: &"e", left: None, right: None };
        root.insert(&"f", &"f");
        root.insert(&"a", &"a");
        root.insert(&"b", &"b");
        root.insert(&"c", &"c");
        root.insert(&"d", &"d");

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
        let mut root = TreeNode { key: &person1.id, val: &person1, left: None, right: None };

        let person2: Person = Person { id: 7, name: String::from("John Doe"), age: 18 };
        root.insert(&person2.id, &person2);

        let person3: Person = Person { id: 3, name: String::from("Jane Doe"), age: 18 };
        root.insert(&person3.id, &person3);

        let person4: Person = Person { id: 4, name: String::from("Maxine Muster"), age: 18 };
        root.insert(&person4.id, &person4);

        //act
        let person_to_find = root.get_by_key(&4);

        //assert
        assert_eq!(person_to_find, Some(&person4));

    }

    #[test]
    fn test_binary_search_tree_get_by_key_i32_complex_value_not_exists() {
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
        let mut root = TreeNode { key: &person1.id, val: &person1, left: None, right: None };

        let person2: Person = Person { id: 7, name: String::from("John Doe"), age: 18 };
        root.insert(&person2.id, &person2);

        let person3: Person = Person { id: 3, name: String::from("Jane Doe"), age: 18 };
        root.insert(&person3.id, &person3);

        let person4: Person = Person { id: 4, name: String::from("Maxine Muster"), age: 18 };
        root.insert(&person4.id, &person4);

        //act
        let person_to_find = root.get_by_key(&8);

        //assert
        assert_eq!(person_to_find, None);

    }
}