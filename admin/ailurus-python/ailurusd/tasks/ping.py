import socket
import logging

import netifaces
from tornado.httpclient import AsyncHTTPClient
from tornado.escape import json_encode, json_decode

from ..settings import AILURUS_API_URI


logger = logging.getLogger('SendPing')


def SendPing():
    http_client = AsyncHTTPClient()
    post_data = {'int_ip': _GetInternalIp()}
    http_client.fetch(AILURUS_API_URI + '/ping',
                      _HandleResponse,
                      method='POST',
                      body=json_encode(post_data))


def _GetInternalIp():
    interfaces = netifaces.interfaces()
    if 'en0' in interfaces:
        return netifaces.ifaddresses('en0')[netifaces.AF_INET][0]['addr']
    elif 'eth0' in interfaces:
        return netifaces.ifaddresses('eth0')[netifaces.AF_INET][0]['addr']
    logger.warn('Cannot determine interface')
    return None


def _HandleResponse(response):
    if response.body:
        json_body = json_decode(response.body)
        if json_body.get('response') == 'ok':
            logger.info("Ping success")
        else:
            logger.error("Ping failed")
    else:
        logger.error("Ping failed")
