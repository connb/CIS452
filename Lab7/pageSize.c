#include <stdio.h>
#include <errno.h>
#include <limits.h>
#include <unistd.h>
extern errno ;
int main()
{
    errno = 0;
    long page_size = 0;
    page_size = sysconf(_SC_PAGESIZE);
    printf("%ld\n", page_size);
    printf("err msg: %m\n");
    return 0;
}  
