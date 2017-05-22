Hello Professor,

This is how you will compile our database application "Project Vigilante":

1. The project was originally made in eclipse, opening it in eclipse and compiling it there is the simplest option
	NOTE: if you do not want to use eclipse to compile the source yourself (As an open source advocate... you should). A jar file has been supplied before hand
		to run this file, open your local command processor to the jar's directory
		Command = java -jar "Project Vigilante FrontEnd.jar"
2. XAMPP is the source for our database. Install it (which you probably have)
	1. Copy vigilante.sql into the mysql bin folder
	2. Open a command window in the bin folder and "source vigilante.sql"
	3. This sql file is a snapshot of our database, created through phpMyAdmin's export function. If it doesnt work then update your XAMPP to the newest version
3. Launch XAMPP, Launch SQL
4. Execute the Project Vigilante
	NOTE: if your database uses a root password (like all smartly designed dbs should) please enter it when asked. I wont save it, promise :-)
5. Register yourself as a new user if you have not already
6. If you have already registered, please login
	NOTE: In order to properly test the privilege test, you need to use a user that is already in the system, you may use pnc@lol.com with the password 'Hello World 1'
7. You will be entered into a command line if you successfully register or login
8. From here you can type 'help' to get your bearings
	NOTE: This is a full command line interface, it has a REAL tokenizer that can parse a multitude of data types
		This means that using 'A String With Spaces' with no quotes will be seperated into different arguments delimited by spaces, "A String With Spaces" with quotes is accepted as a single argument 'A String With Spaces' 

Thank you for using our app. With your help, Crime will become obsolete using the power of BigData and the willingness of the local populace.		
		