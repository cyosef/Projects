#include "HumanFactory.h"
#include "PirateHumanDecorator.h"
#include "DataCollector.h"

IEntity* HumanFactory::createEntity(const JsonObject& entity) {
  std::string type = entity["type"];
  std::string name = entity["name"];
  if (type.compare("human") == 0) {
    Human* h = nullptr;

    DataCollector* datacollector = DataCollector::GetInstance("data.csv");
    datacollector->AddHuman();

    int randomNumber = rand() % 100;  // 40% of spawned humans will be pirates
    if (randomNumber > 60 && name != "Human-OG") {
        std::cout << "Porch Pirate Created\n";
        h = new PirateHumanDecorator(new Human(entity));
        h->setName("Pirate");
    } else {
        h = new Human(entity);
        std::cout << "Human Created\n";
    }

    //  Spawn humans in a random place on the map
    double posX, posZ;
    posX = rand() % (1200 + 1200 + 1) -1200;
    posZ = rand() % (800 + 800 + 1) -800;;
    Vector3 spawn(posX, 290, posZ);
    h->setPosition(spawn);
    return h;
  }
  return nullptr;
}
