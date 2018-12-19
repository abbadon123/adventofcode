class Node:

    def __init__(self, code, children=[]):
        self.code = code
        self.children = children

    def meta_count(self):
        return sum(self.code[2:]) + sum([c.meta_count() for c in self.children])

    def len(self):
        return len(self.code) + sum([c.len() for c in self.children])

    def __repr__(self):
        return f'code={self.code} children={self.children}'


def decode(list_of_numbers):
    children_count, metadata_count, *rest = list_of_numbers
    children = []
    for _ in range(0, children_count):
        node = decode(rest)
        children.append(node)
        rest = rest[node.len():]
    return Node([children_count, metadata_count] + rest[:metadata_count], children)


if __name__ == '__main__':
    with open("input.txt") as f:
        for line in f.read().splitlines():
            list_of_numbers = [int(n) for n in line.split(' ')]
            root = decode(list_of_numbers)
            print(root.meta_count())
            # 49426
