Hello! This web project is made for young _Harry Potter_ fans!
You can add Hogwarts heroes via a post request, specifying their name and age in the request body. The data will be saved to the database, the ID will be set automatically.
You can also add faculties in the same way as you did with students, only the properties of the faculties are: name and color, and everything is in string format! I set the relationship between the Student and Faculty models Many-to-One, so you can fully use the requests and poke around in the table.
I recently studied the streams and added the ability to add avatars for each faculty to the project (put a goblin on Hermione's avatar).
You can test all the methods in swagger. Run the project, go to http://localhost:8080/swagger-ui/index.html#/ and have fun)