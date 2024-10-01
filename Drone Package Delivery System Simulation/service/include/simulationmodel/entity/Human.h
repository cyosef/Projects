#ifndef HUMAN_H_
#define HUMAN_H_

#include "IEntity.h"
#include "IObserver.h"
#include "IStrategy.h"

class Human : public IEntity{
 public:
  /**
   * @brief Drones are created with a name
   * @param obj JSON object containing the drone's information
   */
  Human(const JsonObject& obj);

  /**
   * @brief destructor for Human.
   */
  ~Human();

  /**
   * @brief Updates the human's position
   * @param dt Delta time
   */
  void update(double dt);

 private:
  static Vector3 kellerPosition;
  IStrategy* movement = nullptr;
  bool atKeller = false;
};

#endif
