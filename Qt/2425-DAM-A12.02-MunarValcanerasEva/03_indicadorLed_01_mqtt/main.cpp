#include "indicadorled.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    IndicadorLed w;
    w.show();
    return a.exec();
}
