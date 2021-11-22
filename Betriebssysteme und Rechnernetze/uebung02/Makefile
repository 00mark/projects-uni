all: received receiver_udp receiver_tcp sender_udp sender_tcp
received:
	mkdir received

receiver_udp:
	gcc receiver_udp.c -lssl -lcrypto -o receiver_udp

receiver_tcp:
	gcc receiver_tcp.c -lssl -lcrypto -o receiver_tcp

sender_udp:
	gcc sender_udp.c -lssl -lcrypto -o sender_udp 

sender_tcp:
	gcc sender_tcp.c -lssl -lcrypto -o sender_tcp
	
clean:
	rm *.tar.gz receiver_udp receiver_tcp sender_udp sender_tcp
	rm -rf received
