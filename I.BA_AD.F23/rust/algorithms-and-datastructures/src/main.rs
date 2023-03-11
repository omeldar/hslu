use edslib::binary_search_tree::*;

fn main() {
    let mut root = TreeNode {
        val: &"3",
        left: None,
        right: None,
    };
    root.insert(&"1");
    root.insert(&"6");
    root.insert(&"4");
    root.insert(&"8");
    root.insert(&"0");
    println!("{:?}", root);
}
