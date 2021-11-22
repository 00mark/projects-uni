import socket
import sys

def Main():
    '''Check command line arguments for host, start_port and end_port
       and scan all ports of host from start_port to end_port. if no start_port
       is supplied, scan all 1 - end_port ports, if only host is supplied, scan
       all ports
    '''
    open_ports = []
    host = ""
    start_p = end_p = 0
    timeout = 0.1

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

    try:
        # scan all ports from start_port to end_port
        for port in range(start_p, end_p + 1):
            # if port is open, append it to open_ports
            if(scan_port((host, port), timeout)):
                open_ports.append(port)
    except KeyboardInterrupt:
        print("interrupted")

    for port in open_ports:
        print("Port %d is open" % port)

def scan_port(addr, timeout):
    '''tries to connect to addr
       :param addr: host and port of the target
       :param timeout: timeout while trying to connect
    '''
    try:
        sock = socket.create_connection(addr, timeout) 
        sock.close()
        return True
    # timeout or error -> assume port is closed
    except (socket.error, socket.timeout) as err:
        return False

def usage():
    '''displays usage info'''
    print("python3.6 ./simple_scanner.py Target [Start Port] [End Port]")
    print("if start port and end port are omitted, scan all ports")
    print("if start port is omitted, scan all ports till end port")
    
if __name__ == "__main__":
    Main()
