use elib::binary_search_tree::*;

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
        let mut root = TreeNode { val: &person1, left: None, right: None };

        let person2: Person = Person { id: 7, name: String::from("John Doe"), age: 18 };
        root.insert( &person2);

        let person3: Person = Person { id: 3, name: String::from("Jane Doe"), age: 18 };
        root.insert( &person3);

        let person4: Person = Person { id: 4, name: String::from("Maxine Muster"), age: 18 };

        //act
        let person_to_find = root.search(&person4);

        //assert
        assert_eq!(person_to_find, None);

    }
}