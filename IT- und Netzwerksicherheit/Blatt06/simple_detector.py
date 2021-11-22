import socket
import sys
import asyncio
import time
import resource

class DetectorProtocol(asyncio.Protocol):
    '''attempts to detect if a given connecton was done by a portscanner
       by checking the time spend between connection start and connection end
    '''

    min_time = 0.05

    def connection_made(self, transport):
        self.transport = transport
        # connection time
        self.starttime = time.time()

    def connection_lost(self, exc):
        # total time connected < min_time -> assume portscanner
        if(time.time() - self.starttime < self.min_time):
            print("Scan detected from IP %s on port %d" % (
                self.transport.get_extra_info( "peername")[0],
                self.transport.get_extra_info("socket").getsockname()[1]))

def Main():
    '''Check command line arguments for start_port and end_port and listen
       on all ports from start_port to end_port for connections
    '''
    server_list = []
    start_p = end_p = 0
    # maximum number of open files
    file_limit = resource.getrlimit(resource.RLIMIT_NOFILE)[0]
    
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
            usage()
            exit(0)
    if(start_p <= 0 or start_p > 65535 or end_p <= 0 or end_p > 65535 or 
            start_p > end_p):
        usage()
        exit(0)
    # allow up to (maximum number of open files) / 2 sockets
    elif(end_p - start_p >= file_limit / 2):
        print("number of ports must not exceed (limit of open files) / 2")
        print("current limit: %d" % file_limit)
        exit(0)

    loop = asyncio.get_event_loop()
    for port in range(start_p, end_p + 1):
        try:
            # listen on every port from start_port to end_port and append 
            # each socket to server_list
            server_list.append(loop.run_until_complete(loop.create_server(
                DetectorProtocol, "127.0.0.1", port)))
        except PermissionError:
            print("[Warning] cannot listen on port %d" % port)

    try:
        # run until interrupted
        loop.run_forever()
    except KeyboardInterrupt:
        pass

    # close every socket
    for server in server_list:
        server.close()
        loop.run_until_complete(server.wait_closed())
    loop.close()

def usage():
    '''Display usage info'''
    print("Usage: python3.6 simple_detector.py start_port end_port")

if __name__ == "__main__":
    Main()
