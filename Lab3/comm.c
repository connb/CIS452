#include <stdio.h> 
#include <unistd.h> 
#include <stdlib.h> 
#include <signal.h>
#include <time.h>

void sigExitHandler (int);
void sigUsr1Handler (int);
void sigUsr2Handler (int);

int main() 
{ 

	pid_t pid;
	signal (SIGINT, sigExitHandler);
	signal (SIGUSR1, sigUsr1Handler);
	signal (SIGUSR2, sigUsr2Handler);
	
	if ((pid = fork()) < 0) {
	    perror("fork failure");
	    exit(1);
	}

	else if (pid == 0) {

	    while (1) {//CHILD
		    //printf ("waiting.........\n"); 
		    srand(time(0));
		    int randomnum;
		    randomnum = (rand() % 5) + 1;
		    sleep(randomnum);
		    int randomnum2;
		    randomnum2 = (rand() % 2);
		    if (randomnum2 == 0) {
			kill(getppid(), SIGUSR1);
		    } else {
		    	kill(getppid(), SIGUSR2);
		    }
		    
	    }
	}

	else {
	    
	    //PARENT
	    printf ("waiting...\n");
	    pause();
	    while (1) {
			printf ("waiting...\n");
			pause();
			signal (SIGINT, sigExitHandler);
			signal (SIGUSR1, sigUsr1Handler);
			signal (SIGUSR2, sigUsr2Handler);
			
	    }

	}
	
 
    return 0; 
}

void 
sigExitHandler (int sigNum) 
{ 

	printf ("^C received.\n"); 
	// this is where shutdown code would be inserted 
	sleep (1); 
	printf ("Shutting Down.\n"); 
	exit(0); 

}


void 
sigUsr1Handler (int sigNum) 
{ 

	printf ("SIGUSR1 signal received.\n\n"); 


}

void 
sigUsr2Handler (int sigNum) 
{ 

	printf ("SIGUSR2 signal received.\n\n"); 

}
