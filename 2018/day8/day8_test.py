import unittest

from day8 import decode


class Test(unittest.TestCase):

    def test_one_node(self):
        self.assertTrue(decode([0, 0]).meta_count() == 0)

    def test_one_child(self):
        self.assertTrue(decode([1, 0, 0, 0]).meta_count() == 0)

    def test_one_child_child_have_metadata(self):
        print(decode([1, 0, 0, 1, 666]))
        self.assertTrue(decode([1, 0, 0, 1, 666]).meta_count() == 666)

    def test_one_child_child_have_metadata_parent_have_metadata(self):
        self.assertTrue(decode([1, 1, 0, 1, 666, 9999]).meta_count() == 9999 + 666)

    def test_two_children(self):
        self.assertTrue(
            decode([2, 3, 0, 3, 10, 11, 12, 0, 1, 99, 1, 1, 2]).meta_count()
            ==
            1 + 1 + 2 + 10 + 11 + 12 + 99)

    def test_two_children_second_child_has_child(self):
        self.assertTrue(
            decode([2, 3, 0, 3, 10, 11, 12, 1, 1, 0, 1, 99, 2, 1, 1, 2]).meta_count()
            ==
            1 + 1 + 2 + 10 + 11 + 12 + 2 + 99)

    def test_something(self):
        self.assertTrue(
            decode([2, 3, 1, 1, 0, 1, 99, 2, 0, 3, 10, 11, 12, 1, 1, 2]).meta_count()
            ==
            1 + 1 + 2 + 10 + 11 + 12 + 2 + 99)


if __name__ == '__main__':
    unittest.main()
