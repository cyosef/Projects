#ifndef IPUBLISHER_H_
#define IPUBLISHER_H_

#include <set>
#include <string>

#include "IObserver.h"


/**
 * @class IPublisher
 * @brief Interface for a publisher in the observer design pattern.
 */
class IPublisher {
 public:
   /**
   * @brief Add an observer to the notification list.
   * @param o Pointer to the observer to be added.
   */
  void addObserver(const IObserver* o);

  /**
   * @brief Remove an observer from the notification list.
   * @param o Pointer to the observer to be removed.
   */
  void removeObserver(const IObserver* o);

    /**
   * @brief Notify all registered observers with a message.
   * @param message The message to notify the observers with.
   */
  void notifyObservers(const std::string& message) const;

 private:
  std::set<const IObserver*> observers;
};

#endif  // IPUBLISHER_H_
