#include <stdio.h>
#include <stdlib.h>

/***********************************
* Lab 9 Memory Management
* Fall 2018
* Authors: Brandon Conn & Mark Baker
***********************************/

char bigArray[1000];
char smallArray[10];
//char* data1;
//char* data2;

int foo(char* data){
	printf("Inside foo, the function name foo is stored at %p\n",foo);
	printf("Inside the function foo, the function parameter 'data' is stored at %p\n",&data);
	printf("Inside the function foo, bigArray is stored at %p\n", &bigArray);
	printf("Inside the function foo, smallArray is stored at %p\n", &smallArray);
	return 0;
}

int main() {
    	int i;
	char* data1;
	char* data2;


	printf ("\nThe main function begins at %p\n", main);
        printf ("The bigArray is stored at %p\n", bigArray);
	printf ("The smallArray is stored at %p\n\n", smallArray);

	printf ("The memory address of data1 is: %p\n", &data1);
	printf ("The address data1 is pointing to is: %p\n", data1);

	printf ("\nThe memory address of data2 is: %p\n", &data2);
	printf ("The address data2 is pointing to is: %p\n\n", data2);

	data1 =(char*) malloc (16);
	data2 =(char*) malloc (16);

	printf ("The memory address of data1 after the malloc is now: %p\n", &data1);
	printf ("The address data1 is pointing to after the malloc is: %p\n", data1);

	printf ("\nThe memory address of data2 after the malloc is now: %p\n", &data2);
	printf ("The address data2 is pointing to after the malloc is: %p\n", data2);

	printf ("\nThe stack variable i is at %p\n", &i);
	i = 5;
	printf("i has been set to 5\n");
	printf ("The stack variable i is now at %p\n\n", &i);

	printf("In main, the function foo is stored at %p\n", foo);
	foo(data1);

	free(data1);

	printf ("\nThe memory address of data1 after free(): %p\n", &data1);
	printf ("The address data1 is pointing to after free(): %p\n\n", data1);

	free(data2);

        printf ("\nThe memory address of data2 after free(): %p\n", &data2);
        printf ("The address  data2 is pointing to after free(): %p\n\n", data2);

    return 0;
}
