# DroneRadar

## Running the application
1. Create directory for storing generated files
2. From the "target" folder type in turn:
  - docker build -t frontend DroneRadarUI
  - docker run frontend
  - java -jar backend.jar --path.path=CREATED_DIRECOTRY_PATH
  - java -jar simulator.jar CREATED_DIRECOTRY_PATH
 
  where CREATED_DIRECOTRY_PATH is the absolute path to the directory created in step 1
