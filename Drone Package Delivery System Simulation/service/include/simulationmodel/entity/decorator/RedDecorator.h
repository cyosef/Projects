#ifndef RED_DECORATOR_H_
#define RED_DECORATOR_H_

#include "PackageColorDecorator.h"

/**
 * @class RedDecorator
 * @brief Decorates a package with a red color.
 *
 * This class extends PackageColorDecorator to apply a specific red color
 * to a package. It sets the color by using hue, saturation, and lightness
 * values predefined for red.
 */
class RedDecorator : public PackageColorDecorator {
 public:
  /**
   * @brief Constructs a new RedDecorator object.
   *
   * @param p Pointer to the Package to be decorated.
   */
  RedDecorator(Package*);
};

#endif  // RED_DECORATOR_H_
