#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <sys/time.h>
#include <sys/resource.h>

int main(int argc, char** argv) {

    pid_t pid;
    struct rusage ru;
    char userInput[1000];
    char confirmQuit[255];
    char src[255];
    char dest[15];
    long prevSec = 0;
    long prevUsec = 0;
    long prevSwitch = 0;
    int numTokens = 0;
    int status;

    //Welcome the user and let them know how to exit the program
    printf("\nWelcome to the Simple Shell!\nType 'quit' to exit\n\n\nInput a command to begin: \n\n");

    while (1) {

	//Command prompt symbol
        printf("> ");
	
	//Stores the user input from stdin in a array of characters named userInput
        fgets(userInput, 1000, stdin);

	//Remove trailing newline character caused by fgets()
	//https://stackoverflow.com/questions/2693776/
	userInput[strcspn(userInput, "\n")] = 0;
	
	//User provides no input but presses enter,
	//just continue on like a bash shell does
	//with a new line with the command prompt symbol
        if (strlen(userInput) <= 0) {
            continue;
        }

	//User enters 'quit'
        if (strcmp(userInput, "quit") == 0) {
	
	    //Confirm user really wants to quit
	    printf("\nAre you sure you want to quit the Simple Shell?\nType 'y' or 'n'\n\n");

	    //Stores the user input from stdin in a array of characters named confirmQuit
	    fgets(confirmQuit, 255, stdin);

	    //Remove trailing newline character caused by fgets()
            //https://stackoverflow.com/questions/2693776/
	    confirmQuit[strcspn(confirmQuit, "\n")] = 0;
 	
 	    //User pressed return without entering anything. Program resumes
	    if (strlen(confirmQuit) <= 0) {
		printf("No input received\nResuming Simple Shell...\n\n");
                continue;
            }

	    //User confirms quit by typing 'y'. Program exits
	    else if (strcmp(confirmQuit, "y") == 0) {
	        printf("\n<<<<< QUITTING >>>>>\n\n");
                break;
	    }

	    //User unconfirms quit by typing 'n'. Program resumes
	    else if (strcmp(confirmQuit, "n") == 0) {
		printf("\nResuming Simple Shell...\n\n");
		continue;
	    }

	    //User types anything else. Program resumes
	    else {
		printf("\nInvalid command\nResuming Simple Shell...\n\n");
		continue;
	    }
        }

	//Create an array of characters named tempUserInput
	//and copy the contents of userInput into it so that
	//tempUserInput can be used to count the number of tokens
	//that are in userInput
        char tempUserInput[strlen(userInput)];
        for (int i = 0; i < strlen(userInput); i++) {
            tempUserInput[i] = userInput[i];
        }
        
	//Creates an array of characters called 'token' that uses strtok()
	//to break the contents of tempUserInput up into smaller strings (tokens)
	//using a single space as a delimiter for each token
        char* token = strtok(tempUserInput, " ");

	//Loop through tokens until there are none, to get a count of the number of tokens
	//https://www.tutorialspoint.com/c_standard_library/c_function_strtok.htm
        while(token != NULL) {
            numTokens++;
         
            token = strtok(NULL, " ");
        }

        char* args[numTokens];

	//Creates an array of characters called 'token' that uses strtok()
        //to break the contents of userInput up into smaller strings (tokens)
        //using a single space as a delimiter for each token
        char* command = strtok(userInput, " ");

	//Argument 0 is the actual command the user input
        args[0] = command;

	//Inserts each parameter of the command into the array of characters named args
        for (int i = 1; i < numTokens; i++) {
            args[i] = strtok(NULL, " ");
        }

        args[numTokens] = NULL;

        if ((pid = fork()) < 0) {
            perror("fork failure");
            exit(1);
        }

        else if (pid == 0) {

	    //CHILD
            if (execvp(command, args) < 0) {

		//https://www.tutorialspoint.com/c_standard_library/c_function_strcat.htm
                strcpy(src, command);
                strcpy(dest, "\nSimpleShell: ");
                strcat(dest, src);

		perror(dest);
                exit(1);
            }else {
                exit(0);
            }
        }

        else {
            //PARENT
            pid = wait(&status);
            
            if (getrusage(RUSAGE_CHILDREN, &ru) < 0) {
                printf("Unable to return resource usage statistics\n");
            }

	    else {
                printf("\nUser CPU time used:\t%ld seconds, %ld microseconds\n", 
			ru.ru_utime.tv_sec - prevSec, ru.ru_utime.tv_usec - prevUsec);
                printf("Involuntary context switches:\t%ld\n\n", ru.ru_nivcsw - prevSwitch);
                prevSec = ru.ru_utime.tv_sec;
                prevUsec = ru.ru_utime.tv_usec;
                prevSwitch = ru.ru_nivcsw;
            }
        }
    }
    return 0;
}
