//READER

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
 * reader.c
 *
 * Repeatedly reads each new string from the shared memory written
 * to by the writer and displays the string to the screen
 *
 * @author Brandon Conn & Mark baker
 * @version Fall 2018
 *
 */

#define SHMSIZE 4096

void sigExitHandler(int sigNum);

int main () {

	int shmID, *p, memAddr;
	char *shmPtr, *readPtr, *reader[2];

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

	while(1) {
		
		//Makes a copy of the pointer to shared memory space
		p = (int*)shmPtr;

		//Get next place in shared memory space where writer should be pointed at
		readPtr = shmPtr + sizeof(int) * 2;

		//Save off this pointer
		reader[0] = readPtr;
		
		//Moves readPtr to the next spot in memory to read from next round
		readPtr += *p++;

		//Save readPtr
		reader[1] = readPtr;
		sprintf(readPtr, "0");

		//Gets input the first time around
		sscanf(readPtr, "%d", &memAddr);
	
		//Continues to get input
		while (!memAddr) {
			sscanf(readPtr, "%d", &memAddr);
		}

		//Prints the output from the shared memory space 
                printf("Output: %s\n", reader[0]);

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
	printf("\n\n...Shutting Down Reader...\n\n");
	exit(0);
}
