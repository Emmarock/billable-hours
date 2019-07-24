# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)

### Guides
The following guides illustrate how to run this application:

### Starting the Application
The application shipped with an embedded tomcat server,
you can run this application by running any of this commands on your terminal from the project root directory

#### Option 1
> spring-boot:run 

#### Option 2

>  mvn package 
* The command above will generate an executable jar, then run
> java -jar target/demo-0.0.1-SNAPSHOT.jar

### Testing the Application
A sample test file is associated, this can be found at src/main/resources/static/billable.csv

The Application consist of an API endpoint and a User Interface 

1. To use the API endpoint in postman, go to 

* [localhost](http://localhost:8080/upload) 
* Create a post request, with the body as form-data.
* On the key interface, enter file as the name. 
* On the value interface, select the test file above.
* The click post.
* The sample response will be similar to the one below 

        `` 
        {
               "Interswitch": [
                   {
                       "employeeId": "3",
                       "numberOfHours": 5,
                       "unitPrice": 200,
                       "cost": 1000,
                       "company": "Interswitch"
                   },
                   {
                       "employeeId": "4",
                       "numberOfHours": 15,
                       "unitPrice": 400,
                       "cost": 6000,
                       "company": "Interswitch"
                   }
               ],
               "Google": [
                   {
                       "employeeId": "1",
                       "numberOfHours": 8,
                       "unitPrice": 300,
                       "cost": 2400,
                       "company": "Google"
                   },
                   {
                       "employeeId": "2",
                       "numberOfHours": 5,
                       "unitPrice": 200,
                       "cost": 1000,
                       "company": "Google"
                   }
               ]
           }
``


2. To use the User Interface

When the application is up and running, go to 

* [localhost](http://localhost:8080/index) to use the user interface.

On the interface, choose the sample test file and click upload, to upload the test file.

This will return a processed Billable hours similar to 

![Sample_Output](/src/main/resources/static/Sample_Output.png)








