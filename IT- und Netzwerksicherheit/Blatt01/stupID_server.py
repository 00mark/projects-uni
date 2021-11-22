# IT Security Lecture summer 2018: Exercises
# Practical exercise 1 - dictionary attack
#
# Maintainer:           Timmy Schüller, Dominik Diekmann, Eric Lanfer
# File Description:     Server script - TCP server receive auth requests and send error or success msgs
#                       Response codes:
#                        00: Authentication failed
#                        01: Authentication successful
#                        02: Connection refused

import socket
import base64
import hashlib as hl
import random


def Main():
    # parameters for creating a tcp socket
    host = "127.0.0.1"
    port = 5000

    try:
        mySocket = socket.socket()
        mySocket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        mySocket.bind((host, port))
        mySocket.listen(1)
    except socket.error as e:
        print('Error while opening socket: %s' % str(e))
        exit(0)

    msg = 'ITS2018:'.encode() + b'/GjAcCSkz40Dz8c4Sc4Fozlqw6JqVm8u3UB3iYiIIHPfX5thCLAnAeVDvHQ7pYOUX2a60QgN5o/H9wwjN8kF/w=='

    # receive tcp packets and check payload for correct password
    try:
        while True:
            conn, addr = mySocket.accept()
            print("Connection from: " + str(addr))

            data = conn.recv(512)
            while data:
                rand = random.randint(0, 100000)
                if rand < 10:
                    conn.send('02 - Connection refused.'.encode())
                    conn.close()
                    break
                if data == msg:
                    conn.send('01 - Password correct.'.encode())
                else:
                    conn.send('00 - Password false.'.encode())
                data = conn.recv(512)
            print(str(addr) + " disconnected.")
    except KeyboardInterrupt:
        print("Interrupt received, stopping server..")
        mySocket.close()


if __name__ == '__main__':
    Main()
