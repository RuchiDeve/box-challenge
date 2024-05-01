## Box Service
This project implements a REST API service for managing boxes used for delivering item. 
The service provides functionalities such as registering a box, loading item onto a box, checking loaded item for a given box, checking available boxes for loading, and checking the box's battery level.

## API Endpoints
## 1. Create a Box
  Endpoint: POST/boxes
## 2. Add Item to a Box
Endpoint: POST/boxes/{txref}/items
## 3. Check Available Boxs for Loading
Endpoint: GET /boxes/available
## 4. Check Battery Level
Endpoint: GET /boxes/{txRef}/battery
## 5. Check Loaded Items for a Given Box
