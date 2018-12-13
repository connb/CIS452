#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>

/*
*
* tokenRing.c
*
* @author Brandon Conn
* @version Fall 2018
*
*/

#define READ 0
#define WRITE 1
#define MAXBUF 1024

void sighand(int);


int main(int argc, char **argv) {

    if (argc != 2) {
        fprintf(stderr, "Usage: %s <string>\n", argv[0]);
        exit(0);
    }

    pid_t pid;
    int fd[2]; 
    signal(SIGINT, sighand);
    printf("Process 1 ID: \t%d\n", getpid());

    if (pipe(fd) < 0) {
        perror("pipe failed\n");
        exit(1);
    }

    if ((pid = fork()) < 0) {
        perror("fork failed\n");
        exit(0);
    }
    
    if (!pid) {
        
	//Child
        int pid2;
        char buf[MAXBUF];
        int fd2[2];
        printf("Process 2 ID: \t%d\n", getpid());
        close(fd[WRITE]); 

        //Read from pipe
        int recv = read(fd[READ], buf, MAXBUF);
        buf[recv] = 0; 
        printf("Process 2 Received: \t%s\n", buf);
        close(fd[READ]);

        if (pipe(fd2) < 0) {
            perror("pipe failed\n");
            exit(1);
        }

        if ((pid2 = fork()) < 0) {
            perror("fork failed\n");
            exit(1);
        }

        if (!pid2){
        
	    //Grandchild
            printf("Process 3 ID: \t%d\n", getpid());
            char buf2[MAXBUF];
            close(fd2[WRITE]);

	    //Read from pipe 
            int recv2 = read(fd2[READ], buf2, 5);
            close(fd2[READ]);
            buf2[recv2] = 0; 
            printf("Process 3 Received: \t%s\n", buf2);
            sleep(3);
	    exit(0);
        } 

        close(fd2[READ]); 
        write(fd2[WRITE], buf, strlen(buf));
        close(fd2[WRITE]); 
        wait(NULL);
	sleep(3);
        exit(0);

    } else {
        close(fd[READ]); 
        write(fd[WRITE], argv[1], strlen(argv[1]));
        close(fd[WRITE]); 
        wait(NULL);
	sleep(3);
        exit(0);
    }

    return 0;

}

void sighand(int sig) {
    printf("%d Received signal %d\n", getpid(), sig);
    sleep(1);
    exit(0);
}
