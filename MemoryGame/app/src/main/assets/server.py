from http.server import BaseHTTPRequestHandler, HTTPServer
from urllib.parse import parse_qs
import json
import sys
import time
from random import randint

class ClasseGestora(BaseHTTPRequestHandler):

    def _set_headers(self):
        self.send_response(200)
        self.send_header('Content-type', 'application/json')
        self.end_headers()

    def extreu_param_get(self, queryData, key, default=""):
        return queryData.get(key, [default])[0]

    def do_GET(self):
        self._set_headers()
        path, _, query_string = self.path.partition('?')
        query = parse_qs(query_string)
        num_imatge = int(self.extreu_param_get(query, "num_imatge", "1"))


        clues = [
            {'id' : 1,'text':"La imagen correcta era un amiguito de color rosa"},
            {'id' : 2,'text':"La imagen correcta era un iconico mapache naranja"},
            {'id' : 3,'text':"La imagen correcta era alguien que engulle fantasmas"},
            {'id' : 4,'text':"La imagen correcta era un icono parecido a un paraguas"},
            {'id' : 5,'text':"La imagen correcta era un simio simpatico"},
            {'id' : 6,'text':"La imagen correcta era un pixel art con barba"},
            {'id' : 7,'text':"La imagen correcta era un amigable corderito, que tiene una secta"},
            {'id' : 8,'text':"La imagen correcta era tu mejor amigo en Dark Souls"},
            {'id' : 9,'text':"La imagen correcta era un pequeño espadachin"},
            {'id' : 10,'text':"La imagen correcta era un ser amargado de la vida"}
        ]
        pista = next((clue for clue in clues if clue['id'] == num_imatge), None)
        response = {'pista': pista['text'] if pista else 'Pista no encontrada'}
        clues = json.dumps(response)
        self.wfile.write(clues.encode('utf-8'))

def run(server_class=HTTPServer, handler_class=ClasseGestora, port=8001):
    server_address = ('', port)
    httpd = server_class(server_address, handler_class)
    print('iniciant httpd...')
    httpd.serve_forever()

if __name__ == "__main__":
    from sys import argv
    if len(argv) == 2:
        run(port=int(argv[1]))
    else:
        run()
