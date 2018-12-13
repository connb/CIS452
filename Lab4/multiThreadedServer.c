#include <pthread.h> 
#include <stdio.h> 
#include <errno.h> 
#include <unistd.h> 
#include <stdlib.h> 
#include <string.h>
#include <time.h>
#include <signal.h>
#define MAX_FILENAME 1024

void* worker_thread(void* arg);

void sigExitHandler (int signum);

int filerequests = 0;

int main(void) 
{ 
	pthread_t dispatch;
	int status;
	char filename[MAX_FILENAME];
	signal (SIGINT, sigExitHandler);
	srand(time(NULL));
	
	while(1){
		printf("Please enter a filename to access:\n");
                fgets(filename, 1024, stdin);
                //filename[strcspn(filename, "\n")] = 0;
		printf("Searching....\n");

		if ((status = pthread_create (&dispatch, NULL,  worker_thread, filename)) != 0) {
                        fprintf (stderr, "thread create error %d: %s\n", status, strerror(status));
                        exit (1);
                }
	}
}

void* worker_thread(void* arg) {
	char filename[MAX_FILENAME];
	strcpy(filename, (char *)arg);
	filename[strcspn(filename, "\n")] = 0;
	int randomnum = (rand() % 10) + 1;

	if (randomnum < 8) {
		sleep(1);
	} else {
		int randomnum2 = (rand() % 4) + 7;
		sleep(randomnum2);
	}
	
	printf("Worker thread accessed filename: %s\n", filename);
	filerequests++;
	return NULL;

}

void sigExitHandler (int sigNum) 
{ 

	printf (" received.\n"); 
	// this is where shutdown code would be inserted 
	sleep (1); 
	printf("Requests received and processed: %d\n", filerequests);
	sleep(1);
	printf ("Shutting Down.\n"); 
	exit(0); 

}
