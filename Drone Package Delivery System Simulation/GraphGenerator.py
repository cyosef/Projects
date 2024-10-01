import csv
import matplotlib.pyplot as plt

# Reads csv file
def read_csv(filename):
    number_of_drones = []
    average_delivery_times = []
    number_of_humans = []
    number_of_packages_stolen = []

    # Open file
    with open(filename, 'r') as file:
        reader = csv.reader(file)
        for row in reader:
            # Collecting number of drones in simulation
            if row[0] == "Drones in Simulation:":
                number_of_drones.append(int(row[1]))
            # Collecting respective average delivery times
            elif row[0] == "Average Delivery Time:":
                average_delivery_times.append(float(row[1]))
            # Collecting number of humans in simulation
            elif row[0] == "Humans in Simulation:":
                number_of_humans.append(int(row[1]))
            # Collecting number of packages stolen
            elif row[0] == "Packages Stolen:":
                number_of_packages_stolen.append(int(row[1]))
    return number_of_drones, average_delivery_times, number_of_humans, number_of_packages_stolen

def plot_delivery_graph(drones, delivery_times):
    plt.plot(drones, delivery_times, marker='o')
    plt.title('Number of Drones vs. Average Delivery Times')
    plt.xlabel('Number of Drones')
    plt.ylabel('Average Delivery Time (minutes)')
    plt.grid(True)
    plt.show()

def plot_stolen_graph(humans, packages_stolen):
    plt.plot(humans, packages_stolen, marker='o', color='r')
    plt.title('Number of Humans vs. Number of Packages Stolen')
    plt.xlabel('Number of Humans')
    plt.ylabel('Number of Packages Stolen')
    plt.grid(True)
    plt.show()

if __name__ == "__main__":
    filename = "data.csv" 
    drones, delivery_times, humans, packages_stolen = read_csv(filename)

    # Plotting the Number of Drones vs. Average Delivery time Graph
    plot_delivery_graph(drones, delivery_times)

    # Plotting the Number of Humans vs. Number of Packages Stolen Graph
    plot_stolen_graph(humans, packages_stolen)