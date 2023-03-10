use elib::binary_search_tree::*;

fn main() {
    let mut root = TreeNode {key: &"e", val: &0, left: None, right: None};
    root.insert(&"f", &1);
    root.insert(&"a", &2);
    root.insert(&"b", &3);
    root.insert(&"c", &4);
    root.insert(&"d", &5);

    println!("{:?}", root);
}
