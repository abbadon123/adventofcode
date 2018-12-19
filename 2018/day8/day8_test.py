import unittest

from day8 import Node, decode


class Test(unittest.TestCase):

    def test_one_node(self):
        expected = Node([0, 0])
        result = decode([0, 0])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 0)

    def test_one_child(self):
        expected = Node([1, 0, 0, 0])
        expected.add(Node([0, 0]))
        result = decode([1, 0, 0, 0])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 0)

    def test_one_child_child_have_metadata(self):
        expected = Node([1, 0, 0, 1, 666])
        expected.add(Node([0, 1, 666]))
        result = decode([1, 0, 0, 1, 666])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 666)

    def test_one_child_child_have_metadata_parent_have_metadata(self):
        expected = Node([1, 1, 0, 1, 666, 9999])
        expected.add(Node([0, 1, 666]))
        result = decode([1, 1, 0, 1, 666, 9999])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 9999 + 666)

    def test_two_children(self):
        expected = Node([2, 3, 0, 3, 10, 11, 12, 0, 1, 99, 1, 1, 2])
        expected.add(Node([0, 3, 10, 11, 12]))
        expected.add(Node([0, 1, 99]))
        result = decode([2, 3, 0, 3, 10, 11, 12, 0, 1, 99, 1, 1, 2])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 1 + 1 + 2 + 10 + 11 + 12 + 99)

    def test_two_children_second_child_has_child(self):
        expected = Node([2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2])
        expected.add(Node([0, 3, 10, 11, 12]))
        child = Node([1, 1, 0, 1, 99, 2])
        expected.add(child)
        child.add(Node([0, 1, 99]))
        result = decode([2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 1 + 1 + 2 + 10 + 11 + 12 + 2 + 99)

    def test_something(self):
        expected = Node([2, 3, 1, 1, 0, 1, 99, 2, 0, 3, 10, 11, 12, 1, 1, 2])
        expected.add(Node([0, 3, 10, 11, 12]))
        child = Node([1, 1, 0, 1, 99, 2])
        expected.add(child)
        child.add(Node([0, 1, 99]))
        result = decode([2, 3, 1, 1, 0, 1, 99, 2, 0, 3, 10, 11, 12, 1, 1, 2])
        print(expected)
        print(result)
        self.assertTrue(result.meta_count() == expected.meta_count())
        self.assertTrue(result.meta_count() == 1 + 1 + 2 + 10 + 11 + 12 + 2 + 99)


if __name__ == '__main__':
    unittest.main()
