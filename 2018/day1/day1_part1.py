frequency = 0
with open("input.txt") as f:
    for line in f.readlines():
        frequency = frequency + int(line)
print(frequency)
