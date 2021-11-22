default:
	gcc -Wall -std=c99 -pedantic Server.c -o Server
	gcc -Wall -std=c99 -pedantic DataClient.c -o DataClient
	gcc -Wall -std=c99 -pedantic ServerFixed.c -o ServerFixed 

valgrind_server:
	valgrind --leak-check=full ./Server

valgrind_dataclient:
	valgrind --leak-check=full ./DataClient

clean:
	rm Server
	rm DataClient
	rm ServerFixed
