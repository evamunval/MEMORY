#include "indicadorled.h"
#include "ui_indicadorled.h"

#include <QPainter>
#include <QPixmap>
#include <QJsonDocument>

IndicadorLed::IndicadorLed(QWidget *parent)
    : QWidget(parent)
    , ui(new Ui::IndicadorLed)
{
    ui->setupUi(this);

    //CALLS
    vSetLabelColorGreen(ui->etGreen,QColor(Qt::darkGreen));
    vSetLabelColorYellow(ui->etYellow, QColor(Qt::darkYellow));
    vSetLabelColorRed(ui->etRed,QColor(Qt::darkRed));
    vSetLabelColorWhite(ui->etWhite, QColor(Qt::darkGray));

    //TIMER
    bTemporitzadorActiu = false;
    timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()), this, SLOT(vRespostaTemporitzador()));
    timer->start(250);

    //JSON TEXT
    qjoJson = new QJsonObject;

    //Init config LEDS
    QJsonObject qjoL;
    qjoL["ledG"] = 0;
    qjoL["ledY"] = 0;
    qjoL["ledR"] = 0;
    qjoL["ledW"] = false;
    *qjoJson = qjoL;

    // Update the UI with the init JSON
    QString strFromArray = QJsonDocument(*qjoJson).toJson(QJsonDocument::Compact).toStdString().c_str();
    ui->lbJSON->setText(strFromArray);
}

IndicadorLed::~IndicadorLed()
{
    delete ui;
}

void IndicadorLed::vRespostaTemporitzador(){
    static int n = 0;

    qDebug() << QString(tr("%1").arg(++n));
    if(bTemporitzadorActiu){
        vSetLabelColorGreen(ui->etGreen,QColor((n%2)?Qt::green:Qt::darkGreen));
    }

    //vSetLabelColorRed(ui->etRed,QColor((n%2)?Qt::red:Qt::darkRed));
    //vSetLabelColorYellow(ui->etYellow,QColor((n%2)?Qt::yellow:Qt::darkYellow));
    //vSetLabelColorWhite(ui->etWhite,QColor((n%2)?Qt::white:Qt::darkGray));
}

void IndicadorLed::updateLedState(QString led, bool state) {
    //Update the value in the JSON
    if(led == "ledW"){
       (*qjoJson)[led] = state ? true : false;
    }else{
    (*qjoJson)[led] = state ? 1 : 0;
    }

    // Convert to JSON & update the UI
    QString strFromArray = QJsonDocument(*qjoJson).toJson(QJsonDocument::Compact).toStdString().c_str();
    ui->lbJSON->setText(strFromArray);
    qDebug() << strFromArray;

    // Call the function to update the colour in the Ui
    if (led == "ledG") {
        vSetLabelColorGreen(ui->etGreen, state ? Qt::green : Qt::darkGreen);
    }else if(led == "ledY"){
        vSetLabelColorYellow(ui->etYellow,QColor((ui->cbYellow->isChecked())?Qt::yellow:Qt::darkYellow));
    }else if(led == "ledR"){
        vSetLabelColorRed(ui->etRed,QColor((ui->cbRed->isChecked())?Qt::red:Qt::darkRed));
    }else if(led == "ledW"){
        vSetLabelColorWhite(ui->etWhite,QColor((ui->cbWhite->isChecked())?Qt::white:Qt::darkGray));
    }
}

//GREEN
void IndicadorLed::vSetLabelColorGreen(QLabel* et, QColor col){
    QPixmap *pix = new QPixmap(22,22);
    QPainter *paint = new QPainter(pix);
    paint->setBrush(Qt::darkGreen);
    paint->drawRect(0, 0, 21, 21);
    paint->setBrush(col);
    paint->setPen(Qt::white);
    paint->drawEllipse(5, 5, 10, 10);
    et->setPixmap(*pix);
}

void IndicadorLed::on_cbGreen_clicked(bool checked)
{
    if(checked){
        qDebug() << "Seleccionat";
    }else{
        qDebug() << "No seleccionat";
    }
}

void IndicadorLed::on_cbGreen_clicked()
{
    bTemporitzadorActiu = ui->cbGreen->isChecked();
    //vSetLabelColorGreen(ui->etGreen,QColor((ui->cbGreen->isChecked())?Qt::green:Qt::darkGreen));
    //bool checked = ui->cbGreen->isChecked();
    updateLedState("ledG",ui->cbGreen->isChecked());
}

//YELLOW
void IndicadorLed::vSetLabelColorYellow(QLabel* et, QColor col){
    QPixmap *pix = new QPixmap(22,22);
    QPainter *paint = new QPainter(pix);
    paint->setBrush(Qt::darkYellow);
    paint->drawRect(0, 0, 21, 21);
    paint->setBrush(col);
    paint->setPen(Qt::white);
    paint->drawEllipse(5, 5, 10, 10);
    et->setPixmap(*pix);
}

void IndicadorLed::on_cbYellow_clicked(bool checked)
{
    if(checked){
        qDebug() << "Seleccionat";
    }else{
        qDebug() << "No seleccionat";
    }
}

void IndicadorLed::on_cbYellow_clicked()
{
    //vSetLabelColorYellow(ui->etYellow,QColor((ui->cbYellow->isChecked())?Qt::yellow:Qt::darkYellow));
    updateLedState("ledY",ui->cbYellow->isChecked());

}

//RED
void IndicadorLed::vSetLabelColorRed(QLabel* et, QColor col){
    QPixmap *pix = new QPixmap(22,22);
    QPainter *paint = new QPainter(pix);
    paint->setBrush(Qt::darkRed);
    paint->drawRect(0, 0, 21, 21);
    paint->setBrush(col);
    paint->setPen(Qt::white);
    paint->drawEllipse(5, 5, 10, 10);
    et->setPixmap(*pix);
}

void IndicadorLed::on_cbRed_clicked(bool checked)
{
    if(checked){
        qDebug() << "Seleccionat";
    }else{
        qDebug() << "No seleccionat";
    }
}

void IndicadorLed::on_cbRed_clicked()
{
    //vSetLabelColorRed(ui->etRed,QColor((ui->cbRed->isChecked())?Qt::red:Qt::darkRed));
    updateLedState("ledR",ui->cbRed->isChecked());
}

//WHITE
void IndicadorLed::vSetLabelColorWhite(QLabel* et, QColor col){
    QPixmap *pix = new QPixmap(22,22);
    QPainter *paint = new QPainter(pix);
    paint->setBrush(Qt::darkGray);
    paint->drawRect(0, 0, 21, 21);
    paint->setBrush(col);
    paint->setPen(Qt::white);
    paint->drawEllipse(5, 5, 10, 10);
    et->setPixmap(*pix);
}

void IndicadorLed::on_cbWhite_clicked(bool checked)
{
    if(checked){
        qDebug() << "Seleccionat";
    }else{
        qDebug() << "No seleccionat";
    }
}

void IndicadorLed::on_cbWhite_clicked()
{
    //vSetLabelColorWhite(ui->etWhite,QColor((ui->cbWhite->isChecked())?Qt::white:Qt::darkGray));
    updateLedState("ledW",ui->cbWhite->isChecked());
}


