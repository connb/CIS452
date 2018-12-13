//WRITER

#include <signal.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <unistd.h>

/*
 * writer.c
 *
 * Creates a shared memory segment, then repeatedly accepts an arbitrary 
 * user input string from the terminal and writes it into shared memory
 *
 * @author Brandon Conn & Mark baker
 * @version Fall 2018
 *
 */

#define SHMSIZE 4096
#define BUFSIZE 1024

void sigExitHandler(int signum);

int main () {

	int shmID;
	char *shmPtr;
	char *buf = malloc(BUFSIZE * sizeof(char));
	
        signal(SIGINT, sigExitHandler);

	//Creates a shared key for the system
	key_t key = ftok("/etc", 99);
	
	//Creates a shared memory segment
	if ((shmID = shmget(key, SHMSIZE, IPC_CREAT|S_IRUSR|S_IWUSR)) < 0) {
		perror ("Can't create memory segment\n");
		exit (1);
	}
   
	//Attaches the shared memory segment to the address of a suitable (unused) page-aligned address
	if ((shmPtr = shmat(shmID, NULL, 0)) == (void*) -1) {
		perror ("Can't attach\n");
		exit (1);
	}
	
	int memAddress[2], memAddr1, memAddr2;
	char *shmPtr1, *shmPtr2, *writePtr;

	while(1) {
		
		//Prints the input to be put into shared memory
		printf("Input: ");
		fgets(buf, BUFSIZE, stdin);
		printf("\n");

		//Move ptr into shared data	
		writePtr = shmPtr + sizeof(memAddress);

		//Initialize memory address size
		memAddress[0] = 32;
		
		//Put buffer in writePtr
		sprintf(writePtr, buf);

		//Move pointer memory Address size
		writePtr += memAddress[0];

		//Save off first pointer
		shmPtr1 = writePtr;

		//Designate next memory Address, with blank string
		memAddress[1] = sprintf(writePtr, "1") + 1;

		//Move pointer for next memory Address
		writePtr += memAddress[1];

		//Save off second pointer
		shmPtr2 = writePtr;

		//Set pointer back to blank string
		sprintf(writePtr, "1");

		//Return shared memory pointer
		memcpy(shmPtr, &memAddress, sizeof(memAddress));

		//Gets input the first time around
		sscanf(shmPtr1, "%d", &memAddr1);
		sscanf(shmPtr2, "%d", &memAddr2);

		//Continues to get input
		while (memAddr1 && memAddr2) {
			sscanf(shmPtr1, "%d", &memAddr1);
			sscanf(shmPtr2, "%d", &memAddr2);
		}

	}

	//Detaches shared memory segment
	if (shmdt(shmPtr) < 0) {
		perror ("Can't detach\n");
		exit (1);
	}

	//Marks the shared memory segment for destruction
    	if (shmctl(shmID, IPC_RMID, 0) < 0) {
      		perror ("Can't mark for destruction\n"); 
      		exit(1); 
   	}  

	exit(0);
}

void sigExitHandler(int sigNum) {
	printf("\n\n...Shutting Down Writer...\n\n");
	exit(0);
}
