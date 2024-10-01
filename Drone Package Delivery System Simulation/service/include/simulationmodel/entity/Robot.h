#ifndef ROBOT_H
#define ROBOT_H

#include <vector>

#include "IEntity.h"
#include "IStrategy.h"
#include "Drone.h"
#include "IObserver.h"
#include "math/vector3.h"
#include "util/json.h"

class Package;

/**
 * @class Robot
 * @brief Represents a robot in a physical system
 *
 * Robots move using euler integration based on a specified
 * velocity and direction.
 */
class Robot : public IEntity, public IObserver{
 public:
  /**
   * @brief Constructor
   * @param obj JSON object containing the robot's information
   */
  Robot(const JsonObject& obj);

  /**
   * @brief Robot destructor
   */
  ~Robot();

  /**
   * @brief Updates the Package
   *
   * @param dt difference in time since last update
   */
  void update(double dt);

  /**
   * @brief Receives the passed in package
   *
   * @param Package Package to be received by the robot
   */
  void receive(Package* p);

  /**
   * @brief Updates the robot's data on being sent to the CSV file
   */
  void updateRobotData();

  Vector3 getPackageDestination() const;

  /**
   * @brief Sets package's final destination
   *
   * @param Package Vector destination with package coordinates
   */
  void setPackageDestination(Vector3 dest);

  /**
   * @brief Receives the passed in package
   *
   * @param Package* Package to be received by the robot
   */
  void receiveOrder(Package* p);

   /**
   * @brief Receive notification from a publisher.
   * @param message The message from the publisher.
   */
  void notify(const std::string& message) const;

  bool requestedDelivery = true;

 protected:
  Vector3 packageDestination;
  Package* package = nullptr;
  Package* deliveredPackage = nullptr;
  IStrategy* toPackage = nullptr;
  mutable bool packageAvailable = true;
};

#endif  // ROBOT_H
