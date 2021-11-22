import gnupg
import socket

def Main():
    addr = ("127.0.0.1", 5000)

    gpg = gnupg.GPG(keyring = "server_keyring.gpg")
    # generate a new key if keyring has no key
    if(gpg.list_keys() == []):
        gpg.gen_key(gpg.gen_key_input(name_email = "trustedlogs@server.com"))
    key = gpg.list_keys()[0]

    try:
        sock = socket.socket()
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        sock.bind(addr)
        sock.listen(1)
    except socket.error as err1:
        print("err: %s" % str(err1))
        exit(0)
    conn, addr = sock.accept()

    try:
        while(1):
            # receive the clients public key and import
            client_pub_key = conn.recv(8192)
            fingerprint_client = gpg.import_keys(client_pub_key).fingerprints
            # send the servers public key
            conn.sendall(gpg.export_keys(key["keyid"]).encode())
            while(1):
                try:
                    data = conn.recv(8192)
                    # no data received => accept new connection
                    if(len(data) == 0):
                        print("Connection lost, restarting...")
                        conn, addr = sock.accept()
                        break
                    # decrypt the data
                    dec_data = gpg.decrypt(data)
                    # verify the decrypted data
                    verified = gpg.verify(str(dec_data))
                    if(not dec_data.ok):
                        conn.sendall("ERROR".encode())
                        print("Error: Unable to decrypt")
                    elif(not verified):
                        conn.sendall("UNTRUSTED".encode())
                        print("Warning: msg could not be verified\n%s"
                                %dec_data)
                    else:
                        conn.sendall("TRUSTED".encode())
                        print(dec_data)
                except socket.error as err2:
                    print("Connection lost, restarting...")
                    sock.close()
                    exit(0)
    except KeyboardInterrupt:
        print("Interrupt received, exiting...")
        sock.close()

if __name__ == "__main__":
    Main()
