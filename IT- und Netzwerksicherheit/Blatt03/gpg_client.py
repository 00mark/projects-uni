import socket
import gnupg

def Main():
    addr = ("127.0.0.1", 5000)
    gpg = gnupg.GPG()
    keylist = gpg.list_keys()
    found = False
    resp = ""
    msg = ""

    # loop until the correct key has been found
    while(not found):
        print("enter uid\n> ", end = '')
        inp = input()
        key = search_for_uid(keylist, inp)
        print("is this the correct key? (y/n)\n%s" %key)
        inp = input("> ")
        if(inp == 'y' or inp == 'Y'):
            found = True

    try:
        sock = socket.create_connection(addr)
    except socket.error as err1:
        print("err: %s" % str(err1))
        exit(0)
    # send the public key
    sock.sendall(gpg.export_keys(key["keyid"]).encode())
    # receive the servers public key
    server_pub_key = sock.recv(8192)
    fingerprint_server = gpg.import_keys(server_pub_key).fingerprints

    try:
        while(resp != b"UNTRUSTED" and resp != b"ERROR"):
            msg = input("> ")
            # sign the message
            msg = gpg.sign(msg)
            # encrypt the signed message
            enc_data = gpg.encrypt(str(msg), fingerprint_server,
                    always_trust = True)
            try:
                sock.sendall(str(enc_data).encode())
                resp = sock.recv(16)
            except socket.error as err2:
                print("err: %s" % str(err2))
                sock.close()
                exit(0)
    except KeyboardInterrupt:
        print("Interrupt received, exiting...")
        sock.close()
        exit(0)

    print("Received msg '%s' from server, exiting..." %resp.decode())
    sock.close()


def search_for_uid(keylist, string):
    """Searches for a string in each keys uid and returns the best matching key
       :param list keylist: list of available keys
       :param str string: pattern to search for
       """
    best_key = keylist[0]
    best = 0

    for key in keylist:
        cur_best = 0
        current = 0
        uid = str(key["uids"])
        for c in uid:
            # whole string has been found => return
            if(current == len(string)):
                return key
            elif(c.lower() == string[current].lower()):
                current += 1
            else:
                if(current > cur_best):
                    cur_best = current
                current = 0
        # new best? 
        if(cur_best > best):
            best = cur_best
            best_key = key
    return best_key

if __name__ == "__main__":
    Main()
    
