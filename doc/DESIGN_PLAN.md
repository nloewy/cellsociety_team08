# Cell Society Design Plan
### Team 8
### Names


#### Examples

Here is a graphical look at my design:

![This is cool, too bad you can't see it](images/online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


Here is our amazing UI:

![This is cool, too bad you can't see it](images/29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).



## Overview

In our program, we hope to model 5 different kinds of Cellular Automata (CA). In the Cell Society project, we hope that our final implementation of our code, we will be able to model any form of Cellular Automata relatively easily, regardless of any transition functions, parameters, or use cases. We hope to do this by writing generalizable, extendable, and modular code, as we have discussed in CS308.

On a higher level, we hope to break down our code into three different sections. The model section of our codebase will house all of our core algorithms, including the code for simulating these automata, and the basic units for these automata – Cells. The view section of our codebase will contain code for our user interface, which we will build using JavaFX. Lastly, the config package will enable our code to read and write XML files, so we can load, save configurations and the state of our simulations.

## User Interface
__Home Page:__


![landing page](images/landing-page.png "title, homepage")


__Select simulation page:__


![select simulation page](images/choose-simulation.png "menu, select simulation")
- clicking on ```back``` goes back to home page
- clicking on ```Conway's Game of Life``` goes to select pattern page
- clicking on ```Load Custom Simulation``` goes to upload file page
- clicking on other buttons goes to simulation page


__Select pattern page:__


![select simulation pattern](images/choose-pattern.png "choose simulation pattern")


- clicking on ```back``` goes to select simulation page
- clicking on other buttons goes to simulation page


__Upload custom configuration file page:__


![upload custom xml](images/upload-file.png "upload custom xml configuration file")


- shows the warning message if the file is not an xml file
- clicking on ```back``` goes back to select simulation page


__Simulation Page:__


![Simulation](images/simulation.png "simulation page")


- clicking on ```back``` goes back to select simulation page
- clicking on ```about``` pauses current simulation and goes to the about page
- clicking on ```restart``` restarts the current simulation
- user is able to change the simulation speed and save simulation on this page


__About Page:__


![simulation info](images/about.png "simulation info page")


- clicking on ```back``` goes back to simulation

## Configuration File Format


## Design Details


## Use Cases


## Design Considerations


## Team Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3
