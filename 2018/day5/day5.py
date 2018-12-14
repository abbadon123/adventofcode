def react(line):
    i = 0
    while True:
        if i == len(line) - 1:
            return line
        elif line[i].lower() == line[i + 1].lower() and (
                (line[i].islower() and line[i + 1].isupper()) or (line[i].isupper() and line[i + 1].islower())):
            # print("equals", line[i], line[i + 1])
            line = line[: i] + line[i + 2:]
            if i > 0:
                i = i - 1
        else:
            i = i + 1


if __name__ == '__main__':
    with open("input.txt") as f:
        for line in f.read().splitlines():
            import string
            import sys
            minLength = sys.maxsize
            for letter in string.ascii_lowercase:
                reacted = react(line.replace(letter, "").replace(letter.upper(), ""))
                if minLength > len(reacted):
                    minLength = len(reacted)
            print(minLength)
