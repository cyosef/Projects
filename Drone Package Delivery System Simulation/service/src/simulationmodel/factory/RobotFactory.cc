#include "RobotFactory.h"

IEntity* RobotFactory::createEntity(const JsonObject& entity) {
  std::string type = entity["type"];
  if (type.compare("robot") == 0) {
    std::cout << "Robot Created" << std::endl;

    Robot* rob = new Robot(entity);

    Vector3 originalSpawn = rob->getPosition();

    int randX, randZ;
    randX = rand() % 1000 - 500;  // gets random number between -500 an 500
    randZ = rand() % 800 - 400;

    double posX, posZ;
    posX = rob->getPosition()[0] + randX;
    posZ = rob->getPosition()[2] + randZ;

    Vector3 robotSpawn(posX, rob->getPosition()[1], posZ);
    rob->setPosition(robotSpawn);

    return rob;
  }
  return nullptr;
}
