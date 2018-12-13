#include <stdio.h>
#include <stdlib.h>
#include <sys/sysinfo.h>
#include <time.h>

int
main()
{
int rc;
struct timespec res;

	rc = clock_getres(CLOCK_REALTIME, &res);
		printf("CLOCK_REALTIME: %ldns\n", res.tv_nsec); 
}
