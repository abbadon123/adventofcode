def solve(changes):
    frequencies = {0}
    frequency = 0
    while 1:
        for change in changes:
            frequency = frequency + int(change)
            if (frequency in frequencies):
                return frequency
            frequencies.add(frequency)


if __name__ == '__main__':
    with open("input.txt") as f:
        print(solve(f.readlines()))
