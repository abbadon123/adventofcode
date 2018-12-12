import re
from collections import Counter

from dataclasses import dataclass


@dataclass
class Claim:
    id: int
    left: int
    top: int
    wide: int
    tall: int

    @staticmethod
    def claim(text):
        m = re.search('#([0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)', text)
        return Claim(int(m.group(1)), int(m.group(2)), int(m.group(3)), int(m.group(4)), int(m.group(5)))

    def cells(self):
        cells = []
        for x in range(0, self.wide):
            for y in range(0, self.tall):
                cells.append((x + self.left, y + self.top))
        return cells


if __name__ == '__main__':
    with open("input.txt") as f:
        claims = [Claim.claim(line) for line in f.read().splitlines()]
        cells = [cell
                 for claim in claims
                 for cell in claim.cells()
                 ]
        counter = Counter(cells)
        print(len([True for count in counter.values() if count > 1]))
        uniqueCells = [key for key, value in counter.items() if value == 1]
        for claim in claims:
            if set(claim.cells()).issubset(set(uniqueCells)):
                print(claim)
                exit(1)

