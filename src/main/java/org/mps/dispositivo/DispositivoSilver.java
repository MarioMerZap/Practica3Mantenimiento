package org.mps.dispositivo;
public class DispositivoSilver extends Dispositivo{
    
    public DispositivoSilver() {
        configuracionPresion = "configuracionPresionSilver";
        configuracionSonido = "configuracionSonidoSilver";
    }

    @Override
    public boolean conectarSensorPresion() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean conectarSensorSonido() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean configurarSensorPresion() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean configurarSensorSonido() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Float leerSensorPresion() {
        // TODO Auto-generated method stub
        return 0.0f;
    }

    @Override
    public Float leerSensorSonido() {
        // TODO Auto-generated method stub
        return 0.0f;
    }

    @Override
    public boolean estaConectado() {
        // TODO Auto-generated method stub
        return false;
    }

    
}
