import re


class Node:
    def __init__(self, name):
        self.name = name
        self.parents = []
        self.children = []

    def addParent(self, parent):
        self.parents.append(parent)

    def removeParent(self, parent):
        self.parents.remove(parent)

    def addChild(self, child):
        self.children.append(child)
        child.addParent(self)

    def __repr__(self):
        return f'name={self.name} parents={[n.name for n in self.parents]} children={[n.name for n in self.children]}'


def nodes_with_no_parents(nodes):
    return [n for n in nodes if len(n.parents) == 0]


if __name__ == '__main__':
    nodes = {}
    with open("input.txt") as f:
        for line in f.read().splitlines():
            print(line)
            m = re.search('Step (.) must be finished before step (.) can begin', line)
            parentName = m.group(1)
            childName = m.group(2)
            nodes[parentName] = nodes.get(parentName, Node(parentName))
            nodes[childName] = nodes.get(childName, Node(childName))
            nodes[parentName].addChild(nodes[childName])
    steps = []
    while len(nodes) > 0:
        first_node_with_no_parents = sorted(nodes_with_no_parents(nodes.values()), key=lambda x: x.name)[0]
        steps.append(first_node_with_no_parents.name)
        del nodes[first_node_with_no_parents.name]
        for child in first_node_with_no_parents.children:
            child.removeParent(first_node_with_no_parents)
    print(''.join(steps))
