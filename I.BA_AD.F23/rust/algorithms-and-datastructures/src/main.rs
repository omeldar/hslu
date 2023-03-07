use elib::binary_tree::*;

fn main() {
    let mut root = TreeNode { val: &5, left: None, right: None };
    root.insert(&3);
    root.insert(&7);
    root.insert(&2);
    root.insert(&8);
    println!("{:?}", root);
}
