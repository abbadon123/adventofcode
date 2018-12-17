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


def parse_nodes(lines):
    nodes = {}
    for line in lines:
        m = re.search('Step (.) must be finished before step (.) can begin', line)
        parentName = m.group(1)
        childName = m.group(2)
        nodes[parentName] = nodes.get(parentName, Node(parentName))
        nodes[childName] = nodes.get(childName, Node(childName))
        nodes[parentName].addChild(nodes[childName])
    return nodes

class Workers:
    def __init__(self, count):
        self.workers = [Worker(number) for number in range(0, count)]

    def is_any_working(self):
        return any([w.is_working() for w in self.workers])

    def is_any_idle(self):
        return any([w.is_idle() for w in self.workers ])

    def assign(self, node):
        [w for w in self.workers if w.is_idle()][0].assign(node)

    def work(self):
        for w in self.workers:
            w.work()

    def completed(self):
        result = []
        for w in self.workers:
            if w.is_done():
                result.append(w.node)
                w.assign(None)
        return result


def effort(node):
    e = ord(node.name) - 5 + 1
    print(f"effor for {node.name} is {e}")
    return e

class Worker:
    def __init__(self, number):
        self.number = number
        self.node = None
        self.worked = 0

    def __repr__(self):
        return f'{self.number}'

    def is_idle(self):
        return self.node is None

    def assign(self, node):
        self.node = node
        self.worked = 0

    def is_done(self):
        if self.node is not None:
            return self.worked >= effort(self.node)

    def work(self):
        self.worked += 1
        if self.is_done():
            for child in self.node.children:
                child.removeParent(self.node)

    def is_working(self):
        return not self.is_idle() and not self.is_done()


if __name__ == '__main__':
    with open("input.txt") as f:
        nodes = parse_nodes(f.read().splitlines())
        steps = []
        while len(nodes) > 0:
            first_node_with_no_parents = sorted(nodes_with_no_parents(nodes.values()), key=lambda x: x.name)[0]
            steps.append(first_node_with_no_parents.name)
            del nodes[first_node_with_no_parents.name]
            for child in first_node_with_no_parents.children:
                child.removeParent(first_node_with_no_parents)
        print(''.join(steps))

    with open("input.txt") as f:
        nodes = parse_nodes(f.read().splitlines())
        steps = []
        workers = Workers(5)
        seconds = 0
        while len(nodes) > 0 or workers.is_any_working():
            completed_nodes = workers.completed()
            for cn in completed_nodes:
                steps.append(cn.name)
            while workers.is_any_idle() and len(nodes_with_no_parents(nodes.values())) > 0:
                todo = nodes_with_no_parents(nodes.values())[0]
                del nodes[todo.name]
                workers.assign(todo)
            workers.work()
            seconds += 1
        print(seconds) # 991
