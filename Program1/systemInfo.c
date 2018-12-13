#include <syscall.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <pwd.h>
#include <stdlib.h>
#include <string.h>

//Brandon Conn
//CIS 452 Program 1: System Info
//Fall 2018

int main(void){
    int userID, groupID;
    char *loginName;

    //According to RFC 1035, each label (including hostname)
    //of a FQDN is limited to 63 characters
    char hostname[63];


    //Retrieves and prints the user's login name
    loginName = getlogin();
    printf("\nUser is: %s\n\n", loginName);
    
    //Retrieves and prints the user's ID
    userID = getuid();
    printf("UID is: %1d\n", userID);
    
    //Retrieves and prints the group's ID
    groupID = getgid();
    printf("GID is: %1d\n\n", groupID);
    
    //Retrieves and prints the hostname of the computer
    gethostname(hostname, sizeof hostname);
    printf("Host is: %s\n\n", hostname);
    
    //Struct taken from the getpwent() man page 
    struct passwd *p;
    int i = 0;
    
    //Prints the name of the first 5 entries
    while (i < 5){
        p = getpwent();
	
	//pw_name is the username for each entry
        printf("Entry %i: %s\n", i, p->pw_name);
        i++;
    }
    endpwent();

    //The length of the longest environment variable I found
    //when typing the env command was 24, so I added a few
    //more, just in case...
    char userEVar[30];
    char *value;

    //Asks the user to input the environment variable for which they want a value
    printf("\nEnvironment variable desired: ");
    scanf("%s", userEVar);
    value = getenv(userEVar);

    //Checks the user's input. If it doesn't match an environment variable,
    //it is NULL, and prints an appropriate response. If the user's input 
    //matches and environment variable, the values is printed.
    if(value == NULL){
        printf("\nINVALID ENVIRONMENT VARIABLE NAME\n\n");
    }else{
    printf("\nThe environment variable's value is: %s\n\n", value);
    }
    return(0);

}
