#ifndef DATACOLLECTOR_H
#define DATACOLLECTOR_H

#include <fstream>
#include <string>


/**
 * @class DataCollector
 * @brief Manages data collection and writing to a CSV file.
 *
 * This class implements a singleton pattern to ensure only one instance
 * manages data logging throughout the application. It handles tracking of
 * various simulation metrics and periodic updates.
 */
class DataCollector {
 public:
    /**
     * @brief Retrieves the singleton instance of DataCollector.
     * 
     * @param value The value being taken in to give to the data collecting file
     * @return DataCollector& The reference to the singleton instance of DataCollector.
     */
    static DataCollector *GetInstance(const std::string& value);

    /**
     * @brief Updates the data by writing a message to the CSV file.
     * @param message The data to write to the CSV file.
     */
    void UpdateData(const std::string& message);

    /**
     * @brief Adds a package to the global delivery counter.
     */
    void AddPackage();  // Adds package to global counter

    /**
     * @brief Adds a steal event to the counter.
     */
    void AddSteal();  // adds steal to counter

    /**
     * @brief Adds a human to the simulation counter.
     */
    void AddHuman();  // adds human to counter

    /**
     * @brief Adds a drone to the simulation counter.
     */
    void AddDrone();  // adds human to counter

    /**
     * @brief Periodically updates statistics every 5 minutes.
     */
    void IntervalUpdate();  // updates every 5 minutes

    /**
     * @brief Updates the average delivery time when a package is delivered.
     * @param newTime The new delivery time to account for.
     */
    void UpdateDeliveryTime(double newTime);
    /**
     * @brief Updates the average time a package is stolen after delivery.
     * @param newTime The new time to account for.
     */
    void UpdateStealTime(double newTime);  // when package stolen calls this

 protected:
    std::string fileName;
    std::ofstream csvFile;

    // Preventing use of default constructor -> ensures
    // objects can only be created through GetInstance() method.
    // (Which invokes the private constructor and enforces the
    // Singleton pattern.)
    DataCollector() = delete;

 private:
    // Singleton class constructor private. This prevents all other
    // classes from obtaining a Singleton object w/o using the
    // getInstance() method.
    /**
     * @brief Constructs a new DataCollector object and opens the specified file.
     *
     * @param filename The name of the file to open for writing data.
     */
    DataCollector(const std::string filename);

    /**
     * @brief Writes a message to the CSV file.
     * @param message The message to be written.
     */
    void WriteToCSV(const std::string& message);

    /**
     * @brief Destructor for DataCollector object. Closes the file if it's open
     */
    ~DataCollector();
    static DataCollector* singleton;
    //  data that should be contained in a singleton
    int packagesDelivered = 0;
    int packagesStolen = 0;
    int numHumans = 0;
    int numDrones = 0;
    double avgDeliveryTime = 0;
    double avgStealTime = 0;
};

#endif
