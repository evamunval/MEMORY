#ifndef INDICADORLED_H
#define INDICADORLED_H

#include <QLabel>
#include <QWidget>
#include <QTimer>
#include <QJsonObject>

QT_BEGIN_NAMESPACE
namespace Ui {
class IndicadorLed;
}
QT_END_NAMESPACE

class IndicadorLed : public QWidget
{
    Q_OBJECT

public:
    IndicadorLed(QWidget *parent = nullptr);
    ~IndicadorLed();

protected:
    void vSetLabelColorGreen(QLabel*, QColor);
    void vSetLabelColorYellow(QLabel*, QColor);
    void vSetLabelColorRed(QLabel*, QColor);
    void vSetLabelColorWhite(QLabel*, QColor);
    void vSetJson();

private slots:
    void vRespostaTemporitzador();

    void on_cbGreen_clicked(bool checked);
    void on_cbGreen_clicked();

    void on_cbYellow_clicked(bool checked);
    void on_cbYellow_clicked();

    void on_cbRed_clicked(bool checked);
    void on_cbRed_clicked();

    void on_cbWhite_clicked(bool checked);
    void on_cbWhite_clicked();

    void updateLedState(QString led, bool state);

private:
    Ui::IndicadorLed *ui;
    QTimer *timer;
    bool bTemporitzadorActiu;
    QJsonObject *qjoJson;

signals:
    void vTrametJsonLeds(QString);
};
#endif // INDICADORLED_H
