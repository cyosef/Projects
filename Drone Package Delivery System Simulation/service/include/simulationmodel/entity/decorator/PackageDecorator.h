#ifndef PACKAGE_DECORATOR_H_
#define PACKAGE_DECORATOR_H_

#include "IEntityDecorator.h"
#include "Package.h"
#include "Robot.h"

/**
 * @class PackageDecorator
 * @brief Decorates a package entity with additional functionality.
 *
 * This class provides a concrete implementation of IEntityDecorator for Package entities,
 * allowing customization and enhancement of Package behaviors.
 */
class PackageDecorator : public IEntityDecorator<Package> {
 public:
   /**
   * @brief Constructs a PackageDecorator with a specific Package.
   * @param p Pointer to the Package to decorate.
   */
  PackageDecorator(Package* p) : IEntityDecorator(p) {}

  /**
   * @brief Gets the Package's destination
   * @return The Package's destination
   */
  virtual Vector3 getDestination() const { return sub->getDestination(); }

  /**
   * @brief Returns the name of the strategy for this package
   *
   * @returns String name of strategy
   */
  virtual std::string getStrategyName() const { return sub->getStrategyName(); }

  /**
   * @brief Returns the owner of the package
   *
   * @return pointer to Robot owning the package
   */
  virtual Robot* getOwner() const { return sub->getOwner(); }

  /**
   * @brief Returns whether or not the package needs to be delivered
   *
   * @return boolean value of requiresDelivery
   */
  virtual bool requiresDelivery() const { return sub->requiresDelivery(); }

  /**
   * @brief Set the Strategy Name
   *
   * @param strategyName_ Strategy name
   */
  virtual void setStrategyName(std::string strategyName_) {
    return sub->setStrategyName(strategyName_);
  }
  void update(double dt) { timeToSteal += dt; }

  virtual bool checkAvailablePickup() { return sub->checkAvailablePickup(); }
  virtual void initDelivery(Robot* owner) { return sub->initDelivery(owner); }
  virtual void handOff() {
    timeToSteal = 0;
    int id = getId();
    packageAvailableForPickup = true;
    std::cout << "available" << std::endl;
    std::string message = "PackageDelivered" + std::to_string(id);
    notifyObservers(message);
  }
  virtual void packageClaimed(std::string receiver) {
    int id = getId();
    std::string message = "PackageClaimed" + std::to_string(id);
    packageAvailableForPickup = false;
    notifyObservers(message);
    if (receiver == "pirate") {
      updatePackageData();
    }
  }
  virtual void updatePackageData() {
    DataCollector* datacollector = DataCollector::GetInstance("data.csv");
    datacollector->UpdateStealTime(this->timeToSteal);
  }

 protected:
    double timeToSteal = 0;
};

#endif
