#!/bin/bash
if [ -f brpa3_970770_968803.ko ]
then
    sudo insmod brpa3_970770_968803.ko
else
    echo brpa3_970770_968803.ko not found
fi
