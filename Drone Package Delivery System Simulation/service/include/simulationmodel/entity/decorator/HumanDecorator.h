#ifndef HUMAN_DECORATOR_H_
#define HUMAN_DECORATOR_H_

#include "IEntityDecorator.h"
#include "Human.h"


/**
 * @class HumanDecorator
 * @brief Decorates a human entity with additional functionality.
 *
 * This class provides a concrete implementation of IEntityDecorator for Human entities,
 * allowing customization and enhancement of Human behaviors.
 */
class HumanDecorator : public IEntityDecorator<Human> {
 public:
   /**
   * @brief Constructs a HumanDecorator with a specific Human.
   * @param h Pointer to the Human to decorate.
   */
  HumanDecorator(Human* h) : IEntityDecorator(h) {}
  void update(double dt) override { return sub->update(dt); }
};

#endif
