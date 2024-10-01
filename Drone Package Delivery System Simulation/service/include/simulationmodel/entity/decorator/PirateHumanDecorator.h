#ifndef PIRATE_HUMAN_DECORATOR_H_
#define PIRATE_HUMAN_DECORATOR_H_

#include "HumanDecorator.h"
#include <unordered_set>
#include "Human.h"
#include "Package.h"
#include "SimulationModel.h"
#include "AstarStrategy.h"
#include "IStrategy.h"
#include "IEntity.h"


/**
 * @class PirateHumanDecorator
 * @brief Decorates a Human entity with porach pirate-like behaviors.
 *
 * This class extends HumanDecorator to simulate porch pirate behaviors such as seeking packages
 */
class PirateHumanDecorator : public HumanDecorator, public IObserver {
 private:
  double hue = 0;
  double saturation = 100;
  double light = 50;

 public:
  /**
   * @brief Constructs a PirateHumanDecorator with specific porch pirate behaviors
   * @param h Pointer to the Human to be decorated as a pirate.
   */
  PirateHumanDecorator(Human* h);

  /**
   * @brief PirateHumanDecorator destructor
   */
  ~PirateHumanDecorator();

  /**
   * @brief Returns the color representing the pirate entity.
   * @return String describing the pirate color.
   */
  std::string getColor() const;

  /**
   * @brief Updates the human's position
   * @param dt Delta time
   */
  void update(double dt);
  void updatePirateData();
  void notify(const std::string& message) const;

  /**
   * @brief Inserts the packages pirate is watching into vector
   * @param p Pointer to package pirate is watching
   */
  void addPackageToWatch(Package* p);

  /**
   * @brief Removes packages from vector if they've been claimed
   * @param id package ID to remove from vector
   */
  void packageClaimed(int id) const;


 private:
  mutable std::set<Package*> packages;
  mutable std::set<int> availablePackages;
  static Vector3 kellerPosition;
  Package* trackedPackage = nullptr;
  IStrategy* movement = nullptr;
  bool trackingPackage = false;
  bool atKeller = false;
};
#endif  // PIRATE_HUMAN_DECORATOR_H_
