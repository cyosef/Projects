#define _USE_MATH_DEFINES
#include "PirateHumanDecorator.h"

#include <cmath>
#include <limits>

#include "AstarStrategy.h"
#include "BeelineStrategy.h"
#include "SimulationModel.h"

Vector3 PirateHumanDecorator::kellerPosition(64.0, 254.0, -210.0);

PirateHumanDecorator::PirateHumanDecorator(Human* h) : HumanDecorator(h) {
}

PirateHumanDecorator::~PirateHumanDecorator() {
  if (movement) delete movement;
}

std::string PirateHumanDecorator::getColor() const {
    return "red";
}

void PirateHumanDecorator::update(double dt) {
    /*Same behavior as humans but if it sees a 
    package delivered nearby, it goes to steal it*/
  if (movement && !movement->isCompleted()) {
    movement->move(this, dt);
    bool nearKeller = getPosition().dist(kellerPosition) < 85;
    if (nearKeller && !this->atKeller) {
      std::string message = this->getName() + " visited Keller hall";
      notifyObservers(message);
    }
    atKeller = nearKeller;

    if (trackingPackage && trackedPackage) {
      int id = trackedPackage->getId();
      bool available = (availablePackages.find(id) != availablePackages.end());
      if (!available) {
        movement = nullptr;
        trackingPackage = false;
        trackedPackage = nullptr;
      }
    }
    // If one of the available packages is nearby, go get it!
    if (!trackingPackage) {
      for (auto package : packages) {
        bool withinDistance = getPosition().dist(package->getDestination())
        < 200;
        bool available = (availablePackages.find(package->getId())
        != availablePackages.end());
        if (withinDistance && available) {
          trackingPackage = true;
          trackedPackage = package;
          delete movement;

          Vector3 dest = trackedPackage->getDestination();
          if (model) movement = new AstarStrategy(getPosition(),
          dest, model->getGraph());
        }
      }
    }
    } else {
    if (movement) {
      if (movement->isCompleted() && trackingPackage) {
        std::string message = "Pirate has stolen " + trackedPackage->getName();
        notifyObservers(message);
        updatePirateData();
        trackingPackage = false;
        std::string receiver = "pirate";
        trackedPackage->packageClaimed(receiver);
      }
    }
    if (movement) delete movement;
    Vector3 dest;
    dest.x = ((static_cast<double>(rand())) / RAND_MAX) * (2900) - 1400;
    dest.y = position.y;
    dest.z = ((static_cast<double>(rand())) / RAND_MAX) * (1600) - 800;
    if (model) movement = new AstarStrategy(getPosition(),
    dest, model->getGraph());
  }
}

void PirateHumanDecorator::addPackageToWatch(Package* p) {
  packages.insert(p);
}

// if package is claimed, pirate wont go after it so removes it from list
void PirateHumanDecorator::packageClaimed(int id) const {
  // removes package object if id has been claimed
  for (auto it = packages.begin(); it != packages.end();) {
  if ((*it)->getId() == id) {
      it = packages.erase(it);
  } else {
      ++it;
  }
  }
  availablePackages.erase(id);
}


// pirate noitified of the package being stolen or dropped off
void PirateHumanDecorator::notify(const std::string& message) const {
  size_t found = message.find("PackageClaimed");

  if (found != std::string::npos) {  // package claimed. remove from watchlist
    // get the ID number of the package
    size_t pos = message.size();
    while (pos > 0 && std::isdigit(message[pos - 1])) {
        --pos;
    }
    std::string numberStr = message.substr(pos);
    int id = std::stoi(numberStr);

    packageClaimed(id);  // Package has been claimed. not interested
  } else {  // package delivered. can get it
    // get the ID number of the package
    size_t pos = message.size();
    while (pos > 0 && std::isdigit(message[pos - 1])) {
        --pos;
    }
    std::string numberStr = message.substr(pos);
    int id = std::stoi(numberStr);
    availablePackages.insert(id);
  }
}

void PirateHumanDecorator::updatePirateData() {
    DataCollector* datacollector = DataCollector::GetInstance("data.csv");
    double x = getPosition().x;
    double y = getPosition().y;
    double z = getPosition().z;
    std::string pirateData = "Package stolen at: " + std::to_string(x) +
    ", " + std::to_string(y) + ", " + std::to_string(z);
    datacollector->UpdateData(pirateData);
}
