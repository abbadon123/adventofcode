class Node:

    def __init__(self, code, children=[]):
        self.code = code
        self.children = children

    def meta(self):
        return self.code[2:]

    def meta_count(self):
        return sum(self.meta()) + sum([c.meta_count() for c in self.children])

    def len(self):
        return len(self.code) + sum([c.len() for c in self.children])

    def __repr__(self):
        return f'code={self.code} children={self.children}'

    def value(self):
        if len(self.children) == 0:
            return self.meta_count()
        else:
            value_sum = 0
            for index in self.meta():
                index = index - 1
                if len(self.children) > index:
                    value_sum = value_sum + self.children[index].value()
            return value_sum

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
            print(root.value())
