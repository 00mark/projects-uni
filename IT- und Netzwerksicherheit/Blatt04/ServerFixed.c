#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <stdio.h>

#define ERR_UNKNOWN -1
#define ERR_HEARTBLEED -2

enum messageTypes { data_message = 'd', heartbeat_message = 'h'};

int32_t deserializeMessage(char* _buffer, int32_t _bytes_received) {

    char message_type = _buffer[0];

    // Deserialize received Data Message
    if(message_type == data_message) {
        printf("Received a Data Message containing %d bytes.\n", _bytes_received);
        return 0;
    } else if(message_type == heartbeat_message) {
        // Deserialize received Heartbeat Message
        /* Structure of Heartbeat Message
         * | 1 Byte      | 4 Byte                   | requested_message_length Bytes ...|
         * | messageType | requested_message_length | message                        ...|
         */  
        printf("Received a Heartbeat containing %d bytes\n", (*(int32_t*)(_buffer+1)));
        char int_to_byte_conversion_array[4] ={0};
        int_to_byte_conversion_array[0] = _buffer[1];
        int_to_byte_conversion_array[1] = _buffer[2];
        int_to_byte_conversion_array[2] = _buffer[3];
        int_to_byte_conversion_array[3] = _buffer[4];
        int32_t requested_message_length = *(int32_t *)int_to_byte_conversion_array;
        printf("Requested Heartbeat Message Length: %d\n", requested_message_length);
        // Handle Heartbleed
        if(requested_message_length > _bytes_received - 5){
            printf("Received Message with invalid Length:\nLength specified: "
                    "%d, Actual Length: %d\n", requested_message_length,
                    _bytes_received - 5);
            return ERR_HEARTBLEED;
        }
        return requested_message_length + 5;
    } else { // Unknown Message
        printf("Received Unknown Message with %d bytes\n", _bytes_received);
        return ERR_UNKNOWN;
    }
    return 0;
}

int main(int argc, char* argv[]) {
    // Variables needed for the network / socket usage
    int32_t socket_descriptor = 0;
    int32_t return_code = 0;
    int32_t bytes_received = 0;
    socklen_t remote_address_size;
    struct sockaddr_in remote_address;
    struct sockaddr_in local_address;
    remote_address_size = sizeof(remote_address);

    int32_t port = 4000;
    int32_t bufferLength = 32000;
    char buffer[bufferLength];

    // Socket creation
    socket_descriptor = (int32_t)socket(AF_INET, SOCK_DGRAM, 0);
    if (socket_descriptor < 0) {
        printf("%s: cannot open socket \n", argv[0]);
        return EXIT_FAILURE;
    }

    // Bind local server port
    local_address.sin_family = AF_INET;
    local_address.sin_addr.s_addr = htonl(INADDR_ANY);
    local_address.sin_port = htons(port);
    return_code = bind(socket_descriptor, (struct sockaddr*) &local_address,
            sizeof(local_address));

    if (return_code < 0) {
        printf("%s: cannot bind port number %d \n", argv[0], port);
        return EXIT_FAILURE;
    }


    printf("Waiting for data on UDP port: %u\n", port);

    // Receiver loop
    while (42) {
        // Receive Message
        bytes_received = recvfrom(socket_descriptor, buffer, bufferLength, 0,
                (struct sockaddr*) &remote_address, 
                &remote_address_size);

        if (bytes_received < 0) {
            printf("%s: recvfrom error %d\n", argv[0], bytes_received);
            continue;
        } else {
            printf("Socket: %d bytes received.\n", bytes_received);
        }

        // Process reveived Message according to protocol
        int32_t bytes_to_echo = deserializeMessage(buffer, bytes_received);

        if(bytes_to_echo >0) {
            printf("Echo %d bytes to Heartbeat Request.\n", bytes_to_echo);
            sendto(socket_descriptor, buffer, bytes_to_echo, 0, 
                    (struct sockaddr*) &remote_address, sizeof(remote_address));
        }
    }

    return EXIT_SUCCESS;
}

