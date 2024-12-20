# **Final Project Object-Oriented-Programming**

## **Description & Concept**
RChat is a Java messaging application that implements GUI-socket-based server-client concept. It uses localhost as its Server and manage TCP (Transmission Control Protocol) communication between Clients.

## **Prequisities**
>_Java 18 / higher_  
_JavaFX 19_  
_VSCode (This Project has been tested on VSCode, and may have errors on other IDE)_

## **How To Use**
### **First Task : Setting up JavaFX**
Clone this project on your VSCode
>git clone https://github.com/zeon-kun/FP-PBO.git
  
After that, go to your Java Projects, then click on Referenced Libraries. 

Once the file explorer opened, search for your JavaFX folder, and go to **lib** and select all of the jar libraries. _(This will create a new folder .vscode where there will be a file called settings.json inside it)._

>_(optional): you can delete unwanted referenced libraries other than **your downloaded** JavaFX jar files_

After that, press **CTRL+Shift+D** to create a launch.json. And then, go to launch.json and add "vmArgs" to your LaunchClientPanel section and change the path to your JavaFX lib folder path.

_below is an example of the vmArgs._
>"vmArgs": "--module-path D:/javafx-sdk-19/lib --add-modules javafx.controls,javafx.fxml,javafx.media"
### **Second Task : Running the App**
Go to package main, and click on ServerRunnable.java, then run that java file to start the server. 
>This will return a "Server started!" statement in the console, indicating that your SocketServer is running.

After that, run ClientRunnable.java, to create a client that will connect to the Server.