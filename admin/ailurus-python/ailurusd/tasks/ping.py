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
    family = netifaces.AF_INET
    gateway_ip, interface_name = netifaces.gateways()['default'][family]

    if interface_name in netifaces.interfaces(): # this should always be true
        return netifaces.ifaddresses(interface_name)[family][0]['addr']

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
