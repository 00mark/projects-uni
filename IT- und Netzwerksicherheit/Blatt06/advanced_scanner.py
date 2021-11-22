import socket
import sys
import random
from pa6.rawsocket import RawSocket
from pa6.tcp import TCPPacket
from pa6.ip import SimpleIPv4Packet

def Main():
    '''Creates a raw socket and performs a syn scan'''
    open_ports = []
    host = ""
    start_p = end_p = 0

    for i in range(1, len(sys.argv)):
        try:
            if(not start_p):
                start_p = int(sys.argv[i])
            elif(not end_p):
                end_p = int(sys.argv[i])
            else:
                usage()
                exit(0)
        except:
            # argument cannot be cast to int -> assume host
            if(not host):
                host = sys.argv[i]
            else:
                usage()
                exit(0)

    if(not start_p and not end_p):
        start_p = 1
        end_p = 65535
    elif(not end_p):
        end_p = start_p
        start_p = 1
    # check for invalid values
    if(not host or start_p > end_p or start_p < 0 or start_p > 65535 or
            end_p < 0 or end_p > 65535):
        usage()
        exit(0)

    # get ipv4 address of host
    host = socket.gethostbyname(host)
    sock = RawSocket("127.0.0.1")
    try:
        for port in range(start_p, end_p + 1):
            if(scan_port(sock, host, port)):
                open_ports.append(port)
    except KeyboardInterrupt:
        pass
    sock.close()

    for port in open_ports:
        print("Port %d is open" % port)

def scan_port(sock, host, port):
    '''Create a SimpleIPv4Packet Syn packet, send it host on the specified port
       and check the response
       :param sock: raw socket
       :param host: the host to scan
       :param port: the port to scan
    '''
    try:
        packet = SimpleIPv4Packet(src_addr = "127.0.0.1", dest_addr = host,
                encapsulated_protocol = socket.IPPROTO_TCP)
        packet.set_data_payload(TCPPacket(src_port = random.randint(40000,
                60000), dest_port = port, ipv4_packet = packet,
                syn = 1).bytes())
        sock.send(packet.bytes())
        # receive sent packet if scanning localhost
        if(host == "127.0.0.1"):
            sock.recv(1024)
        # receive real packet
        resp = sock.recv(1024)
        # ACK + SYN -> port is open
        if(resp[33] == 18):
            # receive RST
            sock.recv(1024)
            return True
    except socket.timeout:
        pass
    return False 

def usage():
    '''displays usage info'''
    print("python3.6 ./advanced_scanner.py Target [Start Port] [End Port]")
    print("if start port and end port are omitted, scan all ports")
    print("if start port is omitted, scan all ports till end port")

if __name__ == "__main__":
    Main()
