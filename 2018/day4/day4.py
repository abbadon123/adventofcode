import re
from datetime import datetime, date, time


def parse(text):
    m = re.search('\[(.+)\] (.+)', text)
    return (datetime.strptime(m.group(1), "%Y-%m-%d %H:%M"), m.group(2))


def begins(record):
    return str(record[1]).__contains__("Guard")


def guard_id(record):
    return str(record[1]).split("#")[1].split(" ")[0]


def asleep(record):
    return str(record[1]).__contains__("asleep")


def wakeup(record):
    return str(record[1]).__contains__("wakes")


def mostAsleepGuard(guards):
    maxSleep = 0
    for id, sumOfMinutes in [(id, sum(minutes.values())) for id, minutes in guards.items()]:
        if sumOfMinutes > maxSleep:
            maxSleep = sumOfMinutes
            maxId = id
    return maxId


def guards(records):
    guards = {}
    for record in records.items():
        if begins(record):
            id = guard_id(record)
        elif asleep(record):
            asleepAt = record[0]
        elif wakeup(record):
            for minute in range(asleepAt.minute, record[0].minute):
                guard = guards.setdefault(id, {})
                guard[minute] = guard.get(minute, 0) + 1
    return guards


def mostAsleepMinute(guard):
    maxCount = 0
    for minute, count in guard.items():
        if maxCount < count:
            maxCount = count
            maxMinute = minute
    return (maxMinute, maxCount)


if __name__ == '__main__':
    with open("input.txt")as f:
        records = dict([parse(line) for line in f.read().splitlines()])
        sorted = {key: records[key] for key in sorted(records.keys())}
        guards = guards(sorted)
        guard_id = mostAsleepGuard(guards)
        print(int(guard_id) * int(mostAsleepMinute(guards[guard_id])[0]))
        maxAsleepMinute = (0, 0)
        for guard_id in guards.keys():
            minute, count = mostAsleepMinute(guards[guard_id])
            if maxAsleepMinute[1] < count:
                maxAsleepMinute = (minute, count)
                maxAsleepGuardId = guard_id
        print(int(maxAsleepMinute[0]) * int(maxAsleepGuardId))
