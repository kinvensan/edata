# -*- coding: utf-8 -*-

import nsq
import time

def handler(message):
    time.sleep(10)
    print message
    return True

r = nsq.Reader(message_handler=handler,
        lookupd_http_addresses=['http://127.0.0.1:4161'],
        topic='test', channel='asdfxx', lookupd_poll_interval=15)
nsq.run()