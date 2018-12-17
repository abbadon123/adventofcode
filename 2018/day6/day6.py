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
            closest = []
            closest.append(c)
    return closest


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
            if len(closest_coordinate(coordinate_distances)) == 1:
                closest_coordinates.append(closest_coordinate(coordinate_distances)[0])

        sorted_closest_coordinates = sorted(Counter(closest_coordinates).items(), key=lambda x: x[1], reverse=True)
        # finite_coordinates is not working, but second answer is ok
        finite_coordinates = [coordinate for coordinate in sorted_closest_coordinates if coordinate[0][0] > min_x and coordinate[0][0] < max_x and coordinate[0][1] > min_y and coordinate[0][1] < max_y]
        pprint.pprint(finite_coordinates)
        # [((131, 322), 4529),
        #  ((198, 248), 4284),
