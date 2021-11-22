obj-m += brpa3_970770_968803.o
all:
	make -C	/lib/modules/$(shell uname -r)/build M=$(PWD) modules
	gcc brpa3_ioctl.c -o brpa3_ioctl
	gcc elgamal.c -o elgamal
clean:
	make -C	/lib/modules/$(shell uname -r)/build M=$(PWD) clean
	rm brpa3_ioctl
	rm elgamal
