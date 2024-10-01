#ifndef GREEN_DECORATOR_H_
#define GREEN_DECORATOR_H_

#include "PackageColorDecorator.h"

/**
 * @class GreenDecorator
 * @brief Decorates a package with a green color.
 *
 * This class extends PackageColorDecorator to apply a specific green color
 * to a package. It sets the color by using hue, saturation, and lightness
 * values predefined for green.
 */
class GreenDecorator : public PackageColorDecorator {
 public:
    /**
   * @brief Constructs a new GreenDecorator object.
   *
   * @param p Pointer to the Package to be decorated.
   */
  GreenDecorator(Package*);
};

#endif  // GREEN_DECORATOR_H_
