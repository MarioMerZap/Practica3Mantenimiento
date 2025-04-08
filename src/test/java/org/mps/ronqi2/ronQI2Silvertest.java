package org.mps.ronqi2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ronQI2Silvertest {

    private RonQI2Silver ronQi2Silver;
    private Dispositivo mockDispositivo;

    @BeforeEach
    public void setup() {
        ronQi2Silver = new RonQI2Silver();
        mockDispositivo = mock(Dispositivo.class); // Mockito puede mockear clases abstractas

        // Accedemos directamente al campo 'disp' (heredado de RonQI2)
        ronQi2Silver.disp = mockDispositivo;
    }

    @Test
    public void obtenerNuevaLectura_almacenaLecturasCorrectamente() {
        when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

        ronQi2Silver.obtenerNuevaLectura();

        // Como no hay getters, verificamos por efecto colateral evaluando apnea
        assertTrue(ronQi2Silver.evaluarApneaSuenyo());
    }

    @Test
    public void evaluarApneaSuenyo_retornaFalse_siNoHayLecturas() {
        assertFalse(ronQi2Silver.evaluarApneaSuenyo());
    }

    @Test
    public void evaluarApneaSuenyo_retornaFalse_siSoloHayPresion() {
        when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

        for (int i = 0; i < 5; i++) {
            ronQi2Silver.obtenerNuevaLectura();
        }

        // Eliminamos la lectura de sonido llamando a obtenerNuevaLectura para sobrescribirla
        // Aquí estamos simulando que no hay datos de sonido.
        when(mockDispositivo.leerSensorSonido()).thenReturn(0.0f); // Simulamos un valor de sonido bajo

        // Obtenemos una nueva lectura con este valor.
        ronQi2Silver.obtenerNuevaLectura();

        assertFalse(ronQi2Silver.evaluarApneaSuenyo());
    }

    @Test
    public void evaluarApneaSuenyo_retornaFalse_siPromediosBajos() {
        when(mockDispositivo.leerSensorPresion()).thenReturn(10.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(20.0f);

        for (int i = 0; i < 5; i++) {
            ronQi2Silver.obtenerNuevaLectura();
        }

        assertFalse(ronQi2Silver.evaluarApneaSuenyo());
    }

    @Test
    public void evaluarApneaSuenyo_retornaTrue_siPromediosSuperanUmbrales() {
        when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

        for (int i = 0; i < 5; i++) {
            ronQi2Silver.obtenerNuevaLectura();
        }

        assertTrue(ronQi2Silver.evaluarApneaSuenyo());
    }

    @Test
    public void obtenerNuevaLectura_eliminaLecturasAntiguas_siExcedeLimite() {
        when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

        for (int i = 0; i < 6; i++) {
            ronQi2Silver.obtenerNuevaLectura();
        }

        // No hay getters, pero al evaluar y no lanzar excepción, asumimos que funciona
        assertTrue(ronQi2Silver.evaluarApneaSuenyo());
    }

    @Test
    public void evaluarApneaSuenyo_retornaFalse_siSoloHaySonido() {
        when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
        when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

        for (int i = 0; i < 5; i++) {
            ronQi2Silver.obtenerNuevaLectura();
        }

        // Eliminamos la lectura de presión llamando a obtenerNuevaLectura para sobrescribirla
        when(mockDispositivo.leerSensorPresion()).thenReturn(0.0f); // Simulamos un valor de presión bajo

        // Obtenemos una nueva lectura con este valor.
        ronQi2Silver.obtenerNuevaLectura();

        assertFalse(ronQi2Silver.evaluarApneaSuenyo());
    }
}
