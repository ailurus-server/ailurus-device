from tornado.ioloop import PeriodicCallback

from .ping import SendPing
from ..settings import on_ailurus

if on_ailurus:
    SEND_PING_PERIOD = 60 * 1000  # ping minutely
else:
    SEND_PING_PERIOD = 2 * 1000  # ping every two seconds for debugging


def InstallTasks():
    PeriodicCallback(SendPing, SEND_PING_PERIOD).start()

# Warmup tasks (call on boot)
def Warmup():
    SendPing()
