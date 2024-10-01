#include "DroneFactory.h"

IEntity* DroneFactory::createEntity(const JsonObject& entity) {
  std::string type = entity["type"];
  if (type.compare("drone") == 0) {
    DataCollector* datacollector = DataCollector::GetInstance("data.csv");
    datacollector->AddDrone();

    std::cout << "Drone Created" << std::endl;
    return new Drone(entity);
  }
  return nullptr;
}
