Interlocking System Simulation
Project Description
This Java project simulates an interlocking system for a railway network using a Petri net model. The interlocking system ensures that trains move between different track sections without collisions, while giving priority to passenger trains over freight trains.

The system includes:

Places representing different sections of the railway.
Transitions modeling train movements between sections.
A train entry, movement, and exit system, preventing collisions and deadlocks.
This project also includes JUnit 4 tests to verify the correct functionality of the interlocking system, ensuring proper train movement and collision avoidance.

Features
Prevents trains from colliding by ensuring that only one train occupies a section at any given time.
Prioritizes passenger trains over freight trains.
Uses a Petri net structure to model train movement through different sections of the railway.
Includes unit tests using JUnit 4 to verify train entry, movement, collision avoidance, and exit.
How to Run
1. Clone the Repository
git clone https://github.com/your-username/interlocking-system.git
cd interlocking-system
