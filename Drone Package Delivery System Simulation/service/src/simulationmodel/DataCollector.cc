#include "DataCollector.h"
#include <iostream>

// Initializing the singleton instance as nullptr
DataCollector* DataCollector::singleton = nullptr;;

/**
 * @brief Constructs a new DataCollector object and opens the specified file.
 *
 * @param filename The name of the file to open for writing data.
 */
DataCollector::DataCollector(const std::string filename) : fileName(filename) {
    // SPRINT 2: Edit as needed. Just a current place holder.
    csvFile.open(fileName, std::ios::out | std::ios::app);
}

/**
 * @brief Destructor for DataCollector object. Closes the file if it's open
 */
DataCollector::~DataCollector() {
    if (csvFile.is_open()) {
        csvFile.close();
    }
}

DataCollector *DataCollector::GetInstance(const std::string& value) {
    if (singleton == nullptr) {
        singleton = new DataCollector(value);
    }
    return singleton;
}

void DataCollector::UpdateData(const std::string& message) {
  WriteToCSV(message);
}

void DataCollector::WriteToCSV(const std::string& message) {
    if (csvFile.is_open()) {
        csvFile << message << std::endl;
    }
}

void DataCollector::AddPackage() {
    packagesDelivered += 1;
}

void DataCollector::AddSteal() {
    packagesStolen += 1;
}

void DataCollector::AddHuman() {
    numHumans += 1;
}

void DataCollector::AddDrone() {
    numDrones += 1;
}

// New delivery time updated
void DataCollector::UpdateDeliveryTime(double newTime) {
  double sumTimes = avgDeliveryTime * (packagesDelivered);
  sumTimes += newTime;
  AddPackage();
  avgDeliveryTime = sumTimes/packagesDelivered;
}

// New steal time updated
void DataCollector::UpdateStealTime(double newTime) {
  double sumTimes = avgStealTime * (packagesStolen);
  sumTimes += newTime;
  AddSteal();
  avgStealTime = sumTimes/packagesStolen;
}

void DataCollector::IntervalUpdate() {  // Updates every 5 min
    WriteToCSV("__________Five Minute Recap____________");
    WriteToCSV("Packages Delivered:," + std::to_string(packagesDelivered));
    WriteToCSV("Packages Stolen:," + std::to_string(packagesStolen));
    WriteToCSV("Average Delivery Time:," + std::to_string(avgDeliveryTime));
    WriteToCSV("Average Package Steal Time After Delivery:," +
    std::to_string(avgStealTime));
    WriteToCSV("Drones in Simulation:," + std::to_string(numDrones));
    WriteToCSV("Humans in Simulation:," + std::to_string(numHumans));
    WriteToCSV("______________________________________");

    // Reset trackers
    packagesDelivered = 0;
    packagesStolen = 0;
    avgDeliveryTime = 0;
    avgStealTime = 0;
}


