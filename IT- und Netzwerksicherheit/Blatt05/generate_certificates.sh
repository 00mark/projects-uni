#!/bin/bash
echo creating eavesdropper key and cert...
if [ ! -e eavesdropper.key.pem ] && [ ! -e eavesdropper.cert.pem ] 
then
	printf "\n\n\n\n\n\n\n" | openssl req -x509 -newkey rsa:4096 -keyout \
	eavesdropper.key.pem -out eavesdropper.cert.pem -days 365 -nodes \
	&> /dev/null
	if [ -e eavesdropper.key.pem ] && [ -e eavesdropper.cert.pem ] 
	then
		echo success
	else
		echo unable to create cert and key
	fi
else
	echo files exist!
fi
