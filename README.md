MEMORY GAME

-----------FUNCIONAMENT DEL JOC----------------------

Aquest és un joc per a posar a prova la teva memòria.

A la pantalla hi apareixerà cada pocs segons una imatge.

Hauràs de recordar quina imatge has vist i seleccionar la mateixa de les opcions que apareixeran a la part baixa de la pantalla.

Cada encert sumarà 10 punts, cada error restarà 5 punts.

Cada cop que la puntuació augmenti de 50 en 50, la dificultat augmentarà.

-----------FUNCIONALITATS----------------------------

Aquest joc fa ús d'una base de dades SQLite, que introdueix les dades de la data de quan has jugat, la puntuació i el temps que ha trigat la partida, 

tot això son components que té el joc. 

Mentre jugues al joc, internament una vegada té les dades que ha de menester, les va inserint a la base de dades, per després enviar aquestes dades a

la pantalla principal i que es visualitzin al contenidor que tenim. L'altre component que fa servir aquest joc és un backend, és a dir un servidor de 

Python, el qual emmagatzema un JSON amb un sistema de pistes, el qual serveix per a quan t'equivoques, dir-li a l'usuari quin era la imatge correcta.

Una cosa que no hem posat, que era important és una vegada que emmagatzema el dia que has jugat, hauries de poder accedir a l'app de Calendari, dins 

de l'app i que et sortissin ombrejats els dies que has jugat al joc, per poder tenir comptabilitzats els dies.

-----------AUTORS-----------------------------------

Raul Gil Garcia

Eva Munar Valcaneras
