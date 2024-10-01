#ifndef BLUE_DECORATOR_H_
#define BLUE_DECORATOR_H_

#include "PackageColorDecorator.h"

/**
 * @class BlueDecorator
 * @brief Decorates a package with a blue color.
 *
 * This class extends PackageColorDecorator to apply a specific blue color
 * to a package. It sets the color by using hue, saturation, and lightness
 * values predefined for blue.
 */
class BlueDecorator : public PackageColorDecorator {
 public:
   /**
   * @brief Constructs a new BlueDecorator object.
   *
   * @param p Pointer to the Package to be decorated.
   */
  BlueDecorator(Package*);
};

#endif  // BLUE_DECORATOR_H_
