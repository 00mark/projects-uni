#!/usr/bin/env python3
from struct import unpack

class MQTT_Dissector:

    def parse_tcp_packet(self, src_addr, dst_addr, src_port, dst_port, tcp_payload):
        """Dissect TCP payload to identify and extract the username and password from
           a MQTT Connect message.

           :param string src_addr: IPv4 source address (dotted decimal)
           :param string dst_addr: IPv4 destination address (dotted decimal)
           :param int src_port: TCP source port
           :param int dst_port: TCP destination port
           :param bytes tcp_payload: TCP payload
           :return string username: Extracted username (default: None)
           :return string password: Extracted password (default: None)
           """
        fmt = "B" * len(tcp_payload)
        s = unpack(fmt, tcp_payload)

        # dose the payload contain a username and password ?
        if(len(s) >= 10 and s[9] >= (2**7 + 2**6)):
            # 12 = index of first byte after header
            i = 12
            name = ""
            passwd = ""

            # get client-id length
            cur_len = (s[i] << 8) + s[i+1]
            i += 2 + cur_len
            # get name length
            cur_len = (s[i] << 8) + s[i+1]
            i += 2
            j = i
            while(j < i + cur_len):
                name += chr(s[j])
                j += 1
            i += cur_len
            # get password length
            cur_len = (s[i] << 8) + s[i+1]
            i += 2
            j = i
            while(j < i + cur_len):
                passwd += chr(s[j])
                j += 1
            return name, passwd

        # return if username and password not found
        return None, None
