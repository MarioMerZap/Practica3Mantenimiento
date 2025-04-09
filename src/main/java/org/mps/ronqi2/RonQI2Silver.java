package org.mps.ronqi2;

import java.util.ArrayList;
import java.util.List;

public class RonQI2Silver extends RonQI2 {

    private int numLecturas;
    private List<Float> lecturasP;
    private List<Float> lecturasS;
    private float thresholdP;
    private float thresholdS;

    public RonQI2Silver() {
        lecturasP = new ArrayList<Float>();
        lecturasS = new ArrayList<Float>();
        thresholdP = 20.0f;
        thresholdS = 30.0f;
        numLecturas = 5;
    }

    /* 
     * Obtiene las lecturas de presion y sonido del dispositivo y las almacena en sus respectivos
     * contenedores.
     */
    public void obtenerNuevaLectura() {
        lecturasP.add(disp.leerSensorPresion());
        if (lecturasP.size() > numLecturas) {
            lecturasP.remove(0);
        }

        lecturasS.add(disp.leerSensorSonido()); // CORREGIDO: antes leía presión otra vez
        if (lecturasS.size() > numLecturas) {
            lecturasS.remove(0);
        }
    }

    /* 
     * Evalua la apnea del sueño. 
     * - Devuelve true si el promedio de las lecturas de presión y sonido es mayor a los límites 
     *   establecidos
     * - False en otro caso
     */
    @Override
    public boolean evaluarApneaSuenyo() {
    if (lecturasP.isEmpty() || lecturasS.isEmpty()) {
            return false; // protección si no hay lecturas
    }
    
        double avgP = lecturasP.stream()
            .mapToDouble(d -> d)
            .average()
            .orElse(0.0);
        double avgS = lecturasS.stream()
            .mapToDouble(d -> d)
            .average()
            .orElse(0.0);
    
        // CORREGIDO: la apnea ocurre cuando los promedios son mayores que los umbrales
        return avgP >= thresholdP && avgS >= thresholdS;
    }
    
}
