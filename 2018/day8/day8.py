class Node:

    def __init__(self, code):
        self.code = tuple(code)
        self.children = {}

    def add(self, child):
        self.children[child.code] = child

    def full_code(self):
        children_len = sum([len(c.full_code()) for c in self.children.values()])
        meta_len = self.code[1]
        return self.code[: children_len + meta_len + 2]

    def children_len(self):
        return sum([len(c.full_code()) for c in self.children.values()])

    def my_meta_count(self):
        my_full_code = self.full_code()
        return sum(my_full_code[2 + self.children_len(): 2 + self.children_len() + my_full_code[1]])

    def meta_count(self):
        return self.my_meta_count() + sum([c.meta_count() for c in self.children.values()])

    def __repr__(self):
        return f'full_code={self.full_code()} children={list(self.children.values())}'

    def __eq__(self, other):
        return self.full_code() == other.full_code() and list(self.children.values()) == list(other.children.values())


def decode(list_of_numbers):
    children_count = list_of_numbers[0]
    if children_count == 0:
        return Node(list_of_numbers)
    me = Node(list_of_numbers)
    for i in range(0, children_count):
        me.add(decode(list_of_numbers[2 + me.children_len():]))
    return me


if __name__ == '__main__':
    with open("input.txt") as f:
        for line in f.read().splitlines():
            list_of_numbers = [int(n) for n in line.split(' ')]
            root = decode(list_of_numbers)
            # print(root)
            print(root.meta_count())
