from utils import osmHelper as oh

berlinMap = open("../../../../berlin-latest.osm")
graph = oh.read_osm(berlinMap)
print(graph)
extremeNorth = (52.671235,13.279887)
extremeSouth = (52.34046,13.647459)
extremeEast = (52.43818, 13.75809)
extremeWest = (52.435348,13.074094)