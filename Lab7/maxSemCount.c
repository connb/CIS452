#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>

sem_t semaphore;

int main(void){

	sem_init(&semaphore, 0, 4294967296);
	

	return 0;
}
