import os
import logging

from tornado.ioloop import IOLoop, PeriodicCallback
from tornado.options import parse_command_line, define, options
from tornado.web import Application, url
from tornado.httpserver import HTTPServer

from .handlers import HeartbeatHandler
from .tasks import InstallTasks, Warmup


define("port", default=18570, type=int, help="server port")
define("debug", default=False, type=bool, help="enable debugging")


def CreateApplication(debug=False):
    # Common handlers and settings
    application = Application(
        handlers=[
            url(r'/_heartbeat', HeartbeatHandler)
        ],
        debug=debug
    )
    return application


def Main():
    parse_command_line()
    logging.info("Serving on port " + str(options.port))

    app = CreateApplication(options.debug)
    server = HTTPServer(app, xheaders=True)
    server.bind(options.port, '127.0.0.1')
    server.start()

    InstallTasks()
    Warmup()

    IOLoop.current().start()

if __name__ == "__main__":
    # Execute by running: python -m ailurusd.main
    Main()
