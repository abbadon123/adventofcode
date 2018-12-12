def count(letters):
    counts = {}
    for l in letters:
        counts[l] = counts.get(l, 0) + 1
    anyDoubles = any([x % 2 == 0 for x in counts.values()])
    anyTriples = any([x % 3 == 0 for x in counts.values()])
    return (1 if anyDoubles else 0, 1 if anyTriples else 0)


if __name__ == '__main__':
    result = (0, 0)
    with open("input.txt") as f:
        for line in f.readlines():
            result = (result[0] + count(line)[0], result[1] + count(line)[1])
    print(result[0] * result[1])
