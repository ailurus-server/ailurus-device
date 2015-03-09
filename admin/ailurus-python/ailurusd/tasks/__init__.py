from tornado.ioloop import PeriodicCallback

from .ping import SendPing
from ..settings import on_ailurus

if on_ailurus:
    SEND_PING_PERIOD = 10 * 1000  # ping every 10 seconds
else:
    SEND_PING_PERIOD = 2 * 1000  # ping every two seconds for debugging


def InstallTasks():
    PeriodicCallback(SendPing, SEND_PING_PERIOD).start()

# Warmup tasks (call on boot)
def Warmup():
    SendPing()
