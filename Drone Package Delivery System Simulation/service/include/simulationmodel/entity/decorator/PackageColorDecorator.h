#ifndef PACKAGE_COLOR_DECORATOR_H_
#define PACKAGE_COLOR_DECORATOR_H_

#include "PackageDecorator.h"

/**
 * @class PackageColorDecorator
 * @brief Decorates a package with a specific color.
 *
 * This class extends PackageDecorator to apply color properties to a package,
 * allowing dynamic color changes during simulation.
 */
class PackageColorDecorator : public PackageDecorator {
 private:
  double hue = 0;
  double saturation = 0;
  double light = 0;

 public:
  /**
   * @brief Constructs a PackageColorDecorator with color settings.
   * @param package Pointer to the Package to decorate.
   * @param hue Hue component of the color.
   * @param saturation Saturation component of the color.
   * @param light Lightness component of the color.
   */
  PackageColorDecorator(Package*, double = 0, double = 0, double = 0);

  /**
   * @brief Returns the color of the package as a string.
   * @return String representing the color.
   */
  std::string getColor() const;
};

#endif  // PACKAGE_COLOR_DECORATOR_H_
