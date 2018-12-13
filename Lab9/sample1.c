#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main()
{
    char *data1;

    printf ("Please input username: ");
    scanf("%ms", &data1);
    printf ("you entered: %s\n", data1);
    free (data1);
    return 0;
}
