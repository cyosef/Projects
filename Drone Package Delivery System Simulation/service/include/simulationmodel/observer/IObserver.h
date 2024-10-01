#ifndef IOBSERVER_H_
#define IOBSERVER_H_

#include <string>

/**
 * @class IObserver
 * @brief Interface for an observer in the observer design pattern.
 */
class IObserver {
 public:
   /**
   * @brief Receive notification from a publisher.
   * @param message The message from the publisher.
   */
  virtual void notify(const std::string &message) const = 0;
};

#endif  // IOBSERVER_H_
