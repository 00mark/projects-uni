#include <linux/init.h>
#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/miscdevice.h>
#include <linux/fs.h>
#include <linux/uaccess.h>
#include <linux/sched.h>
#include <linux/mutex.h>
#include <linux/slab.h>
#include <linux/errno.h>
#include "brpa3_970770_968803.h"
#include "mod_exp.h"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Mark Hiltenkamp");
MODULE_DESCRIPTION("In-kernel elgamal encryption and decryption");

struct content{
    wait_queue_head_t read_queue;
    struct mutex lock;
    unsigned long data; 
};

struct cyc_group{
    unsigned short order;
    unsigned short generator;
};

struct priv_key{
    unsigned short secret;
    unsigned short openkey;
};

struct op_key{
    unsigned short secret_sender;
    unsigned short openkey_sender;
};

static unsigned long dec_ciphertext;
static short data_available = 0;

static struct cyc_group cyclic_g = {
    .order = 59,
    .generator = 2
};

static struct priv_key private_k = {
    .secret = 5,
    .openkey = 32
};

static struct op_key open_k = {
    .secret_sender = 4,
    .openkey_sender = 16
};

// create a new content structure
static struct content *content_create(unsigned long size){
    struct content *con;

    con = kzalloc(sizeof(*con), GFP_KERNEL);
    init_waitqueue_head(&con->read_queue);
    mutex_init(&con->lock);
    return con;
}

// create a new content structure and set file->private_data
static int enc_open(struct inode *inode, struct file *file){
    struct content *con;
    int err;

    err = 0;
    con = content_create(1);
    if(unlikely(!con)){
		err = -ENOMEM;
		return err;
	}
    file->private_data = con;
    return err;
}

// decrypt c
static unsigned long decrypt(unsigned long c){	
    return mod_exp(mod_exp(open_k.openkey_sender, private_k.secret *
                (cyclic_g.order - 2), cyclic_g.order) * c, 1, cyclic_g.order);
}

// print the decrypted value
static ssize_t enc_read(struct file *file, char __user * out,
                                                    size_t size, loff_t * off){
    struct content *con = file->private_data;
    ssize_t result;
    char tmp_buff[get_num_chars(dec_ciphertext) + 1];

    if(mutex_lock_interruptible(&con->lock)){
		result = -ERESTARTSYS;
		goto out;
    }
    while(!data_available){
        mutex_unlock(&con->lock);
        if(file->f_flags & O_NONBLOCK){
			result = -EAGAIN;
			goto out;
		}
		if(wait_event_interruptible(con->read_queue, data_available)){
			result = -ERESTARTSYS;
			goto out;
		}
		if(mutex_lock_interruptible(&con->lock)){
			result = -ERESTARTSYS;
			goto out;
        }
    }
    set_chars(tmp_buff, dec_ciphertext, get_num_chars(dec_ciphertext));
    tmp_buff[get_num_chars(dec_ciphertext)] = '\0';
    if(copy_to_user(out, tmp_buff, get_num_chars(dec_ciphertext) + 1)){
		result = -EFAULT;
		goto out_unlock;
    }
    result = get_num_chars(dec_ciphertext) + 1;
    
out_unlock:
    mutex_unlock(&con->lock);
out:
    return result;
}

// get the encrypted value and decrypt 
static ssize_t enc_write(struct file *file, const char __user * in,
                                                    size_t size, loff_t * off){
    struct content *con = file->private_data;
	ssize_t result;
	char tmp_buff[size];

	if(mutex_lock_interruptible(&con->lock)){
		result = -ERESTARTSYS;
		goto out;
	}
	if(copy_from_user(tmp_buff, in, size)){
		result = -EFAULT;
		goto out_unlock;
	}
    con->data = decrypt(get_content_data(tmp_buff, size));
    dec_ciphertext = con->data;
    data_available = 1;
	wake_up_interruptible(&con->read_queue);
	result = size;
out_unlock:
	mutex_unlock(&con->lock);
out:
    return result;
}

// free the used content
static int enc_close(struct inode *inode, struct file *file){
    struct content *con;

    con = file->private_data;
    kfree(con);
    return 0;
}

// handle io operations
static long enc_ioctl(struct file *f, unsigned int cmd, unsigned long arg){
    switch(cmd){
        unsigned short secr, opkey, opkey_s;
        case BRPA3_SET_SECRET:
            if(copy_from_user(&secr, (unsigned short *) arg, sizeof(short)))
                return -EACCES;
            if(secr == 0 || secr >= cyclic_g.order)
                return -EINVAL;
            private_k.secret = secr;
            private_k.openkey = mod_exp(cyclic_g.generator, secr, 
                    cyclic_g.order);
            break;
        case BRPA3_SET_OPENKEY:
            if(copy_from_user(&opkey_s, (unsigned short *) arg, sizeof(short)))
                return -EACCES;
            open_k.openkey_sender = opkey_s;
            break;
        case BRPA3_GET_OPENKEY:
            opkey = private_k.openkey;
            if(copy_to_user((unsigned short *) arg, &opkey, sizeof(short)))
                return -EACCES;
            break;
        default:
            return -EINVAL;
    }
    return 0;
}

static struct file_operations enc_fops = {
    .llseek = noop_llseek,
    .owner = THIS_MODULE,
    .open = enc_open,
    .read = enc_read,
    .release = enc_close,
    .unlocked_ioctl = enc_ioctl,
    .write = enc_write
};

static struct miscdevice enc_misc_device = {
    .minor = MISC_DYNAMIC_MINOR,
    .name = "brpa3_970770_968803",
    .fops = &enc_fops
};

static int __init enc_init(void){
    misc_register(&enc_misc_device);
    printk(KERN_INFO "elgamal-encyption device has been registered");
    return 0;
}

static void __exit enc_exit(void){
    misc_deregister(&enc_misc_device);
    printk(KERN_INFO "elgamal-encyption device has been removed");
}

module_init(enc_init);
module_exit(enc_exit);
