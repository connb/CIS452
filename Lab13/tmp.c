#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>
#include <errno.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/*****************************************************************
* CIS 452							 *
* Fall 2018							 *
*								 *
* Lab 13 							 *
* File System Interface: Operations				 *
*								 *
* C implementation of the Linux ln command for hard & soft links *
*								 *
* Authors: Brandon Conn & Mark Baker				 *
*****************************************************************/

int main(int argc, char const *argv[]) {

  int flag = 0;

  //prevents seg fault from next if statement & checks for too few arguments
  if (argc < 3) {
    printf("You didn't enter enough arguments\n");
    printf("Hard Link Usage: ./my_ln oldfile newfile\n");
    printf("Soft Link Usage: ./my_ln -s oldfile newfile\n");
    exit(EXIT_FAILURE);
  }

  //Checks for -s option for symbolic link
  if (strcmp("-s", argv[1]) == 0) { 
    flag = 1;
  }
  
  //Hard Link
  if (argc == 3 && !flag) {
    if (link(argv[1], argv[2]) >= 0) {
      printf("You created a hard link from %s to %s\n", argv[1], argv[2]);
    }
    else {
      perror("Couldn't create a hard link\n");
      exit(EXIT_FAILURE);
    }	
  } 
  //Soft Link
  else if (argc == 4 && flag) {
    if (symlink(argv[2], argv[3]) >= 0) {
      printf("You created a soft link from %s to %s\n", argv[2], argv[3]);
    }
    else {
      perror("Couldn't create a soft link\n");
      exit(EXIT_FAILURE);
    }	
  }
  //Too many arguments
  else {
    printf("You entered too many arguments\n");
    printf("Hard Link Usage: ./ln TARGET LINKNAME\n");
    printf("Soft Link Usage: ./ln -s TARGET LINKNAME\n");
    exit(EXIT_FAILURE);
  }
  return 0;
}
