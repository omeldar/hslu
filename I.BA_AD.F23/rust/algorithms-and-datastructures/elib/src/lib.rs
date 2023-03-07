pub mod binary_tree{
    #[derive(Debug)]
    pub struct Node<'a, T: PartialEq> {   
        pub val: &'a T,
        pub left: Option<Box<Node<'a, T>>>,
        pub right: Option<Box<Node<'a, T>>>
    }

    impl<'a, T: PartialOrd + PartialEq> Node<'a, T> {
        pub fn insert(&mut self, new_val: &'a T) {
            if *self.val == *new_val {
                return
            }
            let target_node: &mut Option<Box<Node<T>>> = if new_val < self.val { &mut self.left } else {&mut self.right };
            match target_node{
                &mut Some(ref mut subnode) => subnode.insert(new_val),
                &mut None => {
                    let new_node = Node { val: new_val, left: None, right: None};
                    let boxed_node: Option<Box<Node<T>>> = Some(Box::new(new_node));
                    *target_node = boxed_node;
                }
            }
        }
    }

    #[cfg(test)]
    mod tests {
        use super::Node;

        #[test]
        fn test_binary_tree_with_i32() {
            let mut root: Node<i32> = Node { val: &5, left: None, right: None };
            root.insert(&3);
            root.insert(&7);
            root.insert(&2);
            root.insert(&8);

            assert_eq!(
                format!("{:?}", root),
                "Node { val: 5, left: Some(Node { val: 3, left: Some(Node { val: 2, left: None, right: None }), right: None }), right: Some(Node { val: 7, left: None, right: Some(Node { val: 8, left: None, right: None }) }) }"
            );
        }
        
        #[test]
        fn test_binary_tree_with_strings() {
            let mut root = Node {val: &"e", left: None, right: None};
            root.insert(&"f");
            root.insert(&"a");
            root.insert(&"b");
            root.insert(&"c");
            root.insert(&"d");

            let expected = "Node { val: \"e\", left: Some(Node { val: \"a\", left: None, right: Some(Node { val: \"b\", left: None, right: Some(Node { val: \"c\", left: None, right: Some(Node { val: \"d\", left: None, right: None }) }) }) }), right: Some(Node { val: \"f\", left: None, right: None }) }";
            assert_eq!(format!("{:?}", root), expected);
        }
    }
}

