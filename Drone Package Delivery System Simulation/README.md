# team-001-57-finalproject

team-001-57

Jake Nauman - nauma109

Alexandra Postolaki - posto022

Caleb Yosef - yosef006

Sahal Saed - saed0006

# The project
This project is a simulation of a drone package delivery system implemented within the University of Minnesota Twin Cities campus. The goal was to create this simulation with sufficient functionality to model at a basic level how such a drone delivery system could function in reality.

# Youtube demonstration
Video demonstration and explanation:

https://youtu.be/K5A3x2eRXT8

# How to run
Linux:
After cloning the project, running is as simple as calling

$ make j

$ make run

Other (DOCKER):
Run the following image using Docker 

https://hub.docker.com/repository/docker/jakenauman/drone_sim/general

Once the container is running, execute the following commands from the main project folder:

$ docker build -t drone_sim .

$ docker run --rm -it -p 8081:8081 drone_sim

And the project will appear at http://localhost:8081/

# What does it do?
Within the simulation, the user can schedule the delivery of packages and observe as the drone entities route themselves to the destination. Upon scheduling a delivery, the user must specify a pickup and drop-off point. The drone will then beeline over the tops of buildings towards the package to pick it up and then follow a specified movement strategy above the road to deliver it. Robot entities are the receivers of the packages and will be notified as soon as their order has arrived. The robot will head to the package and claim it if the package hasn't been stolen yet. Humans and a helicopter exist in the simulation and will wander around from random location to random location. The user has the ability to spawn more humans and more drones to simulate more interactions. There is also a simulation speed bar to control the rate at which everything moves on the map.

# Extension #1: Porch Pirates
Every time a human is spawned, there is a chance that it will be a porch pirate. These pirates are colored red to signify they are evil. These porch pirates roam around the map and 'steal' packages that have been delivered, but whose owner has not claimed it yet. If the pirate is within a short distance, it will spot the package and run to go claim it. Multiple pirates can be locked in on the package if it is nearby. 

This is an interesting extension because its a realistic problem that can happen if a drone delivery system is implemented. Especially on a college campus, it is a great risk to have your package flown through the air and left unmonitored at a doorstep. Its important to show that there will always be people who want to steal the package and therefore there will always be a risk.

This adds to the existing project by providing a relevant job to the previously existing human entities. If a human is spawned as a pirate, it will be wrapped in a pirate human decorator using the decorator design pattern. This will enable the package tracking behavior but the normal human wandering behavior will be the same. It also implements the existing observer design pattern functionality by havivng the package notify its observers (robot owner and pirates) when it is delivered or claimed. This helps the robots and pirates know when the package can be claimed or if they should ignore it.

This new feature can be observed by scheduling a delivery that drops the package off near a pirate. The user may have to spawn a few extra humans to ensure a pirate exists in the simulation. If it is within range, the pirate will make its way over to steal it unless the robot gets there first. 

Retrospective:

We ran into a decent amount of trouble when designing the porch pirates extension. There are many specific and technical issues to think about because of how much behavior relates between objects. The design had to be tweaked throughout the process. The porch pirates weren't initailly going to be a decorator, but since they reuse a lot of the human code it made sense to add. It was also a bit challenging to develop a system where pirates could track available packages and know when they were nearby. Despite having to alter the design plan a bit, we were able to recollect and finish the extension with minimal delays. If we were to do it again, we would have a clear plan and go more in depth on the UML diagram for exactly how the extension would fucntion before starting.

![pirateuml](https://media.github.umn.edu/user/29277/files/bc82649f-14f9-4ee3-8849-fd2a9fec7628)

# Extension #2: Data Collection and Analysis
The data collection feature will track interesting data throughout the simulation and provide a summary to the user of its statistics. This feature is important because it can provide helpful analysis of different features of the simulation. From a business perspective this can help maximize profits and make sure that the idea is functional based on the statistics. Data collection is a very important part of creating a simulation such as this one because the results of testing can provide insight into the applicability of the system and any possible design flaws.

This adds to the existing work by allowing the simulation to be observed from the perspective of a data scientist in which entity behavior can be observed and tested. This is a perfect extension becaue hardly any structure needs to change and the data can be processed from all entities by using a singleton.

The Singleton design pattern is used to have many different objects all gain access to the same instance of one object. This is implemented through the DataCollector class. All objects that track data will obtain the instance of the DataCollector class to update it as certain things happen. For instance, every time a drone tracks that a package is delivered, it will call the data collector to add one delivery count to the running total. This way, all drones can contribute to the global total of packages delivered. Similarly, the data collector will track packages stolen and the count of drones and humans in the simulation along with other data.

The simulation will track package steal locations, the number of packages delivered, number of packages stolen, average delivery time, average steal time after delivery, number of humans and number of drones. This is tracked over intervals of five simulation minutes to output what has occurred within the time frame. The data is then post processed in python to demonstrate notable conclusions in a visual manner through graphs. For example, a simple graph we created displays packages stolen versus the number of humans in the simulation. As you could guess, they are positively correlated.

Retrospective:

We didn't have very much trouble building the data collection extension. We had a clear plan from the beginning and were able to divide and execute each part very well. Because the data collection built off what already existed, we did not have to change much of the surrounding code other than adding some function calls and ways to track each object's data. Having a separate python program to post process the data kept it very neat and optimal for demonstration. We were able to finish this extension in less than the one week sprint we had allotted.

# Printing Data Graphs:
Run the following code in your terminal from the team-001-57-finalproject" directory after running the simulation and obtaining the created csv file:

>> "python3 GraphGenerator.py" 


Running the above code generates 2 graphs: "Number of Drones vs. Average Delivery Times" and "Number of Humans vs. Number of Packages Stolen".


![Data uml](https://media.github.umn.edu/user/29277/files/7da4a9bd-6b00-4666-8ec3-0440ce2cb867)
