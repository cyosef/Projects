#include "Package.h"

#include "Robot.h"
#include "SimulationModel.h"
#include "IObserver.h"

Package::Package(const JsonObject& obj) : IEntity(obj) {}

Vector3 Package::getDestination() const { return destination; }

std::string Package::getStrategyName() const { return strategyName; }

Robot* Package::getOwner() const { return owner; }

bool Package::requiresDelivery() const { return requiresDelivery_; }

void Package::setStrategyName(std::string strategyName_) {
  strategyName = strategyName_;
}

void Package::update(double dt) { timeToSteal += dt; }

void Package::initDelivery(Robot* owner) {
  this->owner = owner;
  owner->requestedDelivery = false;
  requiresDelivery_ = false;
  destination = owner->getPackageDestination();
}

bool Package::checkAvailablePickup() {
  return packageAvailableForPickup;
}

void Package::packageClaimed(std::string receiver) {
  int id = getId();
  std::string message = "PackageClaimed" + std::to_string(id);
  packageAvailableForPickup = false;
  notifyObservers(message);
  if (receiver == "pirate") {
    updatePackageData();
  }
}

void Package::handOff() {
  this->timeToSteal = 0;
  int id = getId();
  packageAvailableForPickup = true;
  std::cout << "available" << std::endl;
  std::string message = "PackageDelivered" + std::to_string(id);
  notifyObservers(message);
}


//  stolen packages
void Package::updatePackageData() {
  DataCollector* datacollector = DataCollector::GetInstance("data.csv");
  datacollector->UpdateStealTime(this->timeToSteal);
}
