import os
import pandas as pd
import bot
import datetime as dt
import bot
from time import time
import json
import matplotlib.pyplot as plt

operations = [
    "BerlinGraphast",
    "BerlinGraphHopper",
    "BerlinGraphHopperContracted",
    "BerlinNeo4j"
]

def resetFile(filename="../test_results.csv"):
    with open(filename,'w') as f:
        f.write("")

def generateTimes(n_tests=10,from_file="../tests.txt",operations=operations,save_file="../test_results.csv"):
    resetFile()

    start = time()
    #files = [open("../{}Results.csv".format(file_name)) for file_name in operations]
    result_file = open(save_file,'a')
    with open(from_file) as tests_file:
        result_file.write(','.join(operations))
        result_file.write(",Distance(m)")
        result_file.write('\n')
        
        for line in tests_file:
            positions = line.split(",")
            positions[3] = positions[3]
            #print(positions)

            results = []
            for op in operations:
                cmd = 'cd ../ && mvn exec:java -Dexec.mainClass="org.graphast.example.{}Example" -Dexec.args="{} {} {} {} {}Output.txt" > /dev/null'.format(op, \
                    positions[0], positions[1], positions[2], positions[3], op)
                sum_times = 0
                #obtendo média dos testes
                for _ in range(n_tests):
                    os.system(cmd)
                    with open("../{}Output.txt".format(op)) as f:
                        sum_times += int(f.readline()[0:-1])
                
                results.append(str(sum_times/n_tests))  

            with open("../{}Output.txt".format(operations[0])) as f:
                f.readline()
                results.append(f.readline()[0:-1])

            print(results)
            result_file.write("{}\n".format(','.join(results)))
    end = time()
    result_file.close()
    bot.send("Tempos gerados em {}".format(str(dt.timedelta(seconds=(end-start)))))

def separateData(max_time=200):
    tests = np.loadtxt("../tests.txt",delimiter=',')
    cases = []
    for i,row in df.iterrows():
        if row["BerlinGraphHopper"]<=max_time:
            cases.append(tests[i,:])
            #print(row["BerlinGraphHopper"])
    return cases

def loadData(file_name="../test_results.csv"):
    return pd.read_csv(file_name)

def loadMemoryData(filename="../memory_results.json"):
    with open(filename) as f:
        data = json.load(f)
    return data

def saveData(data,file_name="separated_test.txt"):
    with open(file_name) as f:
        for d in data:
            f.write(','.join([str(v) for v in d]))

def highlight_max(s):
    '''
    highlight the maximum in a Series red.
    '''
    is_max = s == s.max()
    return ['color: red' if v else '' for v in is_max]

def highlight_min(s):
    '''
    highlight the minimum in a Series green.
    '''
    is_min = s == s.min()
    return ['color: green' if v else '' for v in is_min]

def plotPie(memData,alg):
    objects = memData[alg]["objects"]
    total = sum([objects[o] for o in objects])
    objects = {o:objects[o] for o in objects if objects[o]>total/100}
    objects["Others"] = sum([objects[o] for o in objects]) - total
    values = [objects[o] for o in objects]
    fig = plt.figure(figsize=(9,9))
    ap = plt.subplot(111)
    keys = [key.split("@")[0] for key in objects]
    ap.pie(labels=keys,x=values,autopct='%1.1f%%')
    ap.plot()
    ap.axis("equal")
    plt.legend(keys,bbox_to_anchor=(1.6, 1))
    plt.title("{} objects sizes".format(alg),y=1.08)

def plotMemoryUse(memData):
    sizes = [float(memData[c]["size"])/1024 for c in memData]
    n = len(sizes)
    fig = plt.figure(figsize=(10,5))
    ax = plt.subplot(111)
    plt.grid()
    ax.bar(range(n),sizes,width=0.6)
    ax.set_axisbelow(True)
    plt.xticks(range(n),memData.keys())
    plt.ylabel("Memory in KB")
    plot = plt.title("Memory use for the graphs")

def plotTopMemoryClasses(memData,top=5):
    n_algs = len(memData.keys())

    abreviations = {
        "Graphast":"GP",
        "GraphHopper": "GH",
        "GraphHopperContracted": "GHC",
        "Vanila": "VN"
    }

    biggest_objects_names = []
    biggest_objects_values = []

    for alg in memData:
        sorted_objects = sorted(memData[alg]["objects"].keys(),key=lambda x: memData[alg]["objects"][x],reverse=True)
        for obj in sorted_objects[0:5]:
            biggest_objects_names.append("{} - {}".format(abreviations[alg],obj.split("@")[0][0:-1]))
            biggest_objects_values.append(memData[alg]["objects"][obj]/1024/1024)

    fig = plt.figure(figsize=(12,8))
    ax = plt.subplot(111)
    ax.bar(range(n_algs*top),biggest_objects_values)
    plt.grid()
    ax.set_axisbelow(True)
    plt.xticks(range(n_algs*top),biggest_objects_names,rotation='vertical')
    plt.ylabel("Memory in MB")
    plot = plt.title("Memory use for the classes used in the graphs")