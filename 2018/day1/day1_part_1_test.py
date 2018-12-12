import unittest

import day1_part2


class test(unittest.TestCase):

    def test1(self):
        self.assertTrue(0 == day1_part2.solve([+1, -1]))

    def test2(self):
        self.assertTrue(10 == day1_part2.solve([3, 3, 4, -2, -4]))


if __name__ == '__main__':
    unittest.main()
