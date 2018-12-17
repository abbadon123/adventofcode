from collections import Counter
import pprint
import sys


def distance(x, y):
    return abs(x[0] - y[0]) + abs(x[1] - y[1])


def closest_coordinate(coordinates):
    closest = []
    min_distance = sys.maxsize
    for c, d in coordinates:
        if d == min_distance:
            closest.append(c)
        if d < min_distance:
            min_distance = d
            closest = [c]
    return closest, min_distance


if __name__ == '__main__':
    with open("input.txt") as f:
        coordinates = []
        for line in f.read().splitlines():
            coordinate = (int(line.split(", ")[0]), int(line.split(", ")[1]))
            coordinates.append(coordinate)
        min_x = min([c[0] for c in coordinates])
        max_x = max([c[0] for c in coordinates])
        min_y = min([c[1] for c in coordinates])
        max_y = max([c[1] for c in coordinates])
        distances = {}
        for x in range(min_x, max_x + 1):
            for y in range(min_y, max_y + 1):
                for coordinate in coordinates:
                    distance_from_point_to_x_y = distances.get((x, y), [])
                    distance_from_point_to_x_y.append((coordinate, distance((x, y), coordinate)))
                    distances[(x, y)] = distance_from_point_to_x_y
        closest_coordinates = []
        for coordinate_distances in distances.values():
            closest_coordinates_list, _ = closest_coordinate(coordinate_distances)
            if len(closest_coordinates_list) == 1:
                closest_coordinates.append(closest_coordinates_list[0])
        # finite_coordinates is not working ... but one of the answers is ok, the answer is 4284
        finite_coordinates = [coordinate for coordinate in closest_coordinates if min_x <
                              coordinate[0] < max_x and min_y < coordinate[1] < max_y]
        pprint.pprint(
            sorted(Counter(finite_coordinates).items(), key=lambda coord_to_count: coord_to_count[1], reverse=False))
        # ((198, 248), 4284), this is ok
        # ((53, 309), 4403),
        # ((131, 322), 5099)]

        # part 2
        count = 0
        for x in range(min_x, max_x + 1):
            for y in range(min_y, max_y + 1):
                if sum([distance((x, y), coordinate) for coordinate in coordinates]) < 10000:
                    count = count + 1
        print(count)
