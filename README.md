Status:
======
Latest Status: [![Build Status](https://travis-ci.org/Sheepzez/Matrixonator-Java.svg?branch=master)](https://travis-ci.org/Sheepzez/Matrixonator-Java)

ProjectGoav's Fork Status: [![Build Status](https://travis-ci.org/projectgoav/Matrixonator-Java.svg?branch=master)](https://travis-ci.org/projectgoav/Matrixonator-Java)

A Java port of [The-Matrixonator](https://github.com/Sheepzez/The-Matrixonator) - A program written in Python designed to deal with mathematical matrices. This has already become a richer, better application than the original Matrixonator.

Currently uses JavaFX (a built-in to Java 8 GUI API that replaces AWT), and [ControlsFX](http://fxexperience.com/controlsfx/) to provide features like Dialogs and Wizards that will be implemented in coming Java 8 updates.

Project tries to use Google Java style throughout.
[Link to Eclipse style file](https://code.google.com/p/google-styleguide/source/browse/trunk/eclipse-java-google-style.xml)

View the [JavaDocs](http://sheepzez.github.io/Matrixonator-Java/doc/index.html) for the project.

To Do:
======
See the issue tracker for feature/bugfix requests. Feel free to create some too!

Dependencies:
====================
- Java 8 SDK
- JavaFX SDK
- ControlsFX jar (in /lib)
- openjfx-dialogs jar (in /lib)
- JUnit 4 (included in Java 8 SDK)

Screenshots:
============

Basic start up screen with some added matrices.

![Image](https://cloud.githubusercontent.com/assets/3807889/5889990/9d763196-a43a-11e4-8c48-68538c51ec4a.png)

Clicking 'Show Data' with Identity2 selected.

![Image](https://cloud.githubusercontent.com/assets/3807889/5889991/a18388ec-a43a-11e4-9796-3a3709805a8b.png)

Clicking 'New...' to add a matrix.

![Image](https://cloud.githubusercontent.com/assets/3807889/5889992/a6d04f9c-a43a-11e4-9919-d0d1e6170333.png)

Entering data to the matrix (Blank becomes 0).
![Image](https://cloud.githubusercontent.com/assets/3807889/5889993/aa5a0e1e-a43a-11e4-865c-cc061b93f13e.png)

New matrix has been created, and added to the list.
![Image](https://cloud.githubusercontent.com/assets/3807889/5889995/af9d8a68-a43a-11e4-9fad-bd2c368340d7.png)

Calculating the reduced row echelon form of B.

![Image](https://cloud.githubusercontent.com/assets/3807889/5889998/b6094fcc-a43a-11e4-9dd6-b9c3b60ca602.png)

Other properties (eigenvalues, inverse, etc) will be able to be calculated on demand similar to the RREF.
