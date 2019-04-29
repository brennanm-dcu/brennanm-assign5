
******************************* Matthew Brennan - SDA PROJECT - Student No -17112702 *****************************

                                                   PROJECT PROPOSAL

I work in a training environment, lecturing in Communications, IT and Electronic Engineering to Degree Level, with college students and with students
on other courses.
We have the following reoccurring requirements:
•	The need to initially evaluate students’ needs and suitability for a course.
•	To do quick assessments to monitor students progress.
•	To conduct simple continuous assessment tests.
Most of these are of a brief nature and may simply consist of a short set of questions, multiple choice questions or to identify features from images.
These are not full blown 3 hour exams but quick assessments to determine the state of a student’s knowledge.
These tests have been paper based and consumed time to administer and run. A computer based approach is a solution but availability of these recourse
can be an issue as computer rooms are constantly in use.

The Aim

The aim of my SDA project is to design a smart phone app that can deliver, to a group of students, a simple set of multiplechoice questions that when answered, a score
is returned for each student, and a list of the class scores can be accessed from the app.
This takes advantage of the widespread use of android smart devices, also for students with iphones or other non android platforms, the institution could have a number
of android smart phones, or tablets, in stock for loan to students to do the assessments. It is much more convenient to have a small box of smart devices on a shelf in
the stores, than providing a whole computer room.

The Application:

The Application would allow the instructor to quickly produce a set of simple multiple choice questions with short answers.
Then convey the tests to the students via a download from a Firebase Database.
Then students will then open the questions on the app, and when all questions are answered, the results will automatically calculated and sent to the Database,
where they can be accessed by the app.

The application will use of the following Android API classes:
1.	Multimedia graphics
2.  Firebase Database – to store questions.

 ***************************************************************************************************************
							- SOLUTION - 

							RAPADWISR APP

			      Rapid Assessment Production And Deploynemt With Instant Score Return

                                                       APPLICATION OPERATION
Normal User(student) access:
A normal User will be given a Login which is the Name or Number of the Test they are to undertake, and a general user password, not specific to one student. On successful access
they will be directed to the Student section of the application where they will be required to input their Name or Student ID. When this is completed, and
the continue Button pressed, the app will download the questions for the relevant test from the Firebase Database and begin to
display a series of views each showing one question. Each question contains a question and four possible answers.
The student selects the Radio Button beside the option they think is the right answer and then press the 'Next Question' Button. They do this until they
reach the last question. When finished the results will automatically be sent to the Firebase Database.
PARTICULARS: 1. The app monitors inputs such as names and passwords, and that each question have been answered, if not! Toasts appear indicating that something needs
                to be completed.
             2. After each question the result will be displayed in a Toast and then proceed to the next question,
             3. The initial login using subject Name or ID is not case sensitive but the app converts all inputs for subject name to capitals as I have all subject names on the
                Firebase Database in capitals as a convention This avoids problems with case sensitivity from a user perspective.
             4. The login password IS case sensitive for extra security.
             5. Also there is a maximum number of login attempts allowed after which the login button will disable.
Admin Access:
The Administrator, or the lecturer, will have an Admin Logon, which is 'Admin', and an Admin password, this will take them to the admin section of the app. Here they can access
a number of facilities. One is for the production of multiple choice tests, another allows for the viewing of a set of results from a particular test, and also there is a facility
to change user and admin passwords.

Results - Each right question gets one mark. After a question is answered the score count is incremented for each right answer, and at the end of the test the final score, along with the
          student name is sent to the Firebase Database.
