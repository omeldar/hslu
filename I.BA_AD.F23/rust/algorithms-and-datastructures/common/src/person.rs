use core::cmp::Ordering;

#[derive(Debug)]
#[derive(PartialEq)]
pub struct Person{
    pub id: u32,
    pub name: String,
    pub age: u16,
}

impl PartialOrd for Person {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        match self.id.partial_cmp(&other.id) {
            Some(Ordering::Equal) => return Some(Ordering::Equal),
            Some(Ordering::Less) => return Some(Ordering::Less),
            Some(Ordering::Greater) => return Some(Ordering::Greater),
            None => None
        }
    }
}