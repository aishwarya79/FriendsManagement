**API 1: Adds friend connection**  
Method: POST  
URL: /friendsmanagement/api/v1/friends/connect  
Accepts: application/json
Sample Request:
```
{
  "friends":
    [
        "harry.potter@yahoo.com",
        "ron.weasley@yahoo.com"
    ]
}
```
 Sample Response:
 ```
{
    "success": true
}
```

**API 2: Get friends list of a user**   
Method: POST  
URL: /friendsmanagement/api/v1/friends/list 
Accepts: application/json
Sample Request:
```
{
	"email" : "harry.potter@yahoo.com"
}
```
Sample Response:
```
{
    "success": true,
    "friends": [
        "ron.weasley@yahoo.com"
    ],
    "count": 1
}
```

**API 3: Find Common friends of two users**  
Method: POST  
URL:  /friendsmanagement/api/v1/friends/common 
Accepts: application/json
Sample Request:
```
{
  "friends":
    [
        "hermoine.granger@yahoo.com",
        "harry.potter@yahoo.com"
    ]
}
```
 Sample Response:
 ```
{
    "success": true,
    "friends": [
        "ron.weasley@yahoo.com"
    ],
    "count": 1
}
```

**API 4: Subscribe to another user**  
Method: POST  
URL: /friendsmanagement/api/v1/users/subscribe 
Accepts: application/json
Sample Request:
```
{
    "requestor" : "james.potter@yahoo.com",
    "target": "harry.potter@yahoo.com"
}
```
Sample Response:
```
{
    "success": true
}
```

**API 5: Block a user**  
Method: POST  
URL:  /friendsmanagement/api/v1/users/block 
Accepts: application/json
Sample Request:
```
{
    "requestor" : "draco.malfoy@yahoo.com",
    "target": "harry.potter@yahoo.com"
}
```
Sample Response:
```
{
    "success": true
}
```

**API 6: Get Recipients of a user's update**  
Method: POST  
URL: /friendsmanagement/api/v1/notification/recipients  
Accepts: application/json
Sample Request:
```
{
    "sender": "harry.potter@yahoo.com",
    "text" : "hello  world ! sirius.black@yahoo.com"
}
```
Sample Response:
```
{
    "success": true,
    "recipients": [
        "sirius.black@yahoo.com",
        "james.potter@yahoo.com",
        "ron.weasley@yahoo.com"
    ]
}
```