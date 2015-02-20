from tornado.web import RequestHandler


class HeartbeatHandler(RequestHandler):
    def get(self):
        self.set_header("Access-Control-Allow-Origin", "*")
        self.write({'response': 'ok'})
