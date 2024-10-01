#include "Robot.h"

#include "SimulationModel.h"
#include "AstarStrategy.h"
#include "Package.h"

Robot::Robot(const JsonObject& obj) : IEntity(obj),
requestedDelivery(true), packageAvailable(false) {}

Robot::~Robot() {
    if (toPackage) delete toPackage;
}



void Robot::update(double dt) {
    if (packageAvailable && !toPackage) {
        if (model) toPackage = new AstarStrategy(this->getPosition(),
        deliveredPackage->getDestination(),
        model->getGraph());
    }
    if (!packageAvailable && toPackage) {
      toPackage = nullptr;
    }
    if (toPackage) {
        toPackage->move(this, dt);
        if (toPackage->isCompleted()) {
            toPackage = nullptr;
            packageAvailable = false;
            std::string message = getName() +
            " received " +
            deliveredPackage->getName();
            notifyObservers(message);
            std::string receiver = "robot";
            deliveredPackage->packageClaimed(receiver);
            receive(package);
        }
    }
}


void Robot::receive(Package* p) { package = p; }

void Robot::receiveOrder(Package* p) { deliveredPackage = p; }

void Robot::updateRobotData() {
    DataCollector* datacollector = DataCollector::GetInstance("data.csv");
    datacollector->UpdateData("robot");
}

Vector3 Robot::getPackageDestination() const { return packageDestination; }

void Robot::setPackageDestination(Vector3 dest) { packageDestination = dest; }

// Robot is notified of the package
void Robot::notify(const std::string& message) const {
  size_t found = message.find("PackageClaimed");

  if (found != std::string::npos) {
    // does not get notified if package is delivered. just when claimed
    // get the ID number of the package
    size_t pos = message.size();
    while (pos > 0 && std::isdigit(message[pos - 1])) {
        --pos;
    }
    std::string numberStr = message.substr(pos);
    int id = std::stoi(numberStr);
    if (deliveredPackage) {
      if (deliveredPackage->getId() == id) {
        packageAvailable = false;
      }
    }  // Package has been claimed. not interested anymore
  } else {
    packageAvailable = true;
  }
}
