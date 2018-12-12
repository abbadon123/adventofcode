import unittest

import day2


class test(unittest.TestCase):

    def test1(self):
        self.assertTrue((0, 0) == day2.count('abcdef'))

    def test2(self):
        self.assertTrue((1, 1) == day2.count('bababc'))

    def test3(self):
            self.assertTrue((0, 1) == day2.count('ababab'))



if __name__ == '__main__':
    unittest.main()
