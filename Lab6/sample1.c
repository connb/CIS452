//Authors: Mark Baker and Brandon Conn
//Date: 10/10/2018
//Class: CIS 452 Lab 6

#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <sys/types.h> 
#include <sys/wait.h> 
#include <sys/ipc.h> 
#include <sys/shm.h>
#include <sys/stat.h>
#include <sys/sem.h>
#include <string.h>

#define SIZE 16

int main (int argc, char* argv[]) 
{ 
   int status; 
   long int i, loop, temp, *shmPtr; 
   int shmId, semId; 
   pid_t pid;
   struct sembuf semwait;
   struct sembuf semsignal;

   if (argc<2) {
      printf("Not enough command line arguments given");
      exit(1);
   }

   loop = atoll(argv[1]);

   printf("%ld\n", loop);

   if ((shmId = shmget (IPC_PRIVATE, SIZE, IPC_CREAT|S_IRUSR|S_IWUSR)) < 0) {
      perror ("i can't get no..\n"); 
      exit (1); 
   } 
   if ((shmPtr = shmat (shmId, 0, 0)) == (void*) -1) { 
      perror ("can't attach\n"); 
      exit (1); 
   }

   shmPtr[0] = 0; 
   shmPtr[1] = 1;

   semId = semget (IPC_PRIVATE, 1, 00600);
   semctl (semId, 0, SETVAL, 1);	

   semsignal.sem_num = 0;       
   semsignal.sem_op = 1;         
   semsignal.sem_flg = 0;

   semwait.sem_num = 0;       
   semwait.sem_op = -1;         
   semwait.sem_flg = 0;

   if (!(pid = fork())) { 
      for (i=0; i<loop; i++) { 
         semop(semId, &semwait, 1);
         //temp = shmPtr[0];
         memcpy(&temp, &shmPtr[0], sizeof(shmPtr[0]));
         //shmPtr[0] = shmPtr[1];
         memcpy(&shmPtr[0], &shmPtr[1], sizeof(shmPtr[1]));
         //shmPtr[1] = temp;
         memcpy(&shmPtr[1], &temp, sizeof(temp));
         semop(semId, &semsignal, 1);
      } 
      if (shmdt (shmPtr) < 0) { 
         perror ("just can't let go\n"); 
         exit (1); 
      } 
      exit(0); 
   } 
   else 
      for (i=0; i<loop; i++) { 
         semop(semId, &semwait, 1);
         //temp = shmPtr[1];
         memcpy(&temp, &shmPtr[1], sizeof(shmPtr[1]));
         //shmPtr[1] = shmPtr[0];
         memcpy(&shmPtr[1], &shmPtr[0], sizeof(shmPtr[0]));
         //shmPtr[0] = temp;
         memcpy(&shmPtr[0], &temp, sizeof(temp));
         semop(semId, &semsignal, 1);
      }

   wait (&status); 
   printf ("values: %li\t%li\n", shmPtr[0], shmPtr[1]);

   semctl (semId, 0, IPC_RMID);

   if (shmdt (shmPtr) < 0) { 
      perror ("just can't let go\n"); 
      exit (1); 
   } 
   if (shmctl (shmId, IPC_RMID, 0) < 0) { 
      perror ("can't deallocate\n"); 
      exit(1); 
   }

   return 0; 
} 
