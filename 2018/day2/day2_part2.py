def diff_by_one_letter(xs, ys):
    diff = 0
    for i in range(0, len(xs)):
        x = xs[i]
        y = ys[i]
        if x != y:
            diff = diff + 1
        if diff > 1:
            break
    return diff == 1


def diff_letter(xs, ys):
    for i in range(0, len(xs)):
        if xs[i] != ys[i]:
            return xs[i]


if __name__ == '__main__':
    with open("input.txt") as f:
        lines = f.read().splitlines()
        for line1 in lines:
            for line2 in lines[1:]:
                if diff_by_one_letter(line1, line2):
                    letter = diff_letter(line1, line2)
                    print(line1.replace(letter, ''))
                    exit(0)
